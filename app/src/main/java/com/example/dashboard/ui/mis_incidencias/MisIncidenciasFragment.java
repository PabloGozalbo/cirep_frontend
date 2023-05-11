package com.example.dashboard.ui.mis_incidencias;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.cirep_frontend.R;
import com.example.comun.model.Incidencia;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MisIncidenciasFragment extends Fragment {

    private IncidenciasViewModel viewModel;
    private final int NUM_INCIDENCIAS_FALSAS = 10;
    private Dialog dialogoCarga;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_incidencias, container, false);
       mostrarCarga();
       viewModel = new IncidenciasViewModel();
       viewModel.getIncidenciasUserSuccess().observe(getViewLifecycleOwner(), new Observer<List<Incidencia>>() {
           @Override
           public void onChanged(List<Incidencia> incidencias) {
               loadIncidencias(view, incidencias);
               ocultarCarga();
           }
       });
       viewModel.getIncidenciasUsuario();//UserDataSession.getInstance().getUsuario().getEmail());
       return view;
   }

   public void loadIncidencias(View view, List<Incidencia> incidencias){

       // Crear una nueva vista para la incidencia


       for(Incidencia incidencia:incidencias){
           LinearLayout incidenciasContainer = view.findViewById(R.id.incidencias_container);
           View incidenciaView = getLayoutInflater().inflate(R.layout.item_incidencia, null);
           // Configurar la vista con los datos de la incidencia
           ImageView imagenIncidencia = incidenciaView.findViewById(R.id.incidencia_imagen);
           TextView tituloIncidencia = incidenciaView.findViewById(R.id.incidencia_titulo);
           TextView descripcionIncidencia = incidenciaView.findViewById(R.id.incidencia_descripcion);
           tituloIncidencia.setText(incidencia.getReport_type());
           Bitmap bitmap = BitmapFactory.decodeByteArray(Base64.decode(incidencia.getImage(), Base64.DEFAULT), 0,Base64.decode(incidencia.getImage(), Base64.DEFAULT).length);
           imagenIncidencia.setImageBitmap(bitmap);
           imagenIncidencia.setRotation(90);
           descripcionIncidencia.setText(incidencia.getDescription());
           incidenciasContainer.addView(incidenciaView);
       }
       // ...

       // Añadir la vista al contenedor
   }

   private List<Incidencia> getFalsasIncidencias(){
        List<Incidencia> incidencias = new ArrayList<>();
        for(int i=0; i<NUM_INCIDENCIAS_FALSAS; i++) {
            Incidencia incidencia = new Incidencia();
            incidencia.setImage(Base64.encodeToString(decodeImage(R.drawable.mia_khalifa), Base64.DEFAULT));//TODO cambiar foto pls
            incidencias.add(incidencia);
        }
        return incidencias;
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