package ru.park.friends.newsreader.news.source;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.park.friends.newsreader.news.ApiRepository;
import ru.park.friends.newsreader.news.NewsAPI;
import ru.park.friends.newsreader.news.NewsGetter;

public class SourceNewsViewModel extends AndroidViewModel implements NewsGetter {
    private MediatorLiveData<NewsAPI.News> news = new MediatorLiveData<>();
    private ApiRepository apiRepo = new ApiRepository();

    public SourceNewsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<NewsAPI.News> getNews() {
        return news;
    }

    public void getNewsBySource(String source) {
        apiRepo.getNewsAPI().getNewsBySource(source).enqueue(new Callback<NewsAPI.News>() {
            @Override
            public void onResponse(Call<NewsAPI.News> call, Response<NewsAPI.News> response) {
                if (response.isSuccessful() && response.body() != null) {
                    news.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsAPI.News> call, Throwable t) {
                Log.d("MY_APP_LOG", "can't get news for source: " + source);
            }
        });
    }
}
