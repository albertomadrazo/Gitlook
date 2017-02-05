package com.albertomadrazo.android.gitlook.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.albertomadrazo.android.gitlook.API.RepositoriosAPI;
import com.albertomadrazo.android.gitlook.R;
import com.albertomadrazo.android.gitlook.activity.RepoDetalleActivity;
import com.albertomadrazo.android.gitlook.adapter.Adaptador;
import com.albertomadrazo.android.gitlook.model.Lenguaje;
import com.albertomadrazo.android.gitlook.model.Repositorio;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RepositoriosListaFragment extends Fragment implements ListView.OnItemClickListener{

    private String lenguaje = null;
    private String URL = "https://api.github.com/search";

    private ListView mListView;

    private List<Repositorio> mListaRepositorios;


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        if(this.getArguments() != null){
            lenguaje = this.getArguments().getString("lenguaje");
        }

        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        mListView = (ListView) view.findViewById(R.id.listViewLenguajes);

        if(lenguaje != null){
            getRepositoriosPorLang(lenguaje);
        }

        mListView.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }


    public void getRepositoriosPorLang(String lenguaje){
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(URL).build();
        RepositoriosAPI api = adapter.create(RepositoriosAPI.class);
        api.getRepositoriosPorLang("language:" + lenguaje, new Callback<Lenguaje>() {
            @Override
            public void success(Lenguaje lenguaje, Response response) {
                mListaRepositorios = lenguaje.getItems();
                mostrarLista();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    private void mostrarLista(){
        Adaptador adaptador = new Adaptador(getContext(), (ArrayList<Repositorio>) mListaRepositorios);
        mListView.setAdapter(adaptador);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity().getApplicationContext(), RepoDetalleActivity.class);
        intent.putExtra("repo_name", mListaRepositorios.get(i).getNombre());
        intent.putExtra("repo_full_name", mListaRepositorios.get(i).getNombreCompleto());
        intent.putExtra("repo_owner", mListaRepositorios.get(i).getUser().getLogin());
        intent.putExtra("repo_description", mListaRepositorios.get(i).getDescripcion());

        startActivity(intent);
    }
}