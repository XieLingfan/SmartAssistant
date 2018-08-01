package com.example.xielingfan.smartassistant.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.xielingfan.smartassistant.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

//生产二维码


public class QrCodeActivity extends BaseActivity {

    //我的二维码
    private ImageView iv_qr_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        initView();
    }

    //初始化view
    private void initView() {

        iv_qr_code = (ImageView) findViewById(R.id.iv_qr_code);
        //屏幕的宽
        int width = getResources().getDisplayMetrics().widthPixels;

        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("我是智能助手", width/2,
                width/2, BitmapFactory.decodeResource(getResources(), R.mipmap.login_img));
        iv_qr_code.setImageBitmap(qrCodeBitmap);
    }

}