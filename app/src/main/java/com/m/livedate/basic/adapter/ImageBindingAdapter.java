package com.m.livedate.basic.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * createDate:2020/7/16
 *
 * @author:spc describe：自定义图片加载类
 */
public class ImageBindingAdapter {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter({"app:imageUrl", "app:placeHolder"})
    public static void loadImage(ImageView imageView, String url, Drawable holderDrawable) {
        Glide.with(imageView.getContext()).load(url).placeholder(holderDrawable).into(imageView);
    }
}
