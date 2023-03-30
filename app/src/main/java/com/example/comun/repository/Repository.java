package com.example.comun.repository;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Repository {

    CookieManager cookie;

    public static void pruebaLoginBack() throws Exception {

        Thread hilo=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://127.0.0.1:8000/login/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setDoOutput(true);

                    //crear Json conn email y password
                    JSONObject json = new JSONObject();
                    json.put("email", "pepemartinez@gmail.com");
                    json.put("password", "12345");

                    // Establecer el cuerpo de la solicitud como el objeto JSON
                    String jsonInputString = json.toString();
                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    }

                    // Obtener la respuesta del servidor
                    int responseCode = conn.getResponseCode();
                    System.out.println("Response Code : " + responseCode);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        hilo.start();

      /*  //add request header
        conn.setRequestProperty("cache-control", "no-cache");
        conn.setRequestProperty("X-API-KEY", "myApiKey");
        conn.setRequestProperty("X-API-EMAIL", "myEmail@mail.com");*/

    }

}
