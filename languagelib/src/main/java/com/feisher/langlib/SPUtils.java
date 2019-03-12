package com.feisher.langlib;

import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * sp文件存取的封装处理
 *
 * @Author feisher  on 2017/10/17 9:47 修改
 * Email：458079442@qq.com
 */
public class SPUtils {
    //保存在手机里面的文件名
    public static final String FILE_NAME = "spCache";
    public static SharedPreferences sp;

    private static Context mContext;

    public static SharedPreferences init(Context context) {
        mContext = context.getApplicationContext();
        return sp = context.getApplicationContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 存
     *
     * @param key   键
     * @param value 值
     * @param <E>   泛型，自动根据值进行处理
     */
    public static <E> void put( String key,  E value) {
        put(mContext, key, value);

    }

    /**
     * 取
     *
     * @param key          键
     * @param defaultValue 默认值
     * @param <E>          泛型，自动根据值进行处理
     * @return
     */
    public static <E> E get( String key,  E defaultValue) {
        return get(mContext, key, defaultValue);
    }

    /**
     * 插件间和宿主共用数据 必须 传入context
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static <E> void put(Context context,  String key,  E value) {
        SharedPreferences.Editor editor = init(context).edit();
        if (value instanceof String || value instanceof Integer || value instanceof Boolean ||
                value instanceof Float || value instanceof Long || value instanceof Double) {
            editor.putString(key, String.valueOf(value));
        } else {
            try {
                editor.putString(key, serialize(value));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SPCompat.apply(editor);
    }


    /**
     * 插件间和宿主共用数据 必须 传入context
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static <E> E get(Context context,  String key,  E defaultValue) {
        String value = init(context).getString(key, String.valueOf(defaultValue));
        if (defaultValue instanceof String) {
            return (E) value;
        }
        if (defaultValue instanceof Integer) {
            return (E) Integer.valueOf(value);
        }
        if (defaultValue instanceof Boolean) {
            return (E) Boolean.valueOf(value);
        }
        if (defaultValue instanceof Float) {
            return (E) Float.valueOf(value);
        }
        if (defaultValue instanceof Long) {
            return (E) Long.valueOf(value);
        }
        if (defaultValue instanceof Double) {
            return (E) Double.valueOf(value);
        }
        try {
            return (E) deSerialization(value);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.remove(key);
        SPCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.clear();
        SPCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        return init(context).contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        return init(context).getAll();
    }

    /**
     * 保存对象到sp文件中 被保存的对象须要实现 Serializable 接口
     *
     * @param key
     * @param value
     */
    public static void saveObject(String key, Object value) {
        put(key, value);
    }

    /**
     * desc:获取保存的Object对象
     *
     * @param key
     * @return modified:
     */
    public static <T> T readObject(String key, Class<T> clazz) {
        try {
            return (T) get(key, clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author
     */
    private static class SPCompat {
        private static final Method S_APPLY_METHOD = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (S_APPLY_METHOD != null) {
                    S_APPLY_METHOD.invoke(editor);
                    return;
                }
            } catch (Exception e) {
            }
            editor.commit();
        }
    }


    /**
     * 序列化对象
     *
     * @return
     * @throws IOException
     */
    private static  <E>String serialize(  E object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static <E>E deSerialization(String str) throws IOException, ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        E object = (E) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return object;
    }
}
