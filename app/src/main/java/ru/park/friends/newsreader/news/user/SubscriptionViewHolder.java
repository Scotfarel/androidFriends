package ru.park.friends.newsreader.news.user;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.park.friends.newsreader.FeedRouter;
import ru.park.friends.newsreader.R;
import ru.park.friends.newsreader.news.NewsAPI;

public class SubscriptionViewHolder extends RecyclerView.ViewHolder {

    private TextView subName;

    public SubscriptionViewHolder(@NonNull View itemView) {
        super(itemView);

        subName = itemView.findViewById(R.id.subscription_name);
    }

    public void bind(NewsAPI.Source source) {
        subName.setText(source.name);

        subName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedRouter router = (FeedRouter) subName.getContext();
                router.openSourcePage(source);
            }
        });
    }
}
