package com.example.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    ArrayList<NewsInfo> items = new ArrayList<>();
    public NewsItemClicked listener;

    public NewsViewAdapter(NewsItemClicked listener2) {
        this.listener = listener2;
    }

    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        final NewsViewHolder viewHolder = new NewsViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NewsViewAdapter.this.listener.onItemClicked(NewsViewAdapter.this.items.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsInfo currentItem = this.items.get(position);
        holder.title.setText(currentItem.title);
        holder.author.setText(currentItem.author);
        Glide.with(holder.imageView.getContext()).load(currentItem.imageUrl).into(holder.imageView);
    }

    public int getItemCount() {
        return this.items.size();
    }

    public void updateNews(ArrayList<NewsInfo> updatedNews) {
        this.items.clear();
        this.items.addAll(updatedNews);
        notifyDataSetChanged();
    }
}
