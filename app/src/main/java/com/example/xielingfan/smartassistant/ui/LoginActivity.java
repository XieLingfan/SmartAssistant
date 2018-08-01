package com.example.xielingfan.smartassistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xielingfan.smartassistant.MainActivity;
import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.entity.MyUser;
import com.example.xielingfan.smartassistant.utils.ShareUtils;
import com.example.xielingfan.smartassistant.view.CustomDialog;

import java.io.File;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.ui
 * 文件名:  LoginActivity
 * 创建者： XieLingfan
 * 创建时间:2018/7/23 10:55
 * 描述：   登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_register;
    private EditText et_name;
    private EditText et_pwd;
    private Button btn_login;
    private CheckBox keep_pwd;

    private CustomDialog dialog;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        initView();

        /**
         * 1.定位
         * 2.绘制图层
         *
         */
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name_lg);
        et_pwd = (EditText) findViewById(R.id.et_pwd_lg);
        btn_login =(Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_register = (Button) findViewById(R.id.btn_register_lg);
        btn_register.setOnClickListener(this);
        keep_pwd = (CheckBox) findViewById(R.id.keep_pwd);

        dialog = new CustomDialog(this,0,0,R.layout.dialog_loading,R.style.theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
//        屏幕外点击无效
        dialog.setCancelable(false);

        //设置选中状态
        boolean isCheck = ShareUtils.getBoolean(this,"keeppwd",false);
        keep_pwd.setChecked(isCheck);
        if(isCheck) {
            //设置密码
            et_name.setText(ShareUtils.getString(this,"name",""));
            et_pwd.setText(ShareUtils.getString(this,"pwd",""));

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register_lg:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.btn_login:
                //获取输入框的值
                String name = et_name.getText().toString().trim();
                String pwd = et_pwd.getText().toString();
                //判断是否为空
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)) {
                    dialog.show();
//                    登录
                    MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(pwd);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if(e == null) {
                                //判断邮箱是否登录
//                                if(user.getEmailVerified()){
//                                    //跳转
//                                } else {
//                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
//                                }
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "登陆失败" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        ShareUtils.putBoolean(this,"keeppwd",keep_pwd.isChecked());

        //是否记住密码
        if(keep_pwd.isChecked()) {
            //记住
            ShareUtils.putString(this,"name",et_name.getText().toString().trim());
            ShareUtils.putString(this,"pwd",et_pwd.getText().toString().trim());

        }else {
            ShareUtils.deleShare(this,"name");
            ShareUtils.deleShare(this,"pwd");
        }
    }
}
