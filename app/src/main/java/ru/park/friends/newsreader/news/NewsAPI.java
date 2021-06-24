package ru.park.friends.newsreader.news;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    public class News {
        public String status;
        public int totalResults;
        public List<Article> articles;
    }

    public class Source {
        public String id;
        public String name;
        public String description;
    }

    public class Article {
        public Source source;
        public String author;
        public String title;
        public String description;
        public String url;
        public String urlToImage;
        public Date publishedAt;
        public String content;
    }

    @GET("/v2/top-headlines?sortBy=popularity&apiKey=c3cbdce5c9c3479a92de13828bb33da3")
    Call<News> getAll(@Query("sources") String sources);

    @GET("/v2/everything?sortBy=popularity&apiKey=c3cbdce5c9c3479a92de13828bb33da3")
    Call<News> getNewsByTitle(@Query("q") String title);

    @GET("/v2/top-headlines?apiKey=c3cbdce5c9c3479a92de13828bb33da3")
    Call<News> getNewsBySource(@Query("sources") String source);
}
