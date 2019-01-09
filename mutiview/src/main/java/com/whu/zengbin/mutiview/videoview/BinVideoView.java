package com.whu.zengbin.mutiview.videoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.whu.zengbin.mutiview.R;

/**
 * 创建时间: 2018/12/14 14:39 <br>
 * 作者: zengbin <br>
 * 描述: 自定义视频播放器
 */
public class BinVideoView extends RelativeLayout {
  private static final int REFRESH_TIME = 200;
  private ImageView mPlayIcon;
  private BaseVideoView mVideoView;
  private ImageView mPause;
  private TextView mStartTime;
  private SeekBar mSeekBar;
  private TextView mEndTime;
  private LinearLayout mFuncLayout;
  private boolean isFinished = false;
  private int mCurrentPositon;

  private Runnable mRefreshRunnable = new Runnable() {
    @Override
    public void run() {
      int currentTime = mVideoView.getCurrentPosition();
      int totally = mVideoView.getDuration();
      updataTimeFormat(mEndTime, totally);
      updataTimeFormat(mStartTime, currentTime);
      mSeekBar.setMax(totally);
      mSeekBar.setProgress(currentTime);
      mCurrentPositon = currentTime;
      postDelayed(mRefreshRunnable, REFRESH_TIME);
    }
  };

  public BinVideoView(Context context) {
    super(context);
    initView();
  }

  public BinVideoView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public BinVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public BinVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.bin_video_view_layut, this, true);
    mPlayIcon = (ImageView) findViewById(R.id.iv_video_icon);
    mVideoView = (BaseVideoView) findViewById(R.id.video_view);
    mPause = (ImageView) findViewById(R.id.pause_img);
    mStartTime = (TextView) findViewById(R.id.time_start_tv);
    mSeekBar = (SeekBar) findViewById(R.id.seekbar);
    mEndTime = (TextView) findViewById(R.id.time_end_tv);
    mFuncLayout = (LinearLayout) findViewById(R.id.func_layout);
    initViewOnClick();
    initVideoView();
  }

  private void initViewOnClick() {
    mPause.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onPlayClick();
      }
    });

    mPlayIcon.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onPlayClick();
      }
    });

    mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //设置当前的播放时间
        updataTimeFormat(mStartTime, progress);
        if (mVideoView.getDuration() == progress) {
          onFinalState();
        }
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        removeCallbacks(mRefreshRunnable);
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        int newProgress = seekBar.getProgress();
        mVideoView.seekTo(newProgress);
        if (mVideoView.isPlaying()) {
          postDelayed(mRefreshRunnable, REFRESH_TIME);
        }
      }
    });
  }

  private void initVideoView() {
    mVideoView.requestFocus();
    mVideoView.setKeepScreenOn(true);
    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
      public void onPrepared(MediaPlayer mp) {
        mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
          @Override
          public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
              // video 视屏播放的时候把背景设置为透明
              mVideoView.setBackgroundColor(Color.TRANSPARENT);
              mPlayIcon.setVisibility(View.GONE);
              return true;
            }
            return false;
          }
        });
      }
    });
    mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
      @Override
      public void onCompletion(MediaPlayer mp) {
        onFinalState();
      }
    });

    mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
      @Override
      public boolean onError(MediaPlayer mp, int what, int extra) {
        onFinalState();
        return false;
      }
    });
  }

  public void setFuncVisiable(boolean visiable) {
    mFuncLayout.setVisibility(visiable ? VISIBLE : GONE);
  }

  /**
   * 播放按钮点击事件
   */
  private void onPlayClick() {
    if (mVideoView.isPlaying()) {
      stopPlay();
    } else {
      startPlay();
    }
  }

  public void startPlay() {
    isFinished = false;
    mPlayIcon.setVisibility(GONE);
    mVideoView.start();
    mPause.setSelected(true);
    postDelayed(mRefreshRunnable, REFRESH_TIME);
  }

  public void stopPlay() {
    mVideoView.pause();
    mPause.setSelected(false);
    removeCallbacks(mRefreshRunnable);
  }

  public void setThumb(Bitmap thumb) {
    if (!mVideoView.isPlaying()) {
      mVideoView.setBackground(new BitmapDrawable(thumb));
    }
  }

  public void setVideoPath(String localVideoPath) {
    mVideoView.setVideoPath(localVideoPath);
  }

  private void onFinalState() {
    if (!isFinished) {
      mPlayIcon.setVisibility(View.VISIBLE);
      mPause.setSelected(false);
      mSeekBar.setProgress(mVideoView.getDuration());
      removeCallbacks(mRefreshRunnable);
      isFinished = true;
    }
  }

  /**
   * 时间格式化
   *
   * @param textView    时间控件
   * @param millisecond 总时间 毫秒
   */
  private void updataTimeFormat(TextView textView, int millisecond) {
    //将毫秒转换为秒
    int second = millisecond / 1000;
    //计算小时
    int hh = second / 3600;
    //计算分钟
    int mm = second % 3600 / 60;
    //计算秒
    int ss = second % 60;
    //判断时间单位的位数
    String str = null;
    if (hh != 0) {//表示时间单位为三位
      str = String.format("%02d:%02d:%02d", hh, mm, ss);
    } else {
      str = String.format("%02d:%02d", mm, ss);
    }
    //将时间赋值给控件
    textView.setText(str);
  }

  public void onResume() {
    if (mCurrentPositon != 0) {
      mVideoView.seekTo(mCurrentPositon);
      stopPlay();
    }
  }

  public void onPause() {
    stopPlay();
  }

  public void onDestory() {
    mVideoView.stopPlayback();
    mVideoView = null;
    removeCallbacks(mRefreshRunnable);
  }
}
