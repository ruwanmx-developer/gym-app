package com.ruwan.reacongym.api;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalData {

    public static int getUserId(SharedPreferences lcData) {
        return lcData.getInt("userId", 0);
    }

    public static String getUserGender(SharedPreferences lcData) {
        return lcData.getString("userGender", "Male");
    }

    public static void saveUserId(SharedPreferences lcData, int id,String gender) {
        SharedPreferences.Editor editor = lcData.edit();
        editor.putInt("userId", id);
        editor.putString("userGender", gender);
        editor.apply();
    }

    public static int getBodyType(SharedPreferences lcData) {
        return lcData.getInt("userBodyType", 0);
    }

    public static void saveBodyType(SharedPreferences lcData, int type) {
        SharedPreferences.Editor editor = lcData.edit();
        editor.putInt("userBodyType", type);
        editor.apply();
    }

    public static String getServerIp(SharedPreferences lcData) {
        return lcData.getString("ip", "192.168.8.111");
    }

    public static void saveServerIp(SharedPreferences lcData, String ip) {
        SharedPreferences.Editor editor = lcData.edit();
        editor.putString("ip", ip);
        editor.apply();
    }
}
