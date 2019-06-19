package com.alatheer.menu.service;

import com.alatheer.menu.models.AmountModel;
import com.alatheer.menu.models.Country;
import com.alatheer.menu.models.IngredientsModel;
import com.alatheer.menu.models.OrderToUploadModel;
import com.alatheer.menu.models.RestaurantMenuModel;
import com.alatheer.menu.models.Restaurantdata;
import com.alatheer.menu.models.UserModel;
import com.alatheer.menu.models.aboutUsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Services {

    @FormUrlEncoded
    @POST("Api/addUser")
    Call<UserModel> register(@Field("user_name") String user_name,
                             @Field("user_pass") String user_pass,
                             @Field("user_phone") String user_phone,
                             @Field("user_email") String user_email,
                             @Field("country_id_fk") String country_id_fk);

    @FormUrlEncoded
    @POST("Api/Login")
    Call<UserModel> Login(@Field("user_phone") String user_phone,
                          @Field("user_pass") String user_pass);

    @GET("Api/countries")
    Call<List<Country>> getCountries();


    @GET("Api/aboutUs")
    Call<aboutUsModel> getaboutUs();
    @GET("Api/conditions")
    Call<aboutUsModel> getconditions();
    @GET("Api/privacy")
    Call<aboutUsModel> getprivacy();

//    @GET("Api/getLastProducts/{country_id_fk}")
//    Call<List<LastProductsModel>> getLastProducts(@Path("country_id_fk") String country_id_fk);

    @GET("Api/searchResult/{cityId}")
    Call<Restaurantdata> getRestaurant(@Path("cityId") String cityId);

    @GET("Api/getMainProducts/{restId}")
    Call<List<RestaurantMenuModel>> getRestaurantmenu(@Path("restId") String restId);

    @GET("Api/getSubProducts/{mainId}")
    Call<List<RestaurantMenuModel>>  getSubProducts(@Path("mainId") String mailId);

    @GET("Api/getOfferDetails/{mainId}")
    Call<List<RestaurantMenuModel>> getOfferDetails(@Path("mainId") String mailId);


    @GET("Api/getmostSales/{mainId}")
    Call<List<RestaurantMenuModel>> mostSales(@Path("mainId") String mailId);

    @GET("Api/getProductUnits/{subProductId}")
    Call<List<AmountModel>> getAmounts(@Path("subProductId") String subProductId);

    @GET("Api/getProductIngredients/{subProductId}")
    Call<List<IngredientsModel>> getIngredients(@Path("subProductId") String subProductId);

    @FormUrlEncoded
    @POST("Api/searchByName")
    Call<Restaurantdata> getSearchRestaurant (@Field("partName") String partName);




    @POST("Api/saveOrders")
    Call<UserModel> uploadOrder(@Body OrderToUploadModel orderToUploadModel);



}
