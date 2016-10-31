package com.ss.android.theme.demo3;

import android.app.Application;

/**
 * Created by chenlifeng on 16/10/13.
 */
public class ThemeAppliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ThemeManger.ins().init(this);
    }
}
