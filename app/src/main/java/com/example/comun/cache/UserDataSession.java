package com.example.comun.cache;

public class UserDataSession {
    //TODO: definir que datos se meten de la cache y completar esta clase

    private String token;

    public UserDataSession(String token){
        this.token=token;
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
