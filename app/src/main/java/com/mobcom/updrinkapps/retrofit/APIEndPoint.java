package com.mobcom.updrinkapps.retrofit;

import com.mobcom.updrinkapps.models.MenuData;
import com.mobcom.updrinkapps.models.OrderData;
import com.mobcom.updrinkapps.models.UserData;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIEndPoint {

    @GET("Menu")
    Call<MenuData> getAllData();

    @GET("Order")
    Call<OrderData> getOrder(@Query("id_user") String id_user);

    @GET("Order")
    Call<OrderData> getAllOrder();

    @FormUrlEncoded
    @POST("Order")
    Call<OrderData> postOrder(@Field("id_user") String id_user,
                            @Field("full_name") String full_name,
                            @Field("total") String total,
                            @Field("transaction_code") String transaction_code,
                            @Field("status_order") int status_order);

    @GET("User")
    Call<UserData> getUser(@Query("id_user") String id_user, @Query("user_email") String user_email);

    @GET("User")
    Call<UserData> getAllUser();

    @FormUrlEncoded
    @POST("User")
    Call<UserData> postUser(@Field("id_user") String id_user,
                            @Field("person_name") String person_name,
                            @Field("user_email") String user_email,
                            @Field("user_img") String user_img);

//    @GET("Category")
//    Call<CategoryData> getAllCategory();
//
//    @GET("Category?")
//    Call<MenuByCategory> getMenu(@Query("id_category") String id_category);

}
