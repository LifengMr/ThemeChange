package com.ss.android.theme.loader.helper;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by chenlifeng on 16/10/28.
 */
public class AppHelper {
    private static long sMainThreaId = -1;
    private static String sCurProcessName = null;

    public static boolean isMainThread(Context context) {
        if (sMainThreaId == -1) {
            sMainThreaId = getMainProcessRetId(context);
        }
        if (sMainThreaId == Thread.currentThread().getId()) {
            return true;
        }
        return false;
    }

    public static long getMainProcessRetId(Context context){
        boolean main = isMainProcess(context);
        if(main){
            return Thread.currentThread().getId();
        }
        return  0;
    }

    public static boolean isMainProcess(Context context) {
        String processName = getCurProcessName(context);
        if (processName != null && processName.contains(":")) {
            return false;
        }
        return (processName != null && processName.equals(context.getPackageName()));
    }

    public static String getCurProcessName(Context context) {
        String procName = sCurProcessName;
        if (!TextUtils.isEmpty(procName)) {
            return procName;
        }
        try{
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    sCurProcessName = appProcess.processName;
                    return sCurProcessName;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        sCurProcessName = getCurProcessNameFromProc();
        return sCurProcessName;
    }

    private static String getCurProcessNameFromProc() {
        BufferedReader cmdlineReader = null;
        try {
            cmdlineReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + android.os.Process.myPid()
                            + "/cmdline"), "iso-8859-1"));
            int c;
            StringBuilder processName = new StringBuilder();
            while ((c = cmdlineReader.read()) > 0) {
                processName.append((char) c);
            }
            return processName.toString();
        } catch (Throwable e) {
            // ignore
        } finally {
            if (cmdlineReader != null) {
                try {
                    cmdlineReader.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
        return null;
    }
}