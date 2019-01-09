package com.whu.bingo.bingoidea.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.StatFs;
import java.io.File;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;

/**
 * Created by Joker on 2016/5/9.
 */
public class CacheUtil {

  public CacheUtil() {
    throw new IllegalStateException("No instance");
  }

  private static final String ULTRA_CACHE = "picasso-cache";
  private static final String CHAT_IMAGE_CACHE = "chat-image-cache";
  private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB
  private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

  public static int calculateMemoryCacheSize(Context context) {
    ActivityManager am = getService(context, ACTIVITY_SERVICE);
    boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
    int memoryClass = am.getMemoryClass();
    if (largeHeap) {
      memoryClass = ActivityManagerHoneycomb.getLargeMemoryClass(am);
    }
    // Target ~30% of the available heap.
    int memorySize = 1024 * 1024 * memoryClass / 3;
    System.err.println("Picasso memorySize:" + memoryClass / 3);
    return memorySize;
  }

  private static class ActivityManagerHoneycomb {

    static int getLargeMemoryClass(ActivityManager activityManager) {
      return activityManager.getLargeMemoryClass();
    }
  }

  public static File createDiskCacheDir(Context context) {
    File cache = new File(context.getApplicationContext().getCacheDir(), ULTRA_CACHE);
    if (!cache.exists()) {
      cache.mkdirs();
    }
    return cache;
  }

  public static File createChatImageDiskCacheDir(Context context) {
    File cache = new File(context.getApplicationContext().getCacheDir(), CHAT_IMAGE_CACHE);
    if (!cache.exists()) {
      cache.mkdirs();
    }
    return cache;
  }

  public static long calculateDiskCacheSize(File dir) {
    long size = MIN_DISK_CACHE_SIZE;

    try {
      StatFs statFs = new StatFs(dir.getAbsolutePath());
      if (Build.VERSION.SDK_INT < 18) {
        size = statFs.getBlockCountLong() * statFs.getBlockSizeLong() / 50;
      } else {
        size = statFs.getBlockCount() * statFs.getBlockSize() / 50;
      }
    } catch (IllegalArgumentException ignored) {
      ignored.printStackTrace();
    }
    return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
  }

  @SuppressWarnings("unchecked") static <T> T getService(Context context, String service) {
    return (T) context.getSystemService(service);
  }
}
