package com.example.fupengpeng.treasurehunt.demoOkHttpGitPackaging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.example.fupengpeng.treasurehunt.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpGetActivity2 extends AppCompatActivity{
//    @BindView(R.id.btn_ok_http_get)
//    Button mBtnGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_get);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_ok_http_get)
    public void onClick(){
        NetClient.getInstance().getData("https://api.github.com/users/gqq").enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }


}
