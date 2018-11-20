package com.Jason.app.view.act;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.Jason.app.AppManager;
import com.Jason.app.AppPreferences;
import com.Jason.app.Constants;
import com.Jason.app.R;
import com.Jason.app.http.BassImageUtil;
import com.Jason.app.view.listener.LoadingView;
import com.Jason.app.widget.LoadingDialog;
import com.Jason.app.widget.SimpleDia;
/***
 * 功能测试页面
 * 框架大部分功能都在此处
 * */

public class MainActivity1 extends BassActivity implements View.OnClickListener, LoadingView {

    protected TextView text;
    protected ImageView image;
    protected Button a;
    protected Button b;
    protected Button c;
    protected Button d;
    protected Button e;
    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main1);
        initView();
    }

    private void initView() {

        //把当前Acvitity添加到栈中方便后续的管理
        AppManager.getAppManager().addActivity(MainActivity1.this);
        //把登陆标识储存到本地
        AppPreferences.putString(getApplicationContext(), "uid", "1");

        text = (TextView) findViewById(R.id.text);
        String stringExtra = getIntent().getStringExtra("1");
        text.setText(stringExtra);
        image = (ImageView) findViewById(R.id.image);
        a = (Button) findViewById(R.id.a);
        a.setOnClickListener(MainActivity1.this);
        b = (Button) findViewById(R.id.b);
        b.setOnClickListener(MainActivity1.this);
        c = (Button) findViewById(R.id.c);
        c.setOnClickListener(MainActivity1.this);
        d = (Button) findViewById(R.id.d);
        d.setOnClickListener(MainActivity1.this);
        e = (Button) findViewById(R.id.e);
        e.setOnClickListener(MainActivity1.this);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.a) {
            //正常图片加载
            new BassImageUtil().ImageInitNet(getApplicationContext(), Constants.Http_Api_Image, image);
        } else if (view.getId() == R.id.b) {
            //圆形图片加载
            new BassImageUtil().ImageInitCircular(getApplicationContext(), Constants.Http_Api_Image, image);
        } else if (view.getId() == R.id.c) {
            //圆角图片加载
            new BassImageUtil().ImageInitCirBead(getApplicationContext(), Constants.Http_Api_Image, image, 0);
        } else if (view.getId() == R.id.d) {
            //弹框演示
            new SimpleDia(MainActivity1.this, SimpleDia.Ok_No_Type)
                    .setTitleText("弹窗测试")
                    .setContentText(Html.fromHtml("是否退出应用"))
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

                                    AppManager.getAppManager().finishAllActivity();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }).show();
        } else if (view.getId() == R.id.e) {
            //DiaLog启动
            showLoading();
            //模拟网络请求6秒后关闭DiaLog
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //关闭DiaLog
                    hideLoading();
                }
            }.sendEmptyMessageDelayed(0, 1000); // 1秒的模拟延迟时间

        }
    }

    //自定义DiaLog文字显示
    //集成LoadingView实现showLoading()  hideLoading() 方法
    //把如下数据写入到两个方法中即可
    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(MainActivity1.this, "测试数据");
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
