package com.example.register.steps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cirep_frontend.R;


public class StepTypeUserFragment extends Fragment {

    ImageView hall, person;


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


}