package com.albertomadrazo.android.gitlook.activity;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.albertomadrazo.android.gitlook.API.RepositoriosAPI;
import com.albertomadrazo.android.gitlook.R;
import com.albertomadrazo.android.gitlook.model.Contributor;
import com.albertomadrazo.android.gitlook.model.Issue;
import com.squareup.picasso.Picasso;

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
    private LinearLayout parentIssues;

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
        tvNombreRepo.setTextColor(Color.parseColor("#42C0FB"));

        tvDescripcionRepo = (TextView) findViewById(R.id.detalle_descripcion_repo);
        tvDescripcionRepo.setText(repoDescripcion);


        parentContributors = (LinearLayout) findViewById(R.id.parent_layout_contributors);
        parentIssues = (LinearLayout) findViewById(R.id.parent_layout_issues);

        getContributorsFromAPI(repoOwner, repoName);
        getIssuesFromAPI(repoOwner, repoName);
    }


    void getContributorsFromAPI(String repoOwner, String nombreRepo){
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("https://api.github.com").build();
        RepositoriosAPI api = adapter.create(RepositoriosAPI.class);
        api.getContributors(repoOwner, nombreRepo, new Callback<List<Contributor>>() {
            @Override
            public void success(final List<Contributor> contributors, Response response) {
                int arraySize = (contributors.size() >= 3) ? 3 : contributors.size();
                List<LinearLayout> contribLayouts = new ArrayList<LinearLayout>(arraySize);
                List<TextView> contribName = new ArrayList<TextView>(arraySize);
                List<TextView> contribCont = new ArrayList<TextView>(arraySize);

                for(int i = 0; i < arraySize; i++){
                    LinearLayout layout = new LinearLayout(getBaseContext());
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    layout.setWeightSum(10f);

                    final TextView textViewContrib = new TextView(getBaseContext());
                    textViewContrib.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 7f));
                    textViewContrib.setText(contributors.get(i).getLogin());
                    textViewContrib.setTextColor(Color.BLACK);
                    textViewContrib.setTextSize(18);

                    final String shas = contributors.get(i).getContributions();

                    final int k = i;
                    textViewContrib.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Dialog dialog = new Dialog(RepoDetalleActivity.this);
                            dialog.setContentView(R.layout.custom_dialog);
                            dialog.setTitle("Detalles del usuario");



                            // Hace el dialogo a un tamaño que llena la pantalla
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                            dialog.show();
                            dialog.getWindow().setAttributes(lp);




                            ImageView avatar = (ImageView) dialog.findViewById(R.id.user_picture);
                            // avatar.setImageBitmap();

                            Picasso.with(RepoDetalleActivity.this)
                                    .load(contributors.get(k).getAvatarUrl())
                                    .resize(200, 200)
                                    .into(avatar);

                            TextView usuario = (TextView) dialog.findViewById(R.id.user_name);
                            usuario.setText(contributors.get(k).getLogin());

                            TextView contribs = (TextView) dialog.findViewById(R.id.user_contributions);
                            contribs.setText(contributors.get(k).getContributions());

                            TextView url = (TextView) dialog.findViewById(R.id.user_url);
                            url.setText(contributors.get(k).getUrl());


                            Button dialogOk = (Button) dialog.findViewById(R.id.dialogOK);
                            dialogOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        }
                    });

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


    void getIssuesFromAPI(String repoOwner, String nombreRepo){
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("https://api.github.com").build();
        RepositoriosAPI api = adapter.create(RepositoriosAPI.class);
        api.getIssues(repoOwner, nombreRepo, new Callback<List<Issue>>() {
            @Override
            public void success(List<Issue> issues, Response response) {
                int arraySize = (issues.size() >= 3) ? 3 : issues.size();

                List<LinearLayout> issuesLayouts = new ArrayList<LinearLayout>(arraySize);
                List<LinearLayout> issueBodyLayouts = new ArrayList<LinearLayout>(arraySize);

                List<TextView> issueUser = new ArrayList<TextView>(arraySize);
                List<TextView> issueCreated = new ArrayList<TextView>(arraySize);

                for(int i = 0; i < arraySize; i++){
                    LinearLayout layout = new LinearLayout(getBaseContext());
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    layout.setWeightSum(10f);

                    TextView textViewUser = new TextView(getBaseContext());
                    textViewUser.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 5f));
                    textViewUser.setText(issues.get(i).getUser().getLogin());
                    textViewUser.setTextColor(Color.BLACK);
                    textViewUser.setTextSize(18);

                    TextView textViewCreated = new TextView(getBaseContext());
                    textViewCreated.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 5f));
                    textViewCreated.setText(issues.get(i).getCreatedAt());
                    textViewCreated.setTextColor(Color.BLACK);
                    textViewUser.setTextSize(18);

                    // Agrega los TextViews al LinearLayout
                    layout.addView(textViewUser);
                    layout.addView(textViewCreated);
                    //bodyLayout.setPadding(0,0,0,20);

                    LinearLayout bodyLayout = new LinearLayout(getBaseContext());

                    TextView textViewBody = new TextView(getBaseContext());
                    textViewBody.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3f));
                    textViewBody.setText((issues.get(i).getBody().isEmpty()) ? "Sin comentarios" : issues.get(i).getBody());
                    textViewBody.setTextColor(Color.rgb(66, 66, 66));

                    // Parametros para los LinearLayouts
                    LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearParams.setMargins(0,0,0,30);

                    bodyLayout.addView(textViewBody);
                    bodyLayout.setLayoutParams(linearParams);

                    // Agrega los elementos a su array
                    issueUser.add(textViewUser);
                    issueCreated.add(textViewCreated);
                    issuesLayouts.add(layout);
                    issueBodyLayouts.add(bodyLayout);

                    // Agrega el LinearLayout al parent
                    parentIssues.addView(layout);
                    parentIssues.addView(bodyLayout);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(RepoDetalleActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
