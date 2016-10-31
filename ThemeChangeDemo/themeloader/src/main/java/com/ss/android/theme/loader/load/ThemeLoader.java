package com.ss.android.theme.loader.load;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.ss.android.theme.loader.compat.AssetManagerCompat;
import com.ss.android.theme.loader.compat.ThemeLayoutInflaterCompat;
import com.ss.android.theme.loader.constants.ThemeConfig;
import com.ss.android.theme.loader.download.DownLoadManager;
import com.ss.android.theme.loader.entity.ApkEntity;
import com.ss.android.theme.loader.helper.AppHelper;
import com.ss.android.theme.loader.helper.FileHelper;
import com.ss.android.theme.loader.helper.JLog;
import com.ss.android.theme.loader.helper.JavaHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenlifeng on 16/10/13.
 */
public class ThemeLoader {

    public static final String TAG = ThemeLoader.class.getName();
    private static ThemeLoader sLoader;
    private ThemeConfig mThemeConfig;
    private OnThemeLoadListener mOnThemeLoadListener;
    private Resources mThemeResources;
    private Context mContext;
    private String mThemePackageName;
    private List<OnThemeChangedListener> mOnThemeChangedListeners = new ArrayList<>();
    private List<ApkEntity> mThemes = new ArrayList<>();

    private ThemeLoader(){
    }

    public static ThemeLoader inst() {
        if (sLoader == null) {
            synchronized (ThemeLoader.class) {
                if (sLoader == null) {
                    sLoader = new ThemeLoader();
                }
            }
        }
        return sLoader;
    }

    public void init(Context context, ThemeConfig config) {
        if (!AppHelper.isMainProcess(context)) {
            return;
        }
        if (config == null) {
            throw new IllegalArgumentException("config can not be null!");
        }
        mThemeConfig = config;
        mContext = context.getApplicationContext();
        mThemeConfig.init(context);
        DownLoadManager.ins().init(context);
    }

    public boolean isThemeValid(ApkEntity entity) {
        if (entity == null) {
            return false;
        }

        //TODO 添加版本和签名验证

        String themePath = FileHelper.getThemePath(mContext, entity.packageName, entity.name);
        return FileHelper.isFileExists(themePath);
    }

    public boolean isThemeApplied() {
        return !TextUtils.isEmpty(mThemePackageName);
    }

    public void downloadTheme(ApkEntity entity) {
        if (entity != null) {
            JLog.i("clf", "downloadTheme 1");
            DownLoadManager.ins().addTask(mContext, entity);
        }
    }

    public void resetTheme() {
        mThemePackageName = null;
        mThemeResources = mContext.getResources();
        for (final OnThemeChangedListener listener : mOnThemeChangedListeners) {
            if (listener != null) {
                listener.onThemeChanged();
            }
        }
    }

    public void loadTheme(final Context context, final ApkEntity entity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String themePath = FileHelper.getThemePath(context, entity.packageName, entity.name);
                boolean success = true;
                if (!FileHelper.isFileExists(themePath)) {
                    success = FileHelper.copyFile(entity.filePath, themePath);
                }

                if (!success) {
                    JLog.i(TAG, "loadTheme copy failed");
                    if (mOnThemeLoadListener != null) {
                        mOnThemeLoadListener.onFailed();
                    }
                    return;
                }

                mThemePackageName = entity.packageName;
                AssetManager assetManager = AssetManagerCompat.newInstance();
                int cookie = AssetManagerCompat.addAssetPath(assetManager, themePath);
                if (cookie == 0) {
                    if (mOnThemeLoadListener != null) {
                        mOnThemeLoadListener.onFailed();
                    }
                    return;
                }
                Resources resources = context.getResources();
                mThemeResources = new Resources(assetManager, resources.getDisplayMetrics(),
                        resources.getConfiguration());

                Handler handler = new Handler(context.getMainLooper());
                for (final OnThemeChangedListener listener : mOnThemeChangedListeners) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (listener != null) {
                                listener.onThemeChanged();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public ThemeLayoutFactory buildThemeFactory(Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();
        ThemeLayoutInflaterCompat.setFactorySet(inflater, false);
        ThemeLayoutFactory factory = new ThemeLayoutFactory();
        inflater.setFactory(factory);
        return factory;
    }

    public int getColor(int resId) {
        Resources hostResources = mContext.getResources();
        int hostColor = hostResources.getColor(resId);
        if (mThemeResources == null) {
            return hostColor;
        }

        String resName = hostResources.getResourceEntryName(resId);
        int themeResId = mThemeResources.getIdentifier(resName, "color", mThemePackageName);
        int themeColor = 0;
        try {
            themeColor = mThemeResources.getColor(themeResId);
        } catch (Exception e) {
            themeColor = hostColor;
        }
        return themeColor;
    }

    public Drawable getDrawable(int resId) {
        Resources hostResources = mContext.getResources();
        Drawable hostDrawable = hostResources.getDrawable(resId);
        if (mThemeResources == null) {
            return hostDrawable;
        }

        String resName = hostResources.getResourceEntryName(resId);
        int themeResId = mThemeResources.getIdentifier(resName, "drawable", mThemePackageName);
        Drawable themeDrawable = null;
        try {
            themeDrawable = mThemeResources.getDrawable(themeResId);
        } catch (Exception e) {
            JLog.i("clf", "getDrawable e=" + e.getMessage());
            themeDrawable = hostDrawable;
        }
        return themeDrawable;
    }

    public Drawable getMipmap(int resId) {
        Resources hostResources = mContext.getResources();
        Drawable hostDrawable = hostResources.getDrawable(resId);
        if (mThemeResources == null) {
            return hostDrawable;
        }

        String resName = hostResources.getResourceEntryName(resId);
        int themeResId = mThemeResources.getIdentifier(resName, "mipmap", mThemePackageName);
        Drawable themeDrawable = null;
        try {
            themeDrawable = mThemeResources.getDrawable(themeResId);
        } catch (Exception e) {
            JLog.i("clf", "getMipmap e=" + e.getMessage());
            themeDrawable = hostDrawable;
        }
        return themeDrawable;
    }

    public ColorStateList getColorStateList(int resId) {
        Resources hostResources = mContext.getResources();
        ColorStateList hostColorStateList = null;
        try {
            hostColorStateList = hostResources.getColorStateList(resId);
        } catch (Exception e) {
        }
        if (hostColorStateList == null) {
            hostColorStateList = new ColorStateList(new int[1][1],
                    new int[] {hostResources.getColor(resId)});
        }
        if (mThemeResources == null) {
            return hostColorStateList;
        }

        String resName = hostResources.getResourceEntryName(resId);
        int themeResId = mThemeResources.getIdentifier(resName, "color", mThemePackageName);
        if (themeResId == 0) {
            return hostColorStateList;
        }

        ColorStateList themeColorStateList = null;
        try {
            themeColorStateList = mThemeResources.getColorStateList(themeResId);
        } catch (Exception e) {
            themeColorStateList = hostColorStateList;
        }
        return themeColorStateList;
    }

    public void setOnThemeLoadListener(OnThemeLoadListener loadListener) {
        this.mOnThemeLoadListener = loadListener;
    }

    public void registerThemeChangedListener(OnThemeChangedListener listener) {
        if (!mOnThemeChangedListeners.contains(listener)){
            mOnThemeChangedListeners.add(listener);
        }
    }

    public void unRegisterThemeChangedListener(OnThemeChangedListener listener) {
        mOnThemeChangedListeners.remove(listener);
    }

    public String getThemesApi() {
        return mThemeConfig != null ? mThemeConfig.getThemesUrl() : null;
    }

    public void setThemes(List<ApkEntity> themes) {
        if (JavaHelper.isListEmpty(themes)) {
            return;
        }

        mThemes.clear();
        mThemes.addAll(themes);
    }

    public void addTheme(ApkEntity theme) {
        mThemes.add(theme);
    }

    public List<ApkEntity> getThemes() {
        return mThemes;
    }

    public int getCurThemeId() {
        return mThemeConfig.getThemeId();
    }

    public void setCurThemeId(int themeId) {
        mThemeConfig.setThemeId(themeId);
    }
}
