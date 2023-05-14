package com.example.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.ActivityDashboardBinding;
import com.example.comun.cache.UserDataSession;
import com.example.comun.model.Incidencia;
import com.example.dashboard.ui.mapa.MapaViewModel;
import com.example.dashboard.ui.mapa.dialogo.DialogoPersonalizado;
import com.example.incidencia.DetalleIncidenciaActivity;
import com.example.login.ui.login.LoginActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements DialogoPersonalizado.OnMiDialogoPersonalizadoListener, OnMapReadyCallback {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashboardBinding binding;
    public static GoogleMap map;
    public MenuItem logout;
    private MapaViewModel mapaViewModel;
    private List<Incidencia> listaIncidencias;
    private List<Marker> marcadores;
    private static final String EN_REPARACION = "EN REPARACIÓN";
    private static final String PENDIENTE_REVISION = "PENDIENTE REVISIÓN";
    private static final String ARREGLADA = "ARREGLADA";
    private static final String DESCARTADA = "DESCARTADA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        marcadores = new ArrayList<>();

        NavigationView navigation = findViewById(R.id.nav_view);
        View headerView = navigation.getHeaderView(0);

        TextView nombreMenuLateral = headerView.findViewById(R.id.nombreMenuLateral);
        TextView emailMenuLateral = headerView.findViewById(R.id.emailMenuLateral);
        nombreMenuLateral.setText(UserDataSession.getInstance().getNombreCompleto());
        emailMenuLateral.setText(UserDataSession.getInstance().getUsuario().getEmail());

        mapaViewModel = new MapaViewModel();

        setSupportActionBar(binding.appBarDashboard.toolbar);
        binding.appBarDashboard.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        mapaViewModel.getIncidenciasSuccess().observe(this, new Observer<List<Incidencia>>() {
            @Override
            public void onChanged(List<Incidencia> incidencias) {
                listaIncidencias = incidencias;
                fillMap();
            }
        });
        mapaViewModel.getIncidencias();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_mapa, R.id.nav_perfil, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        this.logout = navigationView.getMenu().getItem(3);

        this.logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                doLogout();
                return true;
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }// TODO mapFragment no deberia ser null, pero lo és

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) {
            mapaViewModel.getIncidencias();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);

        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void goToDetalleIncidencia(int idIncidencia) {
        Intent intent = new Intent(DashboardActivity.this, DetalleIncidenciaActivity.class);
        intent.putExtra("incidencia", idIncidencia);
        startActivity(intent);
    }

    public void addMarker(LatLng latLng, Incidencia incidencia){
        // Crear un icono personalizado en un archivo XML vectorial
        BitmapDescriptor iconoPersonalizado = BitmapDescriptorFactory.fromBitmap(
                Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(getResources(), R.drawable.icono_incidencia),
                        50, // ancho deseado en píxeles
                        50, // alto deseado en píxeles
                        false)); // sin filtrado de escala

        // Crear un marcador personalizado en un mapa
        MarkerOptions marcadorPersonalizado = new MarkerOptions()
                .position(latLng)
                .title(incidencia.getReport_type())
                .snippet(formatDistance(distanciaHasta(latLng)))
                .icon(iconoPersonalizado);

        Marker nuevoMarcador= map.addMarker(marcadorPersonalizado);
        marcadores.add(nuevoMarcador);
        nuevoMarcador.setTag(incidencia);
    }

    private float distanciaHasta(LatLng latLng) {
        // Obtener la ubicación actual del usuario
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission")
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

// Crear un objeto Location para la ubicación del usuario
        Location userLocation = new Location("");
        userLocation.setLatitude(location.getLatitude());
        userLocation.setLongitude(location.getLongitude());

// Crear un objeto Location para la ubicación de la incidencia
        Location incidenciaLocation = new Location("");
        incidenciaLocation.setLatitude(latLng.latitude);
        incidenciaLocation.setLongitude(latLng.longitude);

// Calcular la distancia entre las dos ubicaciones en metros
        float distance = userLocation.distanceTo(incidenciaLocation);
        return distance;
    }

    public static String formatDistance(double distance) {
        if (distance > 10000) {
            return String.format("%.2f km", distance / 1000);
        } else {
            return String.format("%.2f m", distance);
        }
    }


    @Override
    public void onAceptarClick(LatLng latLng) {
        goToReportarIncidencia(latLng);
    }

    @Override
    public void onCancelarClick() {
        //Por ahora no se hace nada
    }

    public void fillMap() {
        if(this.listaIncidencias != null) {
            for (Incidencia incidencia : listaIncidencias) {
                LatLng latLng = new LatLng(incidencia.getLatitude(), incidencia.getLongitude());
                addMarker(latLng, incidencia);
            }
        }
    }

    private void doLogout(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
        UserDataSession.getInstance().deleteUser();
        UserDataSession.getInstance().setToken(null);
    }

    private void goToReportarIncidencia(LatLng latLng){
        Intent intent = new Intent(DashboardActivity.this, CameraActivity.class);
        intent.putExtra("latLng", latLng);
        startActivity(intent);
    }

    public void filtrarIncidenciaPorTipo(String tipo){
        String estadoIncidencia = "";
        switch (tipo){
            case EN_REPARACION:
                estadoIncidencia = Incidencia.EstadoIncidencia.EN_PROCESO;
                break;
            case PENDIENTE_REVISION:
                estadoIncidencia = Incidencia.EstadoIncidencia.PENDIENTE_REVISION;
                break;
            case ARREGLADA:
                estadoIncidencia = Incidencia.EstadoIncidencia.ARREGLADA;
                break;
            case DESCARTADA:
                estadoIncidencia = Incidencia.EstadoIncidencia.DESCARTADA;
                break;
            default:
                break;
        }


        if(estadoIncidencia.length() > 0) { //se ha seleccionado un filtro distinto a TODAS
            for (int i = 0; i < marcadores.size(); i++) {
                if (!listaIncidencias.get(i).getState().equals(estadoIncidencia)) {
                    marcadores.get(i).remove();
                }
            }
        } else { //se activan todos los marcadores
            fillMap();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Configurar el listener de los marcadores aquí
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Incidencia incidencia = (Incidencia) marker.getTag();
                goToDetalleIncidencia(incidencia.getId_report());
                return true;
            }
        });
    }

}