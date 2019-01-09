package com.whu.bingo.bingoidea.video;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.whu.bingo.bingoidea.R;
import com.whu.zengbin.mutiview.videoview.BinVideoView;
/**
 * 创建时间: 2019/01/07 22:47 <br>
 * 作者: zengbin <br>
 * 描述: 视频播放页面
 */
public class VideoPlayActivity extends AppCompatActivity {

  private static String EXTRA_VIDEO_PATH = "videoLocalPath";
  private static String EXTRA_THUMB_PATH = "thumbPath";
  private static String EXTRA_IS_LOCAL = "isLocal";

  private String mVideoPath;
  private boolean mIsLocal = false;
  private String mThumbPath;
  private BinVideoView mVideoView;
  private ProgressBar mProgressBar;
  private ImageView mBack;

  /**
   * 本地视频打开方式
   * @param context
   * @param videoPath
   */
  public static void start(@NonNull Context context, String videoPath) {
    Intent intent = new Intent(context, VideoPlayActivity.class);
    intent.putExtra(EXTRA_VIDEO_PATH, videoPath);
    intent.putExtra(EXTRA_IS_LOCAL, true);
    context.startActivity(intent);
  }

  /**
   * 网络视频打开方式
   * @param context
   * @param videoUrlPath
   * @param thumbPath
   */
  public static void start(@NonNull Context context, String videoUrlPath, String thumbPath) {
    Intent intent = new Intent(context, VideoPlayActivity.class);
    intent.putExtra(EXTRA_VIDEO_PATH, videoUrlPath);
    intent.putExtra(EXTRA_IS_LOCAL, false);
    intent.putExtra(EXTRA_THUMB_PATH, thumbPath);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // 设置全屏
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_play);
    mVideoPath = getIntent().getStringExtra(EXTRA_VIDEO_PATH);
    mIsLocal = getIntent().getBooleanExtra(EXTRA_IS_LOCAL, false);
    if (!mIsLocal) {
      mThumbPath = getIntent().getStringExtra(EXTRA_THUMB_PATH);
    }
    mBack = findViewById(R.id.iv_back);
    mVideoView = findViewById(R.id.bin_video_view);
    mProgressBar = findViewById(R.id.progress_circular);
    if (!TextUtils.isEmpty(mVideoPath)) {
      if (mIsLocal) {
        mVideoView.setVideoPath(mVideoPath);
        mVideoView.startPlay();
      } else { //网络视频地址，先下载，再播放

      }
    }
  }

  @Override protected void onResume() {
    super.onResume();
    mVideoView.onResume();
  }

  @Override protected void onPause() {
    mVideoView.onPause();
    super.onPause();
  }

  @Override protected void onDestroy() {
    mVideoView.onDestory();
    super.onDestroy();
  }
}
