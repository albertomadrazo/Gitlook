package com.albertomadrazo.android.gitlook.model;


import android.widget.Toast;

import com.albertomadrazo.android.gitlook.API.RepositoriosAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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


    public void getRepositoriosFromAPI(String lenguaje){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://granfonda.com/").addConverterFactory(GsonConverterFactory.create()).build();
        RepositoriosAPI api = retrofit.create(RepositoriosAPI.class);
        Call<List<Repositorio>> call = api.getRepositorios(lenguaje);
        call.enqueue(new Callback<List<Repositorio>>() {
            @Override
            public void onResponse(Call<List<Repositorio>> call, Response<List<Repositorio>> response) {
                if(response.isSuccessful()){
                    setRepositorios(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Repositorio>> call, Throwable t) {

            }
        });
    }
}
