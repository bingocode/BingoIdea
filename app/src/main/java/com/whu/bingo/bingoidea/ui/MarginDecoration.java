package com.whu.bingo.bingoidea.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.whu.bingo.bingoidea.R;

public class MarginDecoration extends RecyclerView.ItemDecoration {

  private int mMargin;

  public MarginDecoration(Context context) {
    mMargin = context.getResources().getDimensionPixelSize(R.dimen.margin_4);
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    outRect.set(mMargin, mMargin, mMargin, mMargin);
  }
}