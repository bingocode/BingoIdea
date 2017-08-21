package com.whu.bingo.bingoidea.utils;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.whu.bingo.bingoidea.glides.GlideApp;


/**
 * Created by zengbin on 2017/8/21.
 */

public class ImageUtil {
    public static  void loadurlimage(Context context, String url, ImageView imageView){
        GlideApp.with(context).load(url).centerCrop().into(imageView);
    }
}
