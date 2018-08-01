package com.example.xielingfan.smartassistant.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.xielingfan.smartassistant.R;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.view
 * 文件名:  CustomDialog
 * 创建者： XieLingfan
 * 创建时间:2018/7/24 8:57
 * 描述：   自定义Dialog
 */
public class CustomDialog extends Dialog{

    //定义模板
    public CustomDialog(Context context,int layout,int style) {
        this(context, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,layout,style, Gravity.CENTER);
    }


    //定义属性

    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity,int anmi) {
        super(context,style);
        //设置属性
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height =  WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anmi);
    }

    //实例
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity) {
        this(context,width,height,layout,style,gravity, R.style.pop_anim_style);

    }
}



