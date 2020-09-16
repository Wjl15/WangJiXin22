package com.wangjixin22.wangjixin.lect10Net;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class NetFileSave {
        public static boolean saveUserInfo(Context context, String number, String password) {
            try {
                FileOutputStream fos = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
                fos.write((number + ":" + password).getBytes());
                fos.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        //从data.txt中去获取刚刚保存的账号密码
        public static Map<String, String> getUserInfo(Context context) {
            String content = "";
            try {
                FileInputStream fis = context.openFileInput("data.txt");
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);//读取
                content = new String(buffer);
                Map<String, String> userMap = new HashMap<String, String>();
                String[] infos = content.split(":");
                userMap.put("number", infos[0]);
                userMap.put("password", infos[1]);
                fis.close();
                return userMap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
    }