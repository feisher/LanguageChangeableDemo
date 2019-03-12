package com.feisher.langlib;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

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
        Resources resources = application.getResources();
        Configuration config = resources.getConfiguration();
        init(application,Locale.ENGLISH);

    }
    public static void init(Application application,Locale locale) {
        String lang = SPUtils.get(application,"language","en");
        if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
            lang = "en";
        }else {
            lang = "zh";
        }
        SPUtils.put(application,"language",lang);
        swithLang(application);
    }

    public static void swithLang(Context context) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        Locale locale = config.locale;
        String lang = SPUtils.get(context,"language","en");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (lang.equals(Locale.ENGLISH.getLanguage())) {
                config.setLocale( Locale.CHINA);
                lang = "zh";
            }else {
                config.setLocale(Locale.ENGLISH );
                lang = "en";
            }
        } else {
            if (lang.equals(Locale.ENGLISH.getLanguage())) {
                config.locale = Locale.CHINA;

                lang = "zh";
            }else {
                config.locale = Locale.ENGLISH;
                lang = "en";
            }
        }
        SPUtils.put(context,"language",lang);

        // updateConfiguration
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(config, dm);
    }

}
