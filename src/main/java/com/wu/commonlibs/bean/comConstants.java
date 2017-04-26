package com.wu.commonlibs.bean;


import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by gw on 2017/3/9 0009.
 */

public class comConstants {
    public static String LOGTAG="wu";
    public static String BASE_HTTP;
    public static  boolean isApkDebugable = false;

    /**
     * 测试还是发布版的地址
     * @param context
     * @param realurl  发布地址
     * @param debugurl 测试地址
     */
    public static void isApkDebugable(Context context,String realurl,String debugurl) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            isApkDebugable = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
        }

        if (isApkDebugable) BASE_HTTP = debugurl;
        else BASE_HTTP = realurl;
    }


}
