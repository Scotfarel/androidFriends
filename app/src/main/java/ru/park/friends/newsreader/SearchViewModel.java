package ru.park.friends.newsreader;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends AndroidViewModel {
    private MediatorLiveData<NewsAPI.News> news = new MediatorLiveData<>();
    private ApiRepository apiRepo = new ApiRepository();

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<NewsAPI.News> getNewsLiveData() {
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
