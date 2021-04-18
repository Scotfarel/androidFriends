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
import androidx.lifecycle.ViewModelProvider;

public class LoginFragment extends Fragment {
    private AccountViewModel accountViewModel;
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
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.login_email);
        password = view.findViewById(R.id.login_password);
        registerButton = view.findViewById(R.id.login_button);
        errorTextView = view.findViewById(R.id.login_error);

        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.clearFocus();
                password.clearFocus();
                accountViewModel.login(email.getText().toString(), password.getText().toString());
            }
        });

        view.findViewById(R.id.go_to_reg_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router router = (Router) getActivity();
                if (router != null) {
                    router.openRegistration();
                }
            }
        });

        accountViewModel = new ViewModelProvider(getActivity()).get(AccountViewModel.class);
        accountViewModel
                .getRegisterState()
                .observe(getViewLifecycleOwner(), new AccountObserver(
                        errorTextView,
                        getResources().getString(R.string.invalid_email),
                        getResources().getString(R.string.invalid_password),
                        (Router) getActivity())
                );
    }
}
