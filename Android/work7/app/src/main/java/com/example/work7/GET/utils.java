package com.example.work7.GET;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

interface receiveRes {
    void onSuccess(String resData);
    void onFailure(Exception e);
}

public class utils {

    //解析JSON
    public jsonData decodeJSON(String json) {
        jsonData jData = new jsonData();
        jData.data = new ArrayList<>();
        try {
            JSONObject jObject = new JSONObject(json);
            jData.errCode = jObject.getInt("errorCode");
            jData.errMsg = jObject.getString("errorMsg");
            JSONArray jArray = jObject.getJSONArray("data");
            jsonData.DD dd;
            for (int i = 0; i < jArray.length(); i++) {
                dd = new jsonData.DD();
                JSONObject temp = jArray.getJSONObject(i);
                dd.desc = temp.getString("desc");
                dd.id = temp.getInt("id");
                dd.imagePath = temp.getString("imagePath");
                dd.isVisible = temp.getInt("isVisible");
                dd.order = temp.getInt("order");
                dd.title = temp.getString("title");
                dd.type = temp.getInt("type");
                dd.url = temp.getString("url");
                jData.data.add(dd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jData;
    }

    //网络请求,GET
    public static class GETHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String quest = msg.obj.toString();
            new Thread(() -> {
                try {
                    //启动网络
                    URL url = new URL(quest);
                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    connect.setRequestMethod("GET");
                    connect.setConnectTimeout(8000);
                    connect.setReadTimeout(8000);
                    connect.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    connect.setRequestProperty("Accept-Encoding", "gzip,deflate");
                    connect.connect();
                    //解析返回信息
                    InputStream IS = connect.getInputStream();
                    utils tool = new utils();
                    String resData = tool.StreamToString(IS);

                    Handler handler=new GETActivity.GetInfo(Looper.getMainLooper());
                    Message message=Message.obtain();
                    message.what=2;
                    message.obj=resData;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Handler handler=new GETActivity.GetInfo(Looper.getMainLooper());
                    Message message=Message.obtain();
                    message.what=-1;
                    message.obj=e.toString();
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }).start();
        }
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
