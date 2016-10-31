package com.ss.android.theme.loader.helper;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by chenlifeng on 16/5/27.
 */
public class FileHelper {
    public static final String TAG = FileHelper.class.getName();

    private static String mkdirs(Context context, String dir) {
        File file = new File(context.getCacheDir().getParentFile(), "theme");
        if (file.exists()) {
            file.mkdirs();
        }
        String apkRoot = file.getPath();
        File dirFile = new File(apkRoot, dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        JLog.i(TAG, "mkdirs dirFile.getPath()=" + dirFile.getPath() + ",dirFile.exists()=" + dirFile.exists());
        return dirFile.getPath();
    }

    public static String getDexDir(Context context, String packageName) {
        return mkdirs(context, "theme/" + packageName);
    }

    public static String getThemePath(Context context, String packageName, String apkName) {
        String path = getDexDir(context, packageName) + "/" + apkName;
        JLog.i(TAG, "getThemePath path=" + path);
        return path;
    }

    public static String getDownloadDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/ss_theme");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static String getDownloadFilePath(String fileName) {
        String dir = getDownloadDir();
        if (TextUtils.isEmpty(dir)) {
            return null;
        }
        return dir + "/" + fileName;
    }

    public static String getDownloadTempDir() {
        String dir = getDownloadDir();
        if (TextUtils.isEmpty(dir)) {
            return null;
        }
        File dirTmp = new File(dir + "/" + "temp");
        if (!dirTmp.exists()) {
            dirTmp.mkdirs();
        }
        return dirTmp.getAbsolutePath();
    }

    public static String getDownloadTempFilePath(String fileName) {
        String dir = getDownloadTempDir();
        if (TextUtils.isEmpty(dir)) {
            return null;
        }
        return dir + "/" + fileName;
    }

    public static boolean copyFile(String sourcePath, String desPath) {
        JLog.i(TAG, "copyFile sourcePath=" + sourcePath + ", desPath=" + desPath);
        if (TextUtils.isEmpty(sourcePath) || TextUtils.isEmpty(desPath)) {
            return false;
        }

        File srcFile = new File(sourcePath);
        JLog.i(TAG, "copyFile srcFile.exists()=" + srcFile.exists());
        if (!srcFile.exists()) {
            return false;
        }

        File desFile = new File(desPath);
        JLog.i(TAG, "copyFile desFile.exists()=" + desFile.exists());
        if (desFile.exists()) {
            desFile.delete();
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(srcFile);
            outputStream = new FileOutputStream(desFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return true;
        } catch (FileNotFoundException e) {
            JLog.i(TAG, "copyFile FileNotFoundException e=" + e.getMessage());
        } catch (IOException e) {
            JLog.i(TAG, "copyFile IOException e=" + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return false;
    }

    public static boolean isFileExists(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        File file = new File(path);
        return file.exists();
    }
}
