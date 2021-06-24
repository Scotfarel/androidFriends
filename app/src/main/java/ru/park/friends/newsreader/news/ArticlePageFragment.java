package ru.park.friends.newsreader.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import ru.park.friends.newsreader.FeedRouter;
import ru.park.friends.newsreader.R;
import ru.park.friends.newsreader.news.user.UserViewModel;

public class ArticlePageFragment extends Fragment {

    private ImageView image;
    private TextView title;
    private TextView content;
    private TextView source;
    private NewsAPI.Article article;

    private UserViewModel userViewModel;

    public static ArticlePageFragment getInstance(NewsAPI.Article article) {
        ArticlePageFragment fragment = new ArticlePageFragment();
        fragment.article = article;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_fragment, container, false);
        setHasOptionsMenu(true);

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        image = view.findViewById(R.id.article_image);
        title = view.findViewById(R.id.article_title);
        content = view.findViewById(R.id.article_content);
        source = view.findViewById(R.id.article_author);

        Glide.with(this).load(article.urlToImage).into(image);
        title.setText(article.title);
        content.setText(article.content);
        source.setText(article.source.name);

        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedRouter router = (FeedRouter) source.getContext();
                router.openSourcePage(article.source);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.source_action_bar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.follow_button) {
            userViewModel.saveArticle(getActivity(), article);
            return true;
        }

        return false;
    }
}
