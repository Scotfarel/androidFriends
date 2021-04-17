package ru.park.friends.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentById(R.id.container) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_container, new RegisterFragment())
                    .commit();
        }
    }
}
