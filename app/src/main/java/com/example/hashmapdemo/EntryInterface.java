package com.example.hashmapdemo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EntryInterface {

    @GET("entries")
    Call<String> STRING_CALL();
}
