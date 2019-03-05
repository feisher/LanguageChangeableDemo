# LanguageChangeableDemo
### 多语言国际化支持库，暂只支持中英文切换，简单易用，无额外其他库依赖，修改可本地存储固化，

默认实现了Textview和Button,直接使用TextView2或Button2即可， 其他控件需要实现LangChangableView 接口，

        内部方法实现拷贝TextView2中的即可 使用前必须在Application中初始化 CLang.init(this);
        亦可指定语言环境使用 CLang.init(this, Locale.CHINA);初始化 需要修改语言的时候CLang.swithLang(context);即可，
        语言环境会使用SharedPreferences存储到本地
