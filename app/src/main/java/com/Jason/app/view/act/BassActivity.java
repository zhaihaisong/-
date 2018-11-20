package com.Jason.app.view.act;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;

import com.Jason.app.util.LogUtils;
import com.Jason.app.util.ToastUtils;
import com.Jason.app.widget.SimpleDia;


import java.io.File;


public class BassActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!this.isTaskRoot()) {
//            Intent mainIntent = getIntent();
//            String action = mainIntent.getAction();
//            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
//                finish();
//                return;
//            }
//        }
        setBarStyle();
    }

    //适配字体
    @Override
    public Resources getResources() {
        try {
            Resources res = super.getResources();
            Configuration config = new Configuration();
            config.setToDefaults();
            res.updateConfiguration(config, res.getDisplayMetrics());
            return res;
        } catch (Exception E) {
            return super.getResources();
        }
    }
//    @Override
//    public boolean moveTaskToBack(boolean nonRoot) {
//        return super.moveTaskToBack(true);
//    }

    /*安装apk程序*/
    public void openFile(File var0) {
        try {
            Intent var2 = new Intent();
            var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            var2.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= 24) {
                Uri uriForFile = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", var0);
                var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                var2.setDataAndType(uriForFile, getContentResolver().getType(uriForFile));
            } else {
                var2.setDataAndType(Uri.fromFile(var0), getMIMEType(var0));
            }

            startActivity(var2);
        } catch (Exception var5) {
            try {
                ToastUtils.show(getApplicationContext(), "请打开手机 设置--安全--未知来源 并重新进行打包，" +
                        "或者手动打开文件管理SR_apk文件夹下newapp.apk，手动安装APP.");
            } catch (Exception E) {
            }
            errtanchuang();
        }
    }

    public String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

    /*安装失败提醒*/
    private void errtanchuang() {
        new SimpleDia(BassActivity.this, SimpleDia.Ok_No_Type).setTitleText("APP自动安装失败")
                .setContentText(Html.fromHtml("请打开手机 设置--安全--未知来源 并重新进行打包，" +
                        "或者手动打开文件管理SR_apk文件夹下newapp.apk，手动安装APP."))
                .setConfirmText("确定")
                .setCancelText("取消")
                .showCancelButton(true)

                .setClickListener(new SimpleDia.OnClickButtonListener() {
                    @Override
                    public void onClick(SimpleDia dialog, int flag) {
                        switch (flag) {
                            case 0: // 取消

                                break;
                            case 1: // 确定


                                break;
                            default:
                                break;
                        }

                    }
                }).show();

    }


    /**
     * 设置透明状态栏，设置此属性的Activity的最顶部布局需设置 android:fitsSystemWindows="true" 属性
     * 以确保状态栏颜色和界面第一个控件背景色一样，否则状态栏背景颜色为界面背景色
     * 如果想以图片为背景时，不要设置此属性
     * android:fitsSystemWindows="true" 属性 控件不占用系统控件位置（例如状态栏，导航栏）
     * 特别注意：根控件为 DrawerLayout时，应为内容布局的适当位置（例如Title布局中，根布局）设置此属性，抽屉布局设置此属性无效（文章后面有举例），自己注意设置paddingTop或marginTop
     */
    public void setBarStyle() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    public void goHome() {
        try {
            Intent intent = new Intent(BassActivity.this, Anjian_List_Act.class);
            startActivity(intent);

        } catch (Exception E) {
        }
    }
}
