package ru.park.friends.newsreader.news.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.park.friends.newsreader.R;

public class UserFragment extends Fragment {
    private UserViewModel userViewModel;
    private SubscriptionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_user));

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        adapter = new SubscriptionAdapter((getActivity()), userViewModel);

        RecyclerView recyclerView = view.findViewById(R.id.user_subscriptions_recycler);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        new ItemTouchHelper(adapter.itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
