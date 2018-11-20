package com.Jason.app.view.act;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Jason.app.BaseApp;
import com.Jason.app.Constants;
import com.Jason.app.R;
import com.Jason.app.http.BaseHttpUtil;
import com.Jason.app.http.HttpFileCallBack;
import com.Jason.app.http.HttpPostCallBack;
import com.Jason.app.util.BitmapUtils;
import com.Jason.app.util.CameraInterface;
import com.Jason.app.util.CameraParaUtil;
import com.Jason.app.util.CameraPreview;
import com.Jason.app.util.DisplayUtil;
import com.Jason.app.util.LogUtils;
import com.Jason.app.util.ToastUtils;
import com.Jason.app.view.listener.LoadingView;
import com.Jason.app.widget.LoadingDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 人脸识别页面
 *
 * @author Jason
 */
public class Renlian_Login_Act extends BassActivity implements LoadingView, CameraInterface.CameraListener {
    private final String TAG = "Test";
    @BindView(R.id.camera_preview)
    FrameLayout preview;
    @BindView(R.id.back_onc)
    LinearLayout backOnc;
    @BindView(R.id.saomiaokuang)
    ImageView saomiaokuang;
    @BindView(R.id.img_take_picture)
    TextView imgTakePicture;
    //    private FrameLayout preview;
    private CameraPreview mSurfaceView;

    private OrientationEventListener mOrientationListener;
    private int cameraOrientation = 0;
    private int picQuality;
    private int picWidth;
    private int previewWidth;
    private int pictureSize;
    String imgA = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1539164331&di=6d1e512b76a68fc77c9ee4d4f46041c2&src=http://img1.dzwww.com:8080/tupian/20161216/62/2291874034971432338.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//拍照过程屏幕一直处于高亮
        setContentView(R.layout.activity_renlian_login_);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        picQuality = intent.getIntExtra(CameraParaUtil.picQuality, CameraParaUtil.defaultPicQuality);
        picWidth = intent.getIntExtra(CameraParaUtil.picWidth, CameraParaUtil.defaultPicWidth);
        previewWidth = intent.getIntExtra(CameraParaUtil.previewWidth, CameraParaUtil.defaultPreviewWidth);
        pictureSize = intent.getIntExtra(CameraParaUtil.pictureSize, 0);


        initView();
        initEvent();

    }

    private void initView() {
//        preview = (FrameLayout) findViewById(R.id.camera_preview);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) saomiaokuang.getLayoutParams();
        layoutParams.width = (int) (DisplayUtil.getWindowWidth(Renlian_Login_Act.this) * 0.54);
        layoutParams.height = (int) (DisplayUtil.getWindowWidth(Renlian_Login_Act.this) * 0.58);
        layoutParams.topMargin = (int) (DisplayUtil.getWindowWidth(Renlian_Login_Act.this) * 0.2);
        saomiaokuang.setLayoutParams(layoutParams);
        initCameraView();
    }

    private void initCameraView() {
//        String [] data={Manifest.permission.CAMERA};
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,data , 120);
//            return;
//        }
        CameraInterface.getInstance().setMinPreViewWidth(previewWidth);
        CameraInterface.getInstance().setMinPicWidth(picWidth);
        CameraInterface.getInstance().setCameraListener(this);
        mSurfaceView = new CameraPreview(this);
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CameraInterface.getInstance().focusOnTouch((int) event.getX(), (int) event.getY(), preview);
                return false;
            }
        });
        preview.addView(mSurfaceView);
    }

    @OnClick(R.id.img_take_picture)
    public void onViewClicked() {
        //拍照
        CameraInterface.getInstance().takePicture();
    }

    private void initEvent() {
        //监听手机旋转角度
        mOrientationListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                cameraOrientation = orientation;
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        mOrientationListener.enable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOrientationListener != null)
            mOrientationListener.disable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTakePhoto();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                cancelTakePhoto();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void cancelTakePhoto() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTakePictureSuccess(File pictureFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapUtils.rotateBitmap(BitmapFactory.decodeFile(pictureFile.getPath(), options), CameraInterface.getInstance().getmCameraId(), cameraOrientation);
        if (pictureSize > 0) {
            bitmap = BitmapUtils.bitmapCompress(bitmap, 120);
        }
        Log.i(TAG, "onTakePictureSuccess bitmap.getWidth: " + bitmap.getWidth() + ", bitmap.getHeight():" + bitmap.getHeight());
        Log.i(TAG, "onTakePictureSuccess picQuality: " + picQuality + ", bitmap.getByteCount():" + bitmap.getByteCount());
        BitmapUtils.saveBitmapToSd(bitmap, pictureFile.getPath(), picQuality);
        //更新本地相册
        Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(pictureFile));
        sendBroadcast(localIntent);
        Log.i(TAG, "拍照成功 pictureFile:" + pictureFile.getPath());
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                CameraInterface.getInstance().getCamera().stopPreview();
//            }
//        });
        showLoading();
        HT_Pic_don(pictureFile);

    }

    /*
    * 后台人脸头像下载
    * */
    private void HT_Pic_don(final File file1) {

        new BaseHttpUtil().doDownloadFile("/sdcard/SR_Pic", "renlian_y.png", imgA, new HttpFileCallBack() {
            @Override
            public void inProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

            }

            @Override
            public void onSuccess(File file2) {
                renlian(file1, file2);

            }

            @Override
            public void onError(Exception e) {
                ToastUtils.show(getApplicationContext(), "认证异常，请重试！");
                starCamer();
                hideLoading();
            }
        });


    }


    /*
    **人脸比对接口
    * application/json     url
    * multipart/form-data    file
    * */

    private void renlian(final Object file, Object file2) {
        new BaseHttpUtil().doPost_renlian("multipart/form-data", file, file2, Constants.TC_renlian, new HttpPostCallBack() {
            @Override
            public void onSuccess(Object result) {
                hideLoading();
                LogUtils.e(result.toString());

                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    String code = jsonObject.optString("message");
                    if (code.equals("OK")) {
                        //识别成功
                        double v = new JSONObject(jsonObject.optString("data")).optDouble("similarity");
                        if (v >= 80) {
                            //认证成功
                            ToastUtils.show(getApplicationContext(), "认证成功！");
                            finish();
                        } else {
                            //认证失败
                            ToastUtils.show(getApplicationContext(), "身份不符，请重试！");
                            starCamer();
                        }
                    } else {
                        //识别失败
                        ToastUtils.show(getApplicationContext(), "人脸检测失败，请重试！");
                        starCamer();
                    }


                } catch (Exception E) {
                    ToastUtils.show(getApplicationContext(), "认证异常，请重试！");
                    starCamer();
                }


            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.show(getApplicationContext(), "认证异常，请重试！");
                starCamer();
                hideLoading();
            }
        });
    }

    /*
    * 重启拍照并对焦
    * */
    private void starCamer() {
        CameraInterface.getInstance().getCamera().startPreview();
        CameraInterface.getInstance().getCamera().cancelAutoFocus();
    }

    @Override
    public void onTakePictureFail(byte[] data) {
        Log.e(TAG, "拍照失败，请检查权限设置!"); //三星A8出现无法创建文件夹的提示，重启恢复正常
        CameraParaUtil.pictureBitmap = BitmapUtils.rotateBitmap(BitmapUtils.Bytes2Bitmap(data), CameraInterface.getInstance().getmCameraId(), cameraOrientation);
        Intent intent = new Intent();
        setResult(CameraParaUtil.REQUEST_CODE_FROM_CAMERA_FAIL, intent);
        finish();
//        Toast.makeText(Renlian_Login_Act.this, "拍照失败，请检查权限设置!", Toast.LENGTH_SHORT).show();
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
                    ToastUtils.show(BaseApp.getContext(), "网络请求超时，请重试！");
                    starCamer();
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
                    mLoadingDialog = LoadingDialog.createLoadingDialog(this, "识别中...  ");
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


}