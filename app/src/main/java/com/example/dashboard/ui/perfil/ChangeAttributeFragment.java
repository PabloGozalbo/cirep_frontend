package com.example.dashboard.ui.perfil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cirep_frontend.R;
import com.example.comun.cache.UserDataSession;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeAttributeFragment extends Fragment {
    String attribute;
    TextView subtitle, title;
    Button confirmar;
    EditText editTextEditProfile;
    PerfilViewModel perfilViewModel;
    private Dialog dialogoCarga;

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
        ChangeAttributeFragment fragment = this;
        confirmar.setEnabled(false);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attribute.equals("phone_number")){
                    if(checkValidPhone(editTextEditProfile.getText().toString())){
                        perfilViewModel.editProfile(attribute,
                                editTextEditProfile.getText().toString(),
                                UserDataSession.getInstance().getUsuario().getEmail(),
                                UserDataSession.getInstance().getToken());
                        actualizarUsuario(editTextEditProfile.getText().toString());
                        getActivity().onBackPressed();
                    } else {
                        editTextEditProfile.setError("Por favor, indica un número de teléfono válido");
                    }
                } else {
                    perfilViewModel.editProfile(attribute,
                            editTextEditProfile.getText().toString(),
                            UserDataSession.getInstance().getUsuario().getEmail(),
                            UserDataSession.getInstance().getToken());
                    actualizarUsuario(editTextEditProfile.getText().toString());
                    getActivity().onBackPressed();
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
                boolean camposLlenos =  !TextUtils.isEmpty(editTextEditProfile.getText());
                boolean validPhone = true;
                if(attribute.equals("phone_number")){
                    Pattern pattern = Pattern.compile("^\\d{9,9}$");
                    Matcher matcher = pattern.matcher(editTextEditProfile.getText().toString());
                    validPhone = matcher.find();
                    if(!validPhone){
                        editTextEditProfile.setError("Por favor, indica un número de teléfono válido");
                    }else{
                        editTextEditProfile.setError(null);
                    }
                }
                confirmar.setEnabled(camposLlenos && validPhone);
            }

            @Override
            public void afterTextChanged(Editable s) {
                confirmar.setEnabled(!editTextEditProfile.getText().toString().isEmpty());
            }
        };
        editTextEditProfile.addTextChangedListener(afterTextChangedListener);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView(){
        switch (attribute){
            case "first_name":
                subtitle.setText("Por favor, a continuación confirme el nuevo nombre");
                title.setText("Cambiar nombre");
                break;
            case "city":
                subtitle.setText("Por favor, a continuación confirme la nueva ciudad");
                title.setText("Cambiar ciudad");
                break;
            case "last_name":
                subtitle.setText("Por favor, a continuación confirme los nuevos apellidos");
                title.setText("Cambiar apellidos");
                break;
            case "phone_number":
                subtitle.setText("Por favor, a continuación confirme el nuevo teléfono");
                title.setText("Cambiar teléfono");
                break;
            case "password":
                subtitle.setText("Por favor, a continuación confirme la nueva contraseña");
                title.setText("Cambiar contraseña");
                break;
        }
    }

    private void actualizarUsuario(String value){
        switch (attribute){
            case "first_name":
                perfilViewModel.setNombreUser(value);
                break;
            case "city":
                perfilViewModel.setCiudadUser(value);
                break;
            case "last_name":
                perfilViewModel.setApellidosUser(value);
                break;
            case "phone_number":
                perfilViewModel.setTelefonoUser(value);
                break;
            case "password":
                perfilViewModel.setPassword(value);
                break;
        }
    }


    private boolean checkValidPhone(String phone){
        return phone.matches(EXPRESION_REGULAR_TELEFONO);
    }


    private void goEditProfile(){
        Intent intent = new Intent(this.getContext(), EditProfileActivity.class);
        startActivity(intent);
    }

}