package com.whu.zengbin.mutiview;

import android.app.Application;

/**
 * 创建时间: 2019/12/09 15:41 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class MutiViewContext {
  private static Application sInstance;

  public static void onCreate(Application application) {
    sInstance = application;
  }

  public static Application getApplication() {
    if (sInstance == null) {
      throw new IllegalStateException("not init app");
    }
    return sInstance;
  }
}
