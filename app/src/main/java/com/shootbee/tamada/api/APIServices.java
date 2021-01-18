package com.shootbee.tamada.api;

import com.shootbee.tamada.model.Banner;
import com.shootbee.tamada.model.Option;
import com.shootbee.tamada.model.TiktokModel;
import com.shootbee.tamada.model.TwitterResponse;
import com.shootbee.tamada.model.Video;
import com.shootbee.tamada.model.story.FullDetailModel;
import com.shootbee.tamada.model.story.StoryModel;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIServices {
    @GET
    Observable<JsonObject> callResult(@Url String Value, @Header("Cookie") String cookie, @Header("User-Agent") String userAgent);

    @FormUrlEncoded
    @POST
    Observable<TwitterResponse> callTwitter(@Url String Url, @Field("id") String id);

    @GET
    Observable<TiktokModel> getTiktokData(@Url String Url, @Query("url") String url);

    @GET
    Observable<StoryModel> getStoriesApi(@Url String Value, @Header("Cookie") String cookie, @Header("User-Agent") String userAgent);

    @GET
    Observable<FullDetailModel> getFullDetailInfoApi(@Url String Value, @Header("Cookie") String cookie, @Header("User-Agent") String userAgent);

    @FormUrlEncoded
    @POST
    Observable<JsonObject> callSnackVideo(@Url String Url, @Field("shortKey") String shortKey, @Field("os") String os, @Field("sig") String sig, @Field("client_key") String client_key);

    @GET
    Call<List<Option>> getOptionsList(@Url String url);

    @GET
    Call<List<Video>> getVideosList(@Url String url);

    @GET
    Call<List<Banner>> getBanner(@Url String url);
}