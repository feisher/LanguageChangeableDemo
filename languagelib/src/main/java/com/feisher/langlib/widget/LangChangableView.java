package com.feisher.langlib.widget;

import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;

/*
* @author feisher
* @emil 458079442@qq.com
* create at 2019-03-04
* description: 可改变语言的view，所有需要多语言适配的view都要implements这个接口，重新方法直接复制粘贴即可
*
*/
public interface LangChangableView {
    String ANDROIDXML = "http://schemas.android.com/apk/res/android";

    //由于setText无法被重写，需要添加以下三个必要的方法，如果你的app不需要对多语言的textview修改值(只是xml写死就够了)，那就不需要实现他们
    void setTextById(@StringRes int id);//手动设置textId
    void setTextWithString(String text);//手动去掉textId,不然重新加载语言的时候会被重置掉
    void setTextByArrayAndIndex(@ArrayRes int arrId, @StringRes int arrIndex);//手动通过TextArray设置语言

    void reLoadLanguage();//修改语言时主要调用的方法
}
