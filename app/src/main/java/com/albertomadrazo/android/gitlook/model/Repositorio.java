package com.albertomadrazo.android.gitlook.model;


public class Repositorio {
    Integer id;
    String nombre;
    String descripcion;
    String issues;
    String contributors;

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
