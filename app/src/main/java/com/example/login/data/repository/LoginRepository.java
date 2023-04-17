package com.example.login.data.repository;

import com.example.comun.repository.Repository;
import com.example.login.data.LoginDataSource;
import com.example.login.data.Result;
import com.example.login.data.model.UserLogged;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository extends Repository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    private UserLogged user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(UserLogged user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
    }

    public Result<UserLogged> login(String username, String password) {
        // handle login
        Result<UserLogged> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<UserLogged>) result).getData());
        }
        return result;
    }
}