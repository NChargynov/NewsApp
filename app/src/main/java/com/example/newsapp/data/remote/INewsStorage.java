package com.example.newsapp.data.remote;

import androidx.lifecycle.LiveData;

import com.example.newsapp.models.Article;

import java.util.List;

public interface INewsStorage {

    LiveData<List<Article>> getAllLive();
}
