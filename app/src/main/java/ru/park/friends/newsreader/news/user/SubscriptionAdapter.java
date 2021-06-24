package ru.park.friends.newsreader.news.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.park.friends.newsreader.R;
import ru.park.friends.newsreader.news.NewsAPI;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionViewHolder> {

    private List<NewsAPI.Source> subscriptions;
    private UserViewModel userViewModel;

    public SubscriptionAdapter(LifecycleOwner  owner, UserViewModel userViewModel) {
        this.userViewModel = userViewModel;
        LiveData<List<NewsAPI.Source>> liveData = userViewModel.getUserSubscriptions();
        liveData.observe(owner, data -> {
            subscriptions = data;
            notifyDataSetChanged();
        });
    }

    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_item, parent, false);
        return new SubscriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {
        if (subscriptions != null) {
            holder.bind(subscriptions.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (subscriptions != null) {
            return subscriptions.size();
        }
        return 0;
    }

    public ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int index = viewHolder.getAdapterPosition();
            userViewModel.unsubscribeFromSource(subscriptions.get(index));
            subscriptions.remove(index);
            notifyDataSetChanged();
        }
    };
}
