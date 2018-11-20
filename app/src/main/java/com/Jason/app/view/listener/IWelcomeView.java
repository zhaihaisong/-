package com.Jason.app.view.listener;

/**
 * 应用启动欢迎界面接口
 * @author  Jason
 */
public interface IWelcomeView {

    /* 显示错误信息 */
    public void showError(String msg, boolean flag);

    /* 跳转到引导页面 */
    public void turnToGuide();

    /* 跳转到登录 */
    public void turnToLogin();

    /* 跳转到主界面 */
    public void turnToMain();
}
