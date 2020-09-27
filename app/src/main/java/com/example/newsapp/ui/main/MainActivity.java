package com.example.newsapp.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsapp.App;
import com.example.newsapp.R;
import com.example.newsapp.models.Article;
import com.example.newsapp.ui.details.DetailsActivity;
import com.example.newsapp.ui.main.recycler.NewsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String ARTICLE = "article";
    private static final String IS_ORIENT = "isOrient";
    private MainViewModel mViewModel;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<Article> list = new ArrayList<>();
    private ProgressBar isLoading, progressDown;
    private NestedScrollView nestedScrollView;
    private SwipeRefreshLayout swipeUp;
    private int pageSize = 10, page = 1;
    private ConnectivityManager cm;
    private Boolean isOrientation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initialization();
        createRecycler();
        getDataFromLiveData();
        listeners();

        if (savedInstanceState != null) {
            isOrientation = savedInstanceState.getBoolean(IS_ORIENT);
            isOrientation = true;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_ORIENT, isOrientation);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        isLoading = findViewById(R.id.progress_bar);
        nestedScrollView = findViewById(R.id.nested_scroll);
        progressDown = findViewById(R.id.progress_bar_down);
        swipeUp = findViewById(R.id.swipe_up);
    }

    private void initialization() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.news_title);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private void createRecycler() {
        adapter = new NewsAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void getDataFromLiveData() {
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            if (App.newsRepository.getAll() != null) App.newsRepository.deleteAll();
            mViewModel.receiveData(page, pageSize);
            mViewModel.news.observe(this, result -> {
                if (isOrientation) {
                    list.clear();
                    App.newsRepository.deleteAll();
                }
                App.newsRepository.insert(result);
                list.addAll(result);
                adapter.updateAdapter(list);
                isOrientation = true;
                progressDown.setVisibility(View.GONE);
                isLoading.setVisibility(View.GONE);
            });
            mViewModel.exception.observe(this, e ->
                    Toast.makeText(MainActivity.this, "Ошибка " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            mViewModel.newsData.observe(this, articles -> {
                if (articles != null) {
                    list.addAll(articles);
                    adapter.updateAdapter(articles);
                    isLoading.setVisibility(View.GONE);
                    progressDown.setVisibility(View.GONE);
                }
            });
        }
    }

    private void listeners() {
        adapter.setOnItemClickListener(pos -> {
            startActivity(new Intent(this, DetailsActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra(ARTICLE, list.get(pos)));
        });

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
                            if (Objects.requireNonNull(mViewModel.news.getValue()).size() >= pageSize) {
                                page++;
                                pageSize = +10;
                                progressDown.setVisibility(View.VISIBLE);
                                mViewModel.receiveData(page, pageSize);
                                if (isOrientation) {
                                    isOrientation = false;
                                }
                            } else {
                                Toast.makeText(this, "Все данные загружены", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "У вас интернет не подключен", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        swipeUp.setOnRefreshListener(() -> {
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
                App.newsRepository.deleteAll();
                list.clear();
                page = 1;
                pageSize = 10;
                mViewModel.receiveData(page, pageSize);
                Toast.makeText(this, "Данные успешно обновлены", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "У вас интернет не подключен", Toast.LENGTH_SHORT).show();
            }
            swipeUp.setRefreshing(false);
        });
    }
}