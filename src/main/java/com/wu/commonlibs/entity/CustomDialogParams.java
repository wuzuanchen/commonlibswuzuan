package com.wu.commonlibs.entity;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * 自定义对话框参数
 * Created by gw on 2017/3/9 0009.
 */

public class CustomDialogParams {
    Context context;
    int iconId;
    View v;
    int titleStr;
    int messageStr;
    int sureStr;
    int cancleStr;
    boolean isCancelable;
    DialogInterface.OnClickListener PositiveButtonListener;
    DialogInterface.OnClickListener NegativeButtonListener;
    //自定义视图的自定义确定取消的事件处理
    int customSureId;
    int customCancleId;
    View.OnClickListener customSureListener;
    View.OnClickListener customCancleListener;

    /**
     * 若没有设置监听事件，默认关闭对话框
     * @param context
     * @param isCancelable
     */
    public CustomDialogParams(Context context, boolean isCancelable) {
        this.context = context;
        this.isCancelable = isCancelable;
        this.v=null;
        this.PositiveButtonListener=null;
        this.NegativeButtonListener=null;
        this.customSureListener=null;
        this.customCancleListener=null;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public View getV() {
        return v;
    }

    public void setV(View v) {
        this.v = v;
    }

    public int getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(int titleStr) {
        this.titleStr = titleStr;
    }

    public int getMessageStr() {
        return messageStr;
    }

    public void setMessageStr(int messageStr) {
        this.messageStr = messageStr;
    }

    public int getSureStr() {
        return sureStr;
    }

    public void setSureStr(int sureStr) {
        this.sureStr = sureStr;
    }

    public int getCancleStr() {
        return cancleStr;
    }

    public void setCancleStr(int cancleStr) {
        this.cancleStr = cancleStr;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
    }

    public DialogInterface.OnClickListener getPositiveButtonListener() {
        return PositiveButtonListener;
    }

    public void setPositiveButtonListener(DialogInterface.OnClickListener positiveButtonListener) {
        PositiveButtonListener = positiveButtonListener;
    }

    public DialogInterface.OnClickListener getNegativeButtonListener() {
        return NegativeButtonListener;
    }

    public void setNegativeButtonListener(DialogInterface.OnClickListener negativeButtonListener) {
        NegativeButtonListener = negativeButtonListener;
    }

    public int getCustomSureId() {
        return customSureId;
    }

    public void setCustomSureId(int customSureId) {
        this.customSureId = customSureId;
    }

    public int getCustomCancleId() {
        return customCancleId;
    }

    public void setCustomCancleId(int customCancleId) {
        this.customCancleId = customCancleId;
    }

    public View.OnClickListener getCustomSureListener() {
        return customSureListener;
    }

    public void setCustomSureListener(View.OnClickListener customSureListener) {
        this.customSureListener = customSureListener;
    }

    public View.OnClickListener getCustomCancleListener() {
        return customCancleListener;
    }

    public void setCustomCancleListener(View.OnClickListener customCancleListener) {
        this.customCancleListener = customCancleListener;
    }
}
