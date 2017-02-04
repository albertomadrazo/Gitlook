package com.albertomadrazo.android.gitlook.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.albertomadrazo.android.gitlook.R;
import com.albertomadrazo.android.gitlook.fragment.RepositoriosListaFragment;

public class MainActivity extends AppCompatActivity {

    private final String LISTA_FRAGMENT_TAG = "ListaFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            RepositoriosListaFragment listaClientes = new RepositoriosListaFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentLista , listaClientes, LISTA_FRAGMENT_TAG).commit();
        }
    }
}
