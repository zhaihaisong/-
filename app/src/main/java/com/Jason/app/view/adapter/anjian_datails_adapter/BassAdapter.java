package com.Jason.app.view.adapter.anjian_datails_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Jason.app.R;
import com.Jason.app.model.anjian_details_info;
import com.Jason.app.view.listener.MyItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2018/10/24.
 */

public class BassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    public MyItemClickListener mItemClickListener;
    private Context context;
    private List<anjian_details_info> data;

    //创建新View，被LayoutManager所调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        if (viewType == 0) {
            return new lines_holder(LayoutInflater.from(context).inflate(R.layout.anjian_details_lines_item, null, false));
        } else if (viewType == 1) {
            return new title_holder(LayoutInflater.from(context).inflate(R.layout.anjian_details_name_item, null, false));

        } else {
            return new nor_holder(LayoutInflater.from(context).inflate(R.layout.anjian_details_nor_item, null, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).isIs_Lines()) {//判断是非为横线
            return 0;
        } else if (data.get(position).isIs_title()) {//判断是否为title
            return 1;
        } else {//正常数据
            return 2;
        }


    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (data.get(position).isIs_Lines()) {//判断是非为横线

        } else if (data.get(position).isIs_title()) {//判断是否为title
            try {
                ((title_holder) holder).bindHolder(data.get(position ));
            } catch (Exception b) {
            }
        } else {//正常数据
            try {
                ((nor_holder) holder).bindHolder1(data.get(position ),this);
            } catch (Exception b) {
            }
        }





////点击事件
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//
//            private int layoutPosition;
//
//            @Override
//            public void onClick(View v) {
//                layoutPosition = holder.getLayoutPosition();
//                mItemClickListener.onItemClick(holder.itemView, layoutPosition);
//            }
//        });

    }

    public void addData(List<anjian_details_info> data) {
        this.data = data;
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return (data != null ? data.size() : 0);
    }

//    /**
//     * 设置Item点击监听
//     *
//     * @param listener
//     */
//    public void setOnItemClickListener(MyItemClickListener listener) {
//        this.mItemClickListener = listener;
//    }

}
