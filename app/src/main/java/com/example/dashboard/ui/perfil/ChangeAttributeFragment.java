package com.example.dashboard.ui.perfil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cirep_frontend.R;

public class ChangeAttributeFragment extends Fragment {
    String attribute;
    TextView subtitle;

    public ChangeAttributeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.attribute = bundle.getString("attribute");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_attribute, container, false);

        this.subtitle = view.findViewById(R.id.subtitle_editProfile);
        initView();

        return view;
    }

    private void initView(){
        switch (attribute){
            case "name":
                subtitle.setText("Por favor, a continuación confirme el nuevo nombre");
                break;
            case "city":
                subtitle.setText("Por favor, a continuación confirme la nueva ciudad");
                break;
            case "lastname":
                subtitle.setText("Por favor, a continuación confirme los nuevos apellidos");
                break;
            case "phone":
                subtitle.setText("Por favor, a continuación confirme el nuevo teléfono");
                break;
            case "psswd":
                subtitle.setText("Por favor, a continuación confirme la nueva contraseña");
                break;
        }
    }
}