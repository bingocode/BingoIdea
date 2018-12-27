package com.whu.zengbin.mutiview.floatview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 创建时间: 2018/12/26 14:48 <br>
 * 作者: zengbin <br>
 * 描述: 适配器基类
 */
public abstract class BaseFloatAdapter<T extends BaseFloatAdapter.ViewHolder> {
  private static final String TAG = "BaseFloatAdapter";
  protected T mViewHolder;

  public View getView(Context context, View convertView) {
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(getResId(),
          null);
      mViewHolder = onCreateViewHolder(convertView);
      convertView.setTag(mViewHolder);
    } else {
      mViewHolder = (T) convertView.getTag();
    }
    onBindViewHolder();
    return convertView;
  }

  protected abstract T onCreateViewHolder(View convertView);

  protected abstract int getResId();

  protected abstract void onBindViewHolder();

  public static class ViewHolder {
    protected final View itemView;

    public ViewHolder(View itemView) {
      this.itemView = itemView;
    }
  }
}
