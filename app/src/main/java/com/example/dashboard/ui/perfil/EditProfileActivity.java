package com.example.dashboard.ui.perfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.cirep_frontend.R;
import com.example.register.steps.Step1Fragment;
import com.example.register.steps.Step3Fragment;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        String attribute = intent.getStringExtra("attribute");

        Bundle bundle = new Bundle();
        bundle.putString("attribute", attribute);

        ChangeAttributeFragment fragment = new ChangeAttributeFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_editProfile, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}