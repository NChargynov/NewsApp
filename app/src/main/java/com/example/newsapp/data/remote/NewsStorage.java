package com.example.newsapp.data.remote;

import androidx.lifecycle.LiveData;

import com.example.newsapp.db.NewsDao;
import com.example.newsapp.models.Article;

import java.util.List;

public class NewsStorage implements INewsStorage {
    private NewsDao dao;

    public NewsStorage(NewsDao dao) {
        this.dao = dao;
    }

    @Override
    public LiveData<List<Article>> getAllLive() {
        return dao.getAllLive();
    }
}
