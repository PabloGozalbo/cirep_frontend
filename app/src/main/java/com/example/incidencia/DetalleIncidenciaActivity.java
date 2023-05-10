package com.example.incidencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cirep_frontend.R;
import com.example.comun.cache.UserDataSession;
import com.example.comun.model.Incidencia;
import com.example.dashboard.DashboardActivity;
import com.example.dashboard.ui.mis_incidencias.IncidenciasViewModel;

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

        // Obtiene la incidencia de la actividad anterior
        int idIncidencia = getIntent().getIntExtra("incidencia", 0);

        if (idIncidencia == 0){
            goToDashboard();
            finish();
        }
        viewModel.getIncidenciaPorId(UserDataSession.getInstance().getToken(),idIncidencia);//TODO llamada
        viewModel.getIncidenciaPorIdCallback().observe();

        // Muestra los datos de la incidencia en la vista
        if (incident != null) {
            // Muestra la imagen de la incidencia
            byte[] imageBytes = incident.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(bitmap);

            descriptionTextView.setText(incident.getDescription());
            reportDateTextView.setText(incident.getReport_date());
            stateTextView.setText(incident.getState());
            incidentTypeTextView.setText(incident.getReport_type());
            latitudeTextView.setText(String.valueOf(incident.getLatitude()));
            longitudeTextView.setText(String.valueOf(incident.getLongitude()));
            authorTextView.setText(incident.getAuthor());
        }
    }

    private void goToDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

}