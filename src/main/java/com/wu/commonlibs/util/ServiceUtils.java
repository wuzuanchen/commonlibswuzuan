package com.wu.commonlibs.util;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wu.commonlibs.bean.comConstants;

/**
 * Created by li on 0019/7/19.
 */
public class ServiceUtils {
    /**
     * 启动服务
     * @param context
     * @param cls
     */
    public static void startService(Context context, Class<? extends Service> cls){
        if(!isRunning(context, cls)) {
            Log.i(comConstants.LOGTAG, "starting  name ="+cls.getName()+" service ");
            context.startService(new Intent(context, cls));
        }
    }

    public static boolean isRunning(Context context, Class<? extends Service> cls) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
