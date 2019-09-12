package com.whu.bingo.bingoidea.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.whu.bingo.bingoidea.R;
import com.whu.zengbin.mutiview.foldtextview.ExpandTextView;

public class FoldTextActivity extends AppCompatActivity {
  private EditText mEdit;
  private ExpandTextView mExpandTv;
  private Button mSubmitBtn;
  private TextView mExpandTip;
  private boolean isExpand = false;

  public static void start(Context context) {
    context.startActivity(new Intent(context, FoldTextActivity.class));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fold_text);
    mEdit = findViewById(R.id.edt_content);
    mExpandTv = findViewById(R.id.expand_tv);
    mSubmitBtn = findViewById(R.id.submit_btn);
    mExpandTip = findViewById(R.id.expand_tip);
    mSubmitBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onTextChanged();
      }
    });
    mExpandTip.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        isExpand = !isExpand;
        mExpandTv.setChanged(isExpand);
      }
    });
  }

  private void onTextChanged() {
    mExpandTv.setText(mEdit.getText().toString(), isExpand, new ExpandTextView.Callback() {

      @Override public void onExpand() {
        mExpandTip.setVisibility(View.VISIBLE);
        mExpandTip.setText("收起");
      }

      @Override public void onCollapse() {
        mExpandTip.setVisibility(View.VISIBLE);
        mExpandTip.setText("展开");
      }

      @Override public void onLoss() {
        // 不满足展开的条件，比如：隐藏“展开”按钮
        mExpandTip.setVisibility(View.GONE);
      }
    });
  }
}
