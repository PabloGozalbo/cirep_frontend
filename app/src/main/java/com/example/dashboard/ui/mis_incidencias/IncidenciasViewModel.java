package com.example.dashboard.ui.mis_incidencias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comun.model.Incidencia;
import com.example.comun.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IncidenciasViewModel {

    Repository repository;
    private MutableLiveData<List<Incidencia>> incidenciasUserSuccess = new MutableLiveData<>();
    private MutableLiveData<List<Incidencia>> incidenciasSuccess = new MutableLiveData<>();
    private MutableLiveData<List<Incidencia>> incidenciasCercaSuccess = new MutableLiveData<>();
    private MutableLiveData<List<Incidencia>> incidenciasEstadoSuccess = new MutableLiveData<>();
    private MutableLiveData<Incidencia> incidenciaPorIdSuccess = new MutableLiveData<>();


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

    public void getIncidenciasCerca(Map<String,Double> location){
        this.repository.getIncidenciasCerca(location ,new Repository.getIncidenciasCercaCallback() {
            @Override
            public void onSuccess(List<Incidencia> incidencias) {
                incidenciasCercaSuccess.postValue(incidencias);
            }

            @Override
            public void onFailure() {
                incidenciasCercaSuccess.postValue(new ArrayList<>());
            }
        });
    }

    public void getIncidenciasEstado(){
        this.repository.getIncidenciasEstado( new Repository.getIncidenciasEstadoCallback() {
            @Override
            public void onSuccess(List<Incidencia> incidencias) {
                incidenciasEstadoSuccess.postValue(incidencias);
            }

            @Override
            public void onFailure() {
                incidenciasEstadoSuccess.postValue(new ArrayList<>());
            }
        });
    }

    public LiveData<List<Incidencia>> getIncidenciasUserSuccess() {
        return incidenciasUserSuccess;
    }

    public LiveData<List<Incidencia>> getIncidenciasSuccess() {
        return incidenciasSuccess;
    }

    public LiveData<List<Incidencia>> getIncidenciasCercaSuccess() {
        return incidenciasCercaSuccess;
    }

    public LiveData<List<Incidencia>> getIncidenciasEstadoSuccess() {
        return incidenciasEstadoSuccess;
    }

    public LiveData<Incidencia> getIncidenciaPorIdCallback() {
        return incidenciaPorIdSuccess;
    }

    public void newIncidencia( Incidencia incidencia, String token){
        this.repository.crearIncidencia( incidencia, token, new Repository.createIncidenciasUserCallback() {

            @Override
            public void onSuccess(List<Incidencia> incidencias) {
                incidenciasUserSuccess.postValue(incidencias);
                incidenciasSuccess.postValue(incidencias);
                incidenciasCercaSuccess.postValue(incidencias);
                incidenciasEstadoSuccess.postValue(incidencias);
            }
            @Override
            public void onFailure() {
                incidenciasUserSuccess.postValue(new ArrayList<>());
                incidenciasSuccess.postValue(new ArrayList<>());
                incidenciasCercaSuccess.postValue(new ArrayList<>());
                incidenciasEstadoSuccess.postValue(new ArrayList<>());
            }
        });
    }

    public void getIncidenciaPorId(String token, int id){
        this.repository.getIncidenciaPorId(token, id, new Repository.getIncidenciaPorIdCallback() {
            @Override
            public void onSuccess(Incidencia incidencia) {
                incidenciaPorIdSuccess.postValue(incidencia);
            }

            @Override
            public void onFailure() {
                incidenciaPorIdSuccess.postValue(null);
            }
        });
    }

    public void desacreditarIncidencia(String token, int id ){
        this.repository.desacreditarIncidencia(token, id, new Repository.desacreditarIncidenciaCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
    }

}
