package com.albertomadrazo.android.gitlook.model;



import android.util.Log;

import com.albertomadrazo.android.gitlook.API.RepositoriosAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ListaRepositorios {
    public static ListaRepositorios getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(ListaRepositorios ourInstance) {
        ListaRepositorios.ourInstance = ourInstance;
    }

    public void setRepositorios(List<Repositorio> repositorios) {
        mRepositorios = repositorios;
    }

    private List<Repositorio> mRepositorios;
    private static ListaRepositorios ourInstance = new ListaRepositorios();

    public static ListaRepositorios getInstance(){
        return ourInstance;
    }

    private ListaRepositorios(){
        mRepositorios = new ArrayList<>();
    }

    public List<Repositorio> getRepositorios(){
        return mRepositorios;
    }

    public Repositorio getRepositorio(String id){
        for(Repositorio repositorio : mRepositorios){
            if((repositorio.getId()).equals(id)){
                return repositorio;
            }
        }
        return null;
    }


}
