package com.example.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

class NewsViewHolder extends RecyclerView.ViewHolder {
    TextView author = ((TextView) this.itemView.findViewById(R.id.author));
    ImageView imageView = ((ImageView) this.itemView.findViewById(R.id.imageView));
    TextView title = ((TextView) this.itemView.findViewById(R.id.title));

    public NewsViewHolder(View itemView) {
        super(itemView);
    }
}
