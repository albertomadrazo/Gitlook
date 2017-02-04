package com.albertomadrazo.android.gitlook.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.albertomadrazo.android.gitlook.R;
import com.albertomadrazo.android.gitlook.model.ListaRepositorios;
import com.albertomadrazo.android.gitlook.model.Repositorio;

import java.io.IOException;
import java.util.List;


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

        setRepositorios(lenguaje);

        mListView.setOnItemClickListener(this);

        return view;
    }

    private void setRepositorios(String lenguaje){
        ListaRepositorios listaRepositorios = ListaRepositorios.getInstance();
        listaRepositorios.getRepositoriosFromAPI(lenguaje);
        mListaRepositorios = listaRepositorios.getRepositorios();
        mostrarLista();
    }

    private void mostrarLista(){
        String[] items = new String[mListaRepositorios.size()];

        for(int i = 0; i < mListaRepositorios.size(); i++){
            items[i] = mListaRepositorios.get(i).getNombre();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getActivity(), "verga", Toast.LENGTH_LONG).show();
    }
}
