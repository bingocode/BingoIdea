package com.whu.bingo.bingoidea.video;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 创建时间: 2018/12/12 15:32 <br>
 * 作者: zengbin <br>
 * 描述: 选择到文件类型
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({ FileType.IMAGE, FileType.VIDEO })
public @interface FileType {
  int IMAGE = 0;

  int VIDEO = 1;
}
