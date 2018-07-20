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
        compile 'com.github.bingocode:mutiview:1.0.0-SNAPSHOT'
    }

![Alt text](https://github.com/zengge6668/BingoIdea/raw/master/Screenshots/switch_horizon.png)

![Alt text](https://github.com/zengge6668/BingoIdea/raw/master/Screenshots/switch_vertical.png)
