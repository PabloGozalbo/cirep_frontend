package com.example.comun.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comun.model.user.Usuario;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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

        String encryptedPassword = encryptPassword(user.getPassword());

        if(encryptedPassword != null) {
            user.setPassword(encryptedPassword);
        }

        apiService.registerUser(user).enqueue(new retrofit2.Callback() {
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

    private String encryptPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            byte[] encryptedBytes = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : encryptedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
