package com.example.newsapp;

import android.app.Application;

import com.example.newsapp.data.NewsRepository;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.data.remote.NewsApiClient;

public class App extends Application {

    public static INewsApiClient iNewsApiClient;
    public static NewsRepository newsRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        iNewsApiClient = new NewsApiClient();
        newsRepository = new NewsRepository(iNewsApiClient);
    }
}
