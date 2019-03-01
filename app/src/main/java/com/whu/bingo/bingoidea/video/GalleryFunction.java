package com.whu.bingo.bingoidea.video;

import com.whu.bingo.bingoidea.bean.FileBean;
import io.reactivex.functions.BiFunction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 创建时间: 2019/01/09 17:53 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class GalleryFunction implements
    BiFunction<List<FileBean>, List<FileBean>, List<FileBean>> {
  @Override public List<FileBean> apply(List<FileBean> videoItems, List<FileBean> imageItems)
      throws Exception {
    List<FileBean> allItems = new ArrayList<>();
    allItems.addAll(videoItems);
    allItems.addAll(imageItems);
    Collections.sort(allItems, new Comparator<FileBean>() {
      @Override
      public int compare(FileBean o1, FileBean o2) {
        return Long.signum (o2.getDate() - o1.getDate());
      }
    });
    return allItems;
  }
}
