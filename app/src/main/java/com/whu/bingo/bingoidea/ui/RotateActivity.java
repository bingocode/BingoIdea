package com.whu.bingo.bingoidea.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.whu.bingo.bingoidea.R;
import com.whu.bingo.bingoidea.bean.RotateBean;
import com.whu.bingo.bingoidea.utils.ImageUtil;
import com.whu.zengbin.mutiview.stereo.HStereoView;
import com.whu.zengbin.mutiview.stereo.VStereoView;
import java.util.ArrayList;
import java.util.List;

public class RotateActivity extends AppCompatActivity {
  private List<RotateBean> datas;
  private VStereoView mVteroView;
  private HStereoView mHStereoView1;
  private HStereoView mHStereoView2;
  private HStereoView mHStereoView3;
  private HStereoView mHStereoView4;
  private TextView backorigion;

  public static Intent startIntent(Context context) {
    Intent intent = new Intent(context, RotateActivity.class);
    return  intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rotate);
    initData();
    initView();
  }

  private void initData() {
    String[] imagePaths = getResources().getStringArray(R.array.imgPaths);
    String[] indexs = getResources().getStringArray(R.array.indexs);
    String[] nexts = getResources().getStringArray(R.array.nexts);
    datas = new ArrayList<>(indexs.length);
    for (int i = 0; i < indexs.length; i++) {
      datas.add(new RotateBean(imagePaths[i], indexs[i], nexts[i]));
    }
  }

  private void initView() {
    mVteroView = (VStereoView) findViewById(R.id.stereoView);
    mHStereoView1 = (HStereoView) findViewById(R.id.stereoView21);
    mHStereoView2 = (HStereoView) findViewById(R.id.stereoView22);
    mHStereoView3 = (HStereoView) findViewById(R.id.stereoView23);
    mHStereoView4 = (HStereoView) findViewById(R.id.stereoView24);
    backorigion = (TextView) findViewById(R.id.backorigion);
    initHStereoView(mHStereoView1, 1);
    initHStereoView(mHStereoView2, 2);
    initHStereoView(mHStereoView3, 3);
    initHStereoView(mHStereoView4, 4);
    backorigion.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mVteroView.setItem(VStereoView.mCurStartScreen);
        mHStereoView2.setItem(HStereoView.curStartScreen);
      }
    });

  }

  private void initHStereoView(HStereoView hStereoView, int page) {
    int j = 4 * (page - 1);
    initRotateLayout(hStereoView.findViewById(R.id.rotate_1), datas.get(j));
    initRotateLayout(hStereoView.findViewById(R.id.rotate_2), datas.get(j + 1));
    initRotateLayout(hStereoView.findViewById(R.id.rotate_3), datas.get(j + 2));
    initRotateLayout(hStereoView.findViewById(R.id.rotate_4), datas.get(j + 3));
  }

  private void initRotateLayout(View rootView, RotateBean bean) {
    ImageView image = rootView.findViewById(R.id.img);
    TextView index = rootView.findViewById(R.id.index);
    TextView next = rootView.findViewById(R.id.next);
    ImageUtil.loadurlimage(this, bean.imgPath, image);
    index.setText(bean.index);
    next.setText(bean.next);
  }
}
