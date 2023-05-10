package com.example.dashboard.ui.mapa.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.ActivityMapsBinding;
import com.example.comun.model.Incidencia;
import com.example.dashboard.DashboardActivity;
import com.example.dashboard.ui.mapa.dialogo.DialogoPersonalizado;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;

    private SupportMapFragment mapFragment;
    private LocationManager locationManager;
    private Location lastLocation;


    public static final int REQUEST_CHECK_SETTINGS = 1001;


    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (isGranted) {
                            zoomMap();
                        } else {
                            // Permiso denegado, debes manejar este caso
                        }
                    });


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Obtener el SupportMapFragment y notificar cuando el mapa esté listo
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);




        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        } else if (!isLocationEnabled()) {
            showAlertDialog();
        } else if(map!=null) {
            zoomMap();
        }

        return view;
    }


    @SuppressLint("MissingPermission")
    private void zoomMap(){
        map.setMyLocationEnabled(true);

        // Obtiene la ubicación actual del usuario
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            lastLocation = location;
            // Mueve la cámara del mapa a la ubicación actual del usuario
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        } else {
            if(lastLocation == null) {
                // Si no se puede obtener la ubicación del usuario, mueve la cámara del mapa a una ubicación predeterminada
                LatLng defaultLatLng = new LatLng(37.7749, -122.4194); // San Francisco, CA
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 12));
            }else{
                LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            }
        }
    }

    private boolean isLocationEnabled(){
        return ((LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void showLocationSettings() {
        // Abre la pantalla de configuración de ubicación para que el usuario pueda activarla
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, REQUEST_CHECK_SETTINGS);
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Activar Ubicación");
        builder.setMessage("Tienes que habilitar la ubicación para mostrarte las incidencias cercanas. ¿Desea activar la ubicación ahora?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showLocationSettings();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            // Comprobar si el usuario ha activado la ubicación
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(!isLocationEnabled()){
                    showAlertDialog();
                }
            } else {
                zoomMap();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        DashboardActivity.map = this.map;
        this.map.setOnMapClickListener(this::onMapClick);
        zoomMap();
    }

    private void onMapClick(LatLng latLng) {
        DialogoPersonalizado dialogoPersonalizado = new DialogoPersonalizado(latLng);
        dialogoPersonalizado.setTargetFragment(this, 0);
        dialogoPersonalizado.show(getFragmentManager(), "mi_dialogo_personalizado");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}