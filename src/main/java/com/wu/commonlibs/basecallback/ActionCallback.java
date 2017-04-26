package com.wu.commonlibs.basecallback;


/**
 * Created by gw on 2017/4/26 0026.
 */

public interface ActionCallback<T> {
    /**
     * 操作成功
     * @param t
     */
    void suceess(T t);

    /**
     * 操作失败
     * @param e
     */
    void fail(Throwable e);
}
