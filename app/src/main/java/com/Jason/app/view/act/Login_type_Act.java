package com.Jason.app.view.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Jason.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录类型选择
 *
 * @author Jason
 */
public class Login_type_Act extends BassActivity {

    @BindView(R.id.mm_sfz)
    EditText mmSfz;
    @BindView(R.id.mm_mima)
    EditText mmMima;
    @BindView(R.id.mm_login)
    LinearLayout mmLogin;
    @BindView(R.id.yzm_sfz)
    EditText yzmSfz;
    @BindView(R.id.yzm_phone)
    EditText yzmPhone;
    @BindView(R.id.yzm_hqyzm)
    TextView yzmHqyzm;
    @BindView(R.id.yzm_yzm)
    EditText yzmYzm;
    @BindView(R.id.yzm_login)
    LinearLayout yzmLogin;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.dl_type)
    TextView dlType;
    private int dl_typ = 0;   //登录方式  0 密码登录   1验证码登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type_);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.yzm_hqyzm, R.id.login, R.id.dl_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yzm_hqyzm:
                break;
            case R.id.login:
                //跳转至案件列表页面
                try {
                    Intent intent = new Intent(Login_type_Act.this, Anjian_List_Act.class);
                    startActivity(intent);
                    Login_type_Act.this.finish();
                } catch (Exception E) {
                }
                break;
            case R.id.dl_type:
                if (dl_typ == 0) {
                    dl_typ = 1;//更改登录类型
                    dlType.setText("账号密码登录");
                    mmLogin.setVisibility(View.GONE);
                    yzmLogin.setVisibility(View.VISIBLE);
                    mmLogin.setAnimation(AnimationUtils.makeOutAnimation(this, false));// 向左边移出
                    yzmLogin.setAnimation(AnimationUtils.makeInAnimation(this, false));// 向左边移入


                } else {
                    dl_typ = 0;//更改登录类型
                    dlType.setText("验证码登录");
                    mmLogin.setVisibility(View.VISIBLE);
                    yzmLogin.setVisibility(View.GONE);
                    mmLogin.setAnimation(AnimationUtils.makeInAnimation(this, true));// 向右边移入
                    yzmLogin.setAnimation(AnimationUtils.makeOutAnimation(this, true));// 向右边移出
                }
                break;
        }
    }
    /**
     * 返回按钮监听
     */
    private long firstTime = 0;

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出
                Toast.makeText(this, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                firstTime = secondTime;//更新firstTime
                return true;
            } else {
                finish();
                System.exit(0);//否则退出程序
            }


            return true;
        }

        return false;
    }
}
