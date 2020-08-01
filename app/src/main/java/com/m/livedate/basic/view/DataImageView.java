package com.m.livedate.basic.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.m.livedate.R;


@SuppressLint("AppCompatCustomView")
public class DataImageView extends ImageView {

    public DataImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @BindingAdapter(value = {"imageUrl"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).placeholder(R.drawable.ic_launcher).into(imageView);
    }


}
