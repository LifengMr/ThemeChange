package com.ss.android.theme.activity;

import android.content.Context;
import android.content.Intent;

import com.ss.android.theme.R;

/**
 * Created by chenlifeng on 16/10/12.
 */
public class ThemeManager {

    private WeakContainer<ThemeChangedListener> mThemeChangedListeners = new WeakContainer<>();
    private static ThemeManager sThemeManager;
    private boolean mIsNight;

    private ThemeManager(){
    }

    public static ThemeManager ins() {
        if (sThemeManager == null) {
            synchronized (ThemeManager.class) {
                if (sThemeManager == null) {
                    sThemeManager = new ThemeManager();
                }
            }
        }
        return sThemeManager;
    }

    public void apply(Context context) {
        if (mIsNight) {
            context.setTheme(R.style.DayNightTheme_Night);
        } else{
            context.setTheme(R.style.DayNightTheme);
        }
    }

    public void addThemeChangedListener(ThemeChangedListener listener){
        mThemeChangedListeners.add(listener);
    }

    public void removeThemeChangedListener(ThemeChangedListener listener) {
        mThemeChangedListeners.remove(listener);
    }

    public void changeTheme(Context context) {
        mIsNight = !mIsNight;

        for (ThemeChangedListener listener : mThemeChangedListeners) {
            listener.onThemeChanged();
        }

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.setPackage(null);
        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public boolean isNight() {
        return mIsNight;
    }

    public interface ThemeChangedListener {
        void onThemeChanged();
    }
}
