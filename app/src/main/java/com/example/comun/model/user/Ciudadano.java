package com.example.comun.model.user;

import com.example.comun.model.Incidencia;

import java.util.List;

public class Ciudadano extends Usuario {

    public Ciudadano(String firstName, String lastName, String email, String phoneNumber, boolean isStaff, boolean isSuperuser, List<Incidencia> incidencias, CapitalesProvincias ciudad) {
        super(firstName, lastName, email, phoneNumber, isStaff, isSuperuser, incidencias, ciudad);
    }
}
