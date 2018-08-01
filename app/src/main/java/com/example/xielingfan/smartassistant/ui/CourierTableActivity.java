package com.example.xielingfan.smartassistant.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.adapter.CourierAdapter;
import com.example.xielingfan.smartassistant.adapter.CourierTableAdapter;
import com.example.xielingfan.smartassistant.entity.CourierData;
import com.example.xielingfan.smartassistant.entity.CourierTableData;
import com.example.xielingfan.smartassistant.utils.L;
import com.example.xielingfan.smartassistant.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourierTableActivity extends BaseActivity {

    private ListView mListView;
    private List<CourierTableData> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_table);

        //初始化View
        initView();

        String url = "http://v.juhe.cn/exp/com?key=" + StaticClass.COURIER_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //L.i("Json" + t);
                parsingJson(t);
            }
        });
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonResult = jsonObject.getJSONArray("result");

            for(int i = 0; i < jsonResult.length(); i++){
                JSONObject json = (JSONObject) jsonResult.get(i);

                CourierTableData data = new CourierTableData();
                data.setCom(json.getString("com"));
                data.setNo(json.getString("no"));

                mList.add(data);
            }
            //倒序
            CourierTableAdapter adapter = new CourierTableAdapter(this,mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
    }
}
