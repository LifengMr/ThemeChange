package com.ss.android.theme.loader.download;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ss.android.theme.loader.api.FlowCallback;
import com.ss.android.theme.loader.entity.ApkBean;
import com.ss.android.theme.loader.entity.ApkEntity;
import com.ss.android.theme.loader.helper.JLog;
import com.ss.android.theme.loader.helper.JavaHelper;
import com.ss.android.theme.loader.load.ThemeLoader;
import com.ss.android.theme.loader.volley.VolleyError;

import java.util.Map;

public class DownLoadService extends Service implements FlowCallback<ApkBean> {
    public static final String TAG = DownLoadService.class.getName();

    private Map<String, ApkEntity> mPluginsMap;

    @Override
    public void onCreate() {
        super.onCreate();
        DownLoadManager.ins().setDownLoadListener(mDownLoadListener);
        DownLoadManager.ins().restartTasks(this);
//        PluginDAO pluginDAO = new PluginDAO(this);

//        mPluginsMap = pluginDAO.getPluginsMap();
    }

    @Override
    public void onFlowSuccess(ApkBean data) {
        if (data == null || JavaHelper.isListEmpty(data.apks)) {
            return;
        }

        JLog.i("clf", "onFlowSuccess data.apks.size=" + data.apks.size());
        for (ApkEntity entity : data.apks) {
            JLog.i("clf", "onFlowSuccess entity.packageName=" + entity.packageName);
            DownLoadManager.ins().addTask(this, entity);
        }
    }

    @Override
    public void onFlowError(VolleyError error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DownLoadManager.ins().stopAllTask();
        DownLoadManager.ins().onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private DownLoadListener mDownLoadListener = new DownLoadListener() {
        @Override
        public void onStart(String taskID) {
            JLog.i(DownLoadManager.TAG, "onStart taskID=" + taskID);
        }

        @Override
        public void onProgress(ApkEntity downLoadInfo) {
            JLog.i(DownLoadManager.TAG, "onProgress getTaskID=" + downLoadInfo.taskID
                    + ",getDownloadSize=" + downLoadInfo.downloadSize
                    + ",getFilePath=" + downLoadInfo.filePath);
        }

        @Override
        public void onStop(String taskID) {
            JLog.i(DownLoadManager.TAG, "onStop taskID=" + taskID);
        }

        @Override
        public void onError(ApkEntity downLoadInfo) {
            JLog.i(DownLoadManager.TAG, "onError taskID=" + downLoadInfo.taskID);
        }

        @Override
        public void onSuccess(ApkEntity downLoadInfo) {
            JLog.i(DownLoadManager.TAG, "onSuccess taskID=" + downLoadInfo.taskID);
            if (downLoadInfo != null) {
                ThemeLoader.inst().loadTheme(DownLoadService.this, downLoadInfo);
            }
        }
    };
}
