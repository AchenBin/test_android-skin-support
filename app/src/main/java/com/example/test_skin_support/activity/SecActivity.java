package com.example.test_skin_support.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
//import androidx.appcompat.app.SkinAppCompatDelegateImpl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.skinlibrary.SkinBaseActivity;
import com.example.test_skin_support.R;

public class SecActivity extends SkinBaseActivity {
    TextView tv;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        tv = findViewById(R.id.tv);
    }


    public void onClick(View view) {
//        finish();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}