package ru.park.friends.newsreader;

import ru.park.friends.newsreader.news.NewsAPI;

public interface FeedRouter {
    void openArticle(NewsAPI.Article article);
    void openSourcePage(NewsAPI.Source source);
}
