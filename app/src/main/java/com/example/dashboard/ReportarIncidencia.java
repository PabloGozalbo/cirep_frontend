package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.internal.utils.ImageUtil;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.ActivityLoginBinding;
import com.example.cirep_frontend.databinding.ActivityReportarIncidenciaBinding;

import java.nio.ByteBuffer;

public class ReportarIncidencia extends AppCompatActivity {

    private Spinner selector;
    private ActivityReportarIncidenciaBinding binding;
    private Button reportar;
    private ImageView imagen;
    private EditText comentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityReportarIncidenciaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selector = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selector.setAdapter(adapter);

        comentarios = findViewById(R.id.editTextTextMultiLine);
        imagen = findViewById(R.id.imageView2);



    }
}