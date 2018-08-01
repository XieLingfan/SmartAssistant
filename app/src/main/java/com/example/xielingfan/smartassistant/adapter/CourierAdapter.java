package com.example.xielingfan.smartassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.entity.CourierData;

import java.util.List;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.adapter
 * 文件名:  CourierAdapter
 * 创建者： XieLingfan
 * 创建时间:2018/7/25 18:37
 * 描述：   快递查询适配器
 */
public class CourierAdapter extends BaseAdapter{

    private Context mContext;
    private List<CourierData> mList;
    //布局加载器
    private LayoutInflater inflater;

    private CourierData data;

    public CourierAdapter(Context mContext,List<CourierData> mList){
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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
        ViewHolder viewHolder = null;
        //第一次加载
        if(view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.layout_courier_item,null);
            viewHolder.tv_remark = (TextView) view.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = (TextView) view.findViewById(R.id.tv_zone);
            viewHolder.tv_datetime = (TextView) view.findViewById(R.id.tv_datetime);

            //设置缓存
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //设置数据
        data = mList.get(i);

        viewHolder.tv_remark.setText(data.getRemark());
        viewHolder.tv_zone.setText(data.getZone());
        viewHolder.tv_datetime.setText(data.getDatatime());

        return view;
    }

    class ViewHolder{
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;
    }
}
