package com.example.fupengpeng.treasurehunt.demoOkHttpPost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.fupengpeng.treasurehunt.R;
import com.example.fupengpeng.treasurehunt.demoOkHttpGitPackaging.NetClient;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OkHttpPostActivity extends AppCompatActivity {
    @BindView(R.id.et_username)
    EditText mETUserName;
    @BindView(R.id.et_password)
    EditText mETPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_post);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_ok_http_post)
    public void onClick() {

        User user = new User(mETUserName.getText().toString(),mETPassWord.getText().toString());

//        // 去进行请求
//        NetClient.getInstance().register(user).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("TAG","--请求失败"+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.i("TAG","--响应成功");
//            }
//        });
        NetClient.getInstance().register(user).enqueue(new UICallback() {
            @Override
            public void onFailureInUI(Call call, IOException e) {

            }

            @Override
            public void onResponseInUI(Call call, Response response) {

            }
        });

    }
}
