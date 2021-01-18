package com.shootbee.tamada.api;

import android.app.Activity;
import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.shootbee.tamada.util.Utils.BASE_URL;


public class RestClient {
    private static Retrofit retrofit = null;
    private static final RestClient restClient = new RestClient();
    private static Activity mActivity;
    public static RestClient getInstance(Activity activity) {
        mActivity = activity;
        return restClient;
    }

    private RestClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.instagram.com/")
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
    }
    /*final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }*/

    public APIServices getService() {
        return retrofit.create(APIServices.class);
    }
    private void printMsg(String msg) {
        int chunkCount = msg.length() / 4050;
        for (int i = 0; i <= chunkCount; i++) {
            int max = 4050 * (i + 1);
            if (max >= msg.length()) {
                Log.d("Response::", msg.substring(4050 * i));
            } else {
                Log.d("Response::", msg.substring(4050 * i, max));
            }
        }
    }
}