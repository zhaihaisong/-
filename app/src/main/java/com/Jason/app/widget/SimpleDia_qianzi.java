package com.Jason.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.Jason.app.R;
import com.Jason.app.util.FileUtils;
import com.Jason.app.util.LogUtils;
import com.Jason.app.view.adapter.QianMing_List_Adapter;
import com.Jason.app.view.listener.HandWriteView;
import com.Jason.app.view.listener.MyItemClickListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 带轻微动画效果的对话框
 *
 * @author Jason
 */
public class SimpleDia_qianzi extends Dialog implements View.OnClickListener {
    protected LinearLayout upapp2;
    protected LinearLayout image;
    protected LinearLayout text;
    protected LinearLayout audo;
    LinearLayout backOnc;
    HandWriteView huaban;
    RecyclerView qianmingRecy;
    ImageView name;
    Button save;
    Button clear;
    Button qianmingDone;
    private View mDialogView;
    private OnClickButtonListener mOnClickButtonListener;
    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;

    private Animation mOverlayOutAnim;

    private Context context;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "qianzi";
    private int img_num = 0;
    private List<String> qianming_list_path_data = new ArrayList();
    private QianMing_List_Adapter qianMing_list_adapter;


    public interface OnClickButtonListener {
        void onClick(SimpleDia_qianzi dialog, int flag);
    }

    public SimpleDia_qianzi(final Context context) {
        super(context, R.style.alert_dialog);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
        this.context = context;

        mModalInAnim = (AnimationSet) SimpleAnim.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) SimpleAnim.loadAnimation(getContext(), R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LogUtils.e("onAnimationStart");

            }
            @Override
            public void onAnimationEnd(Animation animation) {
             LogUtils.e("onAnimationEnd");
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                LogUtils.e("onAnimationRepeat");

            }
        });


        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = getWindow().getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popu_qian_ming_);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        backOnc = (LinearLayout) findViewById(R.id.back_onc);
        huaban = (HandWriteView) findViewById(R.id.huaban);
        qianmingRecy = (RecyclerView) findViewById(R.id.qianming_recy);
        name = (ImageView) findViewById(R.id.name);
        save = (Button) findViewById(R.id.save);
        clear = (Button) findViewById(R.id.clear);
        qianmingDone = (Button) findViewById(R.id.qianming_done);
        backOnc.setOnClickListener(this);
        save.setOnClickListener(this);
        clear.setOnClickListener(this);
        qianmingDone.setOnClickListener(this);
        initView();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        qianmingRecy.setLayoutManager(linearLayoutManager);
        qianMing_list_adapter = new QianMing_List_Adapter();
        for (int i = 0; i < 10; i++) qianming_list_path_data.add("");
        qianMing_list_adapter.add_data(qianming_list_path_data);
        qianmingRecy.setAdapter(qianMing_list_adapter);
        qianMing_list_adapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

            }
        });
    }

    public SimpleDia_qianzi setClickListener(OnClickButtonListener listener) {
        mOnClickButtonListener = listener;
        return this;
    }

    protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
    }

    @Override
    public void cancel() {
        dismissWithAnimation(true);
    }

    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    //弹框关闭回调
    private void dismissWithAnimation(boolean fromCancel) {
//        mCloseFromCancel = fromCancel;

        if (fromCancel) {
            SimpleDia_qianzi.super.cancel();
        } else {
            SimpleDia_qianzi.super.dismiss();
        }
        //退出后删除签名图片
        for (int i = 0; i < (qianming_list_path_data != null ? qianming_list_path_data.size() : 0); i++) {
            FileUtils.delete(qianming_list_path_data.get(i));
        }
        mDialogView.post(new Runnable() {
            @Override
            public void run() {
                mDialogView.startAnimation(mModalInAnim);
            }
        });

//        image.startAnimation(mModalOutAnim);
//        text.startAnimation(mModalOutAnim);
//        audo.startAnimation(mModalOutAnim);
//        mDialogView.startAnimation(mModalOutAnim);
    }

    //
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_onc:
                break;
            case R.id.save:
                try {
                    if (huaban.isSign()) {
                        huaban.save(path + img_num + ".png", true, 10);//进行图片保存
                        //事先预设了10个空田字格  当数据小于10的时候仅进行数据替换   大于十的时候进行数据增加
                        if (img_num > 9) {
                            qianming_list_path_data.add(path + img_num + ".png");
                        } else {
                            qianming_list_path_data.set(img_num, path + img_num + ".png");
                        }
                        img_num++;
                        huaban.clear();//保存以后清空画板
                        qianMing_list_adapter.add_data(qianming_list_path_data);
                        qianMing_list_adapter.notifyItemChanged(img_num - 1);//指定位置刷新
                        qianmingRecy.smoothScrollToPosition(img_num - 1);//滚动到指定位置
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inSampleSize = 2;
//                        Bitmap bm = BitmapFactory.decodeFile(path + (img_num - 1) + ".png", options);
//                        qianming_list_data.add(bm);
//                        img.setImageBitmap(add2Bitmap(bm, bm));//拼接并进行显示图片
                    } else {
                        Toast.makeText(context, "请先进行签名！", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.clear:
                huaban.clear();
                break;
            case R.id.qianming_done:
                //              提交生成签名时 去除空白田字格

                Bitmap bitmap = add2Bitmap(path2Bitmap(qianming_list_path_data));
                if (bitmap == null) {
                    Toast.makeText(context, "请先进行签名！", Toast.LENGTH_SHORT).show();
                    return;
                }
                save(bitmap);
                name.setImageBitmap(bitmap);
//签名结果回调
                if (mOnClickButtonListener != null) {
                    mOnClickButtonListener.onClick(SimpleDia_qianzi.this, 0);
                    cancel();
                } else {
                    dismissWithAnimation();
                }
                break;
        }
    }

    /**
     * 保存画板
     */
    public void save(Bitmap bitmap) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "qianzi_done.png";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] buffer = bos.toByteArray();
        if (buffer != null) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            try {
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(buffer);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 调整图片大小
     *
     * @param bitmap 源
     * @param dst_w  输出宽度
     * @param dst_h  输出高度
     * @return
     */
    public static Bitmap imageScale(Bitmap bitmap, int dst_w, int dst_h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
//        LogUtils.e("width:"+width);
//        LogUtils.e("height:"+height);
        //设置想要的大小
//        int newWidth=30;
//        int newHeight=30;

        //计算压缩的比率
        float scaleWidth = ((float) dst_w) / width;
        float scaleHeight = ((float) dst_h) / height;

        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        //获取新的bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap.getWidth();
        bitmap.getHeight();
//        LogUtils.e("newWidth"+bitmap.getWidth());
//        LogUtils.e("newHeight"+bitmap.getHeight());
        return bitmap;
    }

    /*
    * 合并图片
    * */
    private Bitmap add2Bitmap(Bitmap first, Bitmap second) {
        int width = first.getWidth() + second.getWidth();
        int height = Math.max(first.getHeight(), second.getHeight());
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, first.getWidth(), 0, null);
        return result;
    }

    /*
       * 合并多张图片
       * */
    private Bitmap add2Bitmap(List<Bitmap> data) {
        if (data.isEmpty()) return null;
        int width = 0;
        int we = 0;
        for (int i = 0; i < (data != null ? data.size() : 0); i++) {
            width += data.get(i).getWidth() + 10;
        }
        int height = data.get(0).getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        for (int i = 0; i < (data != null ? data.size() : 0); i++) {
            if (i > 0) {
                we += data.get(i).getWidth() + 10;
            }
            canvas.drawBitmap(data.get(i), we, 0, null);
        }

        return result;
    }

    private List<Bitmap> path2Bitmap(List<String> path) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (int i = 0; i < (path != null ? path.size() : 0); i++) {
            String pathName = path.get(i);
            if (pathName.equals("")) break;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bm = BitmapFactory.decodeFile(pathName, options);
            LogUtils.e(bm.getWidth() + "@" + bm.getHeight());
            bm = imageScale(bm, 80, 80);
            LogUtils.e(bm.getWidth() + "#" + bm.getHeight());
            bitmaps.add(bm);
        }


        return bitmaps;
    }

}