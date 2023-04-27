package com.example.dashboard.ui.mapa;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.ActivityMapsBinding;
import com.example.comun.model.Incidencia;
import com.example.dashboard.ui.mapa.dialogo.DialogoPersonalizado;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DialogoPersonalizado.OnMiDialogoPersonalizadoListener {

    private GoogleMap map;
    private ActivityMapsBinding binding;
    private MapaViewModel mapaViewModel;
    private static final int REQUEST_CODE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkLocationPermission();

    }

    public void mostrarMapa(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Si los permisos no han sido otorgados, solicitarlos al usuario
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        } else {
            // Si los permisos ya han sido otorgados, mostrar el mapa en la ubicación del usuario
            mostrarMapa();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_LOCATION) {
            // Verificar si el usuario ha otorgado permisos de ubicación
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si los permisos han sido otorgados, mostrar el mapa en la ubicación del usuario
                mostrarMapa();
            } else {
                // Si los permisos no han sido otorgados, mostrar un mensaje al usuario o tomar alguna otra acción
                Toast.makeText(this, "Es necesario otorgar permisos de ubicación para las incidencias de su ciudad", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        this.map.setOnMapClickListener(this::onMapClick);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fillMap(mapaViewModel.getIncidencias());
       // transaction.add(R.id.map, new CustomMapFragment());
        transaction.commit();
    }

    public void fillMap(List<Incidencia> incidencias){
        for(Incidencia incidencia: incidencias){
            LatLng latLng = new LatLng(incidencia.getLatitude(), incidencia.getLongitude());
            addMarker(latLng);
        }
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