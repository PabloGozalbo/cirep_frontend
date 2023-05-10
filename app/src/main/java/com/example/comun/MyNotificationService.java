package com.example.comun;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.cirep_frontend.R;

import java.util.Timer;
import java.util.TimerTask;

public class MyNotificationService extends Service {
    private Handler mHandler = new Handler();
    private Timer mTimer = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Inicia el temporizador
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Llama al método que quieres ejecutar cada 5 minutos
                boolean shouldCreateNotification = miMetodo();

                // Si el método devuelve true, crea la notificación
                if (shouldCreateNotification) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("channel_id", "Nombre del canal", NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.createNotificationChannel(channel);


                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MyNotificationService.this, "channel_id")
                            .setSmallIcon(R.drawable.icono_incidencia)
                            .setContentTitle("La imasión a sudán a comenzado")
                            .setContentText("Haz click aquí para unirte")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    notificationManager.notify(0, builder.build());
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
    private boolean miMetodo() {
        // Lógica de tu método
        return true;
    }
}

