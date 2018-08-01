package com.example.xielingfan.smartassistant.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.xielingfan.smartassistant.MainActivity;
import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.utils.ShareUtils;
import com.example.xielingfan.smartassistant.utils.StaticClass;
import com.example.xielingfan.smartassistant.utils.UtilTools;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.ui
 * 文件名:  SplashActivity
 * 创建者： XieLingfan
 * 创建时间:2018/7/22 15:51
 * 描述：   闪屏
 */

public class SplashActivity extends AppCompatActivity {

    /**
     * 1.延时1000ms
     * 2.判断程序是否第一次运行
     * 3.自定义字体
     * 4.Activity状态主题
     * @param savedInstanceState
     */

    private TextView tv_splash;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否第一次运行
                    if(isFirst()) {
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        initView();

    }

    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,1000);

        tv_splash = (TextView) findViewById(R.id.tv_splash);

        //设置字体
        UtilTools.setFont(this,tv_splash);
    }

    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this,StaticClass.SHAERE_IS_FIRST,true);
        if(isFirst){
            ShareUtils.putBoolean(this,StaticClass.SHAERE_IS_FIRST,false);
            //是第一次运行
            return true;
        } else {
            return false;
        }
    }

    //禁止返回键
    @Override
    public void onBackPressed() {

    }
}
