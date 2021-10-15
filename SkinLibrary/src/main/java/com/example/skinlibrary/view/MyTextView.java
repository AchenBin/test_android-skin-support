package com.example.skinlibrary.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;
import skin.support.widget.SkinCompatTextHelper;
import skin.support.widget.SkinCompatTextView;

public class MyTextView extends SkinCompatTextView implements SkinCompatSupportable {
    int colorId = SkinCompatHelper.INVALID_ID;
    private SkinCompatTextHelper mTextHelper;

    public MyTextView(Context context) {
        super(context);
    }
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextHelper = SkinCompatTextHelper.create(this);
        mTextHelper.loadFromAttributes(attrs,defStyleAttr);

//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinTextAppearance, defStyleAttr, 0);
//        colorId = typedArray.getResourceId(R.styleable.SkinTextAppearance_android_textColor,SkinCompatHelper.INVALID_ID);
//        setTextColor(getResources().getColor(colorId,null));
    }

    public void setTextColorResource(@ColorRes int colorId) {
        this.colorId = colorId;
        colorId = SkinCompatHelper.checkResourceId(colorId);
        if(colorId != SkinCompatHelper.INVALID_ID){
            setTextColor(SkinCompatResources.getColor(getContext(),colorId));
        }
    }

    @Override
    public void applySkin() {
        super.applySkin();
        setTextColorResource(colorId);
    }
}
