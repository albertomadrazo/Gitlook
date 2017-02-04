package com.albertomadrazo.android.gitlook.API;


import com.albertomadrazo.android.gitlook.model.Repositorio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RepositoriosAPI {

    // GET repositorios por lenguaje
    @GET("resto.php")
    Call<List<Repositorio>> getRepositorios(@Query("q") String lenguaje);

    // GET repositorios
}
