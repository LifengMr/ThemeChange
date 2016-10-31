package com.ss.android.theme.demo4;

import android.app.Application;

import com.ss.android.theme.loader.load.ThemeLoader;

/**
 * Created by chenlifeng on 16/10/13.
 */
public class ThemeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ThemeLoader.inst().init(this, new DemoConfig());
    }
}
