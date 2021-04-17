package ru.park.friends.newsreader;

import androidx.lifecycle.MediatorLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class AccountRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    MediatorLiveData<RegisterState> registerUser(String email, String password) {
        MediatorLiveData<RegisterState> regLiveData = new MediatorLiveData<>();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(authTask -> {
                    if (authTask.isSuccessful()) {
                        regLiveData.postValue(RegisterState.SUCCESS);
                        return;
                    }

                    regLiveData.postValue(RegisterState.FAILED);
                });

        return  regLiveData;
    }
}
