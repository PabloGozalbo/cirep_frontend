package com.example.register.steps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cirep_frontend.R;


public class StepTypeUserFragment extends Fragment {

    ImageView hall, person;
    Button atras;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_type_user, container, false);

       initComponents(view);

       initListeners();

        return view;
    }

    private void initComponents(View view){
        this.hall = view.findViewById(R.id.ayuntamiento);
        this.person = view.findViewById(R.id.ciudadano);
        this.atras = view.findViewById(R.id.atras_stepTypeUser);
    }

    private void initListeners(){
        this.hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isStaff", true);
                goToStep2(bundle);
            }
        });

        this.person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isStaff", false);
                goToStep2(bundle);
            }
        });

        this.atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void goToStep2(Bundle bundle){

        // Crear una instancia del nuevo fragmento y establecer el Bundle como su argumento
        StepLocationFragment fragment = new StepLocationFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        transaction.replace(R.id.registerFragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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


}