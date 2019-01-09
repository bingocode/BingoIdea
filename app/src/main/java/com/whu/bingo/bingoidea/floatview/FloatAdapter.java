package com.whu.bingo.bingoidea.floatview;

import android.view.View;
import android.widget.ImageView;
import com.whu.bingo.bingoidea.R;
import com.whu.zengbin.mutiview.floatview.BaseFloatAdapter;

/**
 * 创建时间: 2018/12/26 17:43 <br>
 * 作者: zengbin <br>
 * 描述: 自定义悬浮窗布局适配器
 */
public class FloatAdapter extends BaseFloatAdapter<FloatAdapter.FloatViewHolder> {

  @Override protected FloatViewHolder onCreateViewHolder(View view) {
    return new FloatViewHolder(view);
  }

  @Override protected int getResId() {
    return R.layout.item_float_layout;
  }

  /**
   *  在这里可以初始化视图数据，注意若设置了点击事件，
   *  则会没有滑动效果，因此点击事件请通过FloatView.setClickListener设置
   */
  @Override protected void onBindViewHolder() {

  }

  static class FloatViewHolder extends BaseFloatAdapter.ViewHolder {
    ImageView imageView;


    public FloatViewHolder(View itemView) {
      super(itemView);
      imageView = (ImageView) itemView.findViewById(R.id.float_img);
    }
  }
}
