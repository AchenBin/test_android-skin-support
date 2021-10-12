package com.example.skinlibrary;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;
import androidx.core.view.LayoutInflaterCompat;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCompatActivity;
import skin.support.app.SkinCompatDelegate;
import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatThemeUtils;
import skin.support.content.res.SkinCompatVectorResources;
import skin.support.observe.SkinObservable;
import skin.support.observe.SkinObserver;
import skin.support.widget.SkinCompatSupportable;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;
import static skin.support.widget.SkinCompatHelper.checkResourceId;

public class SkinBaseActivity extends AppCompatActivity implements SkinCompatSupportable{
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
        Log.i(TAG,getPackageName()+":"+getClass().getSimpleName()+"：applySkin，包名："+ SkinCompatResources.getInstance().getSkinPkgName());
    }
}