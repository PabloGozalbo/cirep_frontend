package com.example.comun.cache;

public class UserDataSession {//objeto para guardar el token usando in singleton

    private static UserDataSession instance;
    private String token;

    private UserDataSession(String token){
        this.token=token;
    }

    public static UserDataSession getInstance(){
        if(instance == null){
            instance = new UserDataSession(null);
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSessionValid(){
        return this.token!=null && !this.token.isEmpty();
    }
}
