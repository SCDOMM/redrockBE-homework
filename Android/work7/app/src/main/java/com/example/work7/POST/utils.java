package com.example.work7.POST;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class utils {
    public static class MessageData {
        private String URL;
        private String Account;
        private String Password;
        private String rePassword;
        public MessageData(String url,String account,String password,String rePassword){
            this.Account=account;
            this.URL=url;
            this.Password=password;
            this.rePassword=rePassword;
        }
    }
    //网络请求，但是是POST
    public class POSTHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            MessageData data = (MessageData) msg.obj;
            ginPOST(data);
        }
    }
    private void ginPOST(MessageData data) {
        new Thread(() -> {
            try {
                URL url = new URL(data.URL);
                HttpURLConnection CN = (HttpURLConnection) url.openConnection();
                CN.setRequestMethod("POST");
                CN.setConnectTimeout(8000);
                CN.setReadTimeout(8000);
                CN.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                CN.setRequestProperty("Accept-Encoding", "gzip,deflate");

                HashMap<String, String> params = new HashMap<>();
                params.put("username", data.Account);
                params.put("password", data.Password);
                params.put("repassword", data.rePassword);

                StringBuilder DTW = new StringBuilder();
                for (String key : params.keySet()) {
                    DTW.append(key).append("=").append(params.get(key)).append("&"); // 拼接参数
                }
                CN.connect();
                OutputStream OS = CN.getOutputStream(); // 打开
                OS.write(DTW.substring(0, DTW.length() - 1).getBytes());
                InputStream IS=CN.getInputStream();
                String responseData=StreamToString(IS);

                Handler HD=new POSTActivity.response(Looper.getMainLooper());
                Message message=new Message();
                message.what=2;
                message.obj=responseData;
                HD.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    public String StreamToString(InputStream IS) {
        StringBuilder SB = new StringBuilder();
        String oneLine;
        BufferedReader reader = new BufferedReader(new InputStreamReader(IS));
        try {
            while ((oneLine = reader.readLine()) != null) {
                SB.append(oneLine).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                IS.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return SB.toString();
    }
}
