package com.Jason.app.view.act;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.Jason.app.BaseApp;
import com.Jason.app.R;
import com.Jason.app.model.anjian_info;
import com.Jason.app.util.LogUtils;
import com.Jason.app.util.ToastUtils;
import com.Jason.app.view.adapter.Anjian_List_Adapter;
import com.Jason.app.view.listener.LoadingView;
import com.Jason.app.view.listener.MyItemClickListener;
import com.Jason.app.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 案件列表
 *
 * @author Jason
 */
public class Anjian_List_Act extends BassActivity implements LoadingView {


    @BindView(R.id.chongxinjiazai)
    LinearLayout chongxinjiazai;
    @BindView(R.id.anjian_recycler)
    RecyclerView anjianRecycler;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.shezhi)
    LinearLayout shezhi;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anjian__list_);
        ButterKnife.bind(this);
        initView();
        data();
    }

    private void initView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        anjianRecycler.setLayoutManager(linearLayoutManager);
        swipeRefresh.setColorSchemeResources(R.color.login_title);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data();
            }
        });
    }

    private void data() {
        String anjian_id = "12";
        String anjian_name = "毛清曲与鹤壁中泰矿业有限公司劳动争议纠纷";
        String anjian_type[] = {"已排期", "未排期", "已结束", "未排期"};
        String ss_fayuan = "郑州市金水区人民法院";
        String sessionTime = "2018/01/21 10:00:00";
        chongxinjiazai.setVisibility(View.GONE);
        showLoading();
        final ArrayList<anjian_info> anjian_infos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            anjian_info anjian_info = new anjian_info();
            anjian_info.setAnjian_type(anjian_type[i]);
            anjian_info.setSessionTime(sessionTime);
            anjian_info.setSs_fayuan(ss_fayuan);
            anjian_info.setAnjian_id(anjian_id + i);
            anjian_info.setName(anjian_name + i);
            anjian_infos.add(anjian_info);


        }

/*模拟网络请求时间*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                Anjian_List_Adapter anjian_adapter = new Anjian_List_Adapter();
                anjian_adapter.add_data(anjian_infos);
                anjianRecycler.setAdapter(anjian_adapter);
                   /*
        * 点击事件处理
        * */
                anjian_adapter.setOnItemClickListener(new MyItemClickListener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        try {
                            Intent intent = new Intent(getApplication(), AnJian_Details_Act.class);
                            startActivity(intent);
                        } catch (Exception e) {
                        }
                    }
                });

            }
        }, 500);


    }


    private Timer timer;
    private TimerTask task;
    private Dialog mLoadingDialog;
    private boolean isDialog = true;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    hideLoading();
                    isDialog = true;
                    chongxinjiazai.setVisibility(View.VISIBLE);
                    chongxinjiazai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            data();
                        }
                    });

                    ToastUtils.show(BaseApp.getContext(), "请求超时，请重新加载！");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void showLoading() {
        try {
            isDialog = true;
            if (mLoadingDialog == null) {
                try {
                    mLoadingDialog = LoadingDialog.createLoadingDialog(this, "请稍候！  ");
                    mLoadingDialog.show();
                } catch (Exception e) {
                }
            }
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    if (isDialog) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);

                    }
                }
            };
            timer.schedule(task, 20000);
        } catch (Exception e) {
        }

    }

    @Override
    public void hideLoading() {
        try {
            isDialog = false;
            if (mLoadingDialog != null) {
                mLoadingDialog.dismiss();
                mLoadingDialog = null;
            }
        } catch (Exception E) {
        }
          /*数据请求成功关闭刷新动画*/
        try {
            swipeRefresh.setRefreshing(false);
        } catch (Exception E) {
        }
        try {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            if (task != null) {
                task.cancel();
                task = null;
            }


        } catch (Exception E) {
        }

    }

    @OnClick(R.id.shezhi)
    public void onViewClicked() {
        showPopUsousuo();


    }

    /*初始化popu*/
    private void showPopUsousuo() {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popupWindowContentView = getPopupWindowContentsousuoView();
        popupWindow = new PopupWindow(popupWindowContentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        /*popu 开启与关闭的动画*/
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        int sppo = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        popu.measure(sppo, sppo);
//        int popuheigh = popu.getHeight();
        backgroundAlpha(0.5f);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        /*popu关闭的监听*/
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }

            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private TextView login_out, qingchu, quxiao;

    /*PopupWindow  内容的引用与数据的处理以及点击事件*/
    private View getPopupWindowContentsousuoView() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(R.layout.shezhi_popu, null);
        login_out = (TextView) contentView.findViewById(R.id.login_out);
        login_out.setOnClickListener(menuItemOnClickListener);
        qingchu = (TextView) contentView.findViewById(R.id.qingchu);
        qingchu.setOnClickListener(menuItemOnClickListener);
        quxiao = (TextView) contentView.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(menuItemOnClickListener);

        return contentView;
    }

    //PopupWindow的点击事件的处理
    View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.login_out) {
                try {
                    Intent intent = new Intent(Anjian_List_Act.this, Login_type_Act.class);
                    startActivity(intent);
                    Anjian_List_Act.this.finish();
                    ToastUtils.show(getApplicationContext(), "您已成功注销登录！");
                } catch (Exception r) {
                }

            } else if (v.getId() == R.id.qingchu) {
                ToastUtils.show(getApplicationContext(), "清楚缓存");
            }
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        }
    };

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
