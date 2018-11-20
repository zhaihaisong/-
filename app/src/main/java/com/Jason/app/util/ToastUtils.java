package com.Jason.app.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast提示工具类
 * @author qingf
 */
public class ToastUtils {
	public static boolean isShow = false;	// 是否弹出Toast提示
	
    private ToastUtils() {
        throw new AssertionError();
    }

    public static void show(Context context, CharSequence text, int duration) {
    	Toast.makeText(context, text, duration).show();
    }
    
    public static void debugShow(Context context, CharSequence text, int duration) {
        if(isShow) {
        	Toast.makeText(context, text, duration).show();
        }
    }
    
    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 用于调试打印的时候调用
     * @param context
     * @param text
     */
    public static void debugShow(Context context, CharSequence text) {
    	debugShow(context, text, Toast.LENGTH_SHORT);
    }
    
    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }
}
