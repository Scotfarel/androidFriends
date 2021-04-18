package ru.park.friends.newsreader;

import android.app.Application;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;


public class RegisterViewModel extends AndroidViewModel {

    private String lastEmail = "";
    private String lastPassword = "";
    private MediatorLiveData<RegisterState> authState = new MediatorLiveData<>();
    private AccountRepository repository = new AccountRepository();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<RegisterState> getRegisterState() {
        return authState;
    }

    public void register(String email, String password) {
        if (email == lastEmail && password == lastPassword) {
            return;
        }
        lastEmail = email;
        lastPassword = password;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            authState.postValue(RegisterState.NOT_EMAIL);
            return;
        }

        authState = repository.registerUser(email, password);
    }
}
