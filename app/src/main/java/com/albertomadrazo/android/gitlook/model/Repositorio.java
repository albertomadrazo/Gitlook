package com.albertomadrazo.android.gitlook.model;


import com.google.gson.annotations.SerializedName;

public class Repositorio {
    Integer id;
    @SerializedName("name")
    String nombre;

    @SerializedName("full_name")
    String nombreCompleto;

    @SerializedName("owner")
    User user;

    @SerializedName("description")
    String descripcion;

    String issues;

    String contributors;



    @SerializedName("watchers")
    Integer watchers;

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public User getUser() {
        return user;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getWatchers() {
        return watchers;
    }

    public Integer getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private static Repositorio mRepositorio = new Repositorio();
    private static Repositorio mInstancia = null;

    public static synchronized Repositorio getInstance(){
        if(mInstancia == null){
            mInstancia = new Repositorio();
        }
        return mInstancia;
    }

    public Repositorio(){}

    public String getNombre() {
        return nombre;
    }
}
