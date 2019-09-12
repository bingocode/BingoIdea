package com.whu.zengbin.mutiview.guideview;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * 创建时间: 2019/09/02 15:14 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class GuideLayout extends FrameLayout {

  public static final int DEFAULT_BACKGROUND_COLOR = 0xb2000000;

  private Paint mPaint;
  public Highlight highlight;
  private OnGuideLayoutDismissListener listener;
  private float downX;
  private float downY;
  private int touchSlop;
  private int layoutResId;
  private int backgroundColor;

  public GuideLayout(Context context, @LayoutRes int resId, Highlight hightLightView) {
    super(context);
    init();
    setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
    this.layoutResId = resId;
    this.highlight = hightLightView;
  }

  private GuideLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  private GuideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void init() {
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    mPaint.setXfermode(xfermode);
    //设置画笔遮罩滤镜,可以传入BlurMaskFilter或EmbossMaskFilter，前者为模糊遮罩滤镜而后者为浮雕遮罩滤镜
    //这个方法已经被标注为过时的方法了，如果你的应用启用了硬件加速，你是看不到任何阴影效果的
    mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
    //关闭当前view的硬件加速
    setLayerType(LAYER_TYPE_SOFTWARE, null);
    //ViewGroup默认设定为true，会使onDraw方法不执行，如果复写了onDraw(Canvas)方法，需要清除此标记
    setWillNotDraw(false);
    touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
  }

  @Override
  public boolean performClick() {
    return super.performClick();
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int action = event.getAction();
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        downX = event.getX();
        downY = event.getY();
        break;
      case MotionEvent.ACTION_MOVE:
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL:
        float upX = event.getX();
        float upY = event.getY();
        if (Math.abs(upX - downX) < touchSlop && Math.abs(upY - downY) < touchSlop) {
          performClick();
        }
        break;
    }
    return super.onTouchEvent(event);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawColor(backgroundColor == 0 ? DEFAULT_BACKGROUND_COLOR : backgroundColor);
    drawHighlights(canvas);
  }

  private void drawHighlights(Canvas canvas) {
    if(highlight == null) {
      return;
    }
    RectF rectF = highlight.getRectF((ViewGroup) getParent());
    canvas.drawRoundRect(rectF, highlight.getRound(), highlight.getRound(), mPaint);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    addCustomToLayout();
  }

  /**
   * 将自定义布局填充到guideLayout中
   */
  private void addCustomToLayout() {
    removeAllViews();
    if (layoutResId != 0) {
      View view = LayoutInflater.from(getContext()).inflate(layoutResId, this, false);
      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      addView(view, params);
    }
  }

  public void setDefaultBackgroundColor(@ColorInt int color) {
    this.backgroundColor = color;
  }

  public void setOnGuideLayoutDismissListener(OnGuideLayoutDismissListener listener) {
    this.listener = listener;
  }

  private void dismiss() {
    if (getParent() != null) {
      ((ViewGroup) getParent()).removeView(this);
      if (listener != null) {
        listener.onGuideLayoutDismiss(this);
      }
    }
  }

  public interface OnGuideLayoutDismissListener {
    void onGuideLayoutDismiss(GuideLayout guideLayout);
  }
}
