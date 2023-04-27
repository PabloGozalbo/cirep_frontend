package com.example.comun.model;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cirep_frontend.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;

public class Mapa implements OnMapReadyCallback {

    private List<Incidencia> incidencias;
    private FragmentManager fragmentManager;

    private GoogleMap map;

    public Mapa(List<Incidencia> incidencias, SupportMapFragment supportMapFragment, FragmentManager fragmentManager) {
        this.incidencias = incidencias;
        supportMapFragment.getMapAsync(this);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        this.map.setOnMapClickListener(this::onMapClick);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // transaction.add(R.id.map, new CustomMapFragment());
        transaction.commit();
    }

    private void onMapClick(LatLng latLng) {

    }

    public void addMarker(LatLng latLng){
        // Crear un icono personalizado en un archivo XML vectorial
        BitmapDescriptor iconoPersonalizado = BitmapDescriptorFactory.fromResource(R.drawable.icono_incidencia);

        // Crear un marcador personalizado en un mapa
        MarkerOptions marcadorPersonalizado = new MarkerOptions()
                .position(latLng)
                .title("Añadir nueva incidencia")
                .snippet("¿Estás seguro de que está aquí?")
                .icon(iconoPersonalizado);
        map.addMarker(marcadorPersonalizado);
    }


}
