package com.pku.dormitory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pku.dormitory.Util.SslUtils;
import com.pku.dormitory.bean.MyApplication;
import com.pku.dormitory.people.person_four;
import com.pku.dormitory.people.person_one;
import com.pku.dormitory.people.person_three;
import com.pku.dormitory.people.person_two;
import com.pku.dormitory.people.personthree;

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

public class person extends Activity implements View.OnClickListener{
    private int a;
    private String No;
    private TextView build1,build2,build3,build4;
    private Button mone,mtwo,mthree,mfour;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);
        String gender = ((MyApplication)getApplication()).getGender();
        getbuilding(gender);
        build1 = (TextView) findViewById(R.id.build1);
        build2 = (TextView) findViewById(R.id.build2);
        build3 = (TextView) findViewById(R.id.build3);
        build4 = (TextView) findViewById(R.id.build4);
        mone = (Button)findViewById(R.id.one);
        mone.setOnClickListener(this);
        mtwo = (Button)findViewById(R.id.two);
        mtwo.setOnClickListener(this);
        mthree = (Button)findViewById(R.id.three);
        mthree .setOnClickListener(this);
        mfour = (Button)findViewById(R.id.four);
        mfour.setOnClickListener(this);
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
                    build1.setText(fl1);
                    build2.setText(fl2);
                    build3.setText(fl3);
                    build4.setText(fl4);

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
        switch (view.getId()){
            case R.id.one:
//                No = String.valueOf(1);
//                ((MyApplication)getApplication()).setNo(No);
                Intent intent1 = new Intent(this, person_one.class);//页面跳转
                startActivity(intent1);//加载页面
                this.finish();//关闭此页面
                break;
            case R.id.two:
//                No = String.valueOf(2);
//                ((MyApplication)getApplication()).setNo(No);
                Intent intent2 = new Intent(this, person_two.class);//页面跳转
                startActivity(intent2);//加载页面
                this.finish();//关闭此页面
                break;
            case R.id.three:

                Intent intent3 = new Intent(this,person_three.class);//页面跳转
                startActivity(intent3);//加载页面
                this.finish();//关闭此页面
                break;
            case R.id.four:
//                No = String.valueOf(4);
//                ((MyApplication)getApplication()).setNo(No);
                Intent intent4 = new Intent(this, person_four.class);//页面跳转
                startActivity(intent4);//加载页面
                this.finish();//关闭此页面
                break;
                default:
                    break;
        }
    }
}
