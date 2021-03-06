package com.example.xielingfan.smartassistant.application;

import android.app.Application;
import com.example.xielingfan.smartassistant.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.application
 * 文件名:  BaseApplication
 * 创建者： XieLingfan
 * 创建时间:2018/7/21 14:16
 * 描述：   Application
 */
public class BaseApplication extends Application{

    //创建

    @Override
    public void onCreate() {

        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);

        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=" +StaticClass.VOICE_KEY);


    }
}
