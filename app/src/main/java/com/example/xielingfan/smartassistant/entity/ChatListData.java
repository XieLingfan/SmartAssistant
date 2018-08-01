package com.example.xielingfan.smartassistant.entity;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.entity
 * 文件名:  ChatListData
 * 创建者： XieLingfan
 * 创建时间:2018/7/26 9:07
 * 描述：   对话列表实体
 */
public class ChatListData {
    //区分左右
    private int type;

    //文本
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
