package com.whu.bingo.bingoidea.floatview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.whu.bingo.bingoidea.R;
import com.whu.zengbin.mutiview.floatview.FloatView;
import com.whu.zengbin.mutiview.util.LogUtil;

public class FloatActivity extends AppCompatActivity implements View.OnClickListener {
  private static final String TAG = "FloatActivity";
  public static final int REQUEST_CODE = 10001;
  FloatView mFloat;
  FloatAdapter adapter;

  public static void start(Context context) {
    Intent intent = new Intent(context, FloatActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_float);
    findViewById(R.id.show_float).setOnClickListener(this);
    findViewById(R.id.hide_float).setOnClickListener(this);

    mFloat = new FloatView(this);
    adapter = new FloatAdapter(this);
    mFloat.setAdapter(adapter);
  }

  @Override public void onClick(View v) {
    int i = v.getId();
    if (i == R.id.show_float) {
      if (Build.VERSION.SDK_INT >= 23) {
        if (!Settings.canDrawOverlays(this)) {
          //若没有权限，提示获取.
          final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
          final AlertDialog dialog = new AlertDialog.Builder(this).setTitle("获取权限")
              .setMessage("需要开启悬浮窗权限")
              .setPositiveButton("去开启",
                  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                      intent.setData(Uri.parse("package:" + getPackageName()));
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      startActivityForResult(intent, REQUEST_CODE);
                    }
                  })
              .create();
          dialog.show();
        } else {
          mFloat.show();
        }
      } else {
        mFloat.show();
      }
    } else if (i == R.id.hide_float) {
      mFloat.dismiss();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE) {
      if (Build.VERSION.SDK_INT >= 23) {
        if (Settings.canDrawOverlays(this)) {
          LogUtil.i(TAG, "onActivityResult SYSTEM_ALERT_WINDOW permisssion granted");
          mFloat.show();
        }
      }
    }
  }
}
