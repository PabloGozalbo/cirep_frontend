package com.example.comun.model;

import java.util.Calendar;

public class Incidencia {

    public class Estado{
        public static final String EN_PROCESO = "P";
        public static final String ARREGLADA = "A";
        public static final String PENDIENTE_REVISION = "PR";
        public static final String DESCARTADA = "D";

    }
    private String id_report;
    private String title;
    private Calendar report_date;
    private String description;
    private byte[] image;
    private String state;
    private double latitude;
    private double longitude;
    private String author;

    public String getId_report() {
        return id_report;
    }

    public void setId_report(String id_report) {
        this.id_report = id_report;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getReport_date() {
        return report_date;
    }

    public void setReport_date(Calendar report_date) {
        this.report_date = report_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReport_type() {
        return report_type;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    private String report_type;



}
