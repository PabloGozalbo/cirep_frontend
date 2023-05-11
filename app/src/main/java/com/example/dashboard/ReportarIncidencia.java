package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.ActivityReportarIncidenciaBinding;
import com.example.comun.cache.UserDataSession;
import com.example.comun.model.Incidencia;
import com.example.dashboard.ui.mis_incidencias.IncidenciasViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReportarIncidencia extends AppCompatActivity {

    private IncidenciasViewModel viewModel;
    private Spinner selector;
    private ActivityReportarIncidenciaBinding binding;
    private Button reportar;
    private ImageView imagen;
    private EditText comentarios;
    private ImageData imageData;
    private FusedLocationProviderClient fusedLocationClient;
    private double longitud;
    private double latitud;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    longitud = location.getLongitude();
                    latitud = location.getLatitude();
                }
                else {
                    //TODO crear un aviso para que enchufe el gps
                }
            }
        });

        viewModel = new IncidenciasViewModel();

        binding = ActivityReportarIncidenciaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selector = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selector.setAdapter(adapter);

        comentarios = findViewById(R.id.editTextTextMultiLine);

        imagen = findViewById(R.id.imageView2);
        imageData = ImageData.getInstantce();
        imagen.setImageBitmap(imageData.getImage());
        imagen.setRotation(90);

        comentarios = findViewById(R.id.editTextTextMultiLine);

        reportar = findViewById(R.id.button);
        reportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportar();
            }
        });
    }

    private void reportar() {
        Incidencia incidencia = new Incidencia();

        incidencia.setDescription(comentarios.getText().toString());

        Bitmap bitmap = imageData.getImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        String base64Image = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        incidencia.setImage(base64Image);

        incidencia.setLatitude(latitud);
        incidencia.setLongitude(longitud);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        incidencia.setReport_date(strDate);

        incidencia.setReport_type(selector.getSelectedItem().toString());

        incidencia.setState(Incidencia.Estado.PENDIENTE_REVISION);

        viewModel.newIncidencia(incidencia, UserDataSession.getInstance().getToken());

        goToDashboard();

    }

    private void goToDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}