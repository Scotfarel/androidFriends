package ru.park.friends.newsreader;

import android.app.Application;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;


public class AccountViewModel extends AndroidViewModel {

    private String lastEmail = "";
    private String lastPassword = "";
    private MediatorLiveData<RegisterState> authState = new MediatorLiveData<>();
    private AccountRepository repository = new AccountRepository();

    public AccountViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<RegisterState> getRegisterState() {
        return authState;
    }

    public void register(String email, String password) {
        if (!isValid(email, password)) {
            return;
        }

        authState = repository.registerUser(email, password);
    }

    public void login(String email, String password) {
        if (!isValid(email, password)) {
            return;
        }

        authState = repository.loginUser(email, password);
    }

    private boolean isValid(String email, String password) {
        if (email == lastEmail && password == lastPassword) {
            return false;
        }
        lastEmail = email;
        lastPassword = password;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            authState.postValue(RegisterState.NOT_EMAIL);
            return false;
        }

        if (password.length() < 8) {
            authState.postValue(RegisterState.SHORT_PASSWORD);
            return false;
        }

        return true;
    }
}
