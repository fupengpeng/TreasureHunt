package com.example.fupengpeng.treasurehunt.demoOkHttpGitPackaging;

import com.example.fupengpeng.treasurehunt.demoOkHttpPost.User;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 用于获取okHttpClient对象，在一个应用中只需要一个okHttpClient对象，为了节约资源
 * Created by Administrator on 2016/11/7.
 */
public class NetClient implements UserApi{
    private static NetClient netClient;
    private final OkHttpClient okHttpClient;
    private NetClient(){//私有的构造方法，创建OkHttpClient对象
        /*
        okHttp 提供了拦截器的功能，能将请求和响应的数据拦截下来，展示出来
            1.添加依赖
            2.使用：给OkHttpClient设置
         */
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();//初始化拦截器
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//设置拦截的信息层级

        okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(interceptor)//添加拦截器
                .build();
    }
    public static synchronized NetClient getInstance(){
        if (netClient==null){
            netClient=new NetClient();
        }
        return netClient;
    }

    @Override
    public Call getData(String url){
        Request request=new Request.Builder()//2.Http请求的构建
                .get()
                .url(url)
//                .addHeader("","")
//                .addHeader("","")
                .build();
        return okHttpClient.newCall(request);
    }

    @Override
    public Call register(User user) {
        Gson gson = new Gson();
        RequestBody requestBody=RequestBody.create(null,gson.toJson(user));
        Request request=new Request.Builder()
                .post(requestBody)
                .url(UserApi.URL_REGISTER)
                .build();
        return okHttpClient.newCall(request);
    }
}
