package com.example.tencenter.retrofitdemo.interfaces;

import com.example.tencenter.retrofitdemo.pojo.Users;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by co-mall on 2016/8/11.
 */
public interface UseGsonService {
    @GET("/users/{user}")
    Call<Users> getUser(@Path("user") String user);
}
