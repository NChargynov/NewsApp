package com.example.newsapp.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.App;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.models.Article;

import java.util.List;

public class MainViewModel extends ViewModel {

    MutableLiveData<List<Article>> news = new MutableLiveData<>();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    LiveData<List<Article>> newsData = App.iNewsStorage.getAllLive();
//    public static Integer page = 1;


    void setIsLoading(){
        isLoading.setValue(true);
    }

    void receiveData(Integer pageSize, Integer page) {
        App.newsRepository.getNewsHeadlines("ru", "34d3aa9ece5648a188062fe1b24c84fd", pageSize, page,
                new INewsApiClient.NewsCallBack() {
            @Override
            public void onSuccess(List<Article> result) {
                news.setValue(result);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }
}
