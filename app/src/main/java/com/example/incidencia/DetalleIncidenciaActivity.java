package com.example.incidencia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cirep_frontend.R;
import com.example.comun.cache.UserDataSession;
import com.example.comun.model.Incidencia;
import com.example.dashboard.DashboardActivity;
import com.example.dashboard.ui.mis_incidencias.IncidenciasViewModel;

import java.util.List;
import java.util.Objects;

public class DetalleIncidenciaActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView descriptionTextView;
    private TextView reportDateTextView;
    private TextView stateTextView;
    private TextView incidentTypeTextView;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView authorTextView;
    private IncidenciasViewModel viewModel;
    private Button desacreditarButton;
    private Incidencia incidencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_incidencia);

        viewModel = new IncidenciasViewModel();

        imageView = findViewById(R.id.imageView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        reportDateTextView = findViewById(R.id.reportDateTextView);
        stateTextView = findViewById(R.id.stateTextView);
        incidentTypeTextView = findViewById(R.id.reportTypeTextView);
        latitudeTextView = findViewById(R.id.latitudeTextView);
        longitudeTextView = findViewById(R.id.longitudeTextView);
        authorTextView = findViewById(R.id.authorTextView);
        desacreditarButton = findViewById(R.id.btnDesacreditar);

        desacreditarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desacreditarIncidencia();
            }
        });

        // Obtiene la incidencia de la actividad anterior
        int idIncidencia = getIntent().getIntExtra("incidencia", 0);

        if (idIncidencia == 0){
            goToDashboard();
            finish();
        }
        viewModel.getIncidenciaPorIdCallback().observe(this, new Observer<Incidencia>() {
            @Override
            public void onChanged(Incidencia nuevaincidencia) {
                incidencia = nuevaincidencia;
                loadIncidenciaOnView(nuevaincidencia);
                if (Objects.equals(incidencia.getAuthor(), UserDataSession.getInstance().getUsuario().getEmail())){
                    desacreditarButton.setVisibility(View.INVISIBLE);
                }
            }
        });
        viewModel.getIncidenciaPorId(UserDataSession.getInstance().getToken(),idIncidencia);
    }

    private void desacreditarIncidencia() {
        if (incidencia != null){
            viewModel.desacreditarIncidencia(UserDataSession.getInstance().getToken(), incidencia.getId_report());
        }
    }

    private void loadIncidenciaOnView(Incidencia incidencia) {
        String imageB64 = incidencia.getImage();
        byte[] imageBytes = Base64.decode(imageB64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imageView.setImageBitmap(bitmap);

        descriptionTextView.setText(incidencia.getDescription());
        reportDateTextView.setText(incidencia.getReport_date());
        stateTextView.setText(incidencia.getState());
        incidentTypeTextView.setText(incidencia.getReport_type());
        latitudeTextView.setText(String.valueOf(incidencia.getLatitude()));
        longitudeTextView.setText(String.valueOf(incidencia.getLongitude()));
        authorTextView.setText(incidencia.getAuthor());
        imageView.setRotation(90);
    }

    private void goToDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

}