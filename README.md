# BingoIdea
本项目积累开发过程中的一些有趣的，常用的，炫酷的自定以控件，将所有自定义控件上传至maven，感兴趣的同学可以通过本项目Demo或自行配置gradle学习和使用

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
        compile 'com.github.bingocode:mutiview:1.0.1-SNAPSHOT'
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

![Alt text](https://github.com/zengge6668/BingoIdea/raw/master/Screenshots/switch_vertical.png)

## 版本说明
由于配置的maven有些问题，只能拉下最新的版本
### 1.0.1-SNAPSHOT
增加3D立体旋转控件
### 1.0.2-SNAPSHOT
新增悬浮窗控件
### 1.0.3-SNAPSHOT
修复悬浮窗控件无法取消bug
### 1.0.4-SNAPSHOT
新增视频播放组件






