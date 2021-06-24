package ru.park.friends.newsreader.news.user;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.park.friends.newsreader.news.NewsAPI;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository = new UserRepository();

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void subscribeOnSource(NewsAPI.Source source) {
        userRepository.subscribeOnSource(source);
    }

    public LiveData<List<NewsAPI.Source>> getUserSubscriptions() {
        return userRepository.getUserSubscriptions();
    }

    public void unsubscribeFromSource(NewsAPI.Source source) {
        userRepository.unsubscribeFromSource(source);
    }

    public void saveArticle(Context context, NewsAPI.Article article) {
        userRepository.saveArticle(context, article);
    }

    public List<NewsAPI.Article> getAllSavedArticles(Context context) {
        return userRepository.getAllSavedArticles(context);
    }
}
