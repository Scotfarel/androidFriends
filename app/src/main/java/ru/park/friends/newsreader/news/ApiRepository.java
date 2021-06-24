package ru.park.friends.newsreader.news;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {
    private final NewsAPI newsAPI;

    public ApiRepository() {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(new HttpUrl.Builder().scheme("https")
                        .host("newsapi.org")
                        .build())
                .client(client)
                .build();

        newsAPI = retrofit.create(NewsAPI.class);
    }

    public NewsAPI getNewsAPI() {
        return newsAPI;
    }
}
