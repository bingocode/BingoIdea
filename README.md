# BingoIdea

一个3D立体切换的自定义ViewGroup demo，支持横向和纵向滑动翻转。

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
纵向翻转ViewGroup

        <com.whu.zengbin.mutiview.VStereoView
            android:layout_width="330dp"
            android:layout_height="250dp"
            android:id="@+id/stereoView"
            android:layout_marginTop="50dp">
        <!--添加组件-->
        </com.whu.zengbin.mutiview.VStereoView>
横向翻转ViewGroup

        <com.whu.zengbin.mutiview.HStereoView
            android:id="@+id/stereoView24"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <!--添加组件-->
        </com.whu.zengbin.mutiview.HStereoView>

![Alt text](https://github.com/zengge6668/BingoIdea/raw/master/Screenshots/switch_horizon.png)

![Alt text](https://github.com/zengge6668/BingoIdea/raw/master/Screenshots/switch_vertical.png)
