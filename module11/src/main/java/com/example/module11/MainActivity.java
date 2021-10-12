package com.example.module11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.skinlibrary.SkinBaseActivity;

import skin.support.widget.SkinCompatSupportable;

public class MainActivity extends SkinBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onChange(View view) {
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("skin","");
        getContentResolver().update(Uri.parse("content://com.sv.theme.ThemeProvider"),contentValues1,null,null);

    }
}
