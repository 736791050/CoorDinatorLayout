package com.test.git.coordinatorlayout;

import android.app.Application;

import com.test.git.coordinatorlayout.Utils.Local;

/**
 * Created by lk on 16/8/13.
 */
public class CoorDinatorApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Local.init(this);
    }
}
