package com.whu.bingo.bingoidea.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.whu.bingo.bingoidea.video.FileType;

/**
 * 创建时间: 2019/01/04 18:47 <br>
 * 作者: zengbin <br>
 * 描述: 文件（本地图片，视频）
 */
public class FileBean implements Parcelable, Cloneable {

  private String fileId;// 原始图片Id
  private String filePath;// 图片路径
  private String thumbPath;// 缩略图片路径
  private long date;
  private String fileName;

  @FileType
  private int type; // 文件类型，图片0，视频1

  public FileBean() {
  }

  protected FileBean(Parcel in) {
    fileId = in.readString();
    filePath = in.readString();
    thumbPath = in.readString();
    date = in.readLong();
    fileName = in.readString();
    type = in.readInt();
  }

  public static final Creator<FileBean> CREATOR = new Creator<FileBean>() {
    @Override
    public FileBean createFromParcel(Parcel in) {
      return new FileBean(in);
    }

    @Override
    public FileBean[] newArray(int size) {
      return new FileBean[size];
    }
  };

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getThumbPath() {
    return thumbPath;
  }

  public void setThumbPath(String thumbPath) {
    this.thumbPath = thumbPath;
  }

  public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public FileBean newInstance() {
    try {
      return (FileBean) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    FileBean that = (FileBean) o;
    if (fileName == null) {
      return that.getFileName() == null && this.filePath.equalsIgnoreCase(that.getFilePath());
    } else {
      return this.fileName.equalsIgnoreCase(that.getFileName()) && //
          this.filePath.equalsIgnoreCase(that.getFilePath());
    }
  }

  @Override
  public int hashCode() {
    int result = fileName != null ? fileName.hashCode() : 0;
    result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
    result = 31 * result + (int) (date ^ (date >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "FileBean{"
        + "fileId='"
        + fileId
        + '\''
        + ", filePath='"
        + filePath
        + ", thumbPath='"
        + thumbPath
        + '\''
        + ", date="
        + date
        + ", fileName='"
        + fileName
        + '\''
        + ", type="
        + type
        + '}';
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(fileId);
    dest.writeString(filePath);
    dest.writeString(thumbPath);
    dest.writeLong(date);
    dest.writeString(fileName);
    dest.writeInt(type);
  }
}
