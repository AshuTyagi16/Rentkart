package com.sasuke.rentkart.network;

import com.sasuke.rentkart.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by abc on 3/13/2018.
 */

public interface RentkartApiInterface {

    @POST("register.php")
    Call<User> registerUser(@Body User user);

    @POST("login.php")
    Call<User> loginUser(@Body User user);
}
