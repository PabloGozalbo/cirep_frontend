package com.example.dashboard.ui.perfil;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cirep_frontend.R;
import com.example.cirep_frontend.databinding.FragmentPerfilBinding;
import com.example.comun.cache.UserDataSession;

public class PerfilFragment extends Fragment {

    TextView nombre, apellidos, email, telefono;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = FragmentPerfilBinding.inflate(inflater, container, false).getRoot();
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

        //
    }

    private void initView(){
        UserDataSession userDataSession = UserDataSession.getInstance();

    }




}