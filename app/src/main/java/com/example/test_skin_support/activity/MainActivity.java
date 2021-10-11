package com.example.test_skin_support.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skinlibrary.SkinBaseActivity;
import com.example.test_skin_support.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import skin.support.SkinCompatManager;
import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatUserThemeManager;
import skin.support.content.res.SkinCompatVectorResources;
import skin.support.utils.SkinPreference;
import skin.support.widget.SkinCompatBackgroundHelper;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;
import skin.support.widget.SkinCompatTextHelper;

public class MainActivity extends SkinBaseActivity implements View.OnClickListener , CompoundButton.OnCheckedChangeListener,SkinCompatSupportable{
    public static final String TAG = "换肤";
    LinearLayout layout;
    Button btn_default;
    Button btn_change;
    Button btn_jump;
    TextView tv;
    ImageView img;
    ImageView imageView;
    RadioButton radio_change;
    RadioButton radio_change3;
    RadioButton radio_default;

    SkinCompatTextHelper textHelper;
    int mDropDownBackgroundResId;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_change = findViewById(R.id.btn_change);
        btn_default = findViewById(R.id.btn_default);
        btn_jump = findViewById(R.id.btn_jump);
        tv = findViewById(R.id.tv);
        img = findViewById(R.id.img);
        layout = findViewById(R.id.layout);
        radio_change = findViewById(R.id.radio_change);
        radio_change3 = findViewById(R.id.radio_change3);
        radio_default = findViewById(R.id.radio_default);

        if(radio_change!= null && radio_default != null){
            if(SkinPreference.getInstance().getSkinName().equals("")){
                radio_default.setChecked(true);
            }else if(SkinPreference.getInstance().getSkinName().equals("overlay2.skin")){
                radio_change.setChecked(true);
            }else{
                radio_change3.setChecked(true);
            }
        }
        radio_change.setOnCheckedChangeListener(this);
        radio_default.setOnCheckedChangeListener(this);
        radio_change3.setOnCheckedChangeListener(this);


        btn_change.setOnClickListener(this);
        btn_default.setOnClickListener(this);
        btn_jump.setOnClickListener(this);

//        tv.setTextAppearance(R.style.myTextStyle);
//        tv.setTextColor(SkinCompatResources.getColor(this,R.color.colorBg));
//        tv.setTextColor(R.color.colorAccent);
//        tv.setBackgroundColor(SkinCompatResources.getInstance().getColor(this,R.color.colorAccent));
        tv.setBackgroundResource(R.color.colorAccent);
//        tv.setBackgroundColor(getColor(R.color.colorAccent));
        textHelper = SkinCompatTextHelper.create(tv);
        mDropDownBackgroundResId = SkinCompatTextHelper.checkResourceId(R.color.colorAccent);
        textHelper.applySkin();

        layout.setBackgroundResource(R.color.colorBg);

        img.setImageResource(R.mipmap.album);
        //不行
//        int id = R.mipmap.album;
//        Drawable drawable = SkinCompatResources.getDrawable(this,id);
//        img.setImageDrawable(drawable);



        btn_jump.setBackgroundResource(R.drawable.btn_bg);

//        ImageView imageView = new ImageView(this);
        imageView = (ImageView) View.inflate(this,R.layout.add_img,null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = 100;
        layoutParams.height = 100;
        layoutParams.x = 400;
        layoutParams.y = 200;
        layout.addView(imageView,layoutParams);
        imageView.setImageResource(R.mipmap.album);
    }

    @Override
    public void onClick(View view) {
        Log.e(TAG,"点击按钮");
        switch (view.getId()){
            case R.id.btn_change:
//                SkinCompatManager.getInstance().loadSkin("overlay2.skin", new SkinCompatManager.SkinLoaderListener() {
//                    @Override
//                    public void onStart() {
//                        Log.e("换肤","开始");
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        Log.e(TAG,"成功"+ SkinCompatResources.getInstance().getSkinPkgName());
//                        Log.e(TAG,"成功"+ SkinCompatManager.getInstance().getCurSkinName());
//                        Log.e(TAG,"成功"+ SkinPreference.getInstance().getSkinName());
//                    }
//
//                    @Override
//                    public void onFailed(String errMsg) {
//                        Log.e(TAG,"失败"+errMsg);
//
//                    }
//                }
//                ,SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);


//                //存主题值
//                SharedPreferences sharedPreferences = getSharedPreferences("skin",MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("skin","overlay2.skin").commit();
//                //通知provider变化
//                getContentResolver().notifyChange(Uri.parse("content://"+ThemeProvider.Uri+"/query"), null);

                ContentValues contentValues = new ContentValues();
                contentValues.put("skin","overlay2.skin");
                break;
            case R.id.btn_default:
//                SkinCompatManager.getInstance().restoreDefaultTheme();
//
//                SharedPreferences sharedPreferences1 = getSharedPreferences("skin",MODE_PRIVATE);
//                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
//                editor1.putString("skin","").commit();
//                //通知provider变化
//                getContentResolver().notifyChange(Uri.parse("content://"+ThemeProvider.Uri+"/query"), null);

                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("skin","");
                getContentResolver().update(Uri.parse("content://"+ThemeProvider.Uri),contentValues1,null,null);
                break;
            case R.id.btn_jump:
//                Intent intent = new Intent(this,SecActivity.class);
//                startActivity(intent);
                finish();
                break;
        }
//        Toast.makeText(this, "点击切换", Toast.LENGTH_SHORT).show();
//        Log.e(TAG,"保存："+getSharedPreferences("skin",MODE_PRIVATE).getString("skin",""));
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }

    @Override
    public void applySkin() {
//        Log.i(TAG,getPackageName()+"：applySkin，包名："+SkinCompatResources.getInstance().getSkinPkgName());
        if(tv != null){
//            tv.setTextColor(SkinCompatResources.getColor(this,R.color.colorBg));
//            tv.setTextColor(getResources().getColor(R.color.colorPrimary,null));
//            tv.setBackgroundColor(SkinCompatResources.getColor(this,R.color.colorAccent));
        }
//        if(img != null){
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) img.getLayoutParams();
//            layoutParams.leftMargin += 40;
//            layoutParams.height += 20;
//            layoutParams.weight += 20;
//            img.setLayoutParams(layoutParams);
//        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        ContentValues contentValues = new ContentValues();
        if(b){
            switch (compoundButton.getId()){
                case R.id.radio_change3:
                    contentValues.put("skin","overlay3.skin");
                    getContentResolver().update(Uri.parse("content://"+ThemeProvider.Uri),contentValues,null,null);
//                    //存主题值
//                    SharedPreferences sharedPreferences3 = getSharedPreferences("skin",MODE_PRIVATE);
//                    SharedPreferences.Editor editor3 = sharedPreferences3.edit();
//                    editor3.putString("skin","overlay3.skin").commit();
//                    //通知provider变化
//                    getContentResolver().notifyChange(Uri.parse("content://"+ThemeProvider.Uri+"/query"), null);
                    break;
                case R.id.radio_change:
//                    //存主题值
//                    SharedPreferences sharedPreferences = getSharedPreferences("skin",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("skin","overlay2.skin").commit();
//                    //通知provider变化
//                    getContentResolver().notifyChange(Uri.parse("content://"+ThemeProvider.Uri+"/query"), null);
                    contentValues.put("skin","overlay2.skin");
                    getContentResolver().update(Uri.parse("content://"+ThemeProvider.Uri),contentValues,null,null);
                    break;
                case R.id.radio_default:
//                    SharedPreferences sharedPreferences1 = getSharedPreferences("skin",MODE_PRIVATE);
//                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
//                    editor1.putString("skin","").commit();
//                    //通知provider变化
//                    getContentResolver().notifyChange(Uri.parse("content://"+ThemeProvider.Uri+"/query"), null);
                    contentValues.put("skin","");
                    getContentResolver().update(Uri.parse("content://"+ThemeProvider.Uri),contentValues,null,null);
                    break;
            }
        }
    }

}