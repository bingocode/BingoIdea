package com.whu.bingo.bingoidea.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import com.whu.bingo.bingoidea.R;
import com.whu.zengbin.mutiview.numedittext.NumEditTextView;
import com.whu.zengbin.mutiview.util.LogUtil;

public class NumEditActivity extends AppCompatActivity {
  public static void start(Context context) {
    context.startActivity(new Intent(context, NumEditActivity.class));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_num_edit);
    NumEditTextView editTextView = findViewById(R.id.edit_text);
    editTextView.setMaxLength(200);
    editTextView.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        LogUtil.i(s.toString());
      }
    });
  }
}
