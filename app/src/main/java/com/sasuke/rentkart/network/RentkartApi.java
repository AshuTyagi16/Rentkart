package com.sasuke.rentkart.network;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sasuke.rentkart.model.CartItem;
import com.sasuke.rentkart.model.Category;
import com.sasuke.rentkart.model.Item;
import com.sasuke.rentkart.model.User;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public Call<User> registerUser(String name, String username, String email, String password, String phone_number) {
        return service.registerUser(name, username, email, password, phone_number);
    }

    public Call<User> loginUser(String email, String password) {
        return service.loginUser(email, password);
    }

    public void getCategories(@NonNull final OnGetCategoriesListener onGetCategoriesListener) {
        service.getCategories().enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                onGetCategoriesListener.onGetCategoriesSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Category>> call, Throwable t) {
                onGetCategoriesListener.onGetCategoriesFailure(t);
            }
        });
    }

    public void getItemsForCategory(int userId, int categoryId, final OnGetItemsForCategoryListener onGetItemsForCategoryListener) {
        service.getItemsForCategory(userId, categoryId).enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                onGetItemsForCategoryListener.onGetItemsForCategorySuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                onGetItemsForCategoryListener.onGetItemsForCategoryFailure(t);
            }
        });
    }

    public void getItemsList(int userId, final OnGetItemsListener onGetItemsListener) {
        service.getItemsList(userId).enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                onGetItemsListener.onGetItemsSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                onGetItemsListener.onGetItemsFailure(t);
            }
        });
    }

    public void addItemToCart(int userId, int itemId, int price, final OnAddItemListener onAddItemListener) {
        service.addItemToCart(userId, itemId, price).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                onAddItemListener.onAddItemSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onAddItemListener.onAddItemFailure(t);
            }
        });
    }

    public void removeItemFromCart(int userId, int itemId, final OnRemoveItemListener onRemoveItemListener) {
        service.removeItemFromCart(userId, itemId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                onRemoveItemListener.onRemoveItemSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onRemoveItemListener.onRemoveItemFailure(t);
            }
        });
    }

    public void getUserCart(int userId, final OnGetUserCartListener onGetUserCartListener) {
        service.getUserCart(userId).enqueue(new Callback<ArrayList<CartItem>>() {
            @Override
            public void onResponse(Call<ArrayList<CartItem>> call, Response<ArrayList<CartItem>> response) {
                onGetUserCartListener.onGetUserCartSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<CartItem>> call, Throwable t) {
                onGetUserCartListener.onGetUserCartFailure(t);
            }
        });
    }

    /*************LISTENERS*************/
    public interface OnGetCategoriesListener {
        void onGetCategoriesSuccess(ArrayList<Category> list);

        void onGetCategoriesFailure(Throwable t);
    }

    public interface OnGetItemsForCategoryListener {
        void onGetItemsForCategorySuccess(ArrayList<Item> list);

        void onGetItemsForCategoryFailure(Throwable t);
    }

    public interface OnGetItemsListener {
        void onGetItemsSuccess(ArrayList<Item> list);

        void onGetItemsFailure(Throwable t);
    }

    public interface OnAddItemListener {
        void onAddItemSuccess(ResponseBody responseBody);

        void onAddItemFailure(Throwable t);
    }

    public interface OnRemoveItemListener {
        void onRemoveItemSuccess(ResponseBody responseBody);

        void onRemoveItemFailure(Throwable t);
    }

    public interface OnGetUserCartListener {
        void onGetUserCartSuccess(ArrayList<CartItem> list);

        void onGetUserCartFailure(Throwable t);
    }


    /********Interceptor********/

    private OkHttpClient createHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(logging);

        return builder.build();
    }
}
