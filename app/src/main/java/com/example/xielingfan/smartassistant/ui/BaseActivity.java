package com.example.xielingfan.smartassistant.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.ui
 * 文件名:  BaseActivity
 * 创建者： XieLingfan
 * 创建时间:2018/7/21 14:18
 * 描述：   Activity基类
 *          1.统一的属性
 *          2.统一的接口
 *          3.统一的方法
 */
public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
