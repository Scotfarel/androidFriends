package ru.park.friends.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ru.park.friends.newsreader.account.LoginFragment;
import ru.park.friends.newsreader.account.RegisterFragment;

public class MainActivity extends AppCompatActivity implements Router {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentById(R.id.main_fragment_container) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, new LoginFragment())
                    .commit();
        }
    }

    @Override
    public void openRegistration() {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, new RegisterFragment())
                    .commit();
    }

    @Override
    public void openLogin() {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, new LoginFragment())
                    .commit();
    }

    @Override
    public void openFeed() {
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
    }
}
