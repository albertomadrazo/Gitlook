package com.albertomadrazo.android.gitlook.API;


import com.albertomadrazo.android.gitlook.model.Repositorio;

import java.util.List;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface RepositoriosAPI {

    // GET repositorios por lenguaje
    @GET("/jay")
    void getRepositorios(
            @Query("q") String lenguaje,
            Callback<List<Repositorio>> callback
    );

    // GET repositorios
}
