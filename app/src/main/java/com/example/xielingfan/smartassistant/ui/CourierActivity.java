package com.example.xielingfan.smartassistant.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.ListViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.adapter.CourierAdapter;
import com.example.xielingfan.smartassistant.entity.CourierData;
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

public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_number;
    private Button btn_get_courier;
    private ListView mListView;
    private TextView tv_courier_table;

    private List<CourierData> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        //初始化View
        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        btn_get_courier = (Button) findViewById(R.id.btn_get_courier);
        btn_get_courier.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.mListView);

        tv_courier_table = (TextView) findViewById(R.id.tv_courier_table);
        tv_courier_table.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get_courier:
                /**
                 * 1.获取输入框内容
                 * 2.判断是否为空
                 * 3.拿到数据去请求数据（JSON）
                 * 4.解析JSON
                 * 5.listview适配器
                 * 6.实体类（item）
                 * 7.设置数据，显示效果
                 */


                //1
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();

                //拼接url
                String url = "http://v.juhe.cn/exp/index?key="+ StaticClass.COURIER_KEY +
                        "&com="+ name +"&no="+ number;
                //2
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                   //3
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            //Toast.makeText(CourierActivity.this, t, Toast.LENGTH_SHORT).show();
                            L.i("Json" + t);
                            //4
                            parsingJson(t);
                        }
                    });

                } else {
                    Toast.makeText(this, R.string.should_not_null, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_courier_table:
                Intent intent = new Intent(this,CourierTableActivity.class);
                startActivity(intent);
                break;
        }
    }

    //解析数据
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArry = jsonResult.getJSONArray("list");
            for(int i = 0; i < jsonArry.length(); i++){
                JSONObject json = (JSONObject) jsonArry.get(i);

                CourierData data = new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatatime(json.getString("datetime"));

                mList.add(data);
            }
            //倒序
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


