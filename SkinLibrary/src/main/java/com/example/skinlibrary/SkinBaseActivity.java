package com.example.skinlibrary;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatSupportable;

public class SkinBaseActivity extends AppCompatActivity implements SkinCompatSupportable {
    public static final String TAG = "换肤SkinBaseActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);
    }

    @Override
    public void applySkin() {
        Log.i(TAG,getPackageName()+"：applySkin，包名："+ SkinCompatResources.getInstance().getSkinPkgName());
    }
}