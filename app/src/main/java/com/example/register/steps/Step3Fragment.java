package com.example.register.steps;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cirep_frontend.R;
import com.example.comun.model.user.Usuario;
import com.example.register.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step3Fragment extends Fragment {

    private EditText etTelefono, contrasenya1, contrasenya2;
    private Button btnContinuar, atras;
    private Usuario user;
    private Dialog dialogoCarga;
    private static final String EXPRESION_REGULAR_TELEFONO = "^[67]\\d{8}$";


    public Step3Fragment() {
    }

    //TODO ANTES DE IR AL STEP4 QUE LA INFORMACION SE GUARDE

    public static Step3Fragment newInstance() {
        return new Step3Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.user = new Usuario();
        Bundle bundle = getArguments();
        // Obtener los valores de los tres strings del Bundle
        this.user.setFirstName(bundle.getString("nombre"));
        this.user.setLastName(bundle.getString("apellido"));
        this.user.setEmail(bundle.getString("email"));
        this.user.setStaff(bundle.getBoolean("isStaff"));
        this.user.setCity(bundle.getString("city"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.step3_registro, container, false);
        getComponents(view);
        initComponents();
        observeRegistrationSuccess();
        return view;
    }

    private void getComponents(View view) {
        this.etTelefono = view.findViewById(R.id.campo_telefono);
        this.contrasenya1 = view.findViewById(R.id.campo_contrasenya1);
        this.contrasenya2 = view.findViewById(R.id.campo_contrasenya2);
        this.atras = view.findViewById(R.id.atras_step3);
        this.btnContinuar = view.findViewById(R.id.step3button);
    }

    private void initComponents() {
        // btnContinuar.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean camposLlenos = !TextUtils.isEmpty(etTelefono.getText()) && !TextUtils.isEmpty(contrasenya1.getText()) && !TextUtils.isEmpty(contrasenya2.getText());
                boolean validPasswd = contrasenya2.getText().toString().equals(contrasenya1.getText().toString());

                if (camposLlenos && !validPasswd) {
                    contrasenya2.setError("Las contraseñas deben coincidir");
                } else if (validPasswd) {
                    contrasenya2.setError(null);
                }

                btnContinuar.setEnabled(camposLlenos && validPasswd);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        etTelefono.addTextChangedListener(textWatcher);
        contrasenya1.addTextChangedListener(textWatcher);
        contrasenya2.addTextChangedListener(textWatcher);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setPhoneNumber(String.valueOf(etTelefono.getText()));
                user.setPassword(String.valueOf(contrasenya1.getText()));
                mostrarCarga();
                ((RegisterActivity) getActivity()).registerUser(user);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void onBackPressed() {
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

    private void observeRegistrationSuccess() {
        ((RegisterActivity) getActivity()).getViewModel().getRegistrationSuccessLiveData().observe(getViewLifecycleOwner(), registrationSuccess -> {
            ocultarCarga();
        });
    }

    private boolean checkValidPhone(String phone){
        return phone.matches(EXPRESION_REGULAR_TELEFONO);
    }
}