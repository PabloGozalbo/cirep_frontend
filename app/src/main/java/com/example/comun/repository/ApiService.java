package com.example.comun.repository;
import com.example.comun.model.Incidencia;
import com.example.comun.model.user.Usuario;
import com.example.comun.model.user.UsuarioLogin;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/accounts/create/")
    Call<JsonObject> registerUser(@Body Usuario user);

    @POST("/accounts/login/")
    Call<JsonObject> loginUser(@Body UsuarioLogin user);

    @GET("/incidencias")
    Call<JsonObject> getIncidencias();

    @POST("/incidencias")
    Call<JsonObject> crearIncidencia(@Body Incidencia incidencias, String email);

    @GET("/incidencias/{usuario}")
    Call<JsonObject> getIncidencias(@Path("usuario") String usuario, @Query("email") String email);

    @POST("/accounts/modificar_perfil/{usuario}/")
    Call<JsonObject> modificarPerfil(@Body String attribute, @Query("email") String email);

}
