package com.whu.bingo.bingoidea.video;

import android.view.View;

/**
 * 创建时间: 2019/01/05 13:06 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public interface OnItemClickListener<T> {
  void onItemClick(int position, T item, View v);
}
