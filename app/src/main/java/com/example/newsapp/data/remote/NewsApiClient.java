package com.example.newsapp.data.remote;

import com.example.newsapp.models.News;
import com.example.newsapp.ui.main.recycler.NewsAdapter;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NewsApiClient {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    NewsApi client = retrofit.create(NewsApi.class);



    public interface NewsApi{
        @GET("v2/top-headlines")
        Call<News> getNewsHeadlines(
                @Query("country") String language,
                @Query("apiKey") String apiKey);
    }
}
