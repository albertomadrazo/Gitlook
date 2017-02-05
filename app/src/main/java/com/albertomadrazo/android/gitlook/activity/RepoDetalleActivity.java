package com.albertomadrazo.android.gitlook.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.albertomadrazo.android.gitlook.API.RepositoriosAPI;
import com.albertomadrazo.android.gitlook.R;
import com.albertomadrazo.android.gitlook.model.Contributor;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RepoDetalleActivity extends AppCompatActivity{

    private TextView tvNombreRepo;
    private TextView tvDescripcionRepo;


    private LinearLayout parentContributors;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detalle);

        Bundle extras = getIntent().getExtras();
        String repoName = extras.getString("repo_name");
        String repoFullName = extras.getString("repo_full_name");
        String repoOwner = extras.getString("repo_owner");
        String repoDescripcion = extras.getString("repo_description");

        tvNombreRepo = (TextView) findViewById(R.id.detalle_nombre_repo);
        tvNombreRepo.setText(repoName);

        tvDescripcionRepo = (TextView) findViewById(R.id.detalle_descripcion_repo);
        tvDescripcionRepo.setText(repoDescripcion);


        parentContributors = (LinearLayout) findViewById(R.id.parent_layout_contributors);

        getContributorsFromAPI(repoOwner, repoName);
    }

    void getContributorsFromAPI(String repoOwner, String nombreRepo){
        Toast.makeText(RepoDetalleActivity.this, nombreRepo, Toast.LENGTH_LONG).show();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("https://api.github.com").build();
        RepositoriosAPI api = adapter.create(RepositoriosAPI.class);
        api.getContributors(repoOwner, nombreRepo, new Callback<List<Contributor>>() {
            @Override
            public void success(List<Contributor> contributors, Response response) {
                int arraySize = (contributors.size() >= 3) ? 3 : contributors.size();
                List<LinearLayout> contribLayouts = new ArrayList<LinearLayout>(arraySize);
                List<TextView> contribName = new ArrayList<TextView>(arraySize);
                List<TextView> contribCont = new ArrayList<TextView>(arraySize);

                for(int i = 0; i < arraySize; i++){
                    LinearLayout layout = new LinearLayout(getBaseContext());
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    layout.setWeightSum(10f);

                    TextView textViewContrib = new TextView(getBaseContext());
                    textViewContrib.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 7f));
                    textViewContrib.setText(contributors.get(i).getLogin());
                    textViewContrib.setTextColor(Color.BLACK);

                    TextView textViewCont = new TextView(getBaseContext());
                    textViewCont.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3f));
                    textViewCont.setText("Cont. "+contributors.get(i).getContributions());
                    textViewCont.setTextColor(Color.BLACK);

                    // Agrega los TextViews al LinearLayout
                    layout.addView(textViewContrib);
                    layout.addView(textViewCont);

                    // Agrega los elementos a su array
                    contribName.add(textViewContrib);
                    contribCont.add(textViewCont);
                    contribLayouts.add(layout);

                    // Agrega el LinearLayout al parent
                    parentContributors.addView(layout);

                }

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(RepoDetalleActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
