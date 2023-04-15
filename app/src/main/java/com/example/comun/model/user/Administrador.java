package com.example.comun.model.user;

import com.example.comun.model.Incidencia;

import java.util.List;

public class Administrador extends Usuario {
    public Administrador(String firstName, String lastName, String email, String phoneNumber, boolean isStaff, boolean isSuperuser, List<Incidencia> incidencias, Genero genero) {
        super(firstName, lastName, email, phoneNumber, isStaff, isSuperuser, incidencias, genero);
    }
}
