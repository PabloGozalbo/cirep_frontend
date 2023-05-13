package com.example.comun.model.user;

import com.example.comun.model.Incidencia;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

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

    @SerializedName("city")
    private String ciudad;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("password")
    private String password;

    public Usuario(){
    }

    //Constructor al que se le pueda pasar o no sus incidencias
    public Usuario(String firstName, String lastName, String email, String phoneNumber, boolean isStaff, boolean isSuperuser, List<Incidencia> incidencias, String ciudad) {
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
        this.ciudad = ciudad;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCity(String ciudad) {
        this.ciudad = ciudad;
    }

    public void addIncidencia(Incidencia incidencia){
        this.incidencias.add(incidencia);
    }

    public String getNombreCompleto() {
        // Obtener el nombre completo concatenando el nombre y apellido
        String fullName = firstName + " " + lastName;

        // Convertir la primera letra a mayúscula y concatenar con el resto de la cadena
        String firstLetter = fullName.substring(0, 1).toUpperCase();
        String restOfName = fullName.substring(1);

        return firstLetter + restOfName;
    }

}
