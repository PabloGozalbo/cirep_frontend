package com.example.comun.result;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cirep_frontend.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultOkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultOkFragment extends Fragment {


    public ResultOkFragment() {
        // Required empty public constructor
    }


    public static ResultOkFragment newInstance(String param1, String param2) {
        return new ResultOkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_ok, container, false);
    }




}