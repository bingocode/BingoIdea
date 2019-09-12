package com.whu.zengbin.mutiview.guideview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 创建时间: 2019/09/02 15:14 <br>
 * 作者: zengbin <br>
 * 描述: 新功能引导蒙层
 */
public class BingoGuide {
  public static final String TAG = "BingoGuide";
  public static final long DEFAULT_TIME = -1;
  private static final String SP_GUIDE = "ChatGuide_SP";
  private static final String KEY_SHOW_COUNT = "guide_show_count_";
  private final Activity activity;
  private final Context context;
  private final FrameLayout mParentView;
  private final String label;
  private final long showTime;
  private final int showCount;
  private final @LayoutRes int layout;
  private final @ColorInt int bgColor;
  private final View highLightView;
  private final OnGuideChangedListener onGuideChangedListener;
  private GuideLayout currentLayout;
  private int indexOfChild = -1;//使用anchor时记录的在父布局的位置

  private BingoGuide(Builder builder) {
    this.activity = builder.activity;
    this.context = builder.context;
    this.label = TextUtils.isEmpty(builder.label) ? activity.getLocalClassName() : builder.label;
    this.showTime = builder.showTime;
    this.showCount = builder.showCount;
    this.layout = builder.guideLayout;
    this.bgColor = builder.bgColor;
    this.highLightView = builder.highLightView;
    this.onGuideChangedListener = builder.onGuideChangedListener;
    View anchor = activity.findViewById(android.R.id.content);
    if (anchor instanceof FrameLayout) {
      mParentView = (FrameLayout) anchor;
    } else {
      FrameLayout frameLayout = new FrameLayout(activity);
      ViewGroup parent = (ViewGroup) anchor.getParent();
      indexOfChild = parent.indexOfChild(anchor);
      parent.removeView(anchor);
      if (indexOfChild >= 0) {
        parent.addView(frameLayout, indexOfChild, anchor.getLayoutParams());
      } else {
        parent.addView(frameLayout, anchor.getLayoutParams());
      }
      ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
          (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      frameLayout.addView(anchor, lp);
      mParentView = frameLayout;
    }
  }

  /**
   * 显示
   */
  public void show() {
    int hasShowedCount = getHasShowedCount();
    if (hasShowedCount >= showCount && showCount >= 0) {
      return;
    }
    Highlight hi = null;
    if (highLightView != null) {
      hi = new Highlight(highLightView, 0, 0);
    }
    currentLayout = new GuideLayout(context, layout, hi);
    currentLayout.setDefaultBackgroundColor(bgColor);
    currentLayout.setOnGuideLayoutDismissListener(new GuideLayout.OnGuideLayoutDismissListener() {
      @Override
      public void onGuideLayoutDismiss(GuideLayout guideLayout) {
        if (onGuideChangedListener != null) {
          onGuideChangedListener.onRemoved();
        }
      }
    });
    mParentView.addView(currentLayout, new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    if (onGuideChangedListener != null) {
      onGuideChangedListener.onShowed();
    }
    saveShowCount(hasShowedCount + 1);
    // 设置延时消失
    if (showTime > DEFAULT_TIME) {
      currentLayout.postDelayed(new Runnable() {
        @Override
        public void run() {
          if (currentLayout != null && currentLayout.getParent() != null) {
            ViewGroup parent = (ViewGroup) currentLayout.getParent();
            parent.removeView(currentLayout);
            //移除anchor添加的frameLayout
            if (!(parent instanceof FrameLayout)) {
              ViewGroup original = (ViewGroup) parent.getParent();
              View anchor = parent.getChildAt(0);
              parent.removeAllViews();
              if (anchor != null) {
                if (indexOfChild > 0) {
                  original.addView(anchor, indexOfChild, parent.getLayoutParams());
                } else {
                  original.addView(anchor, parent.getLayoutParams());
                }
              }
            }
            if (onGuideChangedListener != null) {
              onGuideChangedListener.onRemoved();
            }
            currentLayout = null;
          }
        }
      }, showTime);
    }
  }

  private SharedPreferences getGuideSharedPreferences() {
    return context.getSharedPreferences(SP_GUIDE, Context.MODE_PRIVATE);
  }

  private void saveShowCount(int count) {
    getGuideSharedPreferences().edit().putInt(KEY_SHOW_COUNT + label, count).apply();
  }

  private int getHasShowedCount() {
    return getGuideSharedPreferences().getInt(KEY_SHOW_COUNT + label, 0);
  }

  public static class Builder {
    private Activity activity;
    private Fragment fragment;
    private Context context;
    /**
     * 用于保存到本地sp的key
     */
    private String label;
    /**
     * 蒙版显示时间（默认最大值，一直显示）
     */
    private long showTime = DEFAULT_TIME;

    /**
     * 显示次数(默认只显示一次)
     */
    private int showCount = 1;

    /**
     * 蒙版上的布局
     */
    private @LayoutRes int guideLayout;

    /**
     * 蒙版背景色
     */
    private @ColorInt int bgColor;

    /**
     * 需要高亮的View
     */
    private View highLightView;

    private OnGuideChangedListener onGuideChangedListener;

    public Builder(Activity activity) {
      if (activity == null) {
        throw new IllegalStateException("activity is null");
      }
      this.activity = activity;
      label = activity.getLocalClassName();
      context = activity;
    }

    public Builder(Fragment fragment) {
      this(fragment.getActivity());
      this.fragment = fragment;
      this.context = fragment.getContext();
    }

    public Builder context(Context context) {
      this.context = context;
      return this;
    }

    public Builder label(String label) {
      this.label = label;
      return this;
    }

    public Builder time(long showTime) {
      this.showTime = showTime;
      return this;
    }

    public Builder count(int showCount) {
      this.showCount = showCount;
      return this;
    }

    public Builder guideLayout(@LayoutRes int guideLayout) {
      this.guideLayout = guideLayout;
      return this;
    }

    public Builder highLight(View view) {
      this.highLightView = view;
      return this;
    }

    public Builder bgColor(@ColorInt int bgColor) {
      this.bgColor = bgColor;
      return this;
    }

    public Builder setOnGuideChangedListener(OnGuideChangedListener listener) {
      onGuideChangedListener = listener;
      return this;
    }

    public BingoGuide build() {
      return new BingoGuide(this);
    }
  }
}
