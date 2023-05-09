package com.example.dashboard.ui.mis_incidencias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comun.model.Incidencia;
import com.example.comun.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class IncidenciasViewModel {

    Repository repository;
    private MutableLiveData<List<Incidencia>> incidenciasUserSuccess = new MutableLiveData<>();


    public IncidenciasViewModel(){
        this.repository = new Repository();
    }

    public void getIncidenciasUsuario(String email){
        this.repository.getIncidenciasUser(email, new Repository.getIncidenciasUserCallback() {
            @Override
            public void onSuccess(List<Incidencia> incidencias) {
                incidenciasUserSuccess.postValue(incidencias);
            }

            @Override
            public void onFailure() {
                incidenciasUserSuccess.postValue(new ArrayList<>());
            }
        });
    }

    public LiveData<List<Incidencia>> getIncidenciasUserSuccess() {
        return incidenciasUserSuccess;
    }

}
