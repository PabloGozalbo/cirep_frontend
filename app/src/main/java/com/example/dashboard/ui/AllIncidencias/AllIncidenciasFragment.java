package com.example.dashboard.ui.AllIncidencias;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cirep_frontend.R;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cirep_frontend.R;
import com.example.comun.model.Incidencia;
import com.example.dashboard.ui.mis_incidencias.IncidenciasViewModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class AllIncidenciasFragment extends Fragment {

    IncidenciasViewModel viewModel;
    Dialog dialogoCarga;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_incidencias, container, false);

        viewModel = new IncidenciasViewModel();
        viewModel.getIncidenciasSuccess().observe(getViewLifecycleOwner(), new Observer<List<Incidencia>>() {
            @Override
            public void onChanged(List<Incidencia> incidencias) {
                loadIncidencias(view, incidencias);
                ocultarCarga();
            }
        });
        viewModel.getIncidencias();//UserDataSession.getInstance().getUsuario().getEmail());
        return view;
    }

    public void loadIncidencias(View view, List<Incidencia> incidencias){


        for(Incidencia incidencia:incidencias){
            LinearLayout incidenciasContainer = view.findViewById(R.id.incidencias_container);
            View incidenciaView = getLayoutInflater().inflate(R.layout.item_incidencia, null);
            // Configurar la vista con los datos de la incidencia
            ImageView imagenIncidencia = incidenciaView.findViewById(R.id.incidencia_imagen);
            TextView tituloIncidencia = incidenciaView.findViewById(R.id.incidencia_titulo);
            imagenIncidencia.setImageBitmap(BitmapFactory.decodeByteArray(incidencia.getImage(), 0, incidencia.getImage().length));
            incidenciasContainer.addView(incidenciaView);
        }
        // ...


    }


    private byte[] decodeImage(int idImagen){
        // Convierte la imagen en formato Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), idImagen);

        // Convierte la imagen en formato byte[]
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public void mostrarCarga() {
        if (dialogoCarga == null) {
            dialogoCarga = new Dialog(this.getContext(), R.style.LoadingDialog);
            dialogoCarga.setContentView(R.layout.loading_layout);
            dialogoCarga.setCancelable(false);
            dialogoCarga.show();
        }
    }

    public void ocultarCarga() {
        if (dialogoCarga != null && dialogoCarga.isShowing()) {
            dialogoCarga.dismiss();
            dialogoCarga = null;
        }
    }

}