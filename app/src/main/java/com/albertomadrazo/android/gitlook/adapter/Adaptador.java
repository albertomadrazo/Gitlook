package com.albertomadrazo.android.gitlook.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.albertomadrazo.android.gitlook.R;
import com.albertomadrazo.android.gitlook.model.Repositorio;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter{
    ArrayList<Repositorio> mRepositorios;
    Context mContext;

    public Adaptador(Context context, ArrayList<Repositorio> repositorios){
        this.mRepositorios = repositorios;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mRepositorios.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View vista = inflater.inflate(R.layout.lista_item_repositorio, viewGroup, false);

        Repositorio repo = mRepositorios.get(i);
        TextView tvNombreRepositorio = (TextView) vista.findViewById(R.id.nombre_repo);
        tvNombreRepositorio.setText(repo.getNombre());

        TextView tvRepoStars = (TextView) vista.findViewById(R.id.repo_stars);
        tvRepoStars.setText(Integer.toString(repo.getWatchers()));

        TextView tv_descripcionRepositorio = (TextView) vista.findViewById(R.id.descripcion_repo);
        tv_descripcionRepositorio.setText(repo.getDescripcion());
        return vista;
    }
}
