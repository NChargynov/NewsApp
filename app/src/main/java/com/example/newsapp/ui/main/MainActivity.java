package com.example.newsapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

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
    private ProgressBar isLoading, progress;
    private NestedScrollView nestedScrollView;
    private int pageSize = 10;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        createRecycler();
        getDataFromLiveData();
        listeners();
    }

    private void getDataFromLiveData() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {

            mViewModel.receiveData(page, pageSize);
            if (App.newsDataBase.newsDao().getAll() != null) App.newsDataBase.newsDao().deleteAll();

            mViewModel.news.observe(this, result -> {
                App.newsDataBase.newsDao().insert(result);
                list.addAll(result);
                adapter.updateAdapter(list);
                progress.setVisibility(View.GONE);
            });
        } else {
            mViewModel.newsData.observe(this, articles -> {
                if (articles != null) {
                    list.addAll(articles);
                    adapter.updateAdapter(articles);
                    isLoading.setVisibility(View.GONE);
                }
            });
        }
        mViewModel.isLoading.observe(this, aBoolean -> {
            if (aBoolean) isLoading.setVisibility(View.GONE);
        });
    }

    private void initViews() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerView = findViewById(R.id.recycler_view);
        isLoading = findViewById(R.id.progress_bar);
        nestedScrollView = findViewById(R.id.nested_scroll);
        progress = findViewById(R.id.progress_bar_down);
    }

    private void listeners() {
        adapter.setOnItemClickListener(pos -> {
            startActivity(new Intent(this, DetailsActivity.class).putExtra("pos", list.get(pos)));
        });

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                        if (pageSize >= list.size()) {
                            page++;
                            pageSize = pageSize + 10;
                            progress.setVisibility(View.VISIBLE);
                            mViewModel.receiveData(page, pageSize);
                            Log.d("ololo", " page " + page + "pageSize" + pageSize);
                        } else {
                            Toast.makeText(this, "Все данные загружены", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createRecycler() {
        adapter = new NewsAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }
}