package com.wu.commonlibs.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.wu.commonlibs.basecallback.ActionCallback;
import com.wu.commonlibs.bean.comConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * Created by gw on 2017/4/26 0026.
 */

public class FileUtil {
    public static int length;//文件总长度
    public static int downloaded;// 下载的长度

    /**
     * 请求服务器，获取要下载文件的大小
     */
    public static void length(String URL) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(URL).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            conn.connect();
            // 要下载文件的大小
            length = conn.getContentLength();
            Log.v(comConstants.LOGTAG,"file length = "+length);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 下载,若是apk则会自动安装,自动打开文件
     *
     * @param context
     * @param httpURL
     * @param fname
     */
    public static void downloadSimpleFile(Context context, String httpURL, String localUrl, String fname,boolean islocal, ActionCallback actionCallback) {
        length(httpURL);
        HttpURLConnection conn = null;
        try {

            conn = (HttpURLConnection) new URL(httpURL).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            // 设置要做输入的操作
            conn.setDoInput(true);
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = conn.getInputStream();
                // 文件下载到SDCard上
                File file = new File(localUrl,
                        fname);
                FileOutputStream out = new FileOutputStream(file);
                byte[] buff = new byte[1024];
                // len 每次读取的长度
                int len = 0;
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                    // 累计下载量
                    downloaded += len;
                }

                out.close();
                in.close();

                if (islocal)
                openFile(context, localUrl,actionCallback);
                else openFile(context,httpURL,actionCallback);


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 打开一个文件
     *
     * @param filePath 文件的绝对路径
     */
    public static void openFile(Context context, final String filePath, ActionCallback actionCallback) {
        String ext = filePath.substring(filePath.lastIndexOf('.')).toLowerCase(Locale.US);
        try {
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String temp = ext.substring(1);
            String mime = mimeTypeMap.getMimeTypeFromExtension(temp);

            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            File file = new File(filePath);
            intent.setDataAndType(Uri.fromFile(file), mime);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.v(comConstants.LOGTAG,"无法打开后缀名为. "  + ext + "的文件！Exception=" +e.toString());
            //无法打开文件时
            actionCallback.fail(e);
        }
    }
//    /**
//     * 根据文件后缀名获得对应的MIME类型。
//     * @param file
//     */
//    public static String getMIMEType(File file) {
//
//        String type = "*/*";
//        String fName = file.getName();
//        //获取后缀名前的分隔符"."在fName中的位置。
//        int dotIndex = fName.lastIndexOf(".");
//        if (dotIndex < 0) {
//            return type;
//        }
//        /* 获取文件的后缀名*/
//        String end=fName.substring(dotIndex,fName.length()).toLowerCase();
//        if(end=="")return type;
////在MIME和文件类型的匹配表中找到对应的MIME类型。
//        for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
//            if(end.equals(MIME_MapTable[i][0]))
//                type = MIME_MapTable[i][1];
//        }
//        return type;
//    }
//    //{后缀名，MIME类型}
//    private static String[][] MIME_MapTable={
//            {".apk", "application/vnd.android.package-archive"},
//            {".bin", "application/octet-stream"},
//            {".class", "application/octet-stream"},
//            {".doc", "application/msword"},
//            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
//            {".xls", "application/vnd.ms-excel"},
//            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
//            {".exe", "application/octet-stream"},
//            {".gtar", "application/x-gtar"},
//            {".jar", "application/java-archive"},
//            {".gz", "application/x-gzip"},
//            {".js", "application/x-JavaScript"},
//            {".pps", "application/vnd.ms-powerpoint"},
//            {".ppt", "application/vnd.ms-powerpoint"},
//            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
//            {".msg", "application/vnd.ms-outlook"},
//            {".tgz", "application/x-compressed"},
//            {".z", "application/x-compress"},
//            {".zip", "application/x-zip-compressed"}, {".pdf", "application/pdf"},
//            {".mpc", "application/vnd.mpohun.certificate"},
//            {".wps", "application/vnd.ms-works"},
//            {".rtf", "application/rtf"},
//            {".tar", "application/x-tar"},
//
//            {".3gp", "video/3gpp"},
//            {".m4u", "video/vnd.mpegurl"},
//            {".m4v", "video/x-m4v"},
//            {".mov", "video/quicktime"},
//            {".asf", "video/x-ms-asf"},
//            {".avi", "video/x-msvideo"},
//            {".mp4", "video/mp4"},
//            {".mpe", "video/mpeg"},
//            {".mpeg", "video/mpeg"},
//            {".mpg", "video/mpeg"},
//            {".mpg4", "video/mp4"},
//
//            {".m3u", "audio/x-mpegurl"},
//            {".m4a", "audio/mp4a-latm"},
//            {".m4b", "audio/mp4a-latm"},
//            {".m4p", "audio/mp4a-latm"},
//            {".mp2", "audio/x-mpeg"},
//            {".mp3", "audio/x-mpeg"},
//            {".mpga", "audio/mpeg"},
//            {".ogg", "audio/ogg"},
//            {".rmvb", "audio/x-pn-realaudio"},
//            {".wav", "audio/x-wav"},
//            {".wma", "audio/x-ms-wma"},
//            {".wmv", "audio/x-ms-wmv"},
//
//            {".bmp", "image/bmp"},
//            {".gif", "image/gif"},
//            {".jpeg", "image/jpeg"},
//            {".jpg", "image/jpeg"},
//            {".png", "image/png"},
//
//            {".h", "text/plain"},
//            {".htm", "text/html"},
//            {".html", "text/html"},
//            {".c", "text/plain"},
//            {".conf", "text/plain"},
//            {".cpp", "text/plain"},
//            {".java", "text/plain"},
//            {".prop", "text/plain"},
//            {".rc", "text/plain"},
//            {".sh", "text/plain"},
//            {".txt", "text/plain"},
//            {".xml", "text/plain"},
//            {".log", "text/plain"},
//
//            {"", "*/*"}
//    };
}
