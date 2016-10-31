package com.ss.android.theme.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ss.android.theme.R;

public class MainActivity extends AppCompatActivity implements ThemeManager.ThemeChangedListener, View.OnClickListener {

    private View mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.ins().apply(this);
        setContentView(R.layout.activity_main);

        initData();

        initViews();
    }

    private void initData() {
        ThemeManager.ins().addThemeChangedListener(this);
    }

    private void initViews() {
        mBtn = findViewById(R.id.main_btn);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == mBtn.getId()) {
            ThemeManager.ins().changeTheme(this);
        }
    }

    @Override
    public void onThemeChanged() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeManager.ins().removeThemeChangedListener(this);
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(0, 0);
    }
}
