package com.ss.android.theme.demo3;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chenlifeng on 16/10/13.
 */
public class ThemeManger {

    private static final String SP_THEME = "sp_theme";
    private static final String KEY_NIGHT_MODE = "night_mode";

    private WeakContainer<ThemeChangedListener> mThemeChangedListeners = new WeakContainer<>();
    private static ThemeManger sThemeManger;
    private boolean mIsNight;

    private ThemeManger(){
    }

    public static ThemeManger ins() {
        if (sThemeManger == null) {
            synchronized (ThemeManger.class) {
                if (sThemeManger == null) {
                    sThemeManger = new ThemeManger();
                }
            }
        }
        return sThemeManger;
    }

    public void init(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_THEME, Context.MODE_PRIVATE);
        mIsNight = sp.getBoolean(KEY_NIGHT_MODE, false);
    }

    public void addThemeChangedListener(ThemeChangedListener listener){
        mThemeChangedListeners.add(listener);
    }

    public void removeThemeChangedListener(ThemeChangedListener listener) {
        mThemeChangedListeners.remove(listener);
    }

    public void changeTheme(Context context) {
        mIsNight = !mIsNight;
        saveNightMode(context);

        for (ThemeChangedListener listener : mThemeChangedListeners) {
            listener.onThemeChanged();
        }
    }

    public boolean isNight() {
        return mIsNight;
    }

    private void saveNightMode(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_THEME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY_NIGHT_MODE, mIsNight);
        SharedPrefsEditorCompat.apply(editor);
    }

    public interface ThemeChangedListener {
        void onThemeChanged();
    }
}
