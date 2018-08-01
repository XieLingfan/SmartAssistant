package com.example.xielingfan.smartassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.entity.CourierData;
import com.example.xielingfan.smartassistant.entity.CourierTableData;

import java.util.List;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.adapter
 * 文件名:  CourierTableAdapter
 * 创建者： XieLingfan
 * 创建时间:2018/8/1  12:29
 * 描述：   快递公司编号对照表适配器
 */
public class CourierTableAdapter extends BaseAdapter{

    private Context mContext;
    private List<CourierTableData> mList;
    //布局加载器
    private LayoutInflater inflater;

    private CourierTableData data;

    public CourierTableAdapter(Context mContext,List<CourierTableData> mList){
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
            view = inflater.inflate(R.layout.layout_couriertable_item,null);
            viewHolder.tv_com = (TextView) view.findViewById(R.id.tv_com);
            viewHolder.tv_no = (TextView) view.findViewById(R.id.tv_no);

            //设置缓存
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //设置数据
        data = mList.get(i);

        viewHolder.tv_com.setText(data.getCom());
        viewHolder.tv_no.setText(data.getNo());

        return view;
    }

    class ViewHolder{
        private TextView tv_com;
        private TextView tv_no;
    }
}
