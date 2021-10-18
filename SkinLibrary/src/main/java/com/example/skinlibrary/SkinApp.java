package com.example.skinlibrary;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;
import skin.support.utils.SkinPreference;

import static skin.support.SkinCompatManager.SKIN_LOADER_STRATEGY_NONE;

public class SkinApp extends Application {
    public static final String TAG = "换肤App";
    public static final int SKIN_LOADER_STRATEGY = CustomSDCardLoader.SKIN_LOADER_STRATEGY_SDCARD;  //加载策略，自定义sd
//    public static final int SKIN_LOADER_STRATEGY = SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS;  //加载策略，assets

    private volatile List<Activity> activityList = new ArrayList<>();
    public volatile boolean needUpdateOnResume = false; //标记是否module处于前台时再加载资源，但测试显示，加载速度不够快，会闪现切换

    public synchronized void addActivity(Activity activity){
        if(!activityList.contains(activity)){
            activityList.add(activity);
            Log.i(TAG,getPackageName()+"：addActivity后，数量"+activityList.size());
        }
    }

    public synchronized void removeActivity(Activity activity){
        if(activityList.contains(activity)){
            activityList.remove(activity);
            Log.i(TAG,getPackageName()+"：removeActivity后，数量"+activityList.size());
        }
    }

    Uri uri;
    @Override
    public void onCreate() {
        Log.e(TAG,getPackageName()+"onCreate");
        super.onCreate();

        needUpdateOnResume = false;

        SkinCompatManager.withoutActivity(this)
                .addStrategy(new CustomSDCardLoader())
                .addInflater(new SkinAppCompatViewInflater())           // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
//                .loadSkin();  //不使用默认初始化加载，容易造成不一致时的闪屏切换
        ;
        //无关
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);    //Android5.0以下矢量图片兼容

        uri = Uri.parse("content://com.sv.theme.ThemeProvider");
        //启动先查询是否要更换
        changeSkin(uri);
        //监听变化
        getContentResolver().registerContentObserver(uri, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                Log.i(TAG,getPackageName()+"收到provider变化");
                super.onChange(selfChange);
                changeSkin(uri);
            }
        });
    }


    private void changeSkin(final Uri uri) {
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        while(cursor.moveToNext()){
            String skinName = cursor.getString(0);
            changeSkinByName(skinName);
//            if(activityList.size()>0){    //在前台
//                changeSkinByName(skinName);
//            }else{                              //在后台
//                delayChangeSkinByName(skinName);
//            }
            break;
        }
        cursor.close();
    }

    /**
     * 直接切换主题
     * @param skinName
     */
    private void changeSkinByName(String skinName){
        needUpdateOnResume = false;
        Log.i(TAG,getPackageName()+"准备切换默认主题："+skinName);
        if(skinName.equals("")){
            SkinCompatManager.getInstance().restoreDefaultTheme();
        }else{
            SkinCompatManager.getInstance().loadSkin(skinName,SKIN_LOADER_STRATEGY);
        }
    }

    /**
     * 先保存并设置需要切换flag，等待该模块的activity,OnResume时再切换
     * @param skinName
     */
    private void delayChangeSkinByName(String skinName){
        needUpdateOnResume = true;
        Log.i(TAG,getPackageName()+"不在前台，等待resume再切换："+skinName);
        if(skinName.equals("")){
            SkinPreference.getInstance().setSkinName(skinName).setSkinStrategy(SKIN_LOADER_STRATEGY_NONE).commitEditor();
        }else{
            SkinPreference.getInstance().setSkinName(skinName).setSkinStrategy(SKIN_LOADER_STRATEGY).commitEditor();
        }
    }

    //很大可能不会调用的
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG,getPackageName()+"application终止");
    }


}
