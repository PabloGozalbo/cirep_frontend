package com.example.comun.model.user;

import com.example.comun.model.Incidencia;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRegistrado {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("is_staff")
    private boolean isStaff;

    @SerializedName("is_superuser")
    private boolean isSuperuser;

    @SerializedName("incidencias")
    private List<Incidencia> incidencias;

    @SerializedName("genero")
    private Genero genero;

    enum Genero{
        HOMBRE,
        MUJER,
        NA
    }

    //Constructor al que se le pueda pasar o no sus incidencias
    public UsuarioRegistrado(String firstName, String lastName, String email, String phoneNumber, boolean isStaff, boolean isSuperuser, List<Incidencia> incidencias, Genero genero) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isStaff = isStaff;
        this.isSuperuser = isSuperuser;
        if (incidencias!=null) {
            this.incidencias = incidencias;
        } else {
            this.incidencias=new ArrayList<>();
        }
        this.genero = genero;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    public boolean isSuperuser() {
        return isSuperuser;
    }

    public void setSuperuser(boolean superuser) {
        isSuperuser = superuser;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void addIncidencia(Incidencia incidencia){
        this.incidencias.add(incidencia);
    }
}
