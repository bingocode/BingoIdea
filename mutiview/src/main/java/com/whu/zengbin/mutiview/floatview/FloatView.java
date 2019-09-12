package com.whu.zengbin.mutiview.floatview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.whu.zengbin.mutiview.IClickListener;
import com.whu.zengbin.mutiview.util.LogUtil;

/**
 * 创建时间: 2018/12/26 11:10 <br>
 * 作者: zengbin <br>
 * 描述: 可悬浮组件
 * 主要由于需要实现应用外悬浮，需要权限android.permission.SYSTEM_ALERT_WINDOW
 */
public class FloatView {
  private final String TAG = "FloatView";
  private View mView = null;
  private WindowManager mWindowManager;
  private Context mContext;
  public Boolean isShown = false;
  private WindowManager.LayoutParams params;
  private BaseFloatAdapter mAdapter;
  private IClickListener mClickListener;

  int sPhoneWidth;
  int sPhoneHeight;

  /**
   * 移动的位置
   */
  private int startX;
  private int startY;
  private int lastX;
  private int lastY;
  private int finalX = 0;
  private int finalY = 0;

  private static class SingletonHolder {
    static FloatView sInstance = new FloatView();
  }

  public static FloatView getInstance() {
    return SingletonHolder.sInstance;
  }

  private FloatView() {
  }

  public void setAdapter(BaseFloatAdapter adapter) {
    mAdapter = adapter;
  }

  public void show(Context context, IClickListener listener) {
    if (isShown) {
      LogUtil.i(TAG, "return cause already shown");
      return;
    }
    mClickListener = listener;
    mContext = context;
    isShown = true;
    LogUtil.i(TAG, "showFloatView");
    mView = mAdapter.getView(mContext, mView);
    // 获取WindowManager
    mWindowManager = (WindowManager) mContext
        .getSystemService(Context.WINDOW_SERVICE);

    sPhoneWidth = mWindowManager.getDefaultDisplay().getWidth();
    sPhoneHeight = mWindowManager.getDefaultDisplay().getHeight();

    params = new WindowManager.LayoutParams();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
    } else {
      params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
    }
    // 设置flag
    params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
    // FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
    // FLAG_NOT_TOUCH_MODAL 不阻塞事件传递到后面的窗口
    // 不设置这个弹出框的透明遮罩显示为黑色
    params.format = PixelFormat.TRANSLUCENT;

    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    params.x = finalX;
    params.y = finalY;
    //以右上角为原点
    params.gravity = Gravity.TOP | Gravity.END;
    mWindowManager.addView(mView, params);

    mView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        if (mView == null) {
          return true;
        }
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:// 按下 事件
            startX = lastX = (int) event.getRawX();
            startY = lastY = (int) event.getRawY();
            break;
          case MotionEvent.ACTION_MOVE:// 移动 事件
            params.x = params.x - (int) (event.getRawX() - lastX);
            params.y = params.y + (int) (event.getRawY() - lastY);

            //边界设置(自动靠边)
            if (params.x <= 0) {
              params.x = 0;
            } else if (params.x > sPhoneWidth - mView.getWidth()) {
              params.x = sPhoneWidth - mView.getWidth();
            }
            if (params.y <= 0) {
              params.y = 0;
            } else if (params.y > sPhoneHeight - mView.getHeight()) {
              params.y = sPhoneHeight - mView.getHeight();
            }
            mWindowManager.updateViewLayout(v, params);
            lastX = (int) event.getRawX();
            lastY = (int) event.getRawY();
            break;
          case MotionEvent.ACTION_UP:
            //处理点击
            boolean isclick = Math.abs((int) event.getRawX() - startX) < 20
                && Math.abs((int) event.getRawY() - startY) < 20;
            if (params.x > sPhoneWidth / 2 - v.getWidth() / 2) {
              //放手后移到左边
              moveAnim(params.x, sPhoneWidth - v.getWidth(), isclick);
            } else {
              //移到右边
              moveAnim(params.x, 0, isclick);
            }
            break;
        }
        return true;
      }
    });
  }

  public void dismiss() {
    if (isShown && mView != null) {
      LogUtil.i(TAG, "hideFloatView");
      isShown = false;
      mContext = null;
      mClickListener = null;
      mWindowManager.removeView(mView);
      mView = null;
    }
  }

  private void moveAnim(int oldX, int newX, final boolean isclick) {
    ValueAnimator anim = ValueAnimator.ofInt(oldX, newX);
    anim.setDuration(200);
    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        //设置坐标
        params.x = (int) animation.getAnimatedValue();
        if (isShown && mView != null) {
          mWindowManager.updateViewLayout(mView, params);
        }
      }
    });
    anim.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animator) {
      }

      @Override
      public void onAnimationEnd(Animator animator) {
        finalX = params.x;
        finalY = params.y;
        if (isclick) {
          if (mClickListener != null) {
            mClickListener.onItemClick();
          }
          LogUtil.i(TAG, "clickFloatWindow");
        }
      }

      @Override
      public void onAnimationCancel(Animator animator) {
      }

      @Override
      public void onAnimationRepeat(Animator animator) {
      }
    });
    anim.start();
  }
}
