package com.example.fupengpeng.treasurehunt.demoOkHttpPost;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Callback的封装，实现Handler更新UI
 * Created by Administrator on 2016/11/8.
 */
public abstract class UICallback implements Callback{
    private Handler handler=new Handler(Looper.getMainLooper());//Looper.getMainLooper()获取主线程的Looper
    @Override
    public void onFailure(final Call call, final IOException e) {//
        handler.post(new Runnable() {
            @Override
            public void run() {//运行在主线程，可更新UI
                //运行在主线程
                onFailureInUI(call,e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //运行在主线程
                onResponseInUI(call,response);
            }
        });
    }
    //提供给请求成功或者失败的方法，让其可以运行在UI中
    public abstract void onFailureInUI(Call call, IOException e);//需要在Activity中必须重写，故写成抽象方法
    public abstract void onResponseInUI(Call call, Response response);
}
