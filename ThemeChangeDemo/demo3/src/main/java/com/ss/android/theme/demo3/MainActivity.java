package com.ss.android.theme.demo3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtn;
    private View mLayout;
    private ImageView mImg;

    private ThemeManger mThemeManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initViews();
    }

    private void initData() {
        mThemeManger = ThemeManger.ins();
    }

    private void initViews() {
        mBtn = (Button) findViewById(R.id.main_btn);
        mLayout = findViewById(R.id.main_lt);
        mImg = (ImageView) findViewById(R.id.main_img);

        drawViews();

        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == mBtn.getId()) {
            mThemeManger.changeTheme(this);
        }
    }

    @Override
    public void onThemeChanged() {
        super.onThemeChanged();

        drawViews();
    }

    private void drawViews() {
        boolean isNight = ThemeManger.ins().isNight();
        mLayout.setBackgroundColor(ThemeRes.getColor(this, R.color.s0, isNight));
        mBtn.setTextColor(ThemeRes.getColor(this, R.color.s4, isNight));
        mBtn.setText(ThemeRes.getString(this, R.string.btn_text, isNight));
        mBtn.setBackground(ThemeRes.getDrawable(this, R.drawable.bg_btn, isNight));
        mImg.setImageResource(ThemeRes.getId(R.mipmap.image, isNight));
    }
}
