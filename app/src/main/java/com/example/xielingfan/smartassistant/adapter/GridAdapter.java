package com.example.xielingfan.smartassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.entity.EmotionData;
import com.example.xielingfan.smartassistant.utils.PicassoUtils;

import java.util.List;

/**
 * 项目名 : SmartAssistant
 * 包名：   com.example.xielingfan.smartassistant.adapter
 * 文件名:  GridAdapter
 * 创建者： XieLingfan
 * 创建时间:2018/7/27  20:10
 * 描述：   TPDO
 */
public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private List<EmotionData> mList;
    private LayoutInflater inflater;
    private EmotionData data;
    private WindowManager wm;
    //屏幕宽度
    private int width;

    public GridAdapter(Context context,List<EmotionData> list) {
        this.mContext = context;
        this.mList = list;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
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
        if(view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.emotion_item,null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageview);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        data = mList.get(i);
        //解析图片
        String url = data.getUrl();

        PicassoUtils.loadImageViewSize(url,width/3,300,viewHolder.imageView);

        return view;
    }


    class ViewHolder {
        ImageView imageView;
    }
}
