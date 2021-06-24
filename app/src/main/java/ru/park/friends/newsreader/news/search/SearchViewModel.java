package ru.park.friends.newsreader.news.search;

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

public class SearchViewModel extends AndroidViewModel implements NewsGetter {
    private MediatorLiveData<NewsAPI.News> news = new MediatorLiveData<>();
    private ApiRepository apiRepo = new ApiRepository();

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<NewsAPI.News> getNews() {
        return news;
    }

    public void searchNews(String title) {
        apiRepo.getNewsAPI().getNewsByTitle(title).enqueue(new Callback<NewsAPI.News>() {
            @Override
            public void onResponse(Call<NewsAPI.News> call, Response<NewsAPI.News> response) {
                if (response.isSuccessful() && response.body() != null) {
                    news.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsAPI.News> call, Throwable t) {
                Log.d("MY_APP_LOG", "can't get news with title: " + title);
            }
        });
    }
}
