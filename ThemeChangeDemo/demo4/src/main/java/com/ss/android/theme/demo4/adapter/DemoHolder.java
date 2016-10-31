package com.ss.android.theme.demo4.adapter;

import android.content.Context;
import android.view.View;

import com.ss.android.theme.demo4.R;
import com.ss.android.theme.demo4.widget.DownloadButton;
import com.ss.android.theme.loader.download.DownLoadManager;
import com.ss.android.theme.loader.entity.ApkEntity;
import com.ss.android.theme.loader.load.ThemeLoader;

/**
 * Created by chenlifeng on 16/10/28.
 */
public class DemoHolder implements View.OnClickListener {

    private View mRootView;
    private DownloadButton mButton;

    private Context mContext;
    private ApkEntity mTheme;

    public DemoHolder(Context context, View view) {
        mContext = context;
        mRootView = view;

        initViews();
    }

    private void initViews() {
        mButton = (DownloadButton) mRootView.findViewById(R.id.btn);
    }

    public void bind(ApkEntity entity) {
        if (entity == null) {
            return;
        }
        mTheme = entity;
        mButton.setText(entity.title);
        mButton.setDefaultText(entity.title);
        mButton.setOnClickListener(this);

        DownLoadManager.ins().addTaskListener(entity.packageName, mButton);
    }

    public void unBind() {
        if (mTheme != null) {
            DownLoadManager.ins().removeTaskListener(mTheme.packageName);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == mButton.getId()) {
            if (mTheme != null) {
                ThemeLoader themeLoader = ThemeLoader.inst();
                if (themeLoader.isThemeValid(mTheme)) {
                    if (themeLoader.isThemeApplied()) {
                        themeLoader.resetTheme();
                    } else {
                        themeLoader.loadTheme(mContext, mTheme);
                    }
                } else {
                    themeLoader.downloadTheme(mTheme);
                    DownLoadManager.ins().addTaskListener(mTheme.packageName, mButton);
                }
            }
        }
    }
}
