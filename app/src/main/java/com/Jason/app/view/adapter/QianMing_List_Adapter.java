package com.Jason.app.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Jason.app.R;
import com.Jason.app.model.anjian_info;
import com.Jason.app.util.DisplayUtil;
import com.Jason.app.util.LogUtils;
import com.Jason.app.view.listener.MyItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class QianMing_List_Adapter extends RecyclerView.Adapter<QianMing_List_Adapter.ViewHolder> {
    private List<String> data;
    public MyItemClickListener mItemClickListener;
    private Context context;

    public void add_data(List<String> data) {
        this.data = data;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.qianming_list_item, null, false);
        return new ViewHolder(view, mItemClickListener);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String pathName = data.get(position) + "";
        if (pathName.equals(""))return;
        LogUtils.e(pathName + "");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);
        bitmap = imageScale(bitmap, 80, 80);//设置图片的大小
        holder.qianming_img.setImageBitmap(bitmap);//拼接并进行显示图片


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
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        float scale_w = ((float) dst_w) / src_w;
        float scale_h = ((float) dst_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix,
                true);
        return dstbmp;
    }


    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView qianming_img;
        private MyItemClickListener mListener;

        public ViewHolder(View view, MyItemClickListener listener) {
            super(view);
            this.mListener = listener;
            qianming_img = (ImageView) view.findViewById(R.id.qianming_img);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, (getPosition()));
            }
        }
    }
}
