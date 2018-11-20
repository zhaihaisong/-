package com.Jason.app.view.adapter.anjian_datails_adapter;


import android.view.View;
import android.widget.TextView;

import com.Jason.app.R;
import com.Jason.app.model.anjian_details_info;
import com.Jason.app.util.LogUtils;
import com.Jason.app.view.listener.ExpandTextView;


/**
 * Created by Administrator on 2018/10/24.
 */

public class nor_holder extends TypeAbstarctViewHolder {
    private TextView type;
    private ExpandTextView type_data;
    private   BassAdapter item3;
    public nor_holder(View itemView) {
        super(itemView);
        type = (TextView) itemView.findViewById(R.id.type);
        type_data = (ExpandTextView) itemView.findViewById(R.id.type_data);

    }

    @Override
    public void bindHolder(Object item) {

    }

    @Override
    public void bindHolder1(final Object item, Object item2) {
        try {//数据填充
             item3 = (BassAdapter) item2;
            final anjian_details_info item1 = (anjian_details_info) item;
            type.setText(item1.getType());
            /**
             * item.getText()：    显示的文本
             * item.isExpanded()： 保存的是当前行是否是展开状态
             */
            type_data.setText(item1.getType_data(), item1.isExpanded(), new ExpandTextView.Callback() {
                @Override
                public void onExpand() {
                    // 展开状态，比如：显示“收起”按钮
                }

                @Override
                public void onCollapse() {
                    // 收缩状态，比如：显示“全文”按钮
                }

                @Override
                public void onLoss() {
                    // 不满足展开的条件，比如：隐藏“全文”按钮
                }
            });

        type_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存当前行的状态
                item1.setExpanded(!item1.isExpanded());
                // 切换状态
                type_data.setChanged(item1.isExpanded());
            }
        });




        } catch (Exception er) {
        }

    }
}
