package com.wu.commonlibs.basecallback;

/**
 * 网络请求回调接口
 * Created by gw on 2017/3/8 0008.
 */

public interface HttpCallBackListener<T> {

    void onFinish(T t);

    void onError(Throwable e);
}
