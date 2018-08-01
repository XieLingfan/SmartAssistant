package com.example.xielingfan.smartassistant.adapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.entity.ChatListData;

import org.w3c.dom.CharacterData;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.adapter
 * 文件名:  ChatListAdapter
 * 创建者： XieLingfan
 * 创建时间:2018/7/26 9:05
 * 描述：   对话Adapter
 */
public class ChatListAdapter extends BaseAdapter {

    //左边type
    public static final int VALUE_LEFT_TEXT = 1;
    //右边type
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context mContext;
    private LayoutInflater inflater;
    private ChatListData data;
    private List<ChatListData> mList = new ArrayList<>();

    public ChatListAdapter(Context context,List<ChatListData> list) {
        this.mContext = context;
        this.mList = list;
        //获取系统服务
        inflater =(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;

        //获取type区分左右
        int type = getItemViewType(i);
        if(view == null) {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    view = inflater.inflate(R.layout.left_item,null);
                    viewHolderLeftText.tv_left_text = (TextView) view.findViewById(R.id.tv_left_text);
                    view.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    view = inflater.inflate(R.layout.right_item,null);
                    viewHolderRightText.tv_right_text = (TextView) view.findViewById(R.id.tv_right_text);
                    view.setTag(viewHolderRightText);
                    break;
            }
        }else {
            switch (type)
            {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) view.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText) view.getTag();
                    break;
            }
        }

        //赋值
        ChatListData data = mList.get(i);
        switch (type) {
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.tv_right_text.setText(data.getText());
                break;
        }


        return view;
    }

    //根据数据源position返回要显示item

    @Override
    public int getItemViewType(int position) {
        ChatListData data = mList.get(position);
        int type = data.getType();
        return type;
    }

    //返回所以layout数量
    @Override
    public int getViewTypeCount() {
        return 3;   //mList.size() + 3
    }

    //左边文本
    class ViewHolderLeftText{
        private TextView tv_left_text;
    }
    //右边文本
    class ViewHolderRightText{
        private TextView tv_right_text;
    }
}
