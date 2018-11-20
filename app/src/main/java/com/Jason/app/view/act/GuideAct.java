package com.Jason.app.view.act;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.Jason.app.R;
import java.util.ArrayList;

/**
 * 首次启动的logo界面
 * @author Jason
 */
public class GuideAct extends BassActivity {
	private ViewPager guide_pager;
    private ArrayList<View> pageViews;
    private View pageOne, pageTwo, pageThree;
    private TextView button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_guide);
		
		init();
	}
	
	@SuppressLint("InflateParams")
	private void init() {
		LayoutInflater inflater = getLayoutInflater();
        pageOne = inflater.inflate(R.layout.guide_page_one, null);
        pageTwo = inflater.inflate(R.layout.guide_page_two, null);
        pageThree = inflater.inflate(R.layout.guide_page_three, null);
        
        pageViews = new ArrayList<View>();
        pageViews.add(pageOne);
        pageViews.add(pageTwo);
        pageViews.add(pageThree);
        
        guide_pager = (ViewPager) findViewById(R.id.guide_pager);
        guide_pager.setAdapter(new GuidePageAdapter(pageViews));
        
        button = (TextView) pageThree.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //导航栏进入按钮点击事件（根据实际需求跳转到指定页面）
                Intent intent = new Intent(getApplicationContext(), Login_type_Act.class);
                intent.putExtra("1","从导航页面进入根据实际需求进行跳转到指定界面");
                startActivity(intent);
                finish();
            }
        });
	}
	
	public static void comeToGuide(Context context, boolean flag) {
        Intent intent = new Intent(context, GuideAct.class);
        context.startActivity(intent);
        if(flag) ((Activity)context).finish();
    }
	
	// 第一次刷引导页的时候，屏蔽返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {}
        return true;
    }

    class GuidePageAdapter extends PagerAdapter {
    	private ArrayList<View> mListViews;

        public GuidePageAdapter(ArrayList<View> mListViews) {
            this.mListViews = mListViews;
        }

        // 获得当前界面数
        @Override
        public int getCount() {
            if (mListViews != null) {
                return mListViews.size();
            }
            else return 0;
        }

        // 判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        // 当页面滑动出屏幕的时候,销毁position位置的界面
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mListViews.get(position));
        }

        // 初始化position位置的界面
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }
    }
}
