package com.Jason.app.util;

import android.util.Log;
import com.Jason.app.Constants;

/**
 * 日志工具类
 * @author qingf
 */
public class LogUtils {
	private static final String Tag = Constants.App_Tag;
	public static boolean isOpen = false;
	
	private LogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");  
    }
	
	public static void d(String msg) {
		if(isOpen) {
			Log.d(Tag, msg);
		}
	}
	
	public static void e(String msg) {
		if(isOpen) {
			Log.e(Tag, msg);
		}
	}
	
}
