package com.example.tencenter.retrofitdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tencenter.retrofitdemo.interfaces.UseGsonService;
import com.example.tencenter.retrofitdemo.interfaces.UserBasicService;
import com.example.tencenter.retrofitdemo.pojo.Users;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private static final String BASE_URL_USER = "https://api.github.com";
    private Retrofit retrofitBasic;
    private Retrofit retrofitGson;


    //
    @NonNull
    private Button getBacic, getJson;
    private TextView result;
    //View
    private ImageView pic;
    private TextView title, id;
    private EditText paramEt;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        Init();
    }


    private void RetrofitBasicGet(String param) {
        retrofitBasic = new Retrofit.Builder()
                .baseUrl(BASE_URL_USER)
                .build();


        if (loading == null) {
            loading = new ProgressDialog(mContext);
            loading.show();
        }
        UserBasicService service = retrofitBasic.create(UserBasicService.class);
        Call<ResponseBody> call = service.getUsers(param);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (loading != null) {
                    loading.dismiss();
                    loading = null;
                }

                try {
                    String reslult = response.body().string();
                    Log.e("onResponse", reslult);
                    result.setText(reslult);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (loading != null) {
                    loading.dismiss();
                    loading = null;
                }
            }
        });
    }

    private void RetrofitGsonGet(String param) {
        retrofitGson = new Retrofit.Builder()
                .baseUrl(BASE_URL_USER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        if (loading == null) {
            loading = new ProgressDialog(mContext);
            loading.show();
        }

        UseGsonService service = retrofitGson.create(UseGsonService.class);
        Call<Users> call = service.getUser(param);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (loading != null) {
                    loading.dismiss();
                    loading = null;
                }

                Users uses = response.body();
                title.setText(uses.getLogin());
                id.setText(String.valueOf(uses.getId()));
                Glide.with(mContext).load(uses.getAvatar_url()).into(pic);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                if (loading != null) {
                    loading.dismiss();
                    loading = null;
                }
            }
        });

    }

    private void Init() {
        getBacic = (Button) findViewById(R.id.getbacic);
        getBacic.setOnClickListener(this);
        getJson = (Button) findViewById(R.id.getgson);
        getJson.setOnClickListener(this);
        result = (TextView) findViewById(R.id.result);
        paramEt = (EditText) findViewById(R.id.param);
        pic = (ImageView) findViewById(R.id.pic);
        title = (TextView) findViewById(R.id.tile);
        id = (TextView) findViewById(R.id.id);
    }

    @Override
    public void onClick(View v) {
        String param = paramEt.getText().toString();
        if (TextUtils.isEmpty(param)) {
            return;
        }
        switch (v.getId()) {
            case R.id.getbacic:
                RetrofitBasicGet(param);
                break;
            case R.id.getgson:
                RetrofitGsonGet(param);
                break;
            default:
                break;
        }
    }


}
