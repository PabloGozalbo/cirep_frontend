package com.example.comun.result;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.cirep_frontend.R;
import com.example.dashboard.DashboardActivity;
import com.example.register.RegisterActivity;
import com.example.register.steps.Step1Fragment;
import com.example.register.steps.Step2Fragment;


public class ResultOkFragment extends Fragment {

    private Button btnOk;

    public ResultOkFragment() {
        // Required empty public constructor
    }


    public static ResultKoFragment newInstance() {
        return new ResultKoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result_ok, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        this.btnOk = view.findViewById(R.id.resultOKbutton);

        TextView successTextView = view.findViewById(R.id.successTextView);
        successTextView.setText(R.string.ok_message);

        // Cargar la animación desde el archivo xml
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this.getContext(), R.anim.fade_in);

        // Ejecutar la animación en el TextView
        successTextView.setVisibility(View.VISIBLE);
        successTextView.startAnimation(fadeInAnimation);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDashboard();
            }
        });
    }

    private void goToDashboard(){
        Intent intent = new Intent(this.getContext(), DashboardActivity.class);
        startActivity(intent);
    }

}