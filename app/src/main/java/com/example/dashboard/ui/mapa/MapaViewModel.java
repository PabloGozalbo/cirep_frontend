package com.example.dashboard.ui.mapa;

import androidx.lifecycle.ViewModel;

import com.example.comun.model.Incidencia;
import com.example.comun.repository.Repository;

import java.util.List;

public class MapaViewModel extends ViewModel {

    private Repository repository;

    public MapaViewModel() {
        this.repository = new Repository();
    }

    public List<Incidencia> getIncidencias(){
        // Llamar al método de registro de UserRepository
        repository.getIncidencias(new Repository.getIncidenciasCallback() {
            @Override
            public List<Incidencia> onSuccess(List<Incidencia> incidencias) {
                return incidencias;
            }

            //TODO: que se muestre un error de conexión
            @Override
            public void onFailure() {

            }
        });
        return null;
    }
}