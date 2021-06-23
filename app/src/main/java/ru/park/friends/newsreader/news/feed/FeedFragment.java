package ru.park.friends.newsreader.news.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.park.friends.newsreader.R;
import ru.park.friends.newsreader.news.NewsAPI;
import ru.park.friends.newsreader.news.NewsAdapter;
import ru.park.friends.newsreader.news.user.UserViewModel;


public class FeedFragment extends Fragment {

    private FeedViewModel feedViewModel;
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_feed));

        feedViewModel = new ViewModelProvider(getActivity()).get(FeedViewModel.class);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        LiveData<List<NewsAPI.Source>> subscriptions = userViewModel.getUserSubscriptions();

        subscriptions.observe(getActivity(), sources -> {
            feedViewModel.getNewsFromSubscriptions(sources);
        });


        RecyclerView recyclerView = view.findViewById(R.id.news_feed);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new NewsAdapter(getActivity(), feedViewModel));

        return view;
    }
}
