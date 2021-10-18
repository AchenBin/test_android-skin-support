package com.example.module11;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
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
//        my_text.setBackgroundResource(R.color.colorPrimaryDark);
//        my_text.setTextSizeResource(R.dimen.btn_height);
//        my_text.setTextResource(R.string.app_name);
    }


    public void onChange(View view) {
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("skin","");
        getContentResolver().update(Uri.parse("content://com.sv.theme.ThemeProvider"),contentValues1,null,null);

    }
}
