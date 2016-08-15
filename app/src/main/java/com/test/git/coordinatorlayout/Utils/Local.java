package com.test.git.coordinatorlayout.Utils;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import com.test.git.coordinatorlayout.CoorDinatorApplication;


public class Local {
  public static int SDK_VERSION;

  private static float density;
  private static int widthPx;
  private static int heightPx;

  private static CoorDinatorApplication context;

  public static void init(CoorDinatorApplication app) {
    // 系统参数初始化
    SDK_VERSION = Build.VERSION.SDK_INT;
    context = app;
    initWindow();
  }


  private static void initWindow() {
    Resources resources = context.getResources();

    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
    widthPx = displayMetrics.widthPixels;
    heightPx = displayMetrics.heightPixels;
    density = displayMetrics.density;

    Configuration configuration = new Configuration();
    configuration.setToDefaults();
    resources.updateConfiguration(configuration, displayMetrics);
  }

  /**
   * 获取屏幕密度
   *
   * @return
   */
  public static float getDensity() {
    return density;
  }

  /**
   * 获取屏幕宽度
   *
   * @return
   */
  public static int getWidthPx() {
    return widthPx;
  }

  /**
   * 获取屏幕高度
   *
   * @return
   */
  public static int getHeightPx() {
    return heightPx;
  }

  /**
   * 根据手机的分辨率从dp的单位转成为px
   */
  public static int dip2px(float dpValue) {
    return (int) (dpValue * density + 0.5f);
  }

  /**
   * 根据手机的分辨率从px的单位转成为dp
   */
  public static int px2dip(float pxValue) {
    return (int) (pxValue / density + 0.5f);
  }
}
