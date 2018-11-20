# 包含 网络 图片请求


权限管理


屏幕适配 
  <!-- AndroidAutoSize -->
        <meta-data
            android:name="design_width_in_dp"
            android:value="375" />
        <meta-data
            android:name="design_height_in_dp"
            android:value="647" />
             //屏幕适配
    implementation 'me.jessyan:autosize:1.0.6'
           
           
文字适配 
    //适配字体（需要放在Bassactivity）
    @Override
    public Resources getResources() {
        try {
            Resources res = super.getResources();
            Configuration config = new Configuration();
            config.setToDefaults();
            res.updateConfiguration(config, res.getDisplayMetrics());
            return res;
        } catch (Exception E) {
            return super.getResources();
        }
    }
    
    
    
透明状态栏
 /**
     * 设置透明状态栏，设置此属性的Activity的最顶部布局需设置 android:fitsSystemWindows="true" 属性
     * 以确保状态栏颜色和界面第一个控件背景色一样，否则状态栏背景颜色为界面背景色
     * 如果想以图片为背景时，不要设置此属性
     * android:fitsSystemWindows="true" 属性 控件不占用系统控件位置（例如状态栏，导航栏）
     * 特别注意：根控件为 DrawerLayout时，应为内容布局的适当位置（例如Title布局中，根布局）设置此属性，抽屉布局设置此属性无效（文章后面有举例），自己注意设置paddingTop或marginTop
     */
     （需要放在Bassactivity  的onCreate内）
    public void setBarStyle() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }
    
    
    
recyclerview 多布局   以及recyclewview 中可动态显示指定行数的textview



  try {//解决华为手机切换后台再次打开app时应用重启情况
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        } catch (Exception E) {
        }
        
        
