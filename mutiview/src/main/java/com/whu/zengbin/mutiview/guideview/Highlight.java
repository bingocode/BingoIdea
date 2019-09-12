package com.whu.zengbin.mutiview.guideview;

import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * 创建时间: 2019/09/02 15:14 <br>
 * 作者: zengbin <br>
 * 描述: 需要高亮的部分
 */
public class Highlight {

  private View mHole;
  private int round;
  /**
   * 高亮相对view的padding
   */
  private int padding;
  private RectF rectF;

  public Highlight(@NonNull View mHole, int round, int padding) {
    this.mHole = mHole;
    this.round = round;
    this.padding = padding;
  }

  public int getRound() {
    return round;
  }

  public float getRadius() {
    if (mHole == null) {
      throw new IllegalArgumentException("the highlight view is null!");
    }
    return Math.max(mHole.getWidth() / 2, mHole.getHeight() / 2) + padding;
  }

  public RectF getRectF(View target) {
    if (mHole == null) {
      throw new IllegalArgumentException("the highlight view is null!");
    }
    if (rectF == null) {
      rectF = fetchLocation(target);
    }
    return rectF;
  }

  private RectF fetchLocation(View target) {
    RectF location = new RectF();
    Rect locationInView = ViewUtils.getLocationInView(target, mHole);
    location.left = locationInView.left - padding;
    location.top = locationInView.top - padding;
    location.right = locationInView.right + padding;
    location.bottom = locationInView.bottom + padding;
    return location;
  }
}