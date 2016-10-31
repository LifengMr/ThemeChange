package com.ss.android.theme.loader.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by chenlifeng on 16/10/19.
 */
public class NetHelper {

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager manager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            return (info != null && info.isAvailable());
        } catch (Exception e) {
            // ignore
        }
        return false;
    }
}
