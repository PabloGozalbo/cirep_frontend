package com.example.comun.result;

import android.animation.AnimatorSet;
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
import com.example.register.RegisterActivity;
import com.example.register.steps.Step1Fragment;
import com.example.register.steps.Step2Fragment;


public class ResultKoFragment extends Fragment {

    private Button btnVolver;

    public ResultKoFragment() {
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
        return inflater.inflate(R.layout.fragment_result_ko, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        this.btnVolver = view.findViewById(R.id.resultKobutton);

        TextView errorTextView = view.findViewById(R.id.errorTextView);
        errorTextView.setText(R.string.ko_message);

        // Cargar la animación desde el archivo xml
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this.getContext(), R.anim.fade_in);

        // Ejecutar la animación en el TextView
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.startAnimation(fadeInAnimation);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                transaction.replace(R.id.registerFragmentContainerView, new Step1Fragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

}