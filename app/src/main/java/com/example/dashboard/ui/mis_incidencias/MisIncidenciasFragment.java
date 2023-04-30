package com.example.dashboard.ui.mis_incidencias;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.FragmentIncidenciasBinding;
import com.example.comun.model.Incidencia;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MisIncidenciasFragment extends Fragment {

    private IncidenciasViewModel viewModel;
    private final int NUM_INCIDENCIAS_FALSAS = 10;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_incidencias, container, false);
       loadIncidencias(view, getFalsasIncidencias());
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
           imagenIncidencia.setImageBitmap(BitmapFactory.decodeByteArray(incidencia.getImage(), 0, incidencia.getImage().length));
           tituloIncidencia.setText(incidencia.getTitle());
           incidenciasContainer.addView(incidenciaView);
       }
       // ...

       // Añadir la vista al contenedor
   }

   private List<Incidencia> getFalsasIncidencias(){
        List<Incidencia> incidencias = new ArrayList<>();
        for(int i=0; i<NUM_INCIDENCIAS_FALSAS; i++) {
            Incidencia incidencia = new Incidencia();
            incidencia.setTitle("HOLA ESTO ES UNA PRUEBA "+i);
            incidencia.setImage(decodeImage(R.drawable.mia_khalifa));
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
}