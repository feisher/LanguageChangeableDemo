package com.feisher.langlib.util;

import android.view.View;
import android.view.ViewGroup;

import com.feisher.langlib.widget.LangChangableView;


/**
 * Created by george.yang on 2016-4-27.
 */
public class ViewUtil {
    public static void updateViewLanguage(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                updateViewLanguage(vg.getChildAt(i));
            }
        } else if (view instanceof LangChangableView) {
            LangChangableView tv = (LangChangableView) view;
            tv.reLoadLanguage();
        }
    }
}
