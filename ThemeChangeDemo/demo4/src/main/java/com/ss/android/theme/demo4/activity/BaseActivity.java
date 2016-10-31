package com.ss.android.theme.demo4.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ss.android.theme.loader.load.OnThemeChangedListener;
import com.ss.android.theme.loader.load.ThemeLayoutFactory;
import com.ss.android.theme.loader.load.ThemeLoader;

/**
 * Created by chenlifeng on 16/10/28.
 */
public class BaseActivity extends AppCompatActivity implements OnThemeChangedListener {

    private ThemeLayoutFactory mThemeLayoutFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mThemeLayoutFactory = ThemeLoader.inst().buildThemeFactory(this);
        super.onCreate(savedInstanceState);

        ThemeLoader.inst().registerThemeChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeLoader.inst().unRegisterThemeChangedListener(this);
        if (mThemeLayoutFactory != null) {
            mThemeLayoutFactory.destroy();
        }
    }

    @Override
    public void onThemeChanged() {
        if (mThemeLayoutFactory != null) {
            mThemeLayoutFactory.apply();
        }
    }
}
