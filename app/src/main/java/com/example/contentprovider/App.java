package com.example.contentprovider;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class App extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Crash crash = Crash.getInstance();
        crash.init(getApplicationContext());
    }
}
