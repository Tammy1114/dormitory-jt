package com.pku.dormitory;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pku.dormitory.Util.SslUtils;
import com.pku.dormitory.bean.MyApplication;
import com.pku.dormitory.people.person_one;

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

public class info extends Activity implements View.OnClickListener{
    private TextView nam;   //定义控件
    private TextView nu;
    private TextView genderTV;
    private TextView codeTV;
    private Button btnTV;
    //public static String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);//加载布局
        Intent intent =this.getIntent();
        String username=intent.getStringExtra("username");
        //Intent i = new Intent(this,person_one.class);
        //i.putExtra("username",username);
        //System.out.println(username);
        nam = (TextView) findViewById(R.id.nm);//拿到布局的控件，并强制类型转换
        nu =(TextView) findViewById(R.id.stu);
        genderTV = (TextView) findViewById(R.id.gd);
        codeTV = (TextView) findViewById(R.id.vc);
        btnTV = (Button) findViewById(R.id.button1);

        btnTV.setOnClickListener(this);

        loginByGet(username);
    }


    public void loginByGet(String student) {

        String data = "stuid=" + student;
        final String ip = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?" + data;
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
     * @param stuid
     */
    private void getJSON(String stuid) {
        try {
            //字符串转换为JSONObject对象
            JSONObject a = new JSONObject(stuid);
            String code = a.getString("errcode");
            if (code == "0") {//请求成功
                //从jsonObject对象中取出来key是data的对象
                JSONObject data = a.getJSONObject("data");
                if (data != null) {
                    //从data对象里取出
                    String stu = data.getString("studentid");
                    String name = data.getString("name");
                    ((MyApplication)getApplication()).setName(name);
                    String gender = data.getString("gender");
                    ((MyApplication)getApplication()).setGender(gender);
                    String vcode = data.getString("vcode");
                    nu.setText(stu);
                    nam.setText(name);
                    genderTV.setText(gender);
                    codeTV.setText(vcode);


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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, person.class);//页面跳转
        startActivity(intent);//加载页面
        this.finish();//关闭此页面
    }
}