package com.whu.bingo.bingoidea.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zengbin on 2017/8/23.
 */

public class SharedPreferenceUtil {
    public static String[] contentstring = new String[16];
    public static String EXTRA_INFO = "bingoidea_contentstring";


    public static String getExtraInfo(String key, Context content){
        return getSharedPreference(EXTRA_INFO,content).getString(key,"");
    }

    public static void setExtraInfo(String key,String value,Context context){
        getEditor(EXTRA_INFO,context).putString(key,value).apply();
    }

    public static SharedPreferences getSharedPreference(String preferencename, Context ctx){
        return ctx.getSharedPreferences(preferencename,Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(String preferencename, Context ctx){
        return getSharedPreference(preferencename,ctx).edit();
    }
}

