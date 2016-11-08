package com.example.fupengpeng.treasurehunt.demoOkHttpGitPackaging;

import com.example.fupengpeng.treasurehunt.demoOkHttpPost.User;

import okhttp3.Call;

/**
 * 如果有多个的话，方便管理使用请求
 * Created by Administrator on 2016/11/7.
 */
public interface UserApi {
    String URL_REGISTER="";
    Call getData(String url);
    Call register(User user);
}
