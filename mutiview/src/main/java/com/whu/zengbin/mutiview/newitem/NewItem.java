package com.whu.zengbin.mutiview.newitem;

import com.whu.zengbin.mutiview.util.MySpUtil;

/**
 * 创建时间: 2019/12/09 15:51 <br>
 * 作者: zengbin <br>
 * 描述: 标新对象
 */
public class NewItem implements INewItem {
  private String itemKey;

  public NewItem(String itemKey) {
    this.itemKey = itemKey;
  }

  @Override
  public boolean isRead() {
    return MySpUtil.getInstance().isNewItemRead(itemKey);
  }

  @Override
  public void setHasRead() {
    MySpUtil.getInstance().setNewItemHasRead(itemKey);
  }
}

