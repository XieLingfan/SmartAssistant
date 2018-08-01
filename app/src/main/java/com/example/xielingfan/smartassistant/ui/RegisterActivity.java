package com.example.xielingfan.smartassistant.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.ui
 * 文件名:  RegisterActivity
 * 创建者： XieLingfan
 * 创建时间:2018/7/23 12:34
 * 描述：   注册
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText et_user;
    private EditText et_age;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private EditText et_pwd;
    private EditText et_repwd;
    private EditText et_email;
    private Button btnRegister;

    //性别
    private boolean isGender = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_age = (EditText) findViewById(R.id.et_age);
        et_desc = (EditText) findViewById(R.id.et_desc);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_repwd = (EditText) findViewById(R.id.et_repwd);
        et_email = (EditText) findViewById(R.id.et_email);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegister:
                //获取到输入框的值
                String name = et_user.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                String repwd = et_repwd.getText().toString().trim();
                String email = et_email.getText().toString().trim();

                //判断是否为空
                if(!TextUtils.isEmpty(name) &&
                        !TextUtils.isEmpty(age) &&
                        !TextUtils.isEmpty(pwd) &&
                        !TextUtils.isEmpty(repwd) &&
                        !TextUtils.isEmpty(email)){

                    //判断两次密码是否一致
                    if(pwd.equals(repwd)){
                        //先把性别判断一下
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                                if(checkedId == R.id.rb_boy){
                                    isGender = true;
                                } else if(checkedId ==R.id.rb_girl) {
                                    isGender = false;
                                }
                            }
                        });

                        //判断简介是否为空
                        if(TextUtils.isEmpty(desc)) {
                            desc = "这个人很懒，什么都没有留下！";
                        }

                        //注册
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(pwd);
                        user.setEmail(email);
                        user.setAge(Integer.parseInt(age));
                        user.setSex(isGender);
                        user.setDesc(desc);

                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if(e==null){
                                    Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this,"注册失败" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    } else {
                        Toast.makeText(this, "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
