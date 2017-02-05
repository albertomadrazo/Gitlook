package com.albertomadrazo.android.gitlook.API;


import com.albertomadrazo.android.gitlook.model.Contributor;
import com.albertomadrazo.android.gitlook.model.Issue;
import com.albertomadrazo.android.gitlook.model.Lenguaje;
import com.albertomadrazo.android.gitlook.model.Repositorio;

import java.util.List;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface RepositoriosAPI {

    @GET("/repositories")
    void getRepositoriosPorLang(
            @Query("q") String lenguaje,
            Callback<Lenguaje> callback
    );

    @GET("/repos/{owner}/{repoName}/contributors")
    void getContributors(
            @Path("owner") String owner,
            @Path("repoName") String repoName,
            Callback<List<Contributor>> callback
    );

    @GET("/repos/{owner}/{repoName}/issues")
    void getIssues(
            @Path("owner") String owner,
            @Path("repoName") String repoName,
            Callback<List<Issue>> callback
    );
}
