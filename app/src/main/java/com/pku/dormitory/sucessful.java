package com.pku.dormitory;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pku.dormitory.bean.MyApplication;

/**
 * Created by Tammy on 2017/12/15.
 */

public class sucessful extends Activity implements View.OnClickListener{
    private Button btn;
    private TextView nu,nam,genderTV,buildnum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sucessful);//加载布局
        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        setContentView(R.layout.mytext);
        nu = (TextView)findViewById(R.id.myxuehao);
        nam =(TextView)findViewById(R.id.myname);
        genderTV = (TextView)findViewById(R.id.mygender);
        buildnum = (TextView)findViewById(R.id.building_num);
        String xh = ((MyApplication)getApplication()).getStudentid();
        String name = ((MyApplication)getApplication()).getName();
        String gender = ((MyApplication)getApplication()).getGender();
        String  build= ((MyApplication)getApplication()).getBuildnum();
        nu.setText(xh);
        nam.setText(name);
        genderTV.setText(gender);
        buildnum.setText(build);


    }
}
