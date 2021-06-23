package ru.park.friends.newsreader.news.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import ru.park.friends.newsreader.news.NewsAdapter;

public class FavoritesFragment extends Fragment {
    private FavoritesViewModel favoritesViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.source_fragment, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_favorite));

        favoritesViewModel = new ViewModelProvider(getActivity()).get(FavoritesViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.source_recycler_view);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new NewsAdapter(getActivity(), favoritesViewModel));

        return view;
    }
}
