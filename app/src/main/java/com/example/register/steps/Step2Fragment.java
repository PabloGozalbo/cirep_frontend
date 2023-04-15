package com.example.register.steps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cirep_frontend.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step2Fragment extends Fragment {

    private Spinner spinnerGenero;
    private EditText etNombre, etApellido, etEmail;
    private Button btnContinuar, atras;


    public Step2Fragment() {
    }

    //TODO ANTES DE IR AL STEP3 QUE LA INFORMACION SE GUARDE

    public static Step2Fragment newInstance(String param1, String param2) {
        return new Step2Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.step2_registro, container, false);
        this.spinnerGenero = view.findViewById(R.id.spinner_gender);
        this.etNombre = view.findViewById(R.id.campo_nombre);
        this.etApellido = view.findViewById(R.id.campo_apellidos);
        this.etEmail = view.findViewById(R.id.campo_email);
        this.atras = view.findViewById(R.id.atras_step2);

        this.btnContinuar = view.findViewById(R.id.step2button);
        initComponents();

        return view;
    }

    private void initComponents(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(adapter);

        this.spinnerGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                // Hacer algo con el elemento seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean nombreValido = !TextUtils.isEmpty(etNombre.getText());
                boolean apellidoValido = !TextUtils.isEmpty(etApellido.getText());
                boolean emailValido = !TextUtils.isEmpty(etEmail.getText());
                btnContinuar.setEnabled(nombreValido && apellidoValido && emailValido);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        etNombre.addTextChangedListener(textWatcher);
        etApellido.addTextChangedListener(textWatcher);
        etEmail.addTextChangedListener(textWatcher);
        spinnerGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Habilitar botón solo si los campos de nombre y apellido no están vacíos
                boolean nombreValido = !TextUtils.isEmpty(etNombre.getText());
                boolean apellidoValido = !TextUtils.isEmpty(etApellido.getText());
                boolean emailValido = !TextUtils.isEmpty(etEmail.getText());
                btnContinuar.setEnabled(nombreValido && apellidoValido && emailValido);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStep3();
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        // Verificar si hay fragments en la pila antes de volver al fragment anterior
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // Si hay fragments en la pila, eliminar el fragment actual del stack de fragments
            fragmentManager.popBackStack();
        } else {
            // Si no hay fragments en la pila, cerrar la actividad actual
            getActivity().finish();
        }
    }

    private void goToStep3(){

        Bundle bundle = new Bundle();
        bundle.putString("nombre", String.valueOf(this.etNombre.getText()));
        bundle.putString("apellido", String.valueOf(this.etApellido.getText()));
        bundle.putString("email", String.valueOf(this.etEmail.getText()));
        bundle.putString("genero", this.spinnerGenero.getSelectedItem().toString());

        // Crear una instancia del nuevo fragmento y establecer el Bundle como su argumento
        Step3Fragment fragment = new Step3Fragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        transaction.replace(R.id.registerFragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

       // requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.registerFragmentContainerView, new Step2Fragment()).commit();
    }
}