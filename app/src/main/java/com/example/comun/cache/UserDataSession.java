package com.example.comun.cache;

import com.google.gson.annotations.SerializedName;

public class UserDataSession {//objeto para guardar el token usando in singleton

    @SerializedName("token")
    private static String token;

    @SerializedName("first_name")
    private static String firstName;

    @SerializedName("last_name")
    private static String lastName;

    @SerializedName("email")
    private static String email;

    @SerializedName("phone_number")
    private static String phoneNumber;

    @SerializedName("city")
    private static String city;

    private static UserDataSession instance;

    private UserDataSession(String firstName,String lastName,String email,String phoneNumber,String city ,String token){
        UserDataSession.firstName = firstName;
        UserDataSession.lastName = lastName;
        UserDataSession.email = email;
        UserDataSession.phoneNumber = phoneNumber;
        UserDataSession.city = city;
        UserDataSession.token =token;
    }

    public static UserDataSession getInstance(){
        if(instance == null){
            instance = new UserDataSession(null,null,null,null, null,null);
        }
        return instance;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        UserDataSession.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        UserDataSession.lastName = lastName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserDataSession.email = email;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        UserDataSession.phoneNumber = phoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        UserDataSession.token = token;
    }

    public void setData(String firstName,String lastName,String email,String phoneNumber,String city ,String token) {
        UserDataSession.firstName = firstName;
        UserDataSession.lastName = lastName;
        UserDataSession.email = email;
        UserDataSession.phoneNumber = phoneNumber;
        UserDataSession.city = city;
        UserDataSession.token =token;
    }

    public void deleteData() {
        UserDataSession.firstName = null;
        UserDataSession.lastName = null;
        UserDataSession.email = null;
        UserDataSession.phoneNumber = null;
        UserDataSession.city = null;
        UserDataSession.token =null;
    }

    public boolean isSessionValid(){
        return token!=null && !token.isEmpty();
    }
}
