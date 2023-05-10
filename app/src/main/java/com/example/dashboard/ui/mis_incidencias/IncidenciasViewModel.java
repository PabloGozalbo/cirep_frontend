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
    private MutableLiveData<List<Incidencia>> incidenciasSuccess = new MutableLiveData<>();


    public IncidenciasViewModel(){
        this.repository = new Repository();
    }

    public void getIncidenciasUsuario(){
        this.repository.getIncidenciasUser( new Repository.getIncidenciasUserCallback() {
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

    public void getIncidencias(){
        this.repository.getIncidencias( new Repository.getIncidenciasCallback() {
            @Override
            public void onSuccess(List<Incidencia> incidencias) {
                incidenciasSuccess.postValue(incidencias);
            }

            @Override
            public void onFailure() {
                incidenciasSuccess.postValue(new ArrayList<>());
            }
        });
    }

    public LiveData<List<Incidencia>> getIncidenciasUserSuccess() {
        return incidenciasUserSuccess;
    }

    public LiveData<List<Incidencia>> getIncidenciasSuccess() {
        return incidenciasSuccess;
    }


    public void newIncidencia( Incidencia incidencia, String token){
        this.repository.crearIncidencia( incidencia, token, new Repository.createIncidenciasUserCallback() {

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

}
