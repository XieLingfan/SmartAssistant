package com.example.xielingfan.smartassistant.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.adapter.ChatListAdapter;
import com.example.xielingfan.smartassistant.adapter.CourierAdapter;
import com.example.xielingfan.smartassistant.entity.ChatListData;
import com.example.xielingfan.smartassistant.utils.L;
import com.example.xielingfan.smartassistant.utils.ShareUtils;
import com.example.xielingfan.smartassistant.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssistantFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;
    private EditText et_text;
    private Button btn_send;

    private ChatListAdapter adapter;

    private List<ChatListData> mList = new ArrayList<>();

    //TTS
    private SpeechSynthesizer mTts ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assistant,null);
        findView(view);
        return view;
    }

    //初始化View
    private void findView(View view) {


        //创建对象
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(),null);
        //.参数设置
        mTts.setParameter( SpeechConstant.VOICE_NAME,"xiaoyan");  //发音人
        mTts.setParameter( SpeechConstant.SPEED,"50");  //语速
        mTts.setParameter( SpeechConstant.VOLUME,"80"); //音量
        mTts.setParameter( SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD );//设置云端



        mChatListView = (ListView) view.findViewById(R.id.mChatListView);
        et_text = (EditText) view.findViewById(R.id.et_text);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);


        //设置适配器
        adapter = new ChatListAdapter(getActivity(),mList);
        mChatListView.setAdapter(adapter);

        //去分割线
        mChatListView.setDivider(null);
        addLeftItem("你好，我是小优。");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                /**
                 * 1获取输入框内容
                 * 2.判断是否为空
                 * 3.判断长度不能大于30
                 * 4.清空输入框
                 * 5.添加输入内容到右边
                 * 6.发送给机器人请求返回内容
                 * 7.拿到机器人返回值添加在左边
                 */

                //1获取输入框内容
                String text = et_text.getText().toString();
                //2.判断是否为空
                if(!TextUtils.isEmpty(text)) {
                    //3.判断长度不能大于30
                    if(text.length() > 30){
                        Toast.makeText(getActivity(), "输入长度超出限制!", Toast.LENGTH_SHORT).show();
                    } else {
                        //4.清空输入框
                        et_text.setText("");
                        //5.添加输入内容到右边
                        addRightItem(text);
                        //6.发送给机器人请求返回内容
                        String url = StaticClass.ROBOT_URL;
                        HttpParams params = new HttpParams();
                        params.put("key",StaticClass.ROBOT_KEY);
                        params.put("info",text);
                        RxVolley.post(url, params, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                //L.i(t);
                                parsingJson(t);
                            }
                        });
                    }
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    /**
     *  "reason":"成功的返回",
     *"result": /*根据code值的不同，返回的字段有所不同
     * {
     *
     * "code":100000
     * "text":"你好啊，希望你今天过的快乐"
     * }
     * @param t
     */

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            String text = jsonObject.getString("text");
            //添加到左边
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //添加左边文本
    private void addLeftItem(String text) {
        boolean isSpeak = ShareUtils.getBoolean(getActivity(),"isSpeak",false);
        if (isSpeak) {
            startSpeak(text);
        }

        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mList.add(data);

        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //添加右边文本
    private void addRightItem(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mList.add(data);

        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //开始说话
    private void startSpeak(String text) {
        mTts.startSpeaking(text,mSynListener);
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener(){
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {}
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}
        //开始播放
        public void onSpeakBegin() {}
        //暂停播放
        public void onSpeakPaused() {}
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {}
        //恢复播放回调接口
        public void onSpeakResumed() {}
        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {}
    };
}
