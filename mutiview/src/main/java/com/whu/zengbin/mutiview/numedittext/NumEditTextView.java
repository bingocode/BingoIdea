package com.whu.zengbin.mutiview.numedittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.whu.zengbin.mutiview.R;
import com.whu.zengbin.mutiview.util.DensityUtil;

/**
 * 创建时间: 2019/11/06 18:12 <br>
 * 作者: zengbin <br>
 * 描述: 带输入数量显示的输入框
 */
public class NumEditTextView extends LinearLayout implements TextWatcher {
  private EditText mPhraseEdit;
  private TextView mNumTv;
  private int mMaxLength = 200;
  private TextWatcher mOuterTextWatcher;

  public NumEditTextView(Context context) {
    super(context);
    initView();
  }

  public NumEditTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public NumEditTextView(Context context, AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public NumEditTextView(Context context, AttributeSet attrs,
      int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.num_edit_text_view, this, true);
    mPhraseEdit = findViewById(R.id.edit_content);
    mNumTv = findViewById(R.id.tv_num);
    mPhraseEdit.addTextChangedListener(this);
    setNum(0);
  }

  public String getFinalPhrase() {
    if (mPhraseEdit == null
        || TextUtils.isEmpty(mPhraseEdit.getText())
        || mPhraseEdit.getText().toString().length() == 0) {
      return null;
    }
    return mPhraseEdit.getText().toString();
  }

  public void setHint(String hint) {
    if (TextUtils.isEmpty(hint)) {
      return;
    }
    mPhraseEdit.setHint(hint);
  }

  public void setEditBackground(Drawable background) {
    findViewById(R.id.edit_container).setBackground(background);
  }

  public void addTextChangedListener(TextWatcher watcher) {
    mOuterTextWatcher = watcher;
  }

  /**
   * 设置整体内边距 dp值
   */
  public void setEditPadding(float left, float top, float right, float bottom) {
    if (left < 0 || top < 0 || right < 0 || bottom < 0) {
      return;
    }
    mPhraseEdit.setPadding(DensityUtil.dip2px(getContext(), left),
        DensityUtil.dip2px(getContext(), top), DensityUtil.dip2px(getContext(), right), 0);
    mNumTv.setPadding(0, 0, DensityUtil.dip2px(getContext(), right),
        DensityUtil.dip2px(getContext(), bottom));
  }

  /**
   * 设置整体外边距和高度 dp值
   */
  public void setEditMarginAndHeight(float left, float top, float right, float bottom,
      float height) {
    if (left < 0 || top < 0 || right < 0 || bottom < 0) {
      return;
    }
    LayoutParams params =
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    params.leftMargin = DensityUtil.dip2px(getContext(), left);
    params.topMargin = DensityUtil.dip2px(getContext(), top);
    params.rightMargin = DensityUtil.dip2px(getContext(), right);
    params.bottomMargin = DensityUtil.dip2px(getContext(), bottom);
    if (height > 0) {
      params.height = DensityUtil.dip2px(getContext(), height);
    }
    findViewById(R.id.edit_container).setLayoutParams(params);
  }

  public void setInitPhrase(String phrase) {
    if (TextUtils.isEmpty(phrase)) {
      return;
    }
    mPhraseEdit.setText(phrase);
    mPhraseEdit.setSelection(mPhraseEdit.getText().length());
    setNum(mPhraseEdit.getText().length());
  }

  public void setMaxLength(int length) {
    if (length <= 0) {
      return;
    }
    mMaxLength = length;
    mNumTv.setText(new StringBuilder("0/").append(mMaxLength));
    mPhraseEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(length) });
  }

  private void setNum(int num) {
    StringBuilder numStr = new StringBuilder(String.valueOf(num));
    numStr.append("/");
    numStr.append(String.valueOf(mMaxLength));
    mNumTv.setText(numStr);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    if (mOuterTextWatcher != null) {
      mOuterTextWatcher.beforeTextChanged(s, start, count, after);
    }
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    if (mOuterTextWatcher != null) {
      mOuterTextWatcher.onTextChanged(s, start, before, count);
    }
  }

  @Override
  public void afterTextChanged(Editable s) {
    String content = mPhraseEdit.getText().toString();
    setNum(content.length());
    if (mOuterTextWatcher != null) {
      mOuterTextWatcher.afterTextChanged(s);
    }
  }
}
