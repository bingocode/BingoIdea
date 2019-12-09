package com.whu.zengbin.mutiview.newitem;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2019/12/09 15:52 <br>
 * 作者: zengbin <br>
 * 描述: 标新复合对象
 */
public class NewItemGroup implements INewItem {
  List<INewItem> children = new ArrayList<>();

  @Override
  public boolean isRead() {
    for (INewItem child : children) {
      if (!child.isRead()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void setHasRead() {
    for (INewItem child : children) {
      child.setHasRead();
    }
  }

  public void addChild(INewItem child) {
    children.add(child);
  }
}
