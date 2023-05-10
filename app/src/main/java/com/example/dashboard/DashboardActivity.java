package com.example.dashboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.ActivityDashboardBinding;
import com.example.comun.cache.UserDataSession;
import com.example.comun.model.Incidencia;
import com.example.dashboard.ui.mapa.dialogo.DialogoPersonalizado;
import com.example.login.ui.login.LoginActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class DashboardActivity extends AppCompatActivity implements DialogoPersonalizado.OnMiDialogoPersonalizadoListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashboardBinding binding;
    public static GoogleMap map;
    public MenuItem logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDashboard.toolbar);
        binding.appBarDashboard.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });



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


    public void addMarker(LatLng latLng){
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
                .title("Mi marcador personalizado")
                .snippet("Este es un marcador personalizado en Google Maps")
                .icon(iconoPersonalizado);

        map.addMarker(marcadorPersonalizado);
    }

    @Override
    public void onAceptarClick(LatLng latLng) {
        addMarker(latLng);
       // goToReportarIncidencia();
    }

    @Override
    public void onCancelarClick() {
        //Por ahora no se hace nada
    }

    public void fillMap(List<Incidencia> incidencias) {
        for (Incidencia incidencia : incidencias) {
            LatLng latLng = new LatLng(incidencia.getLatitude(), incidencia.getLongitude());
            addMarker(latLng);
        }
    }

    private void doLogout(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
        UserDataSession.getInstance().deleteUser();
        UserDataSession.getInstance().setToken(null);
    }

    private void goToReportarIncidencia(){

    }
}