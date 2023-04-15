package com.example.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comun.model.user.Usuario;
import com.example.comun.repository.Repository;
import com.example.register.steps.OnRegistrationSuccessListener;

public class RegisterViewModel {

    private Repository repository;
    private MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();


    public RegisterViewModel(){
        this.repository = new Repository();
    }

    public void registerUser(Usuario user) {
        // Llamar al método de registro de UserRepository
        repository.registerUser(user, new Repository.Callback() {
            @Override
            public void onSuccess() {
                registrationSuccess.postValue(true);
            }

            @Override
            public void onFailure() {
                registrationSuccess.postValue(false);
            }
        });
    }

    // Getter para registrationSuccess
    public LiveData<Boolean> getRegistrationSuccess() {
        return registrationSuccess;
    }

    public LiveData<Boolean> getRegistrationSuccessLiveData() {
        return registrationSuccess;
    }
}
