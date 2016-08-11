package com.example.tencenter.retrofitdemo.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by co-mall on 2016/7/29.
 */
public interface UserBasicService {

    @GET("users/{user}")
    Call<ResponseBody> getUsers(@Path("user") String uses);
}
