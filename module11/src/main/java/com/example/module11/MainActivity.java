package com.example.module11;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.example.skinlibrary.SkinBaseActivity;
import com.example.skinlibrary.view.MyTextView;

public class MainActivity extends SkinBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyTextView my_text = findViewById(R.id.my_text);
        my_text.setTextColorResource(R.color.text_color_tip);
        my_text.setTextSizeResource(R.dimen.btn_height);
        my_text.setTextResource(R.string.app_name);

//        Settings.Secure.putString(getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD, "skin");
    }


    public void onChange(View view) {
//        ContentValues contentValues1 = new ContentValues();
//        contentValues1.put("skin","");
//        getContentResolver().update(Uri.parse("content://com.sv.theme.ThemeProvider"),contentValues1,null,null);
        Settings.System.putString(getContentResolver(),"com.sv.skin","");
    }

    public void onChange3(View view) {
//        ContentValues contentValues1 = new ContentValues();
//        contentValues1.put("skin","overlay3.skin");
//        getContentResolver().update(Uri.parse("content://com.sv.theme.ThemeProvider"),contentValues1,null,null);
        Settings.System.putString(getContentResolver(),"com.sv.skin","overlay3.skin");
    }

    public void onChange2(View view) {
//        ContentValues contentValues1 = new ContentValues();
//        contentValues1.put("skin","overlay2.skin");
//        getContentResolver().update(Uri.parse("content://com.sv.theme.ThemeProvider"),contentValues1,null,null);
        Settings.System.putString(getContentResolver(),"com.sv.skin","overlay2.skin");
    }
}
