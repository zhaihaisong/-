package com.Jason.app.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.Jason.app.R;
import com.Jason.app.model.anjian_info;
import com.Jason.app.util.DisplayUtil;
import com.Jason.app.view.listener.MyItemClickListener;

import java.util.ArrayList;


public class Anjian_List_Adapter extends RecyclerView.Adapter<Anjian_List_Adapter.ViewHolder> {
    private ArrayList<anjian_info> data;
    public MyItemClickListener mItemClickListener;
    private Context context;

    public void add_data(ArrayList<anjian_info> data) {
        this.data = data;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.anjan_list_item, null, false);
        return new ViewHolder(view, mItemClickListener);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*
    * @ anjian_type
     * 1代表 已排期
    * 2代表 未排期
    * 3代表 已结束
    * */
        anjian_info anjian_info = data.get(position);

        try {
            //案件名称
            String name = anjian_info.getName();
            holder.anjian_name.setText(name + "");

        } catch (Exception E) {
        }
        try {
            //开庭时间
            String SessionTime = anjian_info.getSessionTime();
            holder.kt_time.setText(SessionTime);

        } catch (Exception E) {
        }
        try {
            //所属法院
            String Ss_fayuan = anjian_info.getSs_fayuan();
            holder.ss_fayuan.setText(Ss_fayuan);

        } catch (Exception E) {
        }
        try {
            //案件类型
            String anjian_type = anjian_info.getAnjian_type();
            holder.anjian_type.setText(anjian_type);
            if (anjian_type.equals("已排期")) {
                holder.anjian_type.setBackgroundResource(R.drawable.anjian_yipaiqi);
            } else if (anjian_type.equals("未排期")) {
                holder.anjian_type.setBackgroundResource(R.drawable.anjian_weipaiqi);
            } else if (anjian_type.equals("已结束")) {
                holder.anjian_type.setBackgroundResource(R.drawable.anjian_yijieshu);
            }

        } catch (Exception E) {
        }


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
        public TextView anjian_name, kt_time, ss_fayuan, anjian_type;
        private MyItemClickListener mListener;

        public ViewHolder(View view, MyItemClickListener listener) {
            super(view);
            this.mListener = listener;

            anjian_name = (TextView) view.findViewById(R.id.anjian_name);
            anjian_type = (TextView) view.findViewById(R.id.anjian_type);
            kt_time = (TextView) view.findViewById(R.id.kt_time);
            ss_fayuan = (TextView) view.findViewById(R.id.ss_fayuan);
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
