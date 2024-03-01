package com.example.projectp2_android.webservices;

import androidx.lifecycle.MutableLiveData;

import com.example.projectp2_android.MyApplication;
import com.example.projectp2_android.PostDao;
import com.example.projectp2_android.R;
import com.example.projectp2_android.entities.Post;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAPI {
    private MutableLiveData<List<Post>> postListData;
    private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    public PostAPI(MutableLiveData<List<Post>> postListData, PostDao dao) {
        this.postListData = postListData;
        this.dao = dao;

        // TODO connect URL to FooServer
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.serverUrl))
                .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        }
    public void get() {
        Call<List<Post>> call = webServiceAPI.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                new Thread(() -> {
                    //dao.clear();
                    //dao.insertList(response.body());
                    //TODO check if get or index
                    //postListData.postValue(dao.get());
                }).start();
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {}
        });
    }

}
