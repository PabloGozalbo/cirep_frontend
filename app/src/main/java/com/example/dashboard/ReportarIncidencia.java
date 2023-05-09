package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.ActivityLoginBinding;
import com.example.cirep_frontend.databinding.ActivityReportarIncidenciaBinding;
import com.example.comun.cache.UserDataSession;
import com.example.comun.model.Incidencia;
import com.example.comun.repository.Repository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class ReportarIncidencia extends AppCompatActivity {

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

        incidencia.setAuthor(UserDataSession.getInstance().getUsuario().getEmail());

        incidencia.setDescription(comentarios.getText().toString());

        Bitmap bitmap = imageData.getImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        incidencia.setImage(imageInByte);

        incidencia.setLatitude(latitud);
        incidencia.setLongitude(longitud);

        incidencia.setReport_date(Calendar.getInstance());

        incidencia.setReport_type(selector.getPrompt().toString());




    }
}