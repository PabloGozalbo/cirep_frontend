package com.example.comun.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comun.cache.UserDataSession;
import com.example.comun.model.user.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static abstract class Callback {
        public abstract void onSuccess();
        public abstract void onFailure();
    }


    public void registerUser(Usuario user, Callback callback) {
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
    }

}
