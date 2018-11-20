package com.Jason.app.view.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.Jason.app.R;
import com.Jason.app.presenter.WelcomePresenter;
import com.Jason.app.util.CheckPermissionUtils;
import com.Jason.app.view.listener.IWelcomeView;
import com.Jason.app.util.LogUtils;
import com.Jason.app.util.ToastUtils;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 欢迎界面
 *
 * @author Jason
 */
public class WelcomeAct extends BassActivity implements IWelcomeView, EasyPermissions.PermissionCallbacks {
    private WelcomePresenter mWelcomePresenter;
    private boolean flg = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {//解决华为手机切换后台再次打开app时应用重启情况
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        } catch (Exception E) {
        }
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.act_welcome, null);
        setContentView(rootView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
        rootView.startAnimation(animation);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
               /*先判断权限*/
                initPermission();

            }
        });
    }

    /**
     * 初始化权限事件
     */
    private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(this);
        if (permissions.length > 0) {
            LogUtils.e("未申请权限个数" + permissions.length + "" + permissions[0]);
            //申请权限
            EasyPermissions.requestPermissions(this, "", 23, permissions);


        } else {
            LogUtils.e("都申请了");
            if (flg) {
                initSDK();
                flg = false;
            }
        }
    }

    private void initSDK() {
        mWelcomePresenter = new WelcomePresenter(WelcomeAct.this);
        mWelcomePresenter.init();
    }


    @Override
    public void showError(String msg, boolean flag) {
        ToastUtils.show(this, msg);
        if (flag) finish();
    }


    @Override
    public void turnToGuide() {
        GuideAct.comeToGuide(WelcomeAct.this, true);
    }

    @Override
    public void turnToLogin() {
        //跳转到登陆界面
        Intent intent = new Intent(getApplicationContext(), Login_type_Act.class);
        intent.putExtra("1", "跳转到登陆界面");
        startActivity(intent);
        finish();
    }

    @Override
    public void turnToMain() {
        //跳转到主界面
        Intent intent = new Intent(getApplicationContext(), Login_type_Act.class);
        intent.putExtra("1", "跳转到主界面");
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * EsayPermissions接管权限处理逻辑
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.e("回调");
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            LogUtils.e("成功");
            if (flg) {
                LogUtils.e(perms.size() + "zheng");
                initSDK();
                flg = false;
            }
        } catch (Exception E) {
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        try {
            LogUtils.e("失败");
            int length = CheckPermissionUtils.permissions.length;
            if (perms.size() < length) {
                if (flg) {
                    LogUtils.e(perms.size() + "cuo");
                    initSDK();
                    flg = false;
                }
            }
        } catch (Exception E) {
        }
    }
}
