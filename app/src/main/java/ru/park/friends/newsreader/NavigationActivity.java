package ru.park.friends.newsreader;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.park.friends.newsreader.news.ArticlePageFragment;
import ru.park.friends.newsreader.news.favorites.FavoritesFragment;
import ru.park.friends.newsreader.news.feed.FeedFragment;
import ru.park.friends.newsreader.news.NewsAPI;
import ru.park.friends.newsreader.news.search.SearchFragment;
import ru.park.friends.newsreader.news.source.SourceFragment;
import ru.park.friends.newsreader.news.user.UserFragment;

public class NavigationActivity extends AppCompatActivity implements FeedRouter {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new SearchFragment())
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_feed:
                            selectedFragment = new FeedFragment();
                            break;
                        case R.id.navigation_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.navigation_user:
                            selectedFragment = new UserFragment();
                            break;
                        case R.id.navigation_favorite:
                            selectedFragment = new FavoritesFragment();
                            break;
                    }

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, selectedFragment)
                            .commit();

                    return true;
                }
            };

    @Override
    public void openArticle(NewsAPI.Article article) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, ArticlePageFragment.getInstance(article))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openSourcePage(NewsAPI.Source source) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, SourceFragment.getInstance(source))
                .addToBackStack(null)
                .commit();
    }
}
