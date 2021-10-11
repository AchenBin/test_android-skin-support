package com.example.skinlibrary;

import android.content.Context;
import android.util.Log;

import java.io.File;

import skin.support.load.SkinSDCardLoader;
import skin.support.utils.SkinFileUtils;

public class CustomSDCardLoader extends SkinSDCardLoader {
    public static final String TAG = "换肤CustomSDCardLoader";
    public static final int SKIN_LOADER_STRATEGY_SDCARD = Integer.MAX_VALUE;

    @Override
    protected String getSkinPath(Context context, String skinName) {
//        File file = new File(SkinFileUtils.getSkinDir(context), skinName);    //默认:/storage/emulated/0/Android/data/com.XXX.XXX/cache/skins/
        //将skin放在apk同目录
        File packageDir = new File(context.getPackageResourcePath());
        packageDir = new File(packageDir.getParent());
        File file = new File(packageDir.getParent(), skinName);
        String path = file.getAbsolutePath();
        Log.i(TAG,context.getPackageName()+":path = "+path);
        return path;
    }

    @Override
    public int getType() {
        return SKIN_LOADER_STRATEGY_SDCARD;
    }
}