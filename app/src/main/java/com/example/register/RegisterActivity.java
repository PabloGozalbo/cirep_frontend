package com.example.register;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.cirep_frontend.R;
import com.example.comun.model.user.Usuario;
import com.example.comun.result.ResultKoFragment;
import com.example.comun.result.ResultOkFragment;
import com.example.register.steps.Step1Fragment;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        goToFirstStep();
        this.registerViewModel = new RegisterViewModel();

        observeRegistrationSuccess();
    }

    private void observeRegistrationSuccess() {
        registerViewModel.getRegistrationSuccessLiveData().observe(this, registrationSuccess -> {

            if (registrationSuccess) {
                goToResultOk();
            } else {
                goToResultKo();
            }
        });
    }

    public void registerUser(Usuario user){
        registerViewModel.registerUser(user);
    }

    public RegisterViewModel getViewModel(){
        return this.registerViewModel;
    }


    private void goToFirstStep(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.registerFragmentContainerView, new Step1Fragment());
        transaction.commit();
      //  getSupportFragmentManager().beginTransaction().replace(R.id.registerFragmentContainerView, new Step1Fragment()).commit();
    }

    private void goToResultOk(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.registerFragmentContainerView, new ResultOkFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void goToResultKo(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.registerFragmentContainerView, new ResultKoFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
    }

}