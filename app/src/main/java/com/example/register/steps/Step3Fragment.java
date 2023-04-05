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
import android.widget.Button;
import android.widget.EditText;

import com.example.cirep_frontend.R;
import com.example.comun.result.ResultOkFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step3Fragment extends Fragment {

    private EditText etTelefono;
    private Button btnContinuar, atras;


    public Step3Fragment() {
    }

    //TODO ANTES DE IR AL STEP4 QUE LA INFORMACION SE GUARDE

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

        View view=inflater.inflate(R.layout.step3_registro, container, false);
        this.etTelefono = view.findViewById(R.id.campo_telefono);
        this.atras = view.findViewById(R.id.atras_step3);

        this.btnContinuar = view.findViewById(R.id.step3button);
        initComponents();

        return view;
    }

    private void initComponents(){

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean nombreValido = !TextUtils.isEmpty(etTelefono.getText());
                btnContinuar.setEnabled(nombreValido);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        etTelefono.addTextChangedListener(textWatcher);


        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStep4();
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

    private void goToStep4(){
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_out_right, R.anim.slide_out_left);
        transaction.replace(R.id.registerFragmentContainerView, new ResultOkFragment());
        transaction.addToBackStack(null);
        transaction.commit();

        // requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.registerFragmentContainerView, new Step2Fragment()).commit();
    }
}