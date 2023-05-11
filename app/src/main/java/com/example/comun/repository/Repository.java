package com.example.comun.repository;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comun.cache.UserDataSession;
import com.example.comun.model.Incidencia;
import com.example.comun.model.user.Usuario;
import com.example.comun.model.user.UsuarioLogin;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        public abstract void onSuccess(List<Incidencia> incidencias);
        public abstract void onFailure();
    }

    public static abstract class getIncidenciaPorIdCallback {
        public abstract void onSuccess(Incidencia incidencia);
        public abstract void onFailure();
    }

    public static abstract class editProfileCallback {
        public abstract void onSuccess();
        public abstract void onFailure();
    }

    public static abstract class getIncidenciasUserCallback {
        public abstract void onSuccess(List<Incidencia> incidencias);
        public abstract void onFailure();
    }

    public static abstract class createIncidenciasUserCallback {
        public abstract void onSuccess(List<Incidencia> incidencias);
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
                        String tokenJWT = jsonObject.getString("token");
                        String nombre = jsonObject.getString("first_name");
                        String apellidos = jsonObject.getString("last_name");
                        String telefono = jsonObject.getString("phone_number");
                        String email = jsonObject.getString("email");
                        String ciudad = jsonObject.getString("city");

                        UserDataSession.getInstance().setUsuario(new Usuario(nombre, apellidos, email, telefono, false, false, null, ciudad));

                        userDataSession.setToken(tokenJWT);
                    } catch (JSONException e) {
                        e.printStackTrace();
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

                        userDataSession.setToken(tokenJWT);
                        userDataSession.setUsuario(new Usuario(firstName, lastName, email, phoneNumber, false, false, null, city));
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

    public void getIncidencias(getIncidenciasCallback callback) {
        apiService.getIncidencias().enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    try {
                        Gson gson = new Gson();
                        JsonArray jsonArray = gson.fromJson(response.body().toString(), JsonArray.class);
                        ArrayList<Incidencia> incidencias = new ArrayList<>();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonObject issueObject = (JsonObject) jsonArray.get(i);
                            Incidencia incidencia = incidenciaFromJsonObject(issueObject);
                            incidencias.add(incidencia);
                        }
                        callback.onSuccess(incidencias);
                    }catch (Exception e){
                        e.printStackTrace();
                        callback.onFailure();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }
    public void getIncidenciaPorId(String token, int reportId, getIncidenciaPorIdCallback callback) {
        apiService.getIncidenciaPorId(token, reportId).enqueue(new retrofit2.Callback() {
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonResponse = response.body().toString();
                        JSONObject jsonObject = new JSONObject(jsonResponse);

                        Incidencia incidencia = new Incidencia();
                        incidencia.setReport_type(jsonObject.getString("report_type"));
                        incidencia.setAuthor(jsonObject.getString("author"));
                        incidencia.setLongitude(jsonObject.getDouble("longitude"));
                        incidencia.setLatitude(jsonObject.getDouble("latitude"));
                        incidencia.setState(jsonObject.getString("state"));
                        incidencia.setDescription(jsonObject.getString("description"));
                        incidencia.setReport_date(jsonObject.getString("report_date"));
                        incidencia.setId_report(jsonObject.getInt("id"));

                        incidencia.setImage(jsonObject.getString("image"));

                        callback.onSuccess(incidencia);
                    } catch (Exception e){
                        e.printStackTrace();
                        callback.onFailure();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getIncidenciasUser(getIncidenciasUserCallback callback) {
        apiService.getIncidenciasUser(UserDataSession.getInstance().getToken()).enqueue(new retrofit2.Callback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    try {
                        Gson gson = new Gson();
                        JsonArray jsonArray = gson.fromJson(response.body().toString(), JsonArray.class);
                        ArrayList<Incidencia> incidencias = new ArrayList<>();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonObject issueObject = (JsonObject) jsonArray.get(i);
                            Incidencia incidencia = incidenciaFromJsonObject(issueObject);
                            incidencias.add(incidencia);
                        }
                        callback.onSuccess(incidencias);
                    }catch (Exception e){
                        e.printStackTrace();
                        callback.onFailure();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    @NonNull
    private Incidencia incidenciaFromJsonObject(JsonObject issueObject) throws IOException {
        int idIncidencia = issueObject.get("pk").getAsInt();
        JsonObject fieldsObject = issueObject.getAsJsonObject("fields");
        String report_date = fieldsObject.get("report_date").getAsString();
        String description = fieldsObject.get("description").getAsString();
        String image = fieldsObject.get("image").getAsString();
        String state = fieldsObject.get("report_date").getAsString();
        double latitude = fieldsObject.get("latitude").getAsDouble();
        double longitude = fieldsObject.get("longitude").getAsDouble();
        String author = fieldsObject.get("author").getAsString();
        String report_type = fieldsObject.get("report_type").getAsString();

        Incidencia incidencia = new Incidencia(idIncidencia, description, report_date, image, state, latitude, longitude, author, report_type);
        return incidencia;
    }

    public void crearIncidencia( Incidencia incidencia, String token, createIncidenciasUserCallback callback) {
        apiService.crearIncidencia(token, incidencia).enqueue(new retrofit2.Callback() {
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

    public void editProfile(String attribute, String value, String email,String token, editProfileCallback callback) {
        Map<String, String> body =new HashMap<>();
        body.put(attribute, value);
        apiService.modificarPerfil(body,email,token).enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }
}
