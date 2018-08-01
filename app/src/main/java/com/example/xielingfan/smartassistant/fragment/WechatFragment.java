package com.example.xielingfan.smartassistant.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.adapter.WeChatAdapter;
import com.example.xielingfan.smartassistant.entity.WeChatData;
import com.example.xielingfan.smartassistant.ui.WebViewActivity;
import com.example.xielingfan.smartassistant.utils.L;
import com.example.xielingfan.smartassistant.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 微信精选
 */
public class WechatFragment extends Fragment implements View.OnClickListener {

    private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();
    //标题
    private List<String> mLsitTitle = new ArrayList<>();
    //地址
    private List<String> mLsitUrl = new ArrayList<>();
    //上一页
    private Button btn_previous;
    //下一页
    private Button btn_next;
    //页数
    private TextView tv_number;
    //页数
    private int pno = 1;
    //总页数
    private int totalPage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);

        findView(view);
        return view;
    }

    //初始化View
    private void findView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);

        btn_previous = (Button) view.findViewById(R.id.btn_previous);
        btn_previous.setOnClickListener(this);
        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        tv_number = (TextView) view.findViewById(R.id.tv_number);

        //进行网络请求并解析
        getData();

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("title",mLsitTitle.get(i));
                intent.putExtra("url",mLsitUrl.get(i));
                startActivity(intent);

            }
        });
    }

    //进行网络请求并解析
    private void getData() {
        //解析接口 http://v.juhe.cn/weixin/query?key=您申请的KEY
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY + "&ps=30"
                + "&pno=" +pno;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(), "Json:" + t, Toast.LENGTH_SHORT).show();
                //L.i("Json" + t);
                parsingJson(t);
            }
        });
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            totalPage = jsonResult.getInt("totalPage");
            JSONArray jsonList = jsonResult.getJSONArray("list");
            mList.clear();
            mLsitTitle.clear();
            mLsitUrl.clear();
            for(int i = 0; i < jsonList.length(); i++){
                JSONObject json =(JSONObject) jsonList.get(i);
                WeChatData data = new WeChatData();

                String title = json.getString("title");
                String url = json.getString("url");
                data.setTitle(title);
                data.setSource(json.getString("source"));
//                data.setImgUrl(json.getString("firstImg"));

                mList.add(data);
                mLsitTitle.add(title);
                mLsitUrl.add(url);

            }
            WeChatAdapter adapter = new WeChatAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
                case R.id.btn_previous:
                    if(pno > 1) {
                        pno--;
                        tv_number.setText("第"+ pno +"页");
                        getData();
                    } else {
                        Toast.makeText(getActivity(), "已经是第一页！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_next:
                    if(pno < totalPage) {
                        pno++;
                        tv_number.setText("第"+ pno +"页");
                        getData();
                    } else {
                        Toast.makeText(getActivity(), "已经是最后一页！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;

        }

    }
}
