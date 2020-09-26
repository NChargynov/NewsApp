package com.example.newsapp.ui.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.example.newsapp.App;
import com.example.newsapp.R;
import com.example.newsapp.models.Article;
import com.example.newsapp.ui.details.DetailsActivity;
import com.example.newsapp.ui.main.recycler.NewsAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<Article> list = new ArrayList<>();
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    private Integer pageSize = 10;
    private Integer page = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        createRecycler();
        getDataFromLiveData();
        mViewModel.setIsLoading();
        listeners();

    }

    private void getDataFromLiveData() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            App.newsDataBase.newsDao().deleteAll();
            mViewModel.receiveData(pageSize, page);
            mViewModel.news.observe(this, result -> {
                App.newsDataBase.newsDao().insert(result);
                list.addAll(App.newsDataBase.newsDao().getAll());
                adapter.updateAdapter(list);
            });
            Log.d("ololo", "Internet is connected");
        } else {
            mViewModel.newsData.observe(this, articles -> {
                if (articles != null) {
                    adapter.updateAdapter(articles);
                    list = articles;
                }
            });
            Log.d("ololo", "Internet is disconected");
        }
        mViewModel.isLoading.observe(this, aBoolean -> {
            if (aBoolean) progressBar.setVisibility(View.GONE);
        });
    }

    private void initViews() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        nestedScrollView = findViewById(R.id.nested_scroll);
    }

    private void listeners() {
        adapter.setOnItemClickListener(pos -> {
            startActivity(new Intent(this, DetailsActivity.class).putExtra("pos", list.get(pos)));
        });

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight());
                page++;
                pageSize = 10;
                mViewModel.receiveData(pageSize, page);
                Log.d("ololo", " " + page);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createRecycler() {
        adapter = new NewsAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        recyclerView.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
//            pageSize = 10;
//            page++;
//            mViewModel.receiveData(pageSize, page);
//        });

    }
}