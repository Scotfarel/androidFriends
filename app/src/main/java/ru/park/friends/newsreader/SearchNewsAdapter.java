package ru.park.friends.newsreader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class SearchNewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private NewsAPI.News news;

    public SearchNewsAdapter(LifecycleOwner owner, SearchViewModel searchViewModel) {
        LiveData<NewsAPI.News> liveData = searchViewModel.getNewsLiveData();
        liveData.observe(owner, data -> {
            news = data;
            notifyDataSetChanged();
        });
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        if (news != null) {
            holder.bind(news.articles.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (news != null) {
            return news.articles.size();
        }
        return 0;
    }
}
