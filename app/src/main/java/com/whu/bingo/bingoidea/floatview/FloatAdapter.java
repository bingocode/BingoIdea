package com.whu.bingo.bingoidea.floatview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.whu.bingo.bingoidea.R;
import com.whu.bingo.bingoidea.utils.ToastUtil;
import com.whu.zengbin.mutiview.floatview.BaseFloatAdapter;

/**
 * 创建时间: 2018/12/26 17:43 <br>
 * 作者: zengbin <br>
 * 描述: 自定义悬浮窗布局适配器
 */
public class FloatAdapter extends BaseFloatAdapter<FloatAdapter.FloatViewHolder> {

  private Context mContext;
  public FloatAdapter(Context context) {
    mContext = context;
  }

  @Override protected FloatViewHolder onCreateViewHolder(View view) {
    return new FloatViewHolder(view);
  }

  @Override protected int getResId() {
    return R.layout.item_float_layout;
  }

  @Override protected void onBindViewHolder() {
    mViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ToastUtil.showInfo(mContext, "clickFloat");
      }
    });
  }

  static class FloatViewHolder extends BaseFloatAdapter.ViewHolder {
    ImageView imageView;


    public FloatViewHolder(View itemView) {
      super(itemView);
      imageView = (ImageView) itemView.findViewById(R.id.float_img);
    }
  }
}
