package com.example.comun.model.user;

import com.google.gson.annotations.SerializedName;

public class UsuarioLogin {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String psswd;

    public UsuarioLogin(String email, String psswd) {
        this.email = email;
        this.psswd = psswd;
    }
}
