package com.example.dashboard.ui.perfil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cirep_frontend.R;
import com.example.comun.cache.UserDataSession;

public class ChangeAttributeFragment extends Fragment {
    String attribute;
    TextView subtitle, title;
    Button confirmar;
    EditText editTextEditProfile;
    PerfilViewModel perfilViewModel;

    private static final String EXPRESION_REGULAR_TELEFONO = "^[67]\\d{8}$";


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


        perfilViewModel = new PerfilViewModel();
        this.subtitle = view.findViewById(R.id.subtitle_editProfile);
        this.title = view.findViewById(R.id.title_editProfile);
        this.confirmar = view.findViewById(R.id.confirmEditProfile);
        this.editTextEditProfile = view.findViewById(R.id.editText_editProfile);

        confirmar.setEnabled(false);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attribute.equals("phone")){
                    if(checkValidPhone(editTextEditProfile.getText().toString())){
                        perfilViewModel.editProfile(attribute, UserDataSession.getInstance().getUsuario().getEmail());
                    } else {
                        editTextEditProfile.setError("Por favor, indica un número de teléfono válido");
                    }
                } else {
                    perfilViewModel.editProfile(attribute, UserDataSession.getInstance().getUsuario().getEmail());
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                confirmar.setEnabled(!editTextEditProfile.getText().toString().isEmpty());
            }
        };
        confirmar.addTextChangedListener(afterTextChangedListener);
        initView();
        return view;
    }

    private void initView(){
        switch (attribute){
            case "name":
                subtitle.setText("Por favor, a continuación confirme el nuevo nombre");
                title.setText("Cambiar nombre");
                break;
            case "city":
                subtitle.setText("Por favor, a continuación confirme la nueva ciudad");
                title.setText("Cambiar ciudad");
                break;
            case "lastname":
                subtitle.setText("Por favor, a continuación confirme los nuevos apellidos");
                title.setText("Cambiar apellidos");
                break;
            case "phone":
                subtitle.setText("Por favor, a continuación confirme el nuevo teléfono");
                title.setText("Cambiar teléfono");
                break;
            case "psswd":
                subtitle.setText("Por favor, a continuación confirme la nueva contraseña");
                title.setText("Cambiar contraseña");
                break;
        }
    }

    private boolean checkValidPhone(String phone){
        return phone.matches(EXPRESION_REGULAR_TELEFONO);
    }
}