package ru.park.friends.newsreader.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import ru.park.friends.newsreader.R;

public class SearchNewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private NewsAPI.News news;
    private Context context;

    public SearchNewsAdapter(LifecycleOwner owner, SearchViewModel searchViewModel) {
        this.context = (Context) owner;
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
            holder.bind(context, news.articles.get(position));
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
