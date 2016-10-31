package com.ss.android.theme.demo2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by chenlifeng on 16/10/12.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private View mBtn;
    private NightModeHelper mNightModeHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initViews();
    }

    private void initData() {
        mNightModeHelper = new NightModeHelper(this);
    }

    private void initViews() {
        mBtn = findViewById(R.id.main_btn);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == mBtn.getId()) {
            mNightModeHelper.toggle();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
