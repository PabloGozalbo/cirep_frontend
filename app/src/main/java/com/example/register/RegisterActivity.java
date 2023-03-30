package com.example.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.cirep_frontend.R;
import com.example.register.steps.Step1Fragment;
import com.example.register.steps.Step3Fragment;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        goToFirstStep();

    }

    private void goToFirstStep(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.registerFragmentContainerView, new Step1Fragment());
        transaction.addToBackStack(null);
        transaction.commit();
      //  getSupportFragmentManager().beginTransaction().replace(R.id.registerFragmentContainerView, new Step1Fragment()).commit();
    }


}