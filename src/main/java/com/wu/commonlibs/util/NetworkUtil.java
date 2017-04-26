package com.wu.commonlibs.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class NetworkUtil {
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;

	@SuppressLint("NewApi")
	public static int getNetworkType(Context context) {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!extraInfo.isEmpty()) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}
	/**
	 * 是否开启 wifi true：开启 false：关闭
	 *
	 * @param isEnable
	 */
	public static void setWifi(Context context, boolean isEnable) {
		WifiManager mWm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (isEnable) {// 开启wifi
			if (!mWm.isWifiEnabled()) {
				mWm.setWifiEnabled(true);
			}
		} else {
			// 关闭 wifi
			if (mWm.isWifiEnabled()) {
				mWm.setWifiEnabled(false);
			}
		}

	}

	/**
	 * wifi true是否开启
	 */
	public static boolean isWifiOpen(Context context) {
		WifiManager mWm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (!mWm.isWifiEnabled()) {
			return false;
		} else return true;

	}

}
