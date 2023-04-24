package com.example.login.ui.login.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.util.Patterns;

import com.example.cirep_frontend.R;
import com.example.comun.model.user.Usuario;
import com.example.comun.repository.Repository;
import com.example.dashboard.DashboardActivity;
import com.example.login.data.repository.LoginRepository;
import com.example.login.data.Result;
import com.example.login.data.model.UserLogged;
import com.example.login.ui.login.LoggedInUserView;
import com.example.login.ui.login.LoginActivity;
import com.example.login.ui.login.LoginFormState;
import com.example.login.ui.login.LoginResult;


public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private LoginRepository loginRepository;

    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<UserLogged> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            UserLogged data = ((Result.Success<UserLogged>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public void loginUser(String email, String psswd) {
        // Llamar al método de registro de UserRepository
        loginRepository.loginUser(email, psswd, new Repository.Callback() {
            @Override
            public void onSuccess() {
                loginSuccess.postValue(true);
            }

            @Override
            public void onFailure() {
                loginSuccess.postValue(false);
            }
        });
    }

}