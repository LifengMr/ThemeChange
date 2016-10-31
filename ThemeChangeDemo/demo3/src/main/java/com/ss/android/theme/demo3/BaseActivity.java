package com.ss.android.theme.demo3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chenlifeng on 16/10/13.
 */
public class BaseActivity extends AppCompatActivity implements ThemeManger.ThemeChangedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ThemeManger.ins().addThemeChangedListener(this);
    }

    @Override
    public void onThemeChanged() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeManger.ins().removeThemeChangedListener(this);
    }
}
