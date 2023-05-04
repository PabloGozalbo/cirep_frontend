package com.example.dashboard.ui.perfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.comun.cache.UserDataSession;
import com.example.comun.model.user.Usuario;
import com.example.comun.model.user.UsuarioLogin;
import com.example.comun.repository.Repository;

public class PerfilViewModel extends ViewModel {

    Repository repository;
    private MutableLiveData<Boolean> editProfileSuccess = new MutableLiveData<>();

    public PerfilViewModel() {
        repository = new Repository();
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

    public void editProfile(String attribute, String email) { // todo este el bueno
        repository.editProfile(attribute, email, new Repository.editProfileCallback() {
            @Override
            public void onSuccess() {
                editProfileSuccess.postValue(true);
            }

            @Override
            public void onFailure() {
                editProfileSuccess.postValue(false);
            }
        });
    }


}