package com.wu.commonlibs.util;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.wu.commonlibs.R;
import com.wu.commonlibs.basecallback.HttpCallBackListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mali on 2016-08-21.
 */
public class HttpUtil {
    static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getWay(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            return null;
        }

    }

    /**
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static String postWay(String url, Map<String, String> params) throws IOException {
        //公共参数可以在此添加
//        params.put("","");
        Request request = buildPostRequst(url, params);
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            return null;
//            throw new IOException("Unexpected code " + response);
        }

    }

    /**
     * @param url
     * @param params
     * @return
     */
    public static Request buildPostRequst(String url, Map<String, String> params) {
        Request request = null;
        if (params == null) {
            params = new HashMap<>();
        }
        if (params != null) {

            Set<Map.Entry<String, String>> entries = params.entrySet();
            FormEncodingBuilder builder = new FormEncodingBuilder();
            for (Map.Entry<String, String> entry : entries) {
                builder.add(entry.getKey(), entry.getValue());
            }
            request = new Request.Builder().url(url).post(builder.build()).build();
        }
        return request;
    }

    public static PostBuilder newPost(String url) {
        return new PostBuilder(url);
    }

    public static class PostBuilder {
        String url;
        Headers.Builder headersBuilder = new Headers.Builder();

        public PostBuilder(String url) {
            this.url = url;
        }

        public PostBuilder header(String name, String value) {
            headersBuilder.add(name, value);
            return this;
        }

        public String sendJson(String json) {
            RequestBody requestBody = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .headers(headersBuilder.build())
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    return response.body().toString();
                }
            } catch (Exception e) {
                Log.d("fa", "post failure", e);
                return 400 + "";
            }
            return null;
        }
    }

    /**
     * 获取的整个返回结果
     * @param url
     * @return
     * @throws IOException
     */
    public static Response getRespose(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response;
        } else {
              return null;
        }
    }


    /**
     * 通过Observable请求数据
     * @param context
     * @param httpurl              请求地址
     * @param tishistr             提示语，可以ull，不显示。
     * @param httpCallBackListener 回调，在里面的onError实现请求超时处理//请求只有成功和失败
     */
    public static void doHttpGetDatasByObservable(final Context context, final String httpurl, final String tishistr, final HttpCallBackListener httpCallBackListener) {

        if (NetworkUtil.isNetworkConnected(context)) {
            final Dialog mDialog = DialogUtil.createLoadingDialog(context,false, tishistr,null);
            if (tishistr != null)
                mDialog.show();
            Observable
                    .create(new Observable.OnSubscribe<String>() {

                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            try {
                                subscriber.onNext(getWay(httpurl));
                            } catch (Exception e) {
                                subscriber.onError(e);
                            } finally {
                                subscriber.onCompleted();
                            }
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            mDialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable t) {
                            httpCallBackListener.onError(t);
                            Log.d("hu", "req errror", t);
                        }

                        @Override
                        public void onNext(String result) {
                            httpCallBackListener.onFinish(result);

                        }
                    });
        } else {
            //没有网络
            DialogUtil.toast(context, context.getString(R.string.checknetstate), 1000);
        }
    }
}
