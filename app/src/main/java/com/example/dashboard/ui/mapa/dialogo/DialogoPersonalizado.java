package com.example.dashboard.ui.mapa.dialogo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.cirep_frontend.R;
import com.google.android.gms.maps.model.LatLng;

public class DialogoPersonalizado extends DialogFragment {

    private TextView tvTitulo;
    private Button btnCancelar, btnAceptar;
    private LatLng latLng;
    private OnMiDialogoPersonalizadoListener listener;

    // Método para crear una nueva instancia del diálogo
    public static DialogoPersonalizado newInstance(LatLng latLng) {
        return new DialogoPersonalizado(latLng);
    }

    public DialogoPersonalizado(LatLng latLng) {
        this.latLng = latLng;
    }

    // Interface para comunicar los resultados del diálogo a la actividad que lo llamó
    public interface OnMiDialogoPersonalizadoListener {
        void onAceptarClick(LatLng latLng);
        void onCancelarClick();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnMiDialogoPersonalizadoListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " debe implementar OnMiDialogoPersonalizadoListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.mi_dialogo_personalizado, null);

        tvTitulo = view.findViewById(R.id.titulo_incidencia_dialog);
        btnCancelar = view.findViewById(R.id.btn_cancelar);
        btnAceptar = view.findViewById(R.id.btn_aceptar);

        builder.setView(view);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancelarClick();
                dismiss();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAceptarClick(latLng);
                dismiss();
            }
        });

        return builder.create();
    }
}
