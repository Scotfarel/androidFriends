package ru.park.friends.newsreader;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;

public class AccountObserver implements Observer<RegisterState> {
    private TextView errorTextView;
    private String invalidEmail;
    private String invalidPassword;

    AccountObserver(TextView errorTextView, String invalidEmail, String invalidPassword) {
        this.errorTextView = errorTextView;
        this.invalidEmail = invalidEmail;
        this.invalidPassword = invalidPassword;
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
    }
}
