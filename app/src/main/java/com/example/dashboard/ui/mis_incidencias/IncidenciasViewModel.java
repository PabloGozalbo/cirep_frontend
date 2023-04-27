package com.example.dashboard.ui.mis_incidencias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comun.model.Incidencia;
import com.example.comun.repository.Repository;

import java.util.List;

public class IncidenciasViewModel {

    Repository repository;
    private MutableLiveData<List<Incidencia>> incidenciasUserSuccess = new MutableLiveData<>();


    public IncidenciasViewModel(){
        this.repository = new Repository();
    }

    /*public List<Incidencia> getIncidenciasUsuario(String email){
        return this.repository.getIncidenciasUser(email, new Repository.getIncidenciasUserCallback() {
            @Override
            public void onSuccess(List<Incidencia> incidencias) {
                incidenciasUserSuccess.postValue(incidencias);
            }

            @Override
            public void onFailure() {

            }
        });
    }*/

    public LiveData<List<Incidencia>> getIncidenciasUserSuccess() {
        return incidenciasUserSuccess;
    }

}
