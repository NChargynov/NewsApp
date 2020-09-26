package com.example.newsapp;

import android.app.Application;

import androidx.room.Room;

import com.example.newsapp.data.NewsRepository;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.data.remote.INewsStorage;
import com.example.newsapp.data.remote.NewsApiClient;
import com.example.newsapp.data.remote.NewsStorage;
import com.example.newsapp.db.NewsDataBase;

public class App extends Application {

    public static INewsApiClient iNewsApiClient;
    public static NewsRepository newsRepository;
    public static NewsDataBase newsDataBase;
    public static INewsStorage iNewsStorage;

    @Override
    public void onCreate() {
        super.onCreate();
        iNewsApiClient = new NewsApiClient();
        newsDataBase = Room.databaseBuilder(getApplicationContext(),
                NewsDataBase.class, "news.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        iNewsStorage = new NewsStorage(newsDataBase.newsDao());
        newsRepository = new NewsRepository(iNewsApiClient, iNewsStorage, newsDataBase.newsDao());
    }
}
