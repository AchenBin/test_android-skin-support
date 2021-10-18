package com.example.skinlibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.StringRes;

import com.example.skinlibrary.R;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;
import skin.support.widget.SkinCompatTextHelper;
import skin.support.widget.SkinCompatTextView;

public class MyTextView extends SkinCompatTextView implements SkinCompatSupportable {
    private int colorId = SkinCompatHelper.INVALID_ID;
    private int textSizeId = SkinCompatHelper.INVALID_ID;
    private int textId = SkinCompatHelper.INVALID_ID;
//    private SkinCompatTextHelper mTextHelper;

    public MyTextView(Context context) {
        super(context);
    }
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        mTextHelper = SkinCompatTextHelper.create(this);
//        mTextHelper.loadFromAttributes(attrs,defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView, defStyleAttr, 0);
        textSizeId = typedArray.getResourceId(R.styleable.MyTextView_textSize,0);
        setTextSizeResource(textSizeId);
//        colorId = typedArray.getResourceId(R.styleable.SkinTextAppearance_android_textColor,SkinCompatHelper.INVALID_ID);
//        setTextColor(getResources().getColor(colorId,null));
    }

    //设置可换肤字体颜色
    public void setTextColorResource(@ColorRes int colorId) {
        this.colorId = colorId;
        colorId = SkinCompatHelper.checkResourceId(colorId);    //验证id是否合法，不合法返回INVALID_ID
        if(colorId != SkinCompatHelper.INVALID_ID){
            setTextColor(SkinCompatResources.getColor(getContext(),colorId));
        }
    }
    //设置可换肤字体大小，暂时无法使用xml设置
    public void setTextSizeResource(@DimenRes int sizeId) {
        this.textSizeId = sizeId;
        sizeId = SkinCompatResources.getInstance().getTargetResId(getContext(),sizeId);
        sizeId = SkinCompatHelper.checkResourceId(sizeId);
        if(sizeId != SkinCompatHelper.INVALID_ID){
            setTextSize(SkinCompatResources.getInstance().getSkinResources().getDimension(sizeId));
        }
    }
    //设置可换肤文本，暂时无法使用xml设置
    public void setTextResource(@StringRes int textId) {
        this.textId = textId;
        textId = SkinCompatHelper.checkResourceId(textId);
        if(textId != SkinCompatHelper.INVALID_ID){
            setText(SkinCompatResources.getInstance().getSkinResources().getString(textId));
        }
    }

    @Override
    public void applySkin() {
        super.applySkin();
        setTextColorResource(colorId);
        setTextSizeResource(textSizeId);
        setTextResource(textId);
    }
}
