package com.example.dashboard.ui.mis_incidencias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MisIncidenciasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MisIncidenciasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}