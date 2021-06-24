package ru.park.friends.newsreader.news;

import androidx.lifecycle.LiveData;

public interface NewsGetter {
    LiveData<NewsAPI.News> getNews();
}
