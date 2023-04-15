package com.example.comun.repository;
import com.example.comun.model.user.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/accounts/register/")
    Call<Usuario> registerUser(@Body Usuario user);

    @POST("/accounts/login/")
    Call<Usuario> loginUser(@Body Usuario user);

}
