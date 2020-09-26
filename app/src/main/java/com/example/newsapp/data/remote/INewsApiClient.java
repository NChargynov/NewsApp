package com.example.newsapp.data.remote;

import com.example.newsapp.core.IBaseCallBack;
import com.example.newsapp.models.Article;

import java.util.List;

public interface INewsApiClient {

    void getNewsHeadlines(String language, String apiKey, int page, int pageSize, NewsCallBack callBack);

    interface NewsCallBack extends IBaseCallBack<List<Article>>{

        @Override
        void onSuccess(List<Article> result);

        @Override
        void onFailure(Exception e);
    }

}
