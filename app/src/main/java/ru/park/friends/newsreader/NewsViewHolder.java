package ru.park.friends.newsreader;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private ImageView image;
    private TextView title;
    private TextView author;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.news_view_image);
        title = itemView.findViewById(R.id.news_view_title);
        author = itemView.findViewById(R.id.news_view_author);
    }

    public void bind(NewsAPI.Article article) {
        image.setBackgroundColor(Color.GREEN);
        title.setText(article.title);
        author.setText(article.author);
    }
}
