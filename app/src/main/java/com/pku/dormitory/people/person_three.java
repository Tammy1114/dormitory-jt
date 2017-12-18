package com.pku.dormitory.people;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pku.dormitory.R;
import com.pku.dormitory.Util.SslUtils;
import com.pku.dormitory.bean.MyApplication;
import com.pku.dormitory.sucessful;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tammy on 2017/12/11.
 */

public class person_three extends Activity implements View.OnClickListener{
    private TextView nam;   //定义控件
    private TextView nu;
    private TextView genderTV;
    private Button btn;
    private EditText stu1id,v1code,stu2id,v2code;
    private String select2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personthree);
        nu = (TextView) findViewById(R.id.xuehao);
        nam = (TextView) findViewById(R.id.xingming);
        genderTV = (TextView) findViewById(R.id.xingbie);
        stu1id = (EditText) findViewById(R.id.stu3_1);
        v1code = (EditText) findViewById(R.id.v3code_1);
        stu2id = (EditText) findViewById(R.id.stu3_2);
        v2code = (EditText) findViewById(R.id.v3code_2);
        btn = (Button) findViewById(R.id.btn3);
        btn.setOnClickListener(this);
        String xh = ((MyApplication)getApplication()).getStudentid();
        String name = ((MyApplication)getApplication()).getName();
        String gender = ((MyApplication)getApplication()).getGender();
        nu.setText(xh);
        nam.setText(name);
        genderTV.setText(gender);
        initViews();

    }

    @Override
    public void onClick(View view) {
        //拿到学号,密码的值
        String  build= ((MyApplication)getApplication()).getBuildnum();
        String xh = ((MyApplication)getApplication()).getStudentid();
        final String stu1 = stu1id.getText().toString();
        final String v1cd = v1code.getText().toString();
        final String stu2 = stu2id.getText().toString();
        final String v2cd = v2code.getText().toString();
        if (TextUtils.isEmpty(stu1) || TextUtils.isEmpty(stu2) ) {
            Toast.makeText(getApplicationContext(), "学号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(v1cd) || TextUtils.isEmpty(v2cd)) {
            Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(stu1.trim()) || TextUtils.isEmpty(v1cd.trim()) || TextUtils.isEmpty(stu2.trim()) || TextUtils.isEmpty(v2cd.trim())){
            Toast.makeText(this,"用户名或者验证码不能为空",Toast.LENGTH_LONG).show();
        }else {
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("num",4);
            params.put("stuid",xh);
            params.put("stu1id",stu1);
            params.put("v1code",v1cd);
            params.put("stu2id",stu2);
            params.put("v2code",v2cd);
            params.put("buildingNo",build);
            ByPost(params, "utf-8");
        }

    }

    void initViews() {
        List<String> list = new ArrayList<String>();
        list.add("5号楼");
        list.add("9号楼");
        list.add("13号楼");
        list.add("14号楼");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        final Spinner sp = (Spinner) findViewById(R.id.select2);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tvResult = (TextView) findViewById(R.id.buildingt);
                //((MyApplication)getApplication()).setBuildnum(tvResult);
                //获取Spinner控件的适配器
                select2 = (String) sp.getSelectedItem();
                //int i = Integer.parseInt(select3);
                ((MyApplication)getApplication()).setBuildnum(select2);
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                tvResult.setText(adapter.getItem(position));

            }
            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void ByPost(Map<String,Object> params,String encode){
        final byte[] data = getRequestData(params, encode).toString().getBytes();//获得请求体
        final String ip = "https://api.mysspku.com/index.php/V1/MobileCourse/SelectRoom";
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
                    //POST请求
                    conn.setRequestMethod("POST");
                    //设置属性
                    conn.setReadTimeout(8000);//读取数据超时时间
                    conn.setConnectTimeout(8000);//连接的超时时间
                    //拼接处要提交的字符串
                    // @SuppressWarnings("deprecation")
                    //String data ="num"+b+"stuid"+ URLEncoder.encode(xh,"UTF-8")+"stu1id"+URLEncoder.encode(stu1,"UTF-8")+"v1code"+URLEncoder.encode(v1cd,"UTF-8")+"buildingNO"+i;
                    //为post添加两行属性
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                    //因为post是通过流往服务器提交数据的，所以我们需要设置一个输出流
                    //设置打开输出流
                    conn.setDoOutput(true);
                    //拿到输出流
                    OutputStream os = conn.getOutputStream();
                    //使用输出流向服务器提交数据
                    os.write(data);
                    os.flush();
                    InputStream is = conn.getInputStream();//字节流转换成字符串
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        response.append(str + "\n");
                        Log.d("activity_login",str);
                    }
                    //获取字符串
                    String responseStr1 = response.toString();
                    Log.d("activity_login",responseStr1 );
                    getReturn(responseStr1);

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
    public static StringBuffer getRequestData(Map<String, Object>params,String encode){
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for(Map.Entry<String,Object> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode((String) entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;

    }
    private void getReturn(String data){
        try {
            //字符串转换为JSONObject对象
            JSONObject a = new JSONObject(data);
            String code = a.getString("errcode");
            if (code == "0") {//请求成功
                Intent intent = new Intent(this, sucessful.class);//页面跳转
                startActivity(intent);//加载页面
                this.finish();//关闭此页面
            } else {
                Looper.prepare();
                Toast.makeText(this, "请求失败！", Toast.LENGTH_SHORT).show();
                Looper.loop();


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
