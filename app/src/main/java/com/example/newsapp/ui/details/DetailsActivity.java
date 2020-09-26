package com.example.newsapp.ui.details;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsapp.App;
import com.example.newsapp.R;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.models.Article;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private TextView tvTittle, tvDesc, tvUrl, tvPublishedAt, tvAuthor;
    private ImageView imageNews;
    private static final String ARTICLE = "article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
        initialization();
        getDataFromIntent();

    }

    private void initViews() {
        tvTittle = findViewById(R.id.tv_tittle);
        tvDesc = findViewById(R.id.tv_desc);
        imageNews = findViewById(R.id.image_news);
        tvUrl = findViewById(R.id.tv_url);
        tvPublishedAt = findViewById(R.id.tv_publishedAt);
        tvAuthor = findViewById(R.id.tv_author);
    }

    private void initialization() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.details_tittle);
    }

    @SuppressLint("SetTextI18n")
    private void getDataFromIntent() {
        if (getIntent() != null) {
            Article article = (Article) getIntent().getSerializableExtra(ARTICLE);
            if (article != null) {
                tvTittle.setText(article.getTitle());
                tvDesc.setText(article.getDescription());
                Glide.with(imageNews.getContext()).load(article.getUrlToImage()).into(imageNews);
                if (article.getAuthor() != null) {
                    tvAuthor.setText(getString(R.string.author_details) + article.getAuthor());
                } else {
                    tvAuthor.setText(R.string.author_unknown);
                }
                tvUrl.setText(getString(R.string.url_news) + article.getUrl());
                tvPublishedAt.setText(getString(R.string.date_detail) + article.getPublishedAt());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}