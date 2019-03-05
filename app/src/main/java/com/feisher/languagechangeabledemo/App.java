package com.feisher.languagechangeabledemo;

import android.app.Application;

import com.feisher.langlib.CLang;

import java.util.Locale;

/**
 * Created by feisher on 2019-03-04.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CLang.init(this, Locale.CHINA);
    }

}
