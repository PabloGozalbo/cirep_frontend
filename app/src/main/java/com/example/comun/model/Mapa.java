package com.example.comun.model;

import com.google.gson.annotations.SerializedName;

public class Mapa {

    @SerializedName("incidencias")
    private boolean incidencias;

    public Mapa(boolean incidencias) {
        this.incidencias = incidencias;
    }
}
