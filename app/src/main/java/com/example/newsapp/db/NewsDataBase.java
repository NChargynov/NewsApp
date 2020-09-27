package com.example.newsapp.db;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.newsapp.models.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class NewsDataBase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
