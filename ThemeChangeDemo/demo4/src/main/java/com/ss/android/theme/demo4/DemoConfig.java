package com.ss.android.theme.demo4;

import com.ss.android.theme.loader.constants.ThemeConfig;

/**
 * Created by chenlifeng on 16/10/28.
 */
public class DemoConfig extends ThemeConfig {

    public static final String TAG = "http";
    //	private static final String HOST = "http://apker.applinzi.com/";
    private static final String HOST = "http://10.2.212.248/";

    @Override
    public String getThemesUrl() {
        return HOST + "theme";
    }
}
