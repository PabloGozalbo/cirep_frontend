package com.example.dashboard.ui.perfil;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    TextView nombre, apellidos, email, telefono, psswd, ciudad;
    ImageView editName, editLastName, editCity, editPhone,editPsswd;
    PerfilViewModel perfilViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = FragmentPerfilBinding.inflate(inflater, container, false).getRoot();
        perfilViewModel = new PerfilViewModel();
        getComponents(view);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getComponents(View view){
        //TextViews
        this.nombre = view.findViewById(R.id.perfil_nombre);
        this.apellidos = view.findViewById(R.id.perfil_apellidos);
        this.email = view.findViewById(R.id.perfil_email);
        this.telefono = view.findViewById(R.id.perfil_telefono);
        this.psswd = view.findViewById(R.id.perfil_contrasenya);
        this.ciudad = view.findViewById(R.id.perfil_ciudad);

        //

        this.editCity = view.findViewById(R.id.editCity);
        this.editName = view.findViewById(R.id.editName);
        this.editLastName = view.findViewById(R.id.editLastName);
        this.editPsswd = view.findViewById(R.id.editPsswd);
        this.editPhone = view.findViewById(R.id.editPhone);
    }

    private void initView(){
        this.nombre.setText(perfilViewModel.getNombreUser());
        this.apellidos.setText(perfilViewModel.getApellidosUser());
        this.telefono.setText(perfilViewModel.getTelefonoUser());
        this.email.setText(perfilViewModel.getEmailUser());
        this.psswd.setText("*******");
        this.ciudad.setText(perfilViewModel.getCiudadUser());

        //Listeners
        this.editCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChangeUserAttribute("city");
            }
        });

        this.editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChangeUserAttribute("first_name");
            }
        });

        this.editLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChangeUserAttribute("last_name");
            }
        });

        this.editPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChangeUserAttribute("phone_number");
            }
        });
        this.editPsswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChangeUserAttribute("password");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void goToChangeUserAttribute(String attribute){
        Intent intent = new Intent(this.getContext(), EditProfileActivity.class);
        intent.putExtra("attribute", attribute);
        startActivity(intent);
    }
}