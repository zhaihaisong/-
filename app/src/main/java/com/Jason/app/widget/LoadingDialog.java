package com.Jason.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Jason.app.R;

/**
 * @author: Jason
 * @desc: 加载等待框
 */
public class LoadingDialog {

    /**
     * 构造对话框，准备等数据
     * @param context   上下文对象
     * @param msg   // 提示信息
     * @return
     */
	public static Dialog createLoadingDialog(Context context, String msg) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_loading, null);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        TextView tip = (TextView) view.findViewById(R.id.tip);
        Animation animation = AnimationUtils.loadAnimation(context,
                R.anim.dialog_img_rotate);
        img.startAnimation(animation);
        tip.setText(msg);
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;
    }

}
