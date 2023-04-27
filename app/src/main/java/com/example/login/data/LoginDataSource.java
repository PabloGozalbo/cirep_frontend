package com.example.login.data;

import com.example.comun.cache.UserDataSession;
import com.example.login.data.model.UserLogged;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<UserLogged> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            UserLogged fakeUser =
                    new UserLogged(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        UserDataSession userDataSession = UserDataSession.getInstance();
        userDataSession.deleteToken();
    }
}