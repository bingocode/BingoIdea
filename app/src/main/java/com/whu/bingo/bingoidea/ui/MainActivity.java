package com.whu.bingo.bingoidea.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.whu.bingo.bingoidea.R;
import com.whu.bingo.bingoidea.floatview.FloatActivity;
import com.whu.bingo.bingoidea.video.PickVideoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.rotate_btn).setOnClickListener(this);
    findViewById(R.id.float_btn).setOnClickListener(this);
    findViewById(R.id.picVideo_btn).setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    int i = v.getId();
    if (i == R.id.rotate_btn) {
      startActivity(RotateActivity.startIntent(this));
    } else if (i == R.id.float_btn) {
      FloatActivity.start(this);
    } else if (i == R.id.picVideo_btn) {
      PickVideoActivity.start(this);
    }
  }
}
