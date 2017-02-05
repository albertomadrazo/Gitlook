package com.albertomadrazo.android.gitlook.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albertomadrazo.android.gitlook.R;
import com.albertomadrazo.android.gitlook.fragment.RepositoriosListaFragment;

public class MainActivity extends AppCompatActivity {

    private final String LISTA_FRAGMENT_TAG = "ListaFragment";
    private ImageView btn_searchLang;
    private EditText et_lenguaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_lenguaje = (EditText) findViewById(R.id.et_buscador);

        btn_searchLang = (ImageView) findViewById(R.id.btn_busca);
        btn_searchLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarRepos();
            }
        });
        et_lenguaje.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if((keyEvent != null && keyEvent.getKeyCode() == keyEvent.KEYCODE_ENTER) || (i == EditorInfo.IME_ACTION_DONE)){
                    buscarRepos();
                }
                return false;
            }
        });

        if(savedInstanceState == null){
            RepositoriosListaFragment listaClientes = new RepositoriosListaFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentLista , listaClientes, LISTA_FRAGMENT_TAG).commit();
        }
    }

    private void buscarRepos(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        Bundle bundle = new Bundle();
        bundle.putString("lenguaje", et_lenguaje.getText().toString());

        RepositoriosListaFragment fragment = new RepositoriosListaFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLista, fragment).commit();
    }
}
