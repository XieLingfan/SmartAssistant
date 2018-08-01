package com.example.xielingfan.smartassistant.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.ui
 * 文件名:  ForgetPasswordActivity
 * 创建者： XieLingfan
 * 创建时间:2018/7/23 21:48
 * 描述：   忘记/重置密码
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_now;
    private EditText et_new;
    private EditText et_new_passpwrd;
    private Button btn_update_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);

        initView();
    }

    //初始化View
    private void initView() {
        et_now = (EditText) findViewById(R.id.et_now);
        et_new = (EditText) findViewById(R.id.et_new);
        et_new_passpwrd = (EditText) findViewById(R.id.et_new_password);
        btn_update_password = (Button) findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_update_password:
                //获取输入框值
                String now = et_now.getText().toString().trim();
                String nnew = et_new.getText().toString().trim();
                String new_password = et_new_passpwrd.getText().toString().trim();
                //判断输入是否为空
                if(!TextUtils.isEmpty(now) && !TextUtils.isEmpty(nnew) &&
                        !TextUtils.isEmpty(new_password)) {
                    //判断两次密码是否一致
                    if(nnew.equals(new_password)) {
                        //重置密码
                        MyUser.updateCurrentUserPassword(now, nnew, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null) {
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码成功",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码失败",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
