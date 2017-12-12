package com.pku.dormitory.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tammy on 2017/12/12.
 */

public class ConfigUtil {
    private static ConfigUtil configUtil;
    private SharedPreferences userConfig;
    private ConfigUtil(Context context){
        init(context);

    }
    public static ConfigUtil getConfigUtil(Context context){
        if (configUtil == null){
            configUtil = new ConfigUtil(context);
        }
        return configUtil;
    }
    private void init(Context context){
        userConfig = context.getSharedPreferences("selectdormitory",Context.MODE_PRIVATE);
    }
    //获取登录状态
    public Boolean getLogined(){
        return userConfig.getBoolean("logined",false);
    }
    //保存登录状态
    public void setLogined(Boolean flag){
        userConfig.edit().putBoolean("logined",flag).commit();
    }
    //获取用户状态
    public String getusername(){
        return userConfig.getString("username","");
    }
    //保存用户状态
    public void setusername(String username){
        userConfig.edit().putString("username",username).commit();
    }
}
