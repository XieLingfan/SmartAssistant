package com.example.xielingfan.smartassistant.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.adapter.GridAdapter;
import com.example.xielingfan.smartassistant.entity.EmotionData;
import com.example.xielingfan.smartassistant.utils.L;
import com.example.xielingfan.smartassistant.utils.PicassoUtils;
import com.example.xielingfan.smartassistant.view.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment implements View.OnClickListener {


    //图片列表
    private GridView mGridView;
    //数据
    private List<EmotionData> mList = new ArrayList<>();
    //适配器
    private GridAdapter mAdapter;
    //提示框
    private CustomDialog dialog;
    //预览图片
    private ImageView imageView;
    //图片地址数据
    private List<String> mListUrl = new ArrayList<>();
    //PhotoView
    //private PhotoViewAttacher mAttacher;

    //是否还有下一页标志
    private int more_flag;
    //页数
    private int page = 1;
    //上一页
    private Button btn_previous;
    //下一页
    private Button btn_next;
    //页数
    private TextView tv_number;
    //搜索框
    private EditText et_keyword;
    //搜索按钮
    private Button btn_search;
    //搜索关键字
    String keyword ;
    //底层线性布局
    LinearLayout ll_update;

    private boolean girl_flag = false;

    /**
     * 1.监听点击事件
     * 2.提示框
     * 3.加载图片
     * 4.PhotoView
     *
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_image,null);

        findView(view);

        return view;
    }

    //初始化View
    private void findView(View view) {
        mGridView = (GridView) view.findViewById(R.id.mGridView);

        btn_previous = (Button) view.findViewById(R.id.btn_previous);
        btn_previous.setOnClickListener(this);
        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        tv_number = (TextView) view.findViewById(R.id.tv_number);

        et_keyword = (EditText) view.findViewById(R.id.et_keyword);
        btn_search = (Button) view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);

        ll_update = (LinearLayout) view.findViewById(R.id.ll_update);

        //初始化提示框
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT
                ,LinearLayout.LayoutParams.MATCH_PARENT,R.layout.dialog_image
                ,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        imageView = (ImageView) dialog.findViewById(R.id.iv_img);


        //进行网络请求并解析
        //getData();
        //未搜索前上一页下一页隐藏
        ll_update.setVisibility(View.GONE);


        //点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //解析图片
                PicassoUtils.loadImageView(mListUrl.get(i),imageView);
                dialog.show();
            }
        });
        //长按保存
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(new String[]{getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imageView.setDrawingCacheEnabled(true);
                        Bitmap imageBitmap = imageView.getDrawingCache();
                        if (imageBitmap != null) {
                            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //没有授权
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            } else {
                                //已经授权
                                new SaveImageTask().execute(imageBitmap);
                            }
                        }
                    }
                });
                builder.show();

                return true;
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_previous:
                if(girl_flag){
                    if(page > 1){
                        page--;
                        tv_number.setText("第" + page + "页");
                        getGirl();
                    }else {
                        Toast.makeText(getActivity(), "已经是第一页！", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (page > 1) {
                        page--;
                        tv_number.setText("第" + page + "页");
                        getData();
                    } else {
                        Toast.makeText(getActivity(), "已经是第一页！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_next:
                if(girl_flag){
                    page++;
                    tv_number.setText("第" + page + "页");
                    getGirl();
                } else {
                    if (more_flag == 1) {
                        page++;
                        tv_number.setText("第" + page + "页");
                        getData();
                    } else {
                        Toast.makeText(getActivity(), "已经是最后一页！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_search:
                //页数变成1
                page = 1;
                tv_number.setText("第"+ page +"页");
                //获取输入框内容
                keyword = et_keyword.getText().toString().trim();
                //清空输入框
                et_keyword.setText("");
                //判断是否进入彩蛋
                if(keyword.equals("fsnb"))
                {
                    girl_flag = true;
                    //解析
                    getGirl();

                } else {
                    girl_flag = false;
                    //表情包
                    //进行网络请求并解析
                    getData();
                }

                //显示上一页下一页布局
                ll_update.setVisibility(View.VISIBLE);
            default:
                break;

        }
    }

    //进行网络请求并解析
    private void getData() {
        String url = "https://www.doutula.com/api/search?keyword="+ keyword +"&mime=0&page=" + page;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //L.i("Json" + t);
                parsingJson(t);
            }
        });
    }

    //网络请求妹子
    private void getGirl() {
        String url = "https://gank.io/api/data/%E7%A6%8F%E5%88%A9/21/" + page;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                pasingGirl(t);
            }
        });
    }

    private void pasingGirl(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray results = jsonObject.getJSONArray("results");

            mList.clear();
            mListUrl.clear();

            for(int i= 0; i < results.length(); i++) {
                JSONObject item = (JSONObject) results.get(i);
                String url = item.getString("url");
                mListUrl.add(url);
                EmotionData emotionData = new EmotionData();
                emotionData.setUrl(url);
                mList.add(emotionData);
                mAdapter = new GridAdapter(getActivity(),mList);
                mGridView.setAdapter(mAdapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject data = jsonObject.getJSONObject("data");
            more_flag = data.getInt("more");
            JSONArray list = data.getJSONArray("list");

            mList.clear();
            mListUrl.clear();

            for(int i= 0; i < list.length(); i++) {
                JSONObject item = (JSONObject) list.get(i);
                String url = item.getString("image_url");
                mListUrl.add(url);
                EmotionData emotionData = new EmotionData();
                emotionData.setUrl(url);
                mList.add(emotionData);
            }
            mAdapter = new GridAdapter(getActivity(),mList);
            mGridView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class SaveImageTask extends AsyncTask<Bitmap, Void, String> {
        @Override
        protected String doInBackground(Bitmap... params) {
            String result = getResources().getString(R.string.save_picture_failed);
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();

                File file = new File(sdcard + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }

                File imageFile = new File(file.getAbsolutePath(),new Date().getTime()+".jpg");
                FileOutputStream outStream = null;
                outStream = new FileOutputStream(imageFile);
                Bitmap image = params[0];
                image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                result = getResources().getString(R.string.save_picture_success,  file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();

            imageView.setDrawingCacheEnabled(false);
        }
    }
}

