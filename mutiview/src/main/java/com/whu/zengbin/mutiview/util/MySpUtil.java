package com.whu.zengbin.mutiview.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.whu.zengbin.mutiview.MutiViewContext;

/**
 * 创建时间: 2019/12/09 15:40 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class MySpUtil {
  private static final String KEY_NEW_ITEM = "key.new.item";
  private SharedPreferences mPreferences;

  public static MySpUtil getInstance() {
    return MySpUtil.InstanceHolder.sInstance;
  }

  private static class InstanceHolder {
    static MySpUtil sInstance = new MySpUtil();
  }

  private MySpUtil() {
    mPreferences = MutiViewContext.getApplication()
        .getSharedPreferences("mySpUtil", Context.MODE_PRIVATE);
  }

  public boolean isNewItemRead(String itemKey) {
    return mPreferences.getBoolean(KEY_NEW_ITEM + itemKey, false);
  }

  public void setNewItemHasRead(String itemKey) {
    mPreferences.edit().putBoolean(KEY_NEW_ITEM + itemKey, true).apply();
  }
}
