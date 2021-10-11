package com.example.test_skin_support.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.example.test_skin_support.R;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatSupportable;
import skin.support.widget.SkinCompatTextHelper;
import skin.support.widget.SkinCompatTextView;

import static android.content.pm.PackageInstaller.SessionInfo.INVALID_ID;

public class MyView extends LinearLayout implements SkinCompatSupportable {
    SkinCompatTextView tv;
    ImageView img;
    private SkinCompatTextHelper mTextHelper;
    Context context;
    private int mDropDownBackgroundResId = INVALID_ID;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    int color;
    float dimension;
    int id;

    public void init(Context context,AttributeSet attrs){
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_my,null,false);
        addView(view);
        img = view.findViewById(R.id.img_m);
        tv = view.findViewById(R.id.tv_m);
        mTextHelper = SkinCompatTextHelper.create(tv);
//        mTextHelper.loadFromAttributes(attrs,0);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyView);
        //不行
//        color = typedArray.getColor(R.styleable.MyView_firstColor,0);
//        tv.setTextColor(color);

        id = typedArray.getResourceId(R.styleable.MyView_firstColor,0);
        color = SkinCompatResources.getColor(context,id);
        tv.setTextColor(color);

//        id = typedArray.getResourceId(R.styleable.MyView_firstTextAppearance,0);
//        tv.setTextAppearance(
//                typedArray.getResourceId(R.styleable.MyView_firstTextAppearance,0)
//        );


//        dimension = typedArray.getDimension(R.styleable.MyView_firstTextSize, 0.0f);
//        tv.setTextSize(dimension);


        img.setBackgroundResource(typedArray.getResourceId(R.styleable.MyView_imgSrc,R.mipmap.ic_launcher));
//        typedArray.getDrawable()
    }


    @Override
    public void applySkin() {
        Log.e("换肤MyView","applySkin");
        if(mTextHelper != null){
            mTextHelper.applySkin();
            //todo 不行！！！！！
//            tv.setTextColor(SkinCompatResources.getInstance().getColor(context,id));

        }
       if(tv != null){
           color = SkinCompatResources.getColor(context,id);
           tv.setTextColor(color);
//           tv.setTextSize(SkinCompatResources.getInstance().getSkinResources().getDimension(R.dimen.btn_height));
           tv.applySkin();

       }
    }
}
