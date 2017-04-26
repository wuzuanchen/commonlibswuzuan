package com.wu.commonlibs.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.wu.commonlibs.R;
import com.wu.commonlibs.entity.CustomDialogParams;

import java.lang.reflect.Field;

/**
 * Created by li on 0029/8/29.
 */
public class DialogUtil {

    /**
     * 自定义toast的显示时间
     *
     * @param context
     * @param msg
     * @param times
     */
    public static void toast(Context context, String msg, int times) {
        Toast.makeText(context, msg, times).show();
    }

    /**
     * 自定义toast显示位置和显示时间
     *
     * @param context
     * @param msg
     * @param times
     * @param gravity
     */
    public static void toastGravity(Context context, String msg, int times, int gravity) {
        Toast toast = Toast.makeText(context, msg, times);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    /**
     * 加载Dialog
     *
     * @param context
     * @param iscancel  返回键是否可以关闭对话
     * @param msg     提示语
     * @param draw    是否更改背景
     * @return
     */
    public static Dialog createLoadingDialog(Context context,boolean iscancel, String msg, Drawable draw) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view

        if (draw != null)
            v.setBackground(draw);

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        if (msg != null)
            tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(iscancel);// 是否可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;

    }

    /**
     * 弹出网络状态不佳 对话框
     *建议使用custom对话框实现自己的对话提示
     * @param mc
     * @param listener
     */
    public static void showNetStateNotGoodDialog(Context mc, DialogInterface.OnClickListener listener) {
        CustomDialogParams custoparanms = new CustomDialogParams(mc, false);
        custoparanms.setTitleStr(R.string.netstateisnotgood);
        custoparanms.setSureStr(R.string.sure);
        custoparanms.setPositiveButtonListener(listener);
        DialogUtil.showCustomDialog(custoparanms);
    }

    /**
     * 弹出自定义的对话框
     *
     * @param customDialogParams 根据自己的需求来设置参数
     */
    public static void showCustomDialog(CustomDialogParams customDialogParams) {
        AlertDialog.Builder builder = new AlertDialog.Builder(customDialogParams.getContext()).setCancelable(customDialogParams.isCancelable());
        //设置所需的元素 start
        if (customDialogParams.getIconId() != 0) {
            builder.setIcon(customDialogParams.getIconId());
        }
        if (customDialogParams.getV() != null) {
            builder.setView(customDialogParams.getV());
        }
        if (customDialogParams.getTitleStr() != 0) {
            builder.setTitle(customDialogParams.getTitleStr());
        }
        if (customDialogParams.getMessageStr() != 0) {
            builder.setMessage(customDialogParams.getMessageStr());
        }
        if (customDialogParams.getSureStr() != 0 && customDialogParams.getPositiveButtonListener() != null) {
            builder.setPositiveButton(customDialogParams.getSureStr(), customDialogParams.getPositiveButtonListener());

        }
        if (customDialogParams.getCancleStr() != 0 && customDialogParams.getNegativeButtonListener() != null) {
            builder.setNegativeButton(customDialogParams.getCancleStr(), customDialogParams.getNegativeButtonListener());
        }
        //设置所需的元素 end

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        if (customDialogParams.getCustomSureId() != 0 && customDialogParams.getCustomSureListener() != null) {
            customDialogParams.getV().findViewById(customDialogParams.getCustomSureId()).
                    setOnClickListener(customDialogParams.getCustomSureListener());
        }
        if (customDialogParams.getCustomCancleId() != 0 && customDialogParams.getCustomCancleListener() != null) {
            customDialogParams.getV().findViewById(customDialogParams.getCustomCancleId()).
                    setOnClickListener(customDialogParams.getCustomCancleListener());
        }

    }

    /**
     * 返回自定义的对话框
     *
     * @param customDialogParams
     * @return
     */
    public static Dialog customDialog(CustomDialogParams customDialogParams) {
        AlertDialog.Builder builder = new AlertDialog.Builder(customDialogParams.getContext()).setCancelable(customDialogParams.isCancelable());
        //设置所需的元素 start
        if (customDialogParams.getIconId() != 0) {
            builder.setIcon(customDialogParams.getIconId());
        }
        if (customDialogParams.getV() != null) {
            builder.setView(customDialogParams.getV());
        }
        if (customDialogParams.getTitleStr() != 0) {
            builder.setTitle(customDialogParams.getTitleStr());
        }
        if (customDialogParams.getMessageStr() != 0) {
            builder.setMessage(customDialogParams.getMessageStr());
        }
        if (customDialogParams.getSureStr() != 0 && customDialogParams.getPositiveButtonListener() != null) {
            builder.setPositiveButton(customDialogParams.getSureStr(), customDialogParams.getPositiveButtonListener());

        }
        if (customDialogParams.getCancleStr() != 0 && customDialogParams.getNegativeButtonListener() != null) {
            builder.setNegativeButton(customDialogParams.getCancleStr(), customDialogParams.getNegativeButtonListener());
        }
        //设置所需的元素 end

        return builder.create();

    }


    public static final int DELAY = 1000;
    private static long lastClickTime = 0;

    /**
     * 防止用户连续提交
     *
     * @return
     */
    public static boolean isNotFastClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > DELAY) {
            lastClickTime = currentTime;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 强制打开或关闭对话框
     *
     * @param dialog
     * @param b
     */
    public static void keepOrCloseDialog(DialogInterface dialog, boolean b) {

        try {
            Field field = dialog.getClass()
                    .getSuperclass().getDeclaredField(
                            "mShowing");
            field.setAccessible(true);
            // 将mShowing变量设为false，表示对话框已关闭
            field.set(dialog, b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
