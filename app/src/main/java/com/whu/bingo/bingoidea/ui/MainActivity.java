package com.whu.bingo.bingoidea.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.whu.bingo.bingoidea.R;
import com.whu.bingo.bingoidea.floatview.FloatActivity;
import com.whu.bingo.bingoidea.video.PickVideoActivity;
import com.whu.zengbin.mutiview.guideview.BingoGuide;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private Button mGuideButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.rotate_btn).setOnClickListener(this);
    findViewById(R.id.float_btn).setOnClickListener(this);
    findViewById(R.id.picVideo_btn).setOnClickListener(this);
    mGuideButton = findViewById(R.id.guideview_btn);
    mGuideButton.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    int i = v.getId();
    if (i == R.id.rotate_btn) {
      startActivity(RotateActivity.startIntent(this));
    } else if (i == R.id.float_btn) {
      FloatActivity.start(this);
    } else if (i == R.id.picVideo_btn) {
      PickVideoActivity.start(this);
    } else if (i == R.id.guideview_btn) {
      BingoGuide guide = new BingoGuide.Builder(this).guideLayout(R.layout.view_guide_tip)
          .count(-1)
          .highLight(mGuideButton)
          .build();
      guide.show();
    }
  }
}
