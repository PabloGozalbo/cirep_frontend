package com.example.comun.cache;

import com.example.comun.model.user.Usuario;

public class UserDataSession {//objeto para guardar el token usando in singleton

    private static UserDataSession instance;
    private static Usuario usuario;
    private static String token;

    private UserDataSession(String token){
        UserDataSession.token =token;
    }

    private UserDataSession(String token, Usuario usuario){
        UserDataSession.token = token;
        UserDataSession.usuario = usuario;
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

    public void setUsuario(Usuario usuario){
        UserDataSession.usuario = usuario;
    }

    public void deleteToken() {
        token = null;
    }

    public boolean isSessionValid(){
        return token!=null && !token.isEmpty();
    }
}
