package ru.park.friends.newsreader.news;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    public class News {
        public String status;
        public int totalResults;
        public List<Article> articles;

        @Override
        public String toString() {
            String str = "";
            Iterator<Article> iter = articles.iterator();
            while(iter.hasNext()) {
                str += iter.next().toString();
            }

            return str;
        }
    }

    public class Source {
        public String id;
        public String name;

        @Override
        public String toString() {
            return name;
        }
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

        @Override
        public String toString() {
            return "Source: " + source.toString() + "\nAuthor: " + author + "\nTitle: " + title + "\n";
        }
    }

    @GET("/v2/everything?q=Apple&sortBy=popularity&apiKey=")
    Call<News> getAll();

    @GET("/v2/everything?sortBy=popularity&apiKey=")
    Call<News> getNewsByTitle(@Query("q") String title);
}