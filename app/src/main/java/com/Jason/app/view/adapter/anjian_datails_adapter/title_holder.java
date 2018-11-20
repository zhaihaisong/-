package com.Jason.app.view.adapter.anjian_datails_adapter;


import android.view.View;
import android.widget.TextView;

import com.Jason.app.R;
import com.Jason.app.model.anjian_details_info;


/**
 * Created by Administrator on 2018/10/24.
 */

public class title_holder extends TypeAbstarctViewHolder {
    private TextView anjian_detalis_title;

    public title_holder(View itemView) {
        super(itemView);
        anjian_detalis_title = (TextView) itemView.findViewById(R.id.anjian_detalis_title);

    }

    @Override
    public void bindHolder(Object item) {
        try {//数据填充
            anjian_details_info item1 = (anjian_details_info) item;
            anjian_detalis_title.setText(item1.getType_data());
        } catch (Exception er) {
        }


    }

    @Override
    public void bindHolder1(Object a, Object b) {

    }
}
