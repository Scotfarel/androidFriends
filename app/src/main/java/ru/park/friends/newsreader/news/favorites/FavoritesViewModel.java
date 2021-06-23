package ru.park.friends.newsreader.news.favorites;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import ru.park.friends.newsreader.news.NewsAPI;
import ru.park.friends.newsreader.news.NewsGetter;
import ru.park.friends.newsreader.news.user.UserRepository;

public class FavoritesViewModel extends AndroidViewModel implements NewsGetter {
    private UserRepository userRepository = new UserRepository();
    private MediatorLiveData<NewsAPI.News> news = new MediatorLiveData<>();
    private Context context;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }

    @Override
    public LiveData<NewsAPI.News> getNews() {
        NewsAPI.News savedNews = new NewsAPI.News();
        savedNews.articles = userRepository.getAllSavedArticles(context);
        news.postValue(savedNews);

        return news;
    }
}
