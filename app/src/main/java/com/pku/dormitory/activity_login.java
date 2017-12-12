package com.pku.dormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pku.dormitory.Util.ConfigUtil;
import com.pku.dormitory.Util.SslUtils;
import com.pku.dormitory.bean.MyApplication;
import com.pku.dormitory.bean.User;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Created by Tammy on 2017/12/9.
 */

public class activity_login extends Activity implements View.OnClickListener {
    private EditText usernameTV;   //定义控件
    private EditText passwordTV;
    private Button log;
    private ConfigUtil configUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);//加载布局
        configUtil = ConfigUtil.getConfigUtil(this);
        initView();//调用一个方法
    }
    //获取数据的方法
    void initView() {
        usernameTV = (EditText) findViewById(R.id.usernameTV);//拿到布局的控件，并强制类型转换
        passwordTV = (EditText) findViewById(R.id.passwordTV);
        log = (Button) findViewById(R.id.log);
        log.setOnClickListener(this);

    }

    //添加OnClick事件
    @Override
    public void onClick(View view) {

        //拿到学号,密码的值
        final String username = usernameTV.getText().toString().trim();
        final String password = passwordTV.getText().toString().trim();
        //进行输入验证,如果没有输入的话进行提示
       if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "学号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
       if (TextUtils.isEmpty(username.trim()) || TextUtils.isEmpty(password.trim())){
           Toast.makeText(this,"用户名或者密码不能为空",Toast.LENGTH_LONG).show();
       }else {
           //调用get请求的方法
           loginByGet(usernameTV.getText().toString(),passwordTV.getText().toString());
       }

    }


    public void loginByGet(String username, String password) {

        //if (username.length() == 0 || password.length() == 0) {
          //  NetUtil.showToast(activity_login.this, "请输入账户或密码");
          //  return;
       // }
        String data = "username=" + username + "&password=" + password;
        final String ip = "https://api.mysspku.com/index.php/V1/MobileCourse/Login?"+ data;
        Log.d("activity_login", ip);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(ip);
                    //信任所有证书
                    if("https".equalsIgnoreCase(url.getProtocol())){
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
                        //String content=JsonTool.readStream(is);//将json数据转化为字符串  
                    // String weather=JsonTool.readJsonString(content,"live","weather");//获得值 
                        while ((str = reader.readLine()) != null) {
                            response.append(str + "\n");
                            Log.d("activity_login",str);
                        }
                        //获取字符串
                        String responseStr = response.toString();
                        Log.d("activity_login",responseStr );
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
     * @param Jsondata
     */
    private void getJSON(String Jsondata){
        final String username = usernameTV.getText().toString();
        try{

            JSONObject a = new JSONObject(Jsondata);
            System.out.println(a.get("errcode"));
            String code = a.getString("errcode");
            if(code.equals("0")){//请求成功
                //System.out.println("OK");//测试是否是否成功
                //configUtil.setLogined(true);
                //configUtil.setusername(username+"");
                String stu = usernameTV.getText().toString();
                ((MyApplication)getApplication()).setStudentid(stu);//共享
                Intent intent = new Intent(this, info.class);//页面跳转
                intent.putExtra("username",username);
                startActivity(intent);//加载页面
                this.finish();//关闭此页面
             }else if (code.equals("40001")){//用户名或者密码错误
                Looper.prepare();
                Toast.makeText(activity_login.this, "学号不存在" , Toast.LENGTH_SHORT).show();
                Looper.loop();
              }else if (code.equals("40002")){
                Looper.prepare();
                Toast.makeText(activity_login.this, "密码错误" , Toast.LENGTH_SHORT).show();
                Looper.loop();
            }else if(code.equals("40009")){
                Looper.prepare();
                Toast.makeText(activity_login.this, "参数错误" , Toast.LENGTH_SHORT).show();
                Looper.loop();
            }else {
                Looper.prepare();
                Toast.makeText(activity_login.this, "请求失败！" , Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
   /* public static String readStream(InputStream is) throws Exception{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer =  new byte[1024];
        int len = 0;
        while ((len = is.read(buffer))!= -1){
            byteArrayOutputStream.write(buffer,0,len);
        }
        is.close();
        byteArrayOutputStream.close();
        String content = new String(byteArrayOutputStream.toByteArray());
        return content;
    }
    public String readJsonString(String string,String obj){
        String Str = null;
        try{
            JSONObject jsonObject = new JSONObject(string);
            Str = jsonObject.getString(obj);
            //JSONArray jsonArray = jsonObject.getJSONArray(key);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Str;
    }*/

}

