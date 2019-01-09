package com.whu.zengbin.mutiview.videoview;

import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

import com.whu.zengbin.mutiview.util.LogUtil;
import java.io.File;

/**
 * 创建时间：2018/12/13 12:09 <br>
 * 作者：wanghailong <br>
 * 描述：媒体元数据解析器
 */
public class MediaMatedataParser {
  private static final String TAG = "MediaMatedataParser";

  public static VideoMatedata getVideoMatedata(String videoPath) {
    if (TextUtils.isEmpty(videoPath)) {
      return null;
    }

    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
    try {
      File file = new File(videoPath);
      if (!file.exists()) {
        return null;
      }
      mmr.setDataSource(videoPath);
      String videoWidth = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
      String videoHeight = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
      long size = file.length();
      String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
      String bitrate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
      String framerate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE);
      String orientation = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);

      boolean isHorizontal = false;
      if (!TextUtils.isEmpty(orientation)) {
        int orientationValue = Integer.parseInt(orientation);
        isHorizontal = orientationValue != 90 && orientationValue != 270;
      }

      VideoMatedata matedata = new VideoMatedata();
      if (!TextUtils.isEmpty(videoWidth)) {
        if (isHorizontal) {
          matedata.videoWidth = Integer.parseInt(videoWidth);
        } else {
          matedata.videoHeight = Integer.parseInt(videoWidth);
        }
      }
      if (!TextUtils.isEmpty(videoHeight)) {
        if (isHorizontal) {
          matedata.videoHeight = Integer.parseInt(videoHeight);
        } else {
          matedata.videoWidth = Integer.parseInt(videoHeight);
        }
      }
      matedata.isHorizontal = isHorizontal;
      matedata.size = size;
      if (!TextUtils.isEmpty(duration)) {
        matedata.duration = Math.round(Integer.parseInt(duration) / 1000f);
      }
      if (!TextUtils.isEmpty(framerate)) {
        matedata.framerate = (int) Float.parseFloat(framerate);
      }
      if (!TextUtils.isEmpty(bitrate)) {
        matedata.bitrate = Integer.parseInt(bitrate);
      }
      return matedata;
    } catch (Exception e) {
      LogUtil.e(TAG, "getVideoMatedata error" + e);
    } finally {
      mmr.release();
    }

    return null;
  }

  public static class VideoMatedata {

    /**
     * 视频宽度
     */
    public int videoWidth;

    /**
     * 视频高度
     */
    public int videoHeight;

    /**
     * 大小
     */
    public long size;

    /**
     * 时长
     */
    public int duration;

    /**
     * 帧率
     */
    public int framerate;

    /**
     * 码率
     */
    public int bitrate;

    /**
     * 是否横屏
     */
    public boolean isHorizontal;
  }
}
