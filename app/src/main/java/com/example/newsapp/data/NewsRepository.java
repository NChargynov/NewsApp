package com.example.newsapp.data;

import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.models.Article;

import java.util.List;

public class NewsRepository implements INewsApiClient {

    private INewsApiClient iNewsApiClient;

    public NewsRepository(INewsApiClient iNewsApiClient) {
        this.iNewsApiClient = iNewsApiClient;
    }


    @Override
    public void getNewsHeadlines(String language, String apiKey, final NewsCallBack callBack) {
        iNewsApiClient.getNewsHeadlines(language, apiKey, new NewsCallBack() {
            @Override
            public void onSuccess(List<Article> result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onFailure(Exception e) {
                callBack.onFailure(e);
            }
        });
    }
}
