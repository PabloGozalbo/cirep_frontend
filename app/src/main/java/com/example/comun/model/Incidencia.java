package com.example.comun.model;

public class Incidencia {

    public class EstadoIncidencia {
        public static final String EN_PROCESO = "P";
        public static final String ARREGLADA = "A";
        public static final String PENDIENTE_REVISION = "PR";
        public static final String DESCARTADA = "D";

    }
    private int id;
    private String description;
    private String report_date;
    private String image;
    private String state;
    private double latitude;
    private double longitude;
    private String author;
    private String report_type;

    public Incidencia(){}

    public Incidencia(int id, String description, String report_date, String image, String state, double latitude, double longitude, String author, String report_type) {
        this.id = id;
        this.description = description;
        this.report_date = report_date;
        this.image = image;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.author = author;
        this.report_type = report_type;
    }

    public int getId_report() {
        return id;
    }

    public void setId_report(int id_report) {
        this.id = id_report;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public String getStateToString(){
        switch (getState()){
            case EstadoIncidencia.EN_PROCESO:
                return "ESTADO: EN PROCESO";
            case EstadoIncidencia.PENDIENTE_REVISION:
                return "ESTADO: PENDIENTE DE REVISIÓN";
            case EstadoIncidencia.ARREGLADA:
                return "ESTADO: ACABADA";
            case EstadoIncidencia.DESCARTADA:
                return "ESTADO: DESCARTADA";
            default:
                return "";
        }
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }
}
