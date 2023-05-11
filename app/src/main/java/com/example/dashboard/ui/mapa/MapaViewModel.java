package com.example.dashboard.ui.mapa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.comun.model.Incidencia;
import com.example.comun.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MapaViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<Incidencia>> incidenciasSuccess = new MutableLiveData<>();

    public MapaViewModel() {
        this.repository = new Repository();
    }

    public List<Incidencia> getIncidencias(){
        // Llamar al método de registro de UserRepository
        repository.getIncidencias(new Repository.getIncidenciasCallback() {
            @Override
            public void onSuccess(List<Incidencia> incidencias) {
                incidenciasSuccess.postValue(incidencias);
            }

            //TODO: que se muestre un error de conexión
            @Override
            public void onFailure() {
                incidenciasSuccess.postValue(new ArrayList<>());
            }
        });
        return null;
    }

    public LiveData<List<Incidencia>> getIncidenciasSuccess() {
        return incidenciasSuccess;
    }
}