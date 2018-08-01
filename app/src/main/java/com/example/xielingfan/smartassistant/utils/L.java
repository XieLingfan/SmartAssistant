package com.example.xielingfan.smartassistant.utils;

import android.util.Log;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.utils
 * 文件名:  L
 * 创建者： XieLingfan
 * 创建时间:2018/7/21 22:25
 * 描述：   LOG封装类
 */
public class L {

    //开关
    public static final boolean DEBUG = true;
    //TAG
    public static final String TAG = "SmartActivity";

    //五个等级

    public static void d(String text) {
        if(DEBUG){
            Log.d(TAG, text);
        }
    }

    public static void i(String text) {
        if(DEBUG){
            Log.i(TAG, text);
        }
    }

    public static void w(String text) {
        if(DEBUG){
            Log.w(TAG, text);
        }
    }

    public static void e(String text) {
        if(DEBUG){
            Log.e(TAG, text);
        }
    }
}
