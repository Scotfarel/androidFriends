package ru.park.friends.newsreader.news.source;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.park.friends.newsreader.R;
import ru.park.friends.newsreader.news.NewsAPI;
import ru.park.friends.newsreader.news.NewsAdapter;
import ru.park.friends.newsreader.news.user.UserViewModel;

public class SourceFragment extends Fragment {

    private SourceNewsViewModel sourceNewsViewModel;
    private UserViewModel userViewModel;
    private NewsAPI.Source source;

    public static SourceFragment getInstance(NewsAPI.Source source) {
        SourceFragment fragment = new SourceFragment();
        fragment.source = source;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.source_fragment, container, false);
        setHasOptionsMenu(true);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(source.name);

        sourceNewsViewModel = new ViewModelProvider(getActivity()).get(SourceNewsViewModel.class);
        sourceNewsViewModel.getNewsBySource(source.id);

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.source_recycler_view);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new NewsAdapter(getActivity(), sourceNewsViewModel));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.source_action_bar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.follow_button) {
            userViewModel.subscribeOnSource(source);
            return true;
        }

        return false;
    }
}
