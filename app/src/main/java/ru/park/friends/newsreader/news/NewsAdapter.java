package ru.park.friends.newsreader.news;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.park.friends.newsreader.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private final ExecutorService executorServiceNetwork = Executors.newFixedThreadPool(2);
    private NewsAPI.News news;
    private Context context;

    public NewsAdapter(Context context) {
        this.context = context;
        executorServiceNetwork.execute(getNews());
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

    private Runnable getNews() {
        return new Runnable() {
            @Override
            public void run() {
                ApiRepository api = new ApiRepository();
                api.getNewsAPI().getAll().enqueue(new Callback<NewsAPI.News>() {
                    @Override
                    public void onResponse(Call<NewsAPI.News> call, Response<NewsAPI.News> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            news = response.body();
                            notifyDataSetChanged();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsAPI.News> call, Throwable t) {
                        Log.d("MY_APP_LOG", "failure");
                    }
                });
            }
        };
    }
}
