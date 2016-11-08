package com.example.fupengpeng.treasurehunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import okhttp3.OkHttpClient;
import okhttp3.Request;



public class Test1107Activity extends AppCompatActivity {
    String url="www.baidu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1107);
        /*
        http请求：
            1.请求方式
            2.请求url
            3.请求头（根据服务器要求添加）
            4.请求体（get 有 post 没有）
         */

        /*
        响应请求：
            1.响应码
            2.响应头
            3.响应体
         */
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder()
                .url(url)
                .build();
    }
}
