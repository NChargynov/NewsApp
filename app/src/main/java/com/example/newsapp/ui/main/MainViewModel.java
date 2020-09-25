package com.example.newsapp.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.App;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.models.Article;

import java.util.List;

public class MainViewModel extends ViewModel {

    MutableLiveData<List<Article>> news = new MutableLiveData<>();
    private List<Article> mNews;


    void receiveData() {
        App.newsRepository.getNewsHeadlines("ru", "34d3aa9ece5648a188062fe1b24c84fd",
                new INewsApiClient.NewsCallBack() {
            @Override
            public void onSuccess(List<Article> result) {
                mNews = result;
                news.setValue(mNews);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
