package com.Jason.app.http;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * @author: Jason
 * 通用图片请求代理工具
 * @placeholder() 加载是等待图片
 * @error() 加载错误是的图片
 */

public class BassImageUtil {

    /**
     * 普通网络图片加载
     * @context       传递的上下文
     * @url           网络地址
     * @imageViewurl  ImageView对象
     *
     * */
    public void ImageInitNet(Context context,String url,ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }

    /**
     * 资源文件夹（drawable mipmap）图片加载
     * @Context 传递的上下文
     * @Integer  资源文件（drawable mipmap）对象
     * @ImageView ImageView对象
     *
     * */
    public void ImageInitDrawable(Context context,Integer resourceId,ImageView imageView){
        Glide.with(context).load(resourceId).into(imageView);
    }

    /**
     * 本地文件路径图片加载
     * @Context 传递的上下文
     * @File  本地文件路径
     * @ImageView ImageView对象
     * */
    public void ImageInitFile(Context context,File file,ImageView imageView){
        Glide.with(context).load(file).into(imageView);
    }

    /**
     * 圆形图片加载
     * @context       传递的上下文
     * @url           网络地址
     * @imageViewurl  ImageView对象
     * @GlideCircleTransform  对图片进行圆形处理
     * */
    public void ImageInitCircular(Context context,String url,ImageView imageView){
//        Glide.with(context).load(url).bitmapTransform(new GlideCircleTransform(context)).into(imageView);
        RequestOptions mRequestOptions = new RequestOptions()
                .bitmapTransform(new GlideCircleTransform(context));

        Glide.with(context).load(url).apply(mRequestOptions).into(imageView);
    }

    /**
     * 圆角图片加载可调节圆角半径
     * rad为圆角弧度  0为默认弧度   其他数为所输入的弧度
     * @context       传递的上下文
     * @url           网络地址
     * @imageViewurl  ImageView对象
     * @rad           图片圆角半径
     * @GlideCircleTransform  对图片进行圆角化处理
     * */
    public void ImageInitCirBead(Context context,String url,ImageView imageView,int rad){
        if (rad == 0) {
            RequestOptions mRequestOptions = new RequestOptions()
                    .bitmapTransform(new GlideRoundTransform(context, 8));

            Glide.with(context).load(url).apply(mRequestOptions).into(imageView);
        } else {
            RequestOptions mRequestOptions = new RequestOptions()
                    .bitmapTransform(new GlideRoundTransform(context, rad));
            Glide.with(context).load(url).apply(mRequestOptions).into(imageView);
        }
//        if(rad==0){
//            Glide.with(context).load(url).bitmapTransform(new GlideRoundTransform(context,8)).into(imageView);
//        }else {
//            Glide.with(context).load(url).bitmapTransform(new GlideRoundTransform(context,rad)).into(imageView);
//        }
    }


}

