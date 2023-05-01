package com.example.comun.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comun.cache.UserDataSession;
import com.example.comun.model.Incidencia;
import com.example.comun.model.user.Usuario;
import com.example.comun.model.user.UsuarioLogin;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private MutableLiveData<Boolean> registrationResult = new MutableLiveData<>();
    ApiService apiService;

    public Repository(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:/10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.apiService = retrofit.create(ApiService.class);
    }

    public LiveData<Boolean> getRegistrationResult() {
        return registrationResult;
    }

    public static abstract class LoginCallback {
        public abstract void onSuccess();
        public abstract void onFailure();
    }

    public static abstract class getIncidenciasCallback {
        public abstract List<Incidencia> onSuccess(List<Incidencia> incidencias);
        public abstract void onFailure();
    }

    public static abstract class getIncidenciasUserCallback {
        public abstract void onSuccess(List<Incidencia> incidencias);
        public abstract void onFailure();
    }

    public static abstract class createIncidenciasUserCallback {
        public abstract void onSuccess();
        public abstract void onFailure();
    }


    public void registerUser(Usuario user, LoginCallback callback) {
        apiService.registerUser(user).enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    UserDataSession userDataSession = UserDataSession.getInstance();
                    String jsonResponse = response.body().toString();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(jsonResponse);

                        String firstName = jsonObject.getString("first_name");
                        String lastName = jsonObject.getString("last_name");
                        String email = jsonObject.getString("email");
                        String phoneNumber = jsonObject.getString("phone_number");
                        String city = jsonObject.getString("city");
                        String tokenJWT = jsonObject.getString("token");

                        userDataSession.setData(firstName,lastName,email,phoneNumber, city,tokenJWT);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(userDataSession.getToken());
                    callback.onSuccess();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void loginUser(UsuarioLogin user, LoginCallback callback) {
        apiService.loginUser(user).enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    UserDataSession userDataSession = UserDataSession.getInstance();
                    String jsonResponse = response.body().toString();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(jsonResponse);

                        String firstName = jsonObject.getString("first_name");
                        String lastName = jsonObject.getString("last_name");
                        String email = jsonObject.getString("email");
                        String phoneNumber = jsonObject.getString("phone_number");
                        String city = jsonObject.getString("city");
                        String tokenJWT = jsonObject.getString("token");

                        userDataSession.setData(firstName,lastName,email,phoneNumber, city,tokenJWT);
                    } catch (JSONException e) {
                        callback.onFailure();
                    }
                    System.out.println(userDataSession.getToken());
                    callback.onSuccess();
                }else{
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    //TODO: QUE LA RESPUESTA SE CONVIERTA EN UNA LISTA DE
    public void getIncidencias(getIncidenciasCallback callback) {
        apiService.getIncidencias().enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    try {
                        Gson gson = new Gson();
                        JsonObject jsonResponse = (JsonObject) response.body();
                        JsonArray arrayIncidencias = jsonResponse.getAsJsonArray("incidencias");
                        List<Incidencia> listaIncidencias = new ArrayList<>();
                        for (JsonElement elemento : arrayIncidencias) {
                            Incidencia incidencia = gson.fromJson(elemento, Incidencia.class);
                            listaIncidencias.add(incidencia);
                        }
                        callback.onSuccess(listaIncidencias);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }
//todo da error
    /*public List<Incidencia> getIncidenciasUser(String email, getIncidenciasUserCallback callback) {
        apiService.getIncidencias(email, email).enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    UserDataSession userDataSession = UserDataSession.getInstance();
                    String jsonResponse = response.body().toString();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(jsonResponse);
                        String tokenJWT = jsonObject.getString("token");
                        userDataSession.setToken(tokenJWT);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(userDataSession.getToken());
                    callback.onSuccess();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }*/

    public void crearIncidencia(String email, Incidencia incidencia, getIncidenciasCallback callback) {
        apiService.crearIncidencia(incidencia, email).enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });


    }
}
