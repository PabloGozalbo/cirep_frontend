package com.example.comun.repository;
import com.example.comun.model.user.Usuario;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/accounts/register/")
    Call<JsonObject> registerUser(@Body Usuario user);

    @POST("/accounts/login/")
    Call<JsonObject> loginUser(@Body String email, String psswd);

}
