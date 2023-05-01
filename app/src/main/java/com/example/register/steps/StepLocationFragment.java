package com.example.register.steps;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cirep_frontend.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class StepLocationFragment extends Fragment {


    private AutocompleteSupportFragment autocompleteFragment;
    private TextView direccionOculta;
    private Button confirm;
    private AutoCompleteTextView direccion;
    private String address;
    private TextView direccionCompleta;
    private boolean isStaff;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.isStaff = bundle.getBoolean("isStaff");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_location, container, false);

        if (!Places.isInitialized()) {
            Places.initialize(this.getContext(), "AIzaSyC2QpV2SMcolMB47b53ZtCzhgWXYDtF6NQ");
        }

        this.confirm = view.findViewById(R.id.confirm_location);
        this.confirm.setEnabled(false);
        this.direccion = view.findViewById(R.id.direccion);
        this.direccionCompleta = view.findViewById(R.id.direccion_completa);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                configureContinueButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
                configureContinueButton();
            }
        };

        direccionCompleta.addTextChangedListener(afterTextChangedListener);

        this.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });


// Configura la API de Google Places Autocomplete
        AutocompleteSupportFragment autocompleteFragment = new AutocompleteSupportFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.autocomplete_container, autocompleteFragment)
                .commit();
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));

// Configura un listener para detectar cuando el usuario selecciona una dirección
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                // Actualiza el texto del AutoCompleteTextView con la dirección seleccionada
                direccion.setText(place.getName());
                direccion.setSelection(direccion.getText().length());

                // Muestra la dirección completa en el TextView
                direccionCompleta.setText(place.getAddress());
                configureContinueButton();
            }

            @Override
            public void onError(@NotNull Status status) {
                // Maneja errores de la API de Google Places Autocomplete
            }
        });

        return view;
    }

    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¿Seguro que tu dirección es correcta?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                bundle.putString("city",direccion.getText().toString());
                goToStep2(bundle);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada
            }
        });
        builder.create().show();
    }

    //TODO: NO SE ESTA COGIENDO NI PASANDO LA ADDRESS
    private void goToStep2(Bundle bundle){

        // Crear una instancia del nuevo fragmento y establecer el Bundle como su argumento
        Step2Fragment fragment = new Step2Fragment();
        bundle.putBoolean("isStaff", this.isStaff);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        transaction.replace(R.id.registerFragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void configureContinueButton(){
        this.confirm.setEnabled(!this.direccionCompleta.getText().toString().equals(""));
    }

}