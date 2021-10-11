package com.example.test_skin_support.activity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.Provider;
import java.util.Map;

import skin.support.utils.SkinPreference;

public class ThemeProvider extends ContentProvider {
    public static final String TAG = "换肤ThemeProvider";
    public static final String Uri = "com.sv.theme.ThemeProvider";
    SharedPreferences skinPreference;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int RESULT_CODE_QUERY = 0;  //查询返回码
    private static final int RESULT_CODE_UPDATE = 1; //更新返回码

    static{
        uriMatcher.addURI(Uri,"query",RESULT_CODE_QUERY);       //注册匹配查询uri
        uriMatcher.addURI(Uri,"update",RESULT_CODE_UPDATE);     //注册匹配更新uri
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        skinPreference = getContext().getSharedPreferences("skin", Context.MODE_PRIVATE);
        if(skinPreference == null) {
            Log.e("换肤","skinPreference == null");
            return null;
        }
        final String skinName = skinPreference.getString("skin","");
        if(skinName == null) {
            Log.e("换肤", "skinName == null");
            return null;
        }
        MatrixCursor cursor = new MatrixCursor(new String[]{"skin"});
        cursor.addRow(new String[]{skinName});
        return (Cursor) cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    //预留给其他可能要改变主题的模块
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        String skinName = contentValues.getAsString("skin");
        skinPreference = getContext().getSharedPreferences("skin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = skinPreference.edit();
        editor.putString("skin",skinName).commit();
        Log.i(TAG,"更新保存皮肤："+skinName);
        getContext().getContentResolver().notifyChange(uri,null);
        return 0;
    }


}
