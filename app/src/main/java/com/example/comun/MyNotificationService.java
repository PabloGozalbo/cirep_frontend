package com.example.comun;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.cirep_frontend.R;
import com.example.comun.model.Incidencia;
import com.example.dashboard.ui.mis_incidencias.IncidenciasViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

public class MyNotificationService extends Service {
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    private IncidenciasViewModel viewModel = new IncidenciasViewModel();
    private FusedLocationProviderClient fusedLocationClient;
    private double longitud;
    private double latitud;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Inicia el temporizador
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Llama al método que quieres ejecutar cada 5 minutos
                List<Incidencia> incidenciasCerca = new ArrayList<>();
                List<Incidencia> incidenciasEstado = new ArrayList<>();
                try {
                    incidenciasCerca = getIncidenciasCerca();
                    incidenciasEstado = getIncidenciasEstado();
                }catch (Exception e){
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("channel_id", "Nombre del canal", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(channel);


                // Si el método devuelve true, crea la notificación
                int id = 0;
                 for (Incidencia incidencia:incidenciasCerca) {

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MyNotificationService.this, "channel_id")
                            .setSmallIcon(R.drawable.icono_incidencia)
                            .setContentTitle(incidencia.getReport_type()+" cerca de ti")
                            .setContentText("Haz click aquí para ver los detalles")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    notificationManager.notify(id, builder.build());
                     id+=1;
                    }


                    for (Incidencia incidencia:incidenciasEstado) {

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyNotificationService.this, "channel_id")
                                .setSmallIcon(R.drawable.icono_incidencia)
                                .setContentTitle("Se ha actulizado el estado de la incidencia "+ incidencia.getReport_type())
                                .setContentText("Haz click aquí para ver los detalles")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        notificationManager.notify(id, builder.build());
                        id+=1;
                    }
                }
            }
        }, 0, 1 * 60 * 1000); // Programa la ejecución cada 1 minuto

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Detiene el temporizador
        mTimer.cancel();
        mTimer.purge();
        mTimer = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Método que quieres ejecutar cada X tiempo
    @SuppressLint("MissingPermission")
    private List<Incidencia> getIncidenciasCerca() {
        // Call the method to fetch the list of incidencias
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(Executors.newSingleThreadExecutor(),new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    longitud = location.getLongitude();
                    latitud = location.getLatitude();
                } else {
                    //TODO crear un aviso para que enchufe el gps
                }
            }
        });

        Map<String,Double> locationMap = new HashMap<>();
        locationMap.put("longitude",longitud);
        locationMap.put("latitude",latitud);
        viewModel.getIncidenciasCerca(locationMap);
        return viewModel.getIncidenciasCercaSuccess().getValue()!=null?viewModel.getIncidenciasCercaSuccess().getValue():new ArrayList<>();
    }

    private List<Incidencia> getIncidenciasEstado() {
        // Call the method to fetch the list of incidencias
        viewModel.getIncidenciasEstado();
        return viewModel.getIncidenciasEstadoSuccess().getValue();
    }
}

