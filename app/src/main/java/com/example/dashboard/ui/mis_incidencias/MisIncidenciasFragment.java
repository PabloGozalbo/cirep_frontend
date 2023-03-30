package com.example.dashboard.ui.mis_incidencias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cirep_frontend.databinding.FragmentIncidenciasBinding;



public class MisIncidenciasFragment extends Fragment {

    private FragmentIncidenciasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MisIncidenciasViewModel slideshowViewModel =
                new ViewModelProvider(this).get(MisIncidenciasViewModel.class);

        binding = FragmentIncidenciasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}