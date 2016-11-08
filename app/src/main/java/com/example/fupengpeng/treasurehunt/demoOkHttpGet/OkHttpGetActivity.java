package com.example.fupengpeng.treasurehunt.demoOkHttpGet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.Button;

import com.example.fupengpeng.treasurehunt.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpGetActivity extends AppCompatActivity {
    @BindView(R.id.btn_ok_http_get)
    Button mBtnGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_get);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_ok_http_get)
    public void onClick(){
        /*
        OkHttpGet请求
            1.请求操作类 OkHttpClient
            2.构建请求
            3.执行请求
            4.得到响应之后，处理响应数据
         */
        OkHttpClient okHttpClient=new OkHttpClient();//1.初始化OkHttpClient
        Request request=new Request.Builder()//2.Http请求的构建
                .get()
                .url("https://api.github.com/users/gqq")
//                .addHeader("","")
//                .addHeader("","")
                .build();
        okhttp3.Call call=okHttpClient.newCall(request);//3.Http请求的发送
//        call.execute();//4.同步执行
        call.enqueue(new Callback() {//4.异步执行
            /**
             * 失败
             * @param call
             * @param e
             */
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                //后台线程  不能做ui操作
                Log.e("aaaa", "onFailure: 请求失败" );
            }

            /**
             * 成功响应
             * @param call
             * @param response
             * @throws IOException
             */
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                //后台线程  不能做ui操作
                Log.e("aaaa", "onFailure: 请求成功" );
            }
        });

    }
}
