package com.example.newsapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.newsapp.App;
import com.example.newsapp.R;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.data.remote.NewsApiClient;
import com.example.newsapp.models.Article;
import com.example.newsapp.models.News;
import com.example.newsapp.ui.main.recycler.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<Article> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerView = findViewById(R.id.recycler_view);
        list = new ArrayList<>();
        adapter = new NewsAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        App.newsRepository.getNewsHeadlines("ru", "34d3aa9ece5648a188062fe1b24c84fd",
                new INewsApiClient.NewsCallBack() {
            @Override
            public void onSuccess(List<Article> result) {
                adapter.setData(result);
                Log.d("ololo", result.toString());
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }
}