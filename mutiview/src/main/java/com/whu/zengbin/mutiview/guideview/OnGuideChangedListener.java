package com.whu.zengbin.mutiview.guideview;

/**
 * 创建时间: 2019/09/03 14:42 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public interface OnGuideChangedListener {
  /**
   * 引导层显示
   */
  void onShowed();

  /**
   * 引导层消失
   */
  void onRemoved();
}
