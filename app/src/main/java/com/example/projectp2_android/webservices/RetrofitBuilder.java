package com.example.projectp2_android.webservices;

import com.example.projectp2_android.R;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitBuilder {
    private static Retrofit retrofit;
    private static volatile RetrofitBuilder instance;
    public static Retrofit getInstance() {
        if (retrofit == null) {
            synchronized (RetrofitBuilder.class) {
                if (retrofit == null) {
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS) // Increase connection timeout
                            .readTimeout(30, TimeUnit.SECONDS) // Increase read timeout
                            .writeTimeout(30, TimeUnit.SECONDS) // Increase write timeout
                            .build();

                    retrofit = new Retrofit.Builder().
                            baseUrl("http://172.20.10.2:3001/api/")
                            .client(okHttpClient)
                            .callbackExecutor(Executors.newSingleThreadExecutor())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
