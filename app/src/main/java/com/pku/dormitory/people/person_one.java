package com.pku.dormitory.people;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pku.dormitory.R;
import com.pku.dormitory.Util.SslUtils;
import com.pku.dormitory.bean.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tammy on 2017/12/11.
 */

public class person_one extends Activity{
    private static int a;
    private TextView nam;   //定义控件
    private TextView nu;
    private TextView genderTV,fl1n,fl2n,fl3n,fl4n;
    private Spinner select;
    //private ConfigUtil configUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personone);
        nu = (TextView) findViewById(R.id.xhTv);
        nam = (TextView) findViewById(R.id.nTv);
        genderTV = (TextView) findViewById(R.id.gdTv);
        fl1n = (TextView) findViewById(R.id.fl1n);
        fl2n = (TextView) findViewById(R.id.fl2n);
        fl3n = (TextView) findViewById(R.id.fl3n);
        fl4n = (TextView) findViewById(R.id.fl4n);
        String xh = ((MyApplication)getApplication()).getStudentid();
        String name = ((MyApplication)getApplication()).getName();
        String gender = ((MyApplication)getApplication()).getGender();
        nu.setText(xh);
        nam.setText(name);
        genderTV.setText(gender);
        getbuilding(gender);


    }
    public void getbuilding(String gender) {
        if(gender.equals("女")){
             a = 2;
        }else{
             a = 1;
        }
        String data = "gender=" + a;
        final String ip = "https://api.mysspku.com/index.php/V1/MobileCourse/getRoom?" + data;
        Log.d("activity_login", ip);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(ip);
                    //信任所有证书
                    if ("https".equalsIgnoreCase(url.getProtocol())) {
                        SslUtils.ignoreSsl();
                    }
                    //打开链接
                    conn = (HttpURLConnection) url.openConnection();
                    //GET请求
                    conn.setRequestMethod("GET");
                    //设置属性
                    conn.setReadTimeout(8000);//读取数据超时时间
                    conn.setConnectTimeout(8000);//连接的超时时间

                    InputStream is = conn.getInputStream();//字节流转换成字符串
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        response.append(str + "\n");
                        Log.d("activity_login", str);
                    }
                    //获取字符串
                    String responseStr = response.toString();
                    Log.d("activity_login", responseStr);
                    //解析JSON格式
                    getJSON(responseStr);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }

                }
            }
        }).start();

    }

    /**
     * JSON解析方法
     *
     * @param build
     */
    private void getJSON(String build) {
        try {
            //字符串转换为JSONObject对象
            JSONObject a = new JSONObject(build);
            String code = a.getString("errcode");
            if (code == "0") {//请求成功
                //从jsonObject对象中取出来key是data的对象
                JSONObject data = a.getJSONObject("data");
                if (data != null) {
                    //从data对象里取出
                    String fl1 = data.getString("5");
                    String fl2 = data.getString("13");
                    String fl3 = data.getString("14");
                    String fl4 = data.getString("8");
                    fl1n.setText(fl1);
                    fl2n.setText(fl2);
                    fl3n.setText(fl3);
                    fl4n.setText(fl4);

                } else {
                    Looper.prepare();
                    Toast.makeText(this, "请求失败！", Toast.LENGTH_SHORT).show();
                    Looper.loop();


                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
