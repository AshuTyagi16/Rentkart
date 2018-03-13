package com.sasuke.rentkart.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sasuke.rentkart.model.User;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abc on 3/13/2018.
 */

public class RentkartApi {

    public final static int CONNECTION_TIMEOUT = 30;
    private final static int READ_TIMEOUT = 30;
    private final static int WRITE_TIMEOUT = 30;
    public static final String BASE_API_URL = "http://rentkart.000webhostapp.com/";

    private static volatile RentkartApi instance;

    private RentkartApiInterface service;

    private RentkartApi() {
    }

    public static RentkartApi getInstance() {
        if (instance == null) {
            synchronized (RentkartApi.class) {
                if (instance == null) {
                    instance = new RentkartApi();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init() {
        Gson gson = new GsonBuilder().create();
        OkHttpClient httpClient = createHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

        service = retrofit.create(RentkartApiInterface.class);
    }

    public Call<User> registerUser(User user) {
        return service.registerUser(user);
    }

    public Call<User> loginUser(User user) {
        return service.loginUser(user);
    }

    private OkHttpClient createHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(logging);

        return builder.build();
    }
}
