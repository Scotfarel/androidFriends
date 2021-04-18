package ru.park.friends.newsreader;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class RegisterFragment extends Fragment {
    private RegisterViewModel authViewModel;
    private EditText email, password;
    private Button registerButton;
    private TextView errorTextView;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            errorTextView.setVisibility(View.INVISIBLE);
            if (email.getText().toString().length() == 0 ||
                    password.getText().toString().length() == 0) {
                registerButton.setEnabled(false);
                return;
            }

            registerButton.setEnabled(true);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.register_email);
        password = view.findViewById(R.id.register_password);
        registerButton = view.findViewById(R.id.register_button);
        errorTextView = view.findViewById(R.id.register_error);

        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.clearFocus();
                password.clearFocus();
                authViewModel.register(email.getText().toString(), password.getText().toString());
            }
        });

        authViewModel = new ViewModelProvider(getActivity()).get(RegisterViewModel.class);
        authViewModel
                .getRegisterState()
                .observe(getViewLifecycleOwner(), new AuthObserver(errorTextView));

    }

    private class AuthObserver implements Observer<RegisterState> {
        private TextView errorTextView;

        AuthObserver(TextView errorTextView) {
            this.errorTextView = errorTextView;
        }

        @Override
        public void onChanged(RegisterState loginState) {
            if (loginState == RegisterState.NOT_EMAIL) {
                errorTextView.setText(getResources().getString(R.string.invalid_email));
                errorTextView.setVisibility(View.VISIBLE);
            }

            if (loginState == RegisterState.SHORT_PASSWORD) {
                errorTextView.setText(getResources().getString(R.string.invalid_password));
                errorTextView.setVisibility(View.VISIBLE);
            }
        }
    }

}
