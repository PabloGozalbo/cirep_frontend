package com.example.dashboard.ui.mapa;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.ActivityMapsBinding;
import com.example.dashboard.ui.mapa.dialogo.DialogoPersonalizado;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DialogoPersonalizado.OnMiDialogoPersonalizadoListener {

    private GoogleMap map;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        this.map.setOnMapClickListener(this::onMapClick);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
                .title("Mi marcador personalizado")
                .snippet("Este es un marcador personalizado en Google Maps")
                .icon(iconoPersonalizado);
        map.addMarker(marcadorPersonalizado);
    }

    @Override
    public void onAceptarClick(LatLng latLng) {
        addMarker(latLng);
        goToReportarIncidencia();
    }

    @Override
    public void onCancelarClick() {
        //Por ahora no se hace nada
    }

    private void goToReportarIncidencia(){

    }
}