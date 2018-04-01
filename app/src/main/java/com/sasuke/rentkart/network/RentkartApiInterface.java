package com.sasuke.rentkart.network;

import com.sasuke.rentkart.model.Category;
import com.sasuke.rentkart.model.Item;
import com.sasuke.rentkart.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by abc on 3/13/2018.
 */

public interface RentkartApiInterface {

    @GET("register.php")
    Call<User> registerUser(@Query("name") String name, @Query("username") String username,
                            @Query("email") String email, @Query("password") String password,
                            @Query("phone_number") String phone_number);

    @GET("login.php")
    Call<User> loginUser(@Query("email") String email, @Query("password") String password);

    @GET("getCategories.php")
    Call<ArrayList<Category>> getCategories();

    @GET("getItemsForCategoryId.php")
    Call<ArrayList<Item>> getItemsForCategory(@Query("id") int categoryId);
}
