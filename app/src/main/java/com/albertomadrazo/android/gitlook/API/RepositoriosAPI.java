package com.albertomadrazo.android.gitlook.API;


import com.albertomadrazo.android.gitlook.model.Repositorio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RepositoriosAPI {

    // GET repositorios por lenguaje
    @GET("search/repositories")
    Call<List<Repositorio>> getRepositorios(@Query("q") String lenguaje);

    // GET repositorios
}
