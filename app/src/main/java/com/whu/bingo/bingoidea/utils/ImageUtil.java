package com.whu.bingo.bingoidea.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.io.File;

/**
 * Created by zengbin on 2017/8/21.
 */

public class ImageUtil {
  public static void loadurlimage(Context context, String url, ImageView imageView) {
    Glide.with(context).load(url).into(imageView);
  }

  public static void loadCenterCrop(@NonNull Context context, File file,
      @DrawableRes int placeholderResId, @DrawableRes int errorResId, @NonNull ImageView target) {
    if (file == null) {
      target.setImageResource(errorResId);
    } else {
      Glide.with(context)
          .load(file)
          .apply(RequestOptions.centerCropTransform()
              .error(context.getResources().getDrawable(errorResId))
              .placeholder(context.getResources().getDrawable(placeholderResId)))
          .into(target);
    }
  }

}
