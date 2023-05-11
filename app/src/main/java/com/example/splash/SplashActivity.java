package com.example.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cirep_frontend.R;
import com.example.comun.MyNotificationService;
import com.example.dashboard.DashboardActivity;
import com.example.login.ui.login.LoginActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 3000; // tiempo en milisegundos
    ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);

        splash = findViewById(R.id.splashImageView);
        setTheme();
        // Retrasar la transición a la pantalla de inicio de sesión durante 2 segundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MyNotificationService.class);
                startService(intent);
                goToLogin();
            }
        }, SPLASH_TIMEOUT);
    }

    private void setTheme(){
        if (isDarkModeOn()) {
            splash.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.splash_cirep_dark));
        } else {
            splash.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.splash_cirep_light));
        }
    }

    private boolean isDarkModeOn() {
        int nightModeFlags = getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    private void goToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToDashboard() {
        Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}