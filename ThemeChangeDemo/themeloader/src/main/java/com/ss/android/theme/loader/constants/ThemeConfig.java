package com.ss.android.theme.loader.constants;

import android.content.Context;
import android.content.SharedPreferences;

import com.ss.android.theme.loader.compat.SharedPrefsEditorCompat;

/**
 * Created by chenlifeng on 16/10/13.
 */
public abstract class ThemeConfig {
    private static final String SP_THEME = "sp_theme";
    private static final String KEY_THEME_ID = "theme_id";

    private Context mContext;
    private int mThemeId;

    public void init(Context context) {
        mContext = context.getApplicationContext();

        SharedPreferences sp = mContext.getSharedPreferences(SP_THEME, Context.MODE_PRIVATE);
        mThemeId = sp.getInt(SP_THEME, 0);
    }

    public int getThemeId() {
        return mThemeId;
    }

    public void setThemeId(int themeId) {
        mThemeId = themeId;

        SharedPreferences sp = mContext.getSharedPreferences(SP_THEME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY_THEME_ID, mThemeId);
        SharedPrefsEditorCompat.apply(editor);
    }

    public abstract String getThemesUrl();
}
