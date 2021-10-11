package com.example.test_skin_support;

import android.app.Application;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.skinlibrary.SkinApp;

//import skin.support.SkinCompatManager;
//import skin.support.app.SkinAppCompatViewInflater;
//import skin.support.app.SkinCardViewInflater;
//import skin.support.constraint.app.SkinConstraintViewInflater;
//import skin.support.design.app.SkinMaterialViewInflater;
//import skin.support.utils.SkinPreference;

public class App extends SkinApp {
//    public static final String TAG = "换肤App";
//    Uri uri;
//    @Override
//    public void onCreate() {
//        Log.e(TAG,"App：onCreate");
//        super.onCreate();
//        SkinCompatManager.withoutActivity(this)
//                .addInflater(new SkinAppCompatViewInflater())           // 基础控件换肤初始化
//                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
//                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
//                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
//                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
//                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
//                .loadSkin();
//
//        //无关
////        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);    //Android5.0以下矢量图片兼容
//
//        uri = Uri.parse("content://com.sv.theme.ThemeProvider/query");
//        //启动先查询是否要更换
//        changeSkin(uri);
//
//        getContentResolver().registerContentObserver(uri, true, new ContentObserver(new Handler()) {
//            @Override
//            public void onChange(boolean selfChange) {
//                Log.e(TAG,"收到provider变化");
//                super.onChange(selfChange);
//                changeSkin(uri);
//            }
//        });
//    }
//
//
//    private void changeSkin(final Uri uri) {
//        Log.e(TAG,"查询");
//        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
//        while(cursor.moveToNext()){
//            String skinName = cursor.getString(0);
//            if(cursor.getString(0).equals(SkinPreference.getInstance().getSkinName())){
//                Log.e(TAG,"当前已是该主题，无需改变");
//            }else if(skinName.equals("")){
//                Log.e(TAG,"切换默认主题："+skinName);
//                SkinCompatManager.getInstance().restoreDefaultTheme();
//            }else{
//                Log.e(TAG,"切换主题："+skinName);
//                SkinCompatManager.getInstance().loadSkin(skinName,SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
//            }
//            break;
//        }
//        cursor.close();
//    }
//
//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        Log.e(TAG,"application终止");
//    }
}
