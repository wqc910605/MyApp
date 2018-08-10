package com.wwf.common.util;

import android.util.Log;

import static com.wwf.common.constant.Constant.Log.isDebug;

//日志工具类
public class LogUtil {

	public static final String TAG = "LogUtil: ";
	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
	}
	public static void v(String tag, String msg) {
		if (isDebug)
			Log.v(tag, msg);
	}


	public static void d(String tag, String msg) {
		if (isDebug)
			Log.d(tag, msg);
	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void i(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void i(String msg) {
		if (isDebug)
			Log.i(TAG, msg);

	}

	public static void w(String tag, String msg) {
		if (isDebug)
			Log.w(tag, msg);
	}

	public static void w(String msg) {
		if (isDebug)
			Log.w(TAG, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			Log.e(tag, msg);
	}

	public static void e(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
	}
}
