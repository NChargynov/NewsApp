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


    void receiveData(int page, int pageSize) {
        App.newsRepository.getNewsHeadlines("ru", "9941da606ad2474c8a3c60939772cada", page, pageSize,
                new INewsApiClient.NewsCallBack() {
                    @Override
                    public void onSuccess(List<Article> result) {
                        news.setValue(result);
                        isLoading.setValue(true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("ololo", "error" + e);
                    }
                });
    }
}
