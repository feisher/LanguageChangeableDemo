package com.feisher.langlib.util;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by feisher on 2019-03-05.
 */
public class AcLifeCB implements Application.ActivityLifecycleCallbacks {

    private MyBroadcastReceiver receiver;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d("feisher","onActivityCreated生命周期");
        receiver = new MyBroadcastReceiver(activity);
        IntentFilter filter= new IntentFilter();
        filter.addAction("android.intent.action.MyBroadcastReceiver");
//        activity.registerReceiver(receiver, filter)
        LocalBroadcastManager.getInstance(activity).registerReceiver(receiver, filter);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(receiver);
    }

    class MyBroadcastReceiver extends BroadcastReceiver{
        private  Activity activity;
        public MyBroadcastReceiver(Activity activity) {
            this.activity = activity;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("feisher","接受到修改语言广播");
            ViewUtil.updateViewLanguage(activity.findViewById(android.R.id.content));
        }
    }
}
