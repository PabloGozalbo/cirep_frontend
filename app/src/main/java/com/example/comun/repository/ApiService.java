package com.example.comun.repository;
import com.example.comun.model.user.Usuario;
import com.example.comun.model.user.UsuarioLogin;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/accounts/create/")
    Call<JsonObject> registerUser(@Body Usuario user);

    @POST("/accounts/login/")
    Call<JsonObject> loginUser(@Body UsuarioLogin user);

}
