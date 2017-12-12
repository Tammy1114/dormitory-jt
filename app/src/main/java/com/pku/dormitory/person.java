package com.pku.dormitory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.pku.dormitory.people.person_four;
import com.pku.dormitory.people.person_one;
import com.pku.dormitory.people.person_three;
import com.pku.dormitory.people.person_two;


/**
 * Created by Tammy on 2017/12/11.
 */

public class person extends Activity implements View.OnClickListener{
    private Button mone,mtwo,mthree,mfour;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);
        mone = (Button)findViewById(R.id.one);
        mone.setOnClickListener(this);
        mtwo = (Button)findViewById(R.id.two);
        mtwo.setOnClickListener(this);
        mthree = (Button)findViewById(R.id.three);
        mthree .setOnClickListener(this);
        mfour = (Button)findViewById(R.id.four);
        mfour.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Intent intent1 = new Intent(this, person_one.class);//页面跳转
        //startActivity(intent1);//加载页面
        //this.finish();//关闭此页面
        switch (view.getId()){
            case R.id.one:
                Intent intent1 = new Intent(this, person_one.class);//页面跳转
                startActivity(intent1);//加载页面
                this.finish();//关闭此页面
                break;
            case R.id.two:
                Intent intent2 = new Intent(this, person_two.class);//页面跳转
                startActivity(intent2);//加载页面
                this.finish();//关闭此页面
                break;
            case R.id.three:
                Intent intent3 = new Intent(this, person_three.class);//页面跳转
                startActivity(intent3);//加载页面
                this.finish();//关闭此页面
                break;
            case R.id.four:
                Intent intent4 = new Intent(this, person_four.class);//页面跳转
                startActivity(intent4);//加载页面
                this.finish();//关闭此页面
                break;
                default:
                    break;
        }
    }
}
