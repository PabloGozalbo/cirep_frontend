package com.example.comun.repository;

import com.example.comun.model.Incidencia;
import com.example.comun.model.user.Usuario;
import com.example.comun.model.user.UsuarioLogin;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/accounts/create/")
    Call<JsonObject> registerUser(@Body Usuario user);

    @POST("/accounts/login/")
    Call<JsonObject> loginUser(@Body UsuarioLogin user);

    @GET("/reports")
    Call<JsonObject> getIncidencias();

    @GET("/reports/get_report/{reportId}")
    Call<JsonObject> getIncidenciaPorId(@Header("token") String token, @Path("reportId") int reportId);

    @POST("/reports/create_report/")
    Call<JsonObject> crearIncidencia(@Header("token") String token,@Body Incidencia incidencias);

    @GET("/reports/get_user_reports/")
    Call<JsonArray> getIncidenciasUser(@Header("token") String token);

    @POST("/accounts/modificar_perfil/{email}/")
    Call<JsonObject> modificarPerfil(@Body Map<String, String> datos, @Path("email") String email, @Header("token") String token);

}
