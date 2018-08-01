package com.example.xielingfan.smartassistant.entity;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.entity
 * 文件名:  WeChatData
 * 创建者： XieLingfan
 * 创建时间:2018/7/26 17:22
 * 描述：   微信精选的数据类
 */
public class WeChatData {

    //标题
    private String title;
    //出处
    private String source;
    //图片的url
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

//    public String getImgUrl() {
//        return imgUrl;
//    }
//
//    public void setImgUrl(String imgUrl) {
//        this.imgUrl = imgUrl;
//    }

}
