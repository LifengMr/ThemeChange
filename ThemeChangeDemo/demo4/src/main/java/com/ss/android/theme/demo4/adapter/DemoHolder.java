package com.ss.android.theme.demo4.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.ss.android.theme.demo4.R;
import com.ss.android.theme.loader.download.DownLoadListener;
import com.ss.android.theme.loader.download.DownLoadManager;
import com.ss.android.theme.loader.entity.ApkEntity;
import com.ss.android.theme.loader.load.ThemeLoader;

/**
 * Created by chenlifeng on 16/10/28.
 */
public class DemoHolder implements View.OnClickListener, DownLoadListener {

    private View mRootView;
    private Button mButton;

    private Context mContext;
    private ApkEntity mTheme;

    public DemoHolder(Context context, View view) {
        mContext = context;
        mRootView = view;

        initViews();
    }

    private void initViews() {
        mButton = (Button) mRootView.findViewById(R.id.btn);
    }

    public void bind(ApkEntity entity) {
        if (entity == null) {
            return;
        }
        mTheme = entity;
        mButton.setText(entity.title);
        mButton.setOnClickListener(this);

        DownLoadManager.ins().addTaskListener(entity.packageName, this);
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
                    DownLoadManager.ins().addTaskListener(mTheme.packageName, this);
                }
            }
        }
    }

    @Override
    public void onStart(String taskID) {
        mButton.setText(mContext.getString(R.string.download_start));
    }

    @Override
    public void onProgress(ApkEntity downLoadInfo) {
        float percent = 1.0f * downLoadInfo.downloadSize / downLoadInfo.fileSize;
        String text = mContext.getString(R.string.download_ing) + "(" + (int)(percent * 100) + "%)";
//        mButton.setText(text);
    }

    @Override
    public void onStop(String taskID) {
        mButton.setText(mContext.getString(R.string.download_stop));
    }

    @Override
    public void onError(ApkEntity downLoadInfo) {
        mButton.setText(mContext.getString(R.string.download_error));
    }

    @Override
    public void onSuccess(ApkEntity downLoadInfo) {
        if (mTheme != null) {
            mButton.setText(mTheme.title);
        }
    }
}
