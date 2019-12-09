package com.whu.zengbin.mutiview.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * 创建时间: 2019/12/09 16:07 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class DensityUtil {

  /**
   * Don't let anyone instantiate this class.
   */
  private DensityUtil() {
    throw new IllegalStateException("Do not need instantiate!");
  }

  /**
   * dip转换px
   *
   * @param context 上下文
   * @param dpValue dip值
   * @return px值
   */
  public static int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  /**
   * sp转pd
   *
   * @param context 上下文
   * @param spValue sp值
   * @return px值
   */
  public static int sp2px(Context context, float spValue) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
        context.getResources().getDisplayMetrics());
  }

  /**
   * px转换dip
   *
   * @param context 上下文
   * @param pxValue px值
   * @return dip值
   */
  public static int px2dip(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }
}
