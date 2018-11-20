package com.Jason.app.view.adapter.anjian_datails_adapter;

/**
 * Created by Administrator on 2018/10/24.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

//<T>使用了泛型
public abstract class TypeAbstarctViewHolder extends RecyclerView.ViewHolder {
    public TypeAbstarctViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindHolder(Object item);
    public abstract void bindHolder1(Object a, Object b);
}
