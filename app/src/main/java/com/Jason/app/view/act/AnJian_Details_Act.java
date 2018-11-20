package com.Jason.app.view.act;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Jason.app.BaseApp;
import com.Jason.app.R;
import com.Jason.app.model.anjian_details_info;
import com.Jason.app.util.LogUtils;
import com.Jason.app.util.ToastUtils;
import com.Jason.app.view.adapter.anjian_datails_adapter.BassAdapter;
import com.Jason.app.view.listener.LoadingView;
import com.Jason.app.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnJian_Details_Act extends BassActivity implements LoadingView {


    @BindView(R.id.anjian_edtails_back_onc)
    LinearLayout anjianEdtailsBackOnc;
    @BindView(R.id.anjian_detalis_recy)
    RecyclerView anjianDetalisRecy;
    @BindView(R.id.anjian_detalis_jrts)
    TextView anjianDetalisJrts;
    @BindView(R.id.chongxinjiazai)
    LinearLayout chongxinjiazai;
    private BassAdapter bassAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an_jian__details_);
        ButterKnife.bind(this);
        initView();
        data();
    }

    /*
    *初始化RecyclerView控件
    **/
    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        anjianDetalisRecy.setLayoutManager(linearLayoutManager);
        bassAdapter = new BassAdapter();
    }

    /*
    * 数据加载处理
    * */
    private void data() {
        showLoading();
        //模拟网络请求6秒后关闭DiaLog
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //关闭DiaLog
                hideLoading();
                chongxinjiazai.setVisibility(View.GONE);
                List<anjian_details_info> anjian_details_infos = new ArrayList<>();
                anjian_details_infos.add(new anjian_details_info(false, true,false, "", "毛清曲与鹤壁中泰矿业有限公司劳动争议纠纷"));
                anjian_details_infos.add(new anjian_details_info(true, false,false, "案件类型", "民事案件"));
                anjian_details_infos.add(new anjian_details_info(false, false,false, "案号", "（2017）桂0222刑初340号"));
                anjian_details_infos.add(new anjian_details_info(false, false,false, "案由", "应路生诉胡飞追偿权纠纷"));
                anjian_details_infos.add(new anjian_details_info(false, false,false, "案件描述", "若缺席，法院将做缺席判决，把案件移交公安机关，将本人身份证挂公安网处理，一旦归案，将处三年到五倍罚金若缺席，法院将做缺席判决，把案件移交公安机关，将本人身份证挂公安网处理，一旦归案，将处三年到五倍罚金若缺席，法院将做缺席判决，把案件移交公安机关，将本人身份证挂公安网处理，一旦归案，将处三年到五倍罚金"));
                anjian_details_infos.add(new anjian_details_info(true, false,false, "", ""));
                anjian_details_infos.add(new anjian_details_info(false, false, false,"类型", "庭审"));
                anjian_details_infos.add(new anjian_details_info(false, false,false, "法官", "朱行江 13833333335"));
                anjian_details_infos.add(new anjian_details_info(false, false,false, "开庭法院", "郑州市金水区人民法院"));
                anjian_details_infos.add(new anjian_details_info(false, false,false, "开庭时间", "2018-10-23   10:00:00"));
                bassAdapter.addData(anjian_details_infos);
                anjianDetalisRecy.setAdapter(bassAdapter);
                bassAdapter.notifyDataSetChanged();
            }
        }.sendEmptyMessageDelayed(0, 500); // 1秒的模拟延迟时间


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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LogUtils.e("KeyEvent.KEYCODE_BACK");
            goHome();
            finish();
            return true;
        }

        return false;
    }

    @OnClick({R.id.anjian_edtails_back_onc, R.id.anjian_detalis_jrts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.anjian_edtails_back_onc:
                AnJian_Details_Act.this.finish();
                break;
            case R.id.anjian_detalis_jrts:
                break;
        }
    }

    /**
     * 返回按钮监听
     */
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AnJian_Details_Act.this.finish();
            return true;
        }

        return false;
    }
}
