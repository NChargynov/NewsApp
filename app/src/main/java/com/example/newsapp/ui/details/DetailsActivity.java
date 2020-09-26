package com.example.newsapp.ui.details;

import androidx.appcompat.app.AppCompatActivity;

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
    private TextView tvTittle, tvDesc, tvUrl, tvPublishedAt, tvAuthor, tvSource;
    private ImageView imageNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
        if (getIntent() != null) {
            Article article = (Article) getIntent().getSerializableExtra("pos");
            if (article != null) {
                tvTittle.setText(article.getTitle());
                tvDesc.setText(article.getDescription());
                Glide.with(imageNews.getContext()).load(article.getUrlToImage()).into(imageNews);
                if (article.getAuthor() != null) {
                    tvAuthor.setText("Автор " + article.getAuthor());
                } else {
                    tvAuthor.setText("Автор неизвестен");
                }
                tvUrl.setText("Ссылка на новость: " + article.getUrl());
                tvPublishedAt.setText("Дата: " + article.getPublishedAt());

            }
        }
    }

    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvTittle = findViewById(R.id.tv_tittle);
        tvDesc = findViewById(R.id.tv_desc);
        imageNews = findViewById(R.id.image_news);
        tvUrl = findViewById(R.id.tv_url);
        tvPublishedAt = findViewById(R.id.tv_publishedAt);
        tvAuthor = findViewById(R.id.tv_author);
        tvSource = findViewById(R.id.tv_source);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}