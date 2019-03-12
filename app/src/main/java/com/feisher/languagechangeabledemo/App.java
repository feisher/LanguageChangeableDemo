package com.feisher.languagechangeabledemo;

import android.app.Application;
import android.content.res.Configuration;

import com.feisher.langlib.CLang;

/**
 * Created by feisher on 2019-03-04.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CLang.init(this);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        CLang.swithLang(this);
    }
}
