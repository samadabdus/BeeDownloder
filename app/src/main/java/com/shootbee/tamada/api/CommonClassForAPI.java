package com.shootbee.tamada.api;

import android.app.Activity;

import com.shootbee.tamada.model.Banner;
import com.shootbee.tamada.model.Option;
import com.shootbee.tamada.model.TiktokModel;
import com.shootbee.tamada.model.TwitterResponse;
import com.shootbee.tamada.model.Video;
import com.shootbee.tamada.model.story.FullDetailModel;
import com.shootbee.tamada.model.story.StoryModel;
import com.shootbee.tamada.util.Utils;
import com.google.gson.JsonObject;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CommonClassForAPI {
    private static Activity mActivity;
    private static CommonClassForAPI CommonClassForAPI;
    public static CommonClassForAPI getInstance(Activity activity) {
        if (CommonClassForAPI == null) {
            CommonClassForAPI = new CommonClassForAPI();
        }
        mActivity = activity;
        return CommonClassForAPI;
    }
    public void callResult(final DisposableObserver observer,String URL,String Cookie) {
        if (Utils.isNullOrEmpty(Cookie)){
            Cookie="";
        }
        RestClient.getInstance(mActivity).getService().callResult(URL,Cookie,
                "Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(JsonObject o) {
                        observer.onNext(o);
                    }
                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }
                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }
    public void callTwitterApi(final DisposableObserver observer, String URL, String twitterModel) {
        RestClient.getInstance(mActivity).getService().callTwitter(URL,twitterModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TwitterResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(TwitterResponse o) {
                        observer.onNext(o);
                    }
                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }
                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void getStories(final DisposableObserver observer,String Cookie) {
        if (Utils.isNullOrEmpty(Cookie)){
            Cookie="";
        }
        RestClient.getInstance(mActivity).getService().getStoriesApi("https://i.instagram.com/api/v1/feed/reels_tray/",Cookie,
                "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StoryModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(StoryModel o) {
                        observer.onNext(o);
                    }
                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }
                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }
    public void getFullDetailFeed(final DisposableObserver observer,String UserId,String Cookie) {
        RestClient.getInstance(mActivity).getService().getFullDetailInfoApi(
                "https://i.instagram.com/api/v1/users/"+UserId+"/full_detail_info?max_id="
                ,Cookie, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FullDetailModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(FullDetailModel o) {
                        observer.onNext(o);
                    }
                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }
                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void callTiktokVideo(final DisposableObserver observer, String Url) {
        RestClient.getInstance(mActivity).getService().getTiktokData(Utils.TikTokUrl,Url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TiktokModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(TiktokModel o) {
                        observer.onNext(o);
                    }
                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }
                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void callSnackVideoData(final DisposableObserver observer, String URL, String shortKey, String os, String sig, String client_key) {
        RestClient.getInstance(mActivity).getService().callSnackVideo(URL,shortKey,os,sig,client_key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(JsonObject o) {
                        observer.onNext(o);
                    }
                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }
                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }
    public void getOptionsList(String url, Callback<List<Option>> callback)
    {
        Call<List<Option>> call = RestClient.getInstance(mActivity).getService().getOptionsList(url);
        call.enqueue(callback);
    }
    public void getVideosList(String url, Callback<List<Video>> callback)
    {
        Call<List<Video>> call = RestClient.getInstance(mActivity).getService().getVideosList(url);
        call.enqueue(callback);
    }
    public void getBanner(String url, Callback<List<Banner>> callback)
    {
        Call<List<Banner>> call = RestClient.getInstance(mActivity).getService().getBanner(url);
        call.enqueue(callback);
    }


}