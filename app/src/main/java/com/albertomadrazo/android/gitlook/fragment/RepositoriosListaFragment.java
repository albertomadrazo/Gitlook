package com.albertomadrazo.android.gitlook.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.albertomadrazo.android.gitlook.API.RepositoriosAPI;
import com.albertomadrazo.android.gitlook.R;
import com.albertomadrazo.android.gitlook.activity.RepoDetalleActivity;
import com.albertomadrazo.android.gitlook.adapter.Adaptador;
import com.albertomadrazo.android.gitlook.model.ListaRepositorios;
import com.albertomadrazo.android.gitlook.model.Repositorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RepositoriosListaFragment extends Fragment implements ListView.OnItemClickListener{

    private String lenguaje = "python";

    private ListView mListView;

    public List<Repositorio> getListaRepositorios() {
        return mListaRepositorios;
    }

    public void setListaRepositorios(List<Repositorio> listaRepositorios) {
        mListaRepositorios = listaRepositorios;
    }

    private List<Repositorio> mListaRepositorios;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        mListView = (ListView) view.findViewById(R.id.listViewLenguajes);

        Log.i("jidsj", "jifjf");
        //setRepositorios(lenguaje);
        getRepositoriosFromAPI("python");

        mListView.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }


    public void getRepositoriosFromAPI(String lenguaje){
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://granfonda.com/").build();
        RepositoriosAPI api = adapter.create(RepositoriosAPI.class);
        api.getRepositorios(lenguaje, new Callback<List<Repositorio>>(){

            @Override
            public void success(List<Repositorio> repositorios, Response response) {
                //setListaRepositorios(repositorios);
                mListaRepositorios = repositorios;
                // setRepositorios(repositorios);
                for(Repositorio repo : repositorios){
                    //Toast.makeText(getActivity(), repo.getNombre().toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(), repo.getNombre().toString(), Toast.LENGTH_LONG).show();

                    Log.i("success>>>>>>>", repo.getNombre());

                    mostrarLista();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("xxxxxxxxxxxxxx", "vete a la verga");
            }
        });


    }





/*
    private void setRepositorios(String lenguaje){
        getRepositoriosFromAPI(lenguaje);
        //Toast.makeText(getActivity(), getListaRepositorios().size(), Toast.LENGTH_LONG).show();
        mostrarLista();
    }
*/

    private void mostrarLista(){
        Adaptador adaptador = new Adaptador(getActivity().getApplicationContext(), (ArrayList<Repositorio>) mListaRepositorios);
        mListView.setAdapter(adaptador);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity().getApplicationContext(), RepoDetalleActivity.class);
        startActivity(intent);
    }
}