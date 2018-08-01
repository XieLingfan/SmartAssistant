package com.example.xielingfan.smartassistant.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.utils
 * 文件名:  UtilTools
 * 创建者： XieLingfan
 * 创建时间:2018/7/21 14:25
 * 描述：   工具统一类
 */
public class UtilTools {


    public static void setFont(Context mContext, TextView textView){
        Typeface fontType =  Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    //保存图片
    public static void putImageToShare(Context mContext, ImageView imageView){
        //保存
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        //将bitmap压缩至字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //利用Base64将字节输出流转String
        byte [] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        //将String保存
        ShareUtils.putString(mContext,"image_title",imgString);
//        L.i("1:   "+imgString);
//        String test = ShareUtils.getString(getContext(),"image_title","");
//        L.i("2:   "+test);
    }

    //读取图片
    public static void getImageToShare(Context mContext, ImageView imageView){
        //拿到String
        String imgString = ShareUtils.getString(mContext,"image_title","");
        if(!imgString.equals("")){
            //利用Base64将字节输出流转String
            byte [] byteArray = Base64.decode(imgString,Base64.DEFAULT);
            ByteArrayInputStream byStrem = new ByteArrayInputStream(byteArray);
            //生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStrem);
            imageView.setImageBitmap(bitmap);
        }
    }
}
