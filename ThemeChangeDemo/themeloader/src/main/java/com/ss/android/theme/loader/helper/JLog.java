package com.ss.android.theme.loader.helper;

import android.util.Log;

import com.ss.android.theme.loader.constants.Constant;

/**
 * @author chenlifeng1
 */
public class JLog {
	private static final String TAG = "theme";
	private static final String TAG_APK = "apk";

	public static void log(String tag, String msg){
		if(Constant.DEBUG){
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, String msg){
		if(Constant.DEBUG){
			Log.i(tag, msg);
		}
	}

	public static void d(String tag, String msg){
		if(Constant.DEBUG){
			Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg){
		if(Constant.DEBUG){
			Log.e(tag, msg);
		}
	}

	public static void v(String tag, String msg){
		if(Constant.DEBUG){
			Log.v(tag, msg);
		}
	}

	public static void w(String tag, String msg){
		if(Constant.DEBUG){
			Log.w(tag, msg);
		}
	}

	/**
	 * 默认tag是"JAR"
	 * @param msg
	 */
	public static void i(String msg){
		if(Constant.DEBUG){
			Log.i(TAG, msg);
		}
	}
	
	public static void d(String msg){
		if(Constant.DEBUG){
			Log.d(TAG, msg);
		}
	}
	
	public static void e(String msg){
		if(Constant.DEBUG){
			Log.e(TAG, msg);
		}
	}
	
	public static void v(String msg){
		if(Constant.DEBUG){
			Log.v(TAG, msg);
		}
	}
	
	public static void w(String msg){
		if(Constant.DEBUG){
			Log.w(TAG, msg);
		}
	}
	
	/**
	 * 显示当前class信息
	 */
	public static void d(Class clazz, String msg){
		if(Constant.DEBUG){
			Log.d(TAG, clazz.getSimpleName() + "---" + msg);
		}
	}
}
