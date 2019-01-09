package com.whu.bingo.bingoidea.video;

import com.whu.bingo.bingoidea.bean.FileBean;
import java.util.ArrayList;
import java.util.List;

public class FolderEntity implements Cloneable {

  private String folderName = "全部图片";
  private String folderPath = "";
  private String thumbPath = "";

  private List<FileBean> fileEntities;

  /* 是否被选中 */
  private boolean isChecked = false;

  public FolderEntity newInstance() {
    try {
      return (FolderEntity) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return new FolderEntity();
  }

  public String getFolderName() {
    return folderName;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public String getFolderPath() {
    return folderPath;
  }

  public void setFolderPath(String folderPath) {
    this.folderPath = folderPath;
  }

  public String getThumbPath() {
    return thumbPath;
  }

  public void setThumbPath(String thumbPath) {
    this.thumbPath = thumbPath;
  }

  public int getImageCount() {
    return (this.fileEntities != null) ? (this.fileEntities.size()) : 0;
  }

  public List<FileBean> getFileEntities() {
    return fileEntities;
  }

  public void setFileEntities(List<FileBean> fileEntities) {
    this.fileEntities = fileEntities;
  }

  public boolean isChecked() {
    return isChecked;
  }

  public void setChecked(boolean checked) {
    isChecked = checked;
  }

  public void addImage(FileBean imageEntity) {

    if (this.fileEntities == null) {
      this.fileEntities = new ArrayList<>();
    }
    this.fileEntities.add(imageEntity);
  }

  @Override public boolean equals(Object o) {

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    FolderEntity that = (FolderEntity) o;
    return this.folderName.equalsIgnoreCase(that.getFolderName())
        && this.folderPath.equalsIgnoreCase(that.getFolderPath());
  }

  @Override public String toString() {
    return "FolderEntity{" + "folderName='" + folderName + '\'' + ", folderPath='" +
        folderPath + '\'' + ", thumbPath='" + thumbPath + '\'' + ", fileEntities=" +
        fileEntities + ", isChecked=" + isChecked + '}';
  }
}