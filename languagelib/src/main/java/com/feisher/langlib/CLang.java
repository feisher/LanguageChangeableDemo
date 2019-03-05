package com.feisher.langlib;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;

import com.feisher.langlib.util.AcLifeCB;
import com.feisher.langlib.util.SPUtils;

import java.util.Locale;

/**
 * Created by feisher on 2019-03-04.
 */
public class CLang {
    /**
     * 初始化 需要在application中，默认环境是英文即原strings文件，需要添加values-zh 文件夹存放中文strings文件
     * 注意：刚开始启动会默认跟随系统语言
     * @param application
     */
    public static void init(Application application) {
        SPUtils.init(application);
        application.registerActivityLifecycleCallbacks(new AcLifeCB());
        Locale locale = Locale.ENGLISH;
        locale = SPUtils.get("language", "en").equals("en") ? Locale.ENGLISH : Locale.CHINA;
        Locale.setDefault(locale);
        defaultLang(application,locale);
    }
    public static void init(Application application,Locale locale) {
        SPUtils.init(application);
        application.registerActivityLifecycleCallbacks(new AcLifeCB());
        Locale.setDefault(locale);
        defaultLang(application,locale);
    }

    public static void swithLang(Context context) {

        Configuration config = context.getApplicationContext().getResources().getConfiguration();
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.locale = config.locale == Locale.ENGLISH ? Locale.CHINA : Locale.ENGLISH;
        resources.updateConfiguration(config, dm);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MyBroadcastReceiver");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        SPUtils.put("language", config.locale == Locale.ENGLISH ? "en" : "zh");


    }
    public static void defaultLang(Context context,Locale locale) {
        Configuration config = context.getApplicationContext().getResources().getConfiguration();
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.locale = locale;
        resources.updateConfiguration(config, dm);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MyBroadcastReceiver");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        SPUtils.put("language", config.locale == Locale.ENGLISH ? "en" : "zh");
    }

}
