package com.example.xielingfan.smartassistant.entity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.entity
 * 文件名:  MyUser
 * 创建者： XieLingfan
 * 创建时间:2018/7/23 14:14
 * 描述：   用户属性
 */
public class MyUser extends BmobUser{

    private int age;
    private boolean sex;
    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }




}
