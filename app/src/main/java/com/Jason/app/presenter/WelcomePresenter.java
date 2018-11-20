package com.Jason.app.presenter;

        import android.annotation.SuppressLint;
        import android.os.Handler;
        import android.os.Message;
        import com.Jason.app.BaseApp;
        import com.Jason.app.AppPreferences;
        import com.Jason.app.util.LogUtils;
        import com.Jason.app.util.ToastUtils;
        import com.Jason.app.view.listener.IWelcomeView;

/**
 * 欢迎界面控制器
 */
public class WelcomePresenter {
    private IWelcomeView mIWelcomeView;

    public WelcomePresenter(IWelcomeView view) {
        this.mIWelcomeView = view;
    }

    // 检查是否是第一次启动
    private void checkIsFirst() {
        LogUtils.d("do checkIsFirst");
        if (AppPreferences.getBoolean(BaseApp.getContext(), "isFirst", true)) {
            // 如果是第一次进app,则修改状态值，并跳转到引导页
            AppPreferences.putBoolean(BaseApp.getContext(), "isFirst", false);
            mIWelcomeView.turnToGuide();
        } else {    // 否则
            checkIsNetwork();
        }
    }

    // 检查是否有网络
    private void checkIsNetwork() {
        LogUtils.d("do checkIsNetwork");
        if (!BaseApp.getInstance().isNetworkConnected()) {
            // 无网络，则走无网络的初始化流程,但不关闭应用
            ToastUtils.show(BaseApp.getContext(), "请检查网络连接！");
        } else {
            // 有网络，则走网络初始化的流程
            checkIsLogin();
        }
    }

    /**
     * 检查是否登录过
     * turnToLogin() 登陆界面的调用
     * turnToMain（） 主页面的调用
     **/
    private void checkIsLogin() {
        LogUtils.d("do checkIsLogin");
        if (AppPreferences.getString(BaseApp.getContext(),"uid","").equals("11")) {
            mIWelcomeView.turnToMain();
        } else {
            mIWelcomeView.turnToLogin();
        }
    }

    //做一下初始化操作
    @SuppressLint("HandlerLeak")
    public void init() {
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                checkIsFirst();
            }
        }.sendEmptyMessageDelayed(0, 1000); // 1秒的缓冲时间
    }

}
