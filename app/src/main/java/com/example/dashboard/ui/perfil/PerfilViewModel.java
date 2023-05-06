package com.example.dashboard.ui.perfil;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.comun.cache.UserDataSession;
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

    public void editProfile(String attribute, String value, String email, String token) {
        if(attribute.equals("password")){
            repository.editProfile(attribute, value, email,token, new Repository.editProfileCallback() {
                @Override
                public void onSuccess() {
                    editProfileSuccess.postValue(true);
                }

                @Override
                public void onFailure() {
                    editProfileSuccess.postValue(false);
                }
            });
        }else{
            repository.editProfile(attribute, value, email,token, new Repository.editProfileCallback() {
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

    public void setNombreUser(String nombre) {
        UserDataSession.getInstance().getUsuario().setFirstName(nombre);
    }

    public void setApellidosUser(String apellidos) {
        UserDataSession.getInstance().getUsuario().setLastName(apellidos);
    }

    public void setTelefonoUser(String telefono) {
        UserDataSession.getInstance().getUsuario().setPhoneNumber(telefono);
    }

    public void setCiudadUser(String ciudad) {
        UserDataSession.getInstance().getUsuario().setCity(ciudad);
    }

    public void setEmailUser(String email) {
        UserDataSession.getInstance().getUsuario().setEmail(email);
    }

    public void setPassword(String password) {
        UserDataSession.getInstance().getUsuario().setPassword(password);
    }


}