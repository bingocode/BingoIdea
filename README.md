# BingoIdea
本项目积累开发过程中的一些有趣的，常用的，炫酷的自定义控件，感兴趣的同学可以通过本项目Demo或自行配置gradle学习和使用

## 版本说明
由于配置的maven有些问题，只能拉下最新的版本
### 1.0.5-SNAPSHOT
新增引导弹层蒙版，可折叠文本控件
### 1.0.4-SNAPSHOT
新增视频播放组件
### 1.0.3-SNAPSHOT
修复悬浮窗控件无法取消bug
### 1.0.2-SNAPSHOT
新增悬浮窗控件
### 1.0.1-SNAPSHOT
增加3D立体旋转控件

## 项目配置
在项目的build.gradle配置

    allprojects {
        repositories {
            maven {
               url 'https://oss.sonatype.org/content/groups/public'
           }
       }
    }
在app的build.gradle配置

    dependencies {
        compile 'com.github.bingocode:mutiview:1.0.4-SNAPSHOT'
    }

## 使用方式
### 3D立体旋转控件

    纵向翻转ViewGroup
    <com.whu.zengbin.mutiview.stereo.VStereoView
        android:layout_width="330dp"
        android:layout_height="250dp"
        android:id="@+id/stereoView"
        android:layout_marginTop="50dp">
    <!--添加组件-->
    </com.whu.zengbin.mutiview.VStereoView>
    横向翻转ViewGroup
    <com.whu.zengbin.mutiview.stereo.HStereoView
        android:id="@+id/stereoView24"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    <!--添加组件-->
    </com.whu.zengbin.mutiview.HStereoView>
![Alt text](https://github.com/zengge6668/BingoIdea/raw/master/Screenshots/switch_horizon.png)

### 悬浮窗控件
    由于是采用应用外显示悬浮窗方式，需要进行android.permission.SYSTEM_ALERT_WINDOW权限，可参考Demo
    // 创建实例
    FloatView mFloat = FloatView.getInstance();
    // 设置悬浮窗布局，adapter必须继承BaseFloatAdapter ,可以自定义布局
    mFloat.setAdapter(adapter); 局
    // 显示悬浮窗，传入点击监听器
    mFloat.show(this, clickListener);
    // 关闭悬浮窗
    mFloat.dismiss();
### 视频播放控件
    防止旋转屏幕重建需进行Activity设置
    android:configChanges="orientation|keyboardHidden|screenSize"
    播放视频
    mVideoView.setVideoPath(mVideoPath);
    mVideoView.startPlay();
    生命周期管理
    @Override protected void onResume() {
      super.onResume();
      mVideoView.onResume();
    }

    @Override protected void onPause() {
      mVideoView.onPause();
      super.onPause();
    }

    @Override protected void onDestroy() {
      mVideoView.onDestory();
      super.onDestroy();
    }

### 引导弹窗蒙版

![Alt text](https://github.com/bingocode/BingoIdea/blob/master/Screenshots/20190912052754.png)

### 可折叠文本

![Alt text](https://github.com/bingocode/BingoIdea/blob/master/Screenshots/20190912052822.png)

