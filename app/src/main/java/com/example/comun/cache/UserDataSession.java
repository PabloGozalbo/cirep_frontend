package com.example.comun.cache;

public class UserDataSession {//objeto para guardar el token usando in singleton

    private static UserDataSession instance;
    private static String token;

    private UserDataSession(String token){
        UserDataSession.token =token;
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
        UserDataSession.token = token;
    }

    public void deleteToken() {
        token = null;
    }

    public boolean isSessionValid(){
        return token!=null && !token.isEmpty();
    }
}
