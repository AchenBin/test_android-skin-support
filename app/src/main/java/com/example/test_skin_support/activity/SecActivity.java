package com.example.test_skin_support.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
//import androidx.appcompat.app.SkinAppCompatDelegateImpl;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.test_skin_support.R;

public class SecActivity extends BaseActivity {
    TextView tv;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        tv = findViewById(R.id.tv);
//        tv.setTextColor(R.color.colorAccent);
    }


}