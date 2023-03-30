package com.example.dashboard.ui.mapa.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.ActivityMapsBinding;

import com.example.dashboard.DashboardActivity;
import com.example.dashboard.ui.mapa.dialogo.DialogoPersonalizado;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private ActivityMapsBinding binding;
    private SupportMapFragment mapFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Obtener el SupportMapFragment y notificar cuando el mapa esté listo
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        DashboardActivity.map=this.map;
        this.map.setOnMapClickListener(this::onMapClick);
    }

    private void onMapClick(LatLng latLng) {
        DialogoPersonalizado dialogoPersonalizado = new DialogoPersonalizado(latLng);
        dialogoPersonalizado.setTargetFragment(this, 0);
        dialogoPersonalizado.show(getFragmentManager(), "mi_dialogo_personalizado");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}