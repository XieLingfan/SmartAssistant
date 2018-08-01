package com.example.xielingfan.smartassistant.utils;


import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.xielingfan.smartassistant.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import cn.bmob.v3.b.V;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.utils
 * 文件名:  PicassoUtils
 * 创建者： XieLingfan
 * 创建时间:2018/7/27  18:48
 * 描述：   Picasso封装
 */
public class PicassoUtils {

    //加载图片
    public static void loadImageView( String url, ImageView imageView) {
        Picasso.get().load(url).into(imageView);
    }

    //加载图片(指定大小)
    public static void loadImageViewSize(String url,int width,int height,ImageView imageView) {
        Picasso.get()
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }

    //加载图片有默认图片
    public static void loadImageViewHolder(String url,ImageView imageView) {
        Picasso.get()
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    //裁剪图片
    public static void loadImageViewCrop(String url,ImageView imageView) {
        Picasso.get().load(url).transform(new CropSquareTransformation()).into(imageView);

    }


    //按比例裁剪 矩形
    public static class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                //回收
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "xlf"; }
    }
}
