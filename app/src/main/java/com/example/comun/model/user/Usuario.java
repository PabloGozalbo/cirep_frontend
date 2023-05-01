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

    @SerializedName("ciudad")
    private CapitalesProvincias ciudad;

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
    public Usuario(String firstName, String lastName, String email, String phoneNumber, boolean isStaff, boolean isSuperuser, List<Incidencia> incidencias, CapitalesProvincias ciudad) {
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

    public CapitalesProvincias getCiudad() {
        return ciudad;
    }

    public void setCiudad(CapitalesProvincias ciudad) {
        this.ciudad = ciudad;
    }

    public void addIncidencia(Incidencia incidencia){
        this.incidencias.add(incidencia);
    }

    public enum CapitalesProvincias {
        ALAVA("Vitoria-Gasteiz"),
        ALBACETE("Albacete"),
        ALICANTE("Alicante/Alacant"),
        ALMERIA("Almería"),
        ASTURIAS("Oviedo/Uviéu"),
        AVILA("Ávila"),
        BADAJOZ("Badajoz"),
        BARCELONA("Barcelona"),
        BURGOS("Burgos"),
        CACERES("Cáceres"),
        CADIZ("Cádiz"),
        CANTABRIA("Santander"),
        CASTELLON("Castellón de la Plana/Castelló de la Plana"),
        CIUDAD_REAL("Ciudad Real"),
        CORDOBA("Córdoba"),
        CUENCA("Cuenca"),
        GERONA_GIRONA("Gerona/Girona"),
        GRANADA("Granada"),
        GUADALAJARA("Guadalajara"),
        GUIPUZCOA_GIPUZKOA("San Sebastián/Donostia"),
        HUELVA("Huelva"),
        HUESCA("Huesca"),
        ISLAS_BALEARES("Palma de Mallorca"),
        JAEN("Jaén"),
        LA_CORUNA_A_CORUNA("La Coruña/A Coruña"),
        LA_RIOJA("Logroño"),
        LAS_PALMAS("Las Palmas de Gran Canaria"),
        LEON("León"),
        LERIDA_LLEIDA("Lérida/Lleida"),
        LUGO("Lugo"),
        MADRID("Madrid"),
        MALAGA("Málaga"),
        MURCIA("Murcia"),
        NAVARRA("Pamplona/Iruña"),
        ORENSE_OURENSE("Orense/Ourense"),
        PALENCIA("Palencia"),
        PONTEVEDRA("Pontevedra"),
        SALAMANCA("Salamanca"),
        SANTA_CRUZ_DE_TENERIFE("Santa Cruz de Tenerife"),
        SEGOVIA("Segovia"),
        SEVILLA("Sevilla"),
        SORIA("Soria"),
        TARRAGONA("Tarragona"),
        TERUEL("Teruel"),
        TOLEDO("Toledo"),
        VALENCIA("Valencia/València"),
        VALLADOLID("Valladolid"),
        VIZCAYA_BIZKAIA("Bilbao"),
        ZAMORA("Zamora"),
        ZARAGOZA("Zaragoza"),
        CEUTA("Ceuta"),
        MELILLA("Melilla");

        private String capital;

        CapitalesProvincias(String capital) {
            this.capital = capital;
        }

        public String getCapital() {
            return capital;
        }
    }
}
