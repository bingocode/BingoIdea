package com.whu.bingo.bingoidea;

import android.app.Application;
import com.whu.zengbin.mutiview.MutiViewContext;

/**
 * 创建时间: 2019/12/09 16:46 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class MyApp extends Application {
  @Override public void onCreate() {
    super.onCreate();
    MutiViewContext.onCreate(this);
  }
}
