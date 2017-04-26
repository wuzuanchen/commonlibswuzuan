package com.wu.commonlibs.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 将返回的json结果转为List<Map<String, Object>>
 */
public class DataChangeUtils {

    /**
     * 把多个json 转换为List<Map<String, Object>>形式
     *单层获取list
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> getList(String jsonString) {
        List<Map<String, Object>> list = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject;
            list = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                list.add(getMap(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 多层json获取list
     * @param jsonString
     * @param parsmd
     * @return
     */
    public static List<Map<String, Object>> getListForMultiJson(String jsonString, String... parsmd) {
        List<Map<String, Object>> list = null;
        try {
            JSONObject object = new JSONObject(jsonString);
            for (int i = 0; i < parsmd.length - 1; i++) {
                object = object.getJSONObject(parsmd[i]);
            }
            JSONArray jsonArray = object.getJSONArray(parsmd[parsmd.length - 1]);
            list = new ArrayList<Map<String, Object>>();
            JSONObject jsonObject;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(getMap(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将json 数组转换为Map 对象
     *
     * @param jsonString
     * @return
     */
    public static Map<String, Object> getMap(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            @SuppressWarnings("unchecked")
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串毫秒数转yyyy-MM-dd hh:mm:ss时间格式
     *
     * @param str
     * @return
     */
    public static String getDateTimeByMillisecond(String str) {
        Date date = new Date(Long.valueOf(str));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    /**
     * 毫秒数转yyyyMMddHHmmss时间格式
     *
     * @param str
     * @return
     */
    public static String getNumDateTimeByMillisecond(String str) {
        Date date = new Date(Long.valueOf(str));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);
        return time;
    }
    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static String toJson(Object object){
        return gson.toJson(object);
    }
}
