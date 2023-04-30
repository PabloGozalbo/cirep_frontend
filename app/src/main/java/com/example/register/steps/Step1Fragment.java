package com.example.register.steps;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cirep_frontend.R;
import com.example.login.ui.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step1Fragment extends Fragment {

    Button siguiente, atras;


    public Step1Fragment() {
    }


    public static Step1Fragment newInstance() {
        return new Step1Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.step1_registro, container, false);
        siguiente = view.findViewById(R.id.step1button);
        atras = view.findViewById(R.id.atras_step1);
        initComponents();

        return view;
    }

    private void initComponents(){
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTypeStep();
            }
        });
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void goToTypeStep(){
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        transaction.replace(R.id.registerFragmentContainerView, new StepTypeUserFragment());
        transaction.addToBackStack(null);
        transaction.commit();
        //requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.registerFragmentContainerView, new Step2Fragment()).commit();
    }

    private void goToLogin(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    public void onBackPressed(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        // Verificar si hay fragments en la pila antes de volver al fragment anterior
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // Si hay fragments en la pila, eliminar el fragment actual del stack de fragments
            fragmentManager.popBackStack();
        } else {
            // Si no hay fragments en la pila, cerrar la actividad actual
            goToLogin();
        }
    }
}