package com.example.skinlibrary;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.util.List;

import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;
import skin.support.utils.SkinPreference;

public class SkinApp extends Application {
    public static final String TAG = "换肤App";
    public static final int SKIN_LOADER_STRATEGY = CustomSDCardLoader.SKIN_LOADER_STRATEGY_SDCARD;  //加载策略，自定义sd
//    public static final int SKIN_LOADER_STRATEGY = SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS;  //加载策略，assets

    Uri uri;
    @Override
    public void onCreate() {
        Log.e(TAG,getPackageName()+"onCreate");
        super.onCreate();
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
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                Log.e(TAG,getPackageName()+"查询");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                while(cursor.moveToNext()){
                    String skinName = cursor.getString(0);
//                    if(skinName.equals(SkinPreference.getInstance().getSkinName())){  //如果一开始没有loadSkin，那么是不能进行这个判断的。因为虽然已保存，但还未初始化切换好
//                        Log.i(TAG,getPackageName()+"当前已是"+skinName+"主题，无需改变");
//                    }else
                    if(skinName.equals("")){
                        Log.i(TAG,getPackageName()+"准备切换默认主题："+skinName);
                        SkinCompatManager.getInstance().restoreDefaultTheme();
                    }else{
                        Log.i(TAG,getPackageName()+"准备切换主题："+skinName);
                        SkinCompatManager.getInstance().loadSkin(skinName,SKIN_LOADER_STRATEGY);
                    }
                    break;
                }
                cursor.close();
//            }
//        }.start();
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    //很大可能不会调用的
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG,getPackageName()+"application终止");
    }


}
