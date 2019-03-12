# LanguageChangeableDemo
### 多语言国际化支持库，暂只支持中英文切换，简单易用，无额外其他库依赖，修改可本地存储固化，无需重启


## 集成方式

[![](https://jitpack.io/v/feisher/LanguageChangeableDemo.svg)](https://jitpack.io/#feisher/LanguageChangeableDemo)

#### 1.在你的项目根目录 build.gradle 结尾添加(Add it in your root build.gradle at the end of repositories):
```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
#### 2. 在modle的gradle中添加依赖
```groovy
dependencies {
	        implementation 'com.github.feisher:LanguageChangeableDemo:v1.1.0'
    		//注意上面jitpack版本号，
	}
```
## 1.1.0版本发布，，采用完全和1.0.0不同的方案，抛弃需要重写控件的限制，如下
```继承方式同上，版本号为1.1.0```
####1.使用前必须在`Application`中初始化
```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CLang.init(this);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        CLang.swithLang(this);//这里为1.1.0版本新增处理方式，，兼容安卓8.0以上系统
    }
}
```
#### 2.调用方式
```java
findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLang.swithLang(MainActivity.this);//注意这里要传activity，否则8.0以上会失效
                MainActivity.this.recreate();//这句代码很有必要,如设置页面从其他页面跳转则需要再onActivtyRsult中recreate();
                //针对组件化使用Router框架的：如设置页面有接受传入数据，需要自行处理重新加载逻辑
            }
        });
```



## 以下为1.0版本使用方式和介绍，，上方为1.1版本使用方式介绍

默认实现了`Textview`和`Button`,直接使用`TextView2`或`Button2`即可， 其他控件需要实现`LangChangableView` 接口，

内部方法实现拷贝`TextView2`中的即可 使用前必须在`Application`中初始化
```java
CLang.init(this);
```
亦可指定语言环境使用 
```java
CLang.init(this, Locale.CHINA); 
```
初始化 需要修改语言的时候
```java
CLang.swithLang(context);
```
即可，语言环境会使用`SharedPreferences`存储到本地

![演示图](https://github.com/feisher/LanguageChangeableDemo/blob/master/device-2019-03-05-105142.gif?raw=true)

## PS:注意事项

#### 为了简化流程默认 以 values/strings.xml 文件作为英文文件  以   values-zh/strings.xml 作为中文文件，，具体操作可参考demo





#### 问题

发现bug或好的建议欢迎 [issues](https://github.com/feisher/LanguageChangeableDemo/issues) or Email 458079442@qq.com

#### 微信交流

![微信](https://github.com/feisher/LanguageChangeableDemo/blob/master/97af74c179108dc703cf3535de8b73b3.png?raw=true)
