package com.example.newsapp.ui.main.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<String> list;

    public NewsAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.tvTittle.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTittle, tvDesc;
        private ImageView imageNews;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tv_tittle_news);
            tvDesc = itemView.findViewById(R.id.tv_desc_news);
            imageNews = itemView.findViewById(R.id.image_news);
        }
    }

}
