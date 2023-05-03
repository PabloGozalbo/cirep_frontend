package com.example.dashboard.ui.perfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.comun.cache.UserDataSession;
import com.example.comun.model.user.Usuario;

public class PerfilViewModel extends ViewModel {


    public PerfilViewModel() {
    }

    public String getNombreUser() {
        return UserDataSession.getInstance().getUsuario().getFirstName();
    }

    public String getApellidosUser() {
        return UserDataSession.getInstance().getUsuario().getLastName();
    }

    public String getTelefonoUser() {
        return UserDataSession.getInstance().getUsuario().getPhoneNumber();
    }

    public String getCiudadUser() {
        return UserDataSession.getInstance().getUsuario().getCiudad();
    }

    public String getEmailUser() {
        return UserDataSession.getInstance().getUsuario().getEmail();
    }


}