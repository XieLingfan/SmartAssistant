package com.example.xielingfan.smartassistant.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xielingfan.smartassistant.R;
import com.example.xielingfan.smartassistant.entity.MyUser;
import com.example.xielingfan.smartassistant.ui.CourierActivity;
import com.example.xielingfan.smartassistant.ui.ForgetPasswordActivity;
import com.example.xielingfan.smartassistant.ui.LoginActivity;
import com.example.xielingfan.smartassistant.ui.PhoneActivity;
import com.example.xielingfan.smartassistant.utils.L;
import com.example.xielingfan.smartassistant.utils.UtilTools;
import com.example.xielingfan.smartassistant.view.CustomDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;
    //更新按钮
    private Button btn_update_ok;
    //圆形头像
    private CircleImageView profile_image;
    //提示框
    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    //快递查询
    private TextView tv_courier ;
    //归属地查询
    private TextView tv_phone;
    //修改密码
    private Button btn_update_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,null);

        findView(view);
        return view;
    }
    //初始化VIEW
    public void findView(View view) {
        btn_exit_user = (Button) view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user = (TextView) view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);

        tv_courier = (TextView)  view.findViewById(R.id.tv_courier);
        tv_courier.setOnClickListener(this);

        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_phone.setOnClickListener(this);

        btn_update_password = (Button) view.findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);

        et_username = (EditText) view.findViewById(R.id.et_username);
        et_sex = (EditText) view.findViewById(R.id.et_sex);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_desc = (EditText) view.findViewById(R.id.et_desc);

        btn_update_ok = (Button) view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);

        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        //读取图片
        UtilTools.getImageToShare(getContext(),profile_image);



        dialog = new CustomDialog(getActivity(),0,0,R.layout.dialog_photo,R.style.theme_dialog, Gravity.BOTTOM,R.style.pop_anim_style);


        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        //默认不可输入
        setEnabled(false);

        //设置具体值

        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_sex.setText(userInfo.isSex() ? "男" : "女");
        et_age.setText(userInfo.getAge() + "");
        et_desc.setText(userInfo.getDesc());




    }

    //控制焦点
    private void setEnabled(boolean is) {
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_desc.setEnabled(is);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //退出登录
            case R.id.btn_exit_user:
                //退出登录
                //清除缓存用户对象
                MyUser.logOut();
                // 现在的currentUser是null了
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            //编辑资料
            case R.id.edit_user:
                setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_update_ok :
                //拿到输入框的值
                String username = et_username.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String sex = et_sex.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                //判断是否为空(前三个)
                if(!TextUtils.isEmpty(username) &&!TextUtils.isEmpty(age) &&
                        !TextUtils.isEmpty(sex) ){
                        //更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    if(sex.equals("男") ||sex.equals("女")) {
                        switch (sex){
                            case "男":
                                user.setSex(true);
                                break;
                            case "女":
                                user.setSex(false);
                                break;
                        }

                        //简介
                        if(!TextUtils.isEmpty(desc)){
                            user.setDesc(desc);
                        } else {
                            user.setDesc("这个人很懒，什么都没有留下！");
                        }

                        BmobUser bmobUser = BmobUser.getCurrentUser();
                        user.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null) {
                                    setEnabled(false);
                                    btn_update_ok.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "性别输入错误！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.should_not_null, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(),CourierActivity.class));
                break;
            case R.id.tv_phone:
                startActivity(new Intent(getActivity(),PhoneActivity.class));
                break;
            case R.id.btn_update_password:
                startActivity(new Intent(getActivity(),ForgetPasswordActivity.class));
                break;

        }
    }



    private static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int IMAGE_REQUEST_CODE = 101;
    private static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;
    private Uri uritempFile = null;



    //跳转相机
    private void toCamera() {
        tempFile = new File(getActivity().getExternalCacheDir(),PHOTO_IMAGE_FILE_NAME);
        try {
            if(tempFile.exists()){
                tempFile.delete();
            }
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT >= 24) {
            uritempFile = FileProvider.getUriForFile(getActivity(),
                    "com.example.xielingfan.smartassistant.fileprovider",tempFile);
        } else {
            uritempFile = Uri.fromFile(tempFile);
        }

        Intent intent  = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uritempFile);
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相机数据
                case CAMERA_REQUEST_CODE:

                    Bitmap bitmap1 = null;
                    try {
                        bitmap1 = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uritempFile));
                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap1, null,null));
                        startPhotoZoom(uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    profile_image.setImageBitmap(bitmap1);

                    //startPhotoZoom(uritempFile);
                    break;
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case RESULT_REQUEST_CODE:
                    try {
                        L.i(uritempFile +"");
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uritempFile));
                        profile_image.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;


            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void startPhotoZoom(Uri uri) {
        L.i(uri + "");
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);


        //intent.putExtra("return-data", true);
        //裁剪后的图片Uri路径，uritempFile为Uri类变量
        uritempFile = Uri.parse("file://" + "" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());


        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存
        UtilTools.putImageToShare(getContext(),profile_image);

    }
}
