package com.example;

/**
 * Created by co-mall on 2016/9/2.
 */
public interface MyService {
    @BindGet
    void getInfo();

    @BindPost
    void postInfo();
}
