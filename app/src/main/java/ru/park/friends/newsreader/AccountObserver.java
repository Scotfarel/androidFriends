package ru.park.friends.newsreader;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;

public class AccountObserver implements Observer<RegisterState> {
    private TextView errorTextView;
    private String invalidEmail;
    private String invalidPassword;
    private Router router;

    AccountObserver(
            TextView errorTextView,
            String invalidEmail,
            String invalidPassword,
            Router router
    ) {
        this.errorTextView = errorTextView;
        this.invalidEmail = invalidEmail;
        this.invalidPassword = invalidPassword;
        this.router = router;
    }

    @Override
    public void onChanged(RegisterState loginState) {
        if (loginState == RegisterState.NOT_EMAIL) {
            errorTextView.setText(invalidEmail);
            errorTextView.setVisibility(View.VISIBLE);
            return;
        }

        if (loginState == RegisterState.SHORT_PASSWORD) {
            errorTextView.setText(invalidPassword);
            errorTextView.setVisibility(View.VISIBLE);
            return;
        }

        if (loginState == RegisterState.SUCCESS) {
            if (router != null) {
                router.openFeed();
            }
            return;
        }
    }
}
