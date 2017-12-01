package com.example.ahmed.bakingapp;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ahmed.bakingapp.Adapters.RecipeAdapter;
import com.example.ahmed.bakingapp.Models.Recipe;
import com.example.ahmed.bakingapp.Network.RecipeApi;
import com.example.ahmed.bakingapp.Network.RecipeClient;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private ArrayList<Recipe> recipeArrayList;
    private RecipeAdapter recipeAdapter;
    private RecipeDataListener recipeDataListener;

    @BindView(R.id.recipes_list)
    RecyclerView recipeView;
    @BindView(R.id.main_progress_bar)
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        recipeArrayList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getContext(), recipeArrayList, recipeDataListener);
        recipeView.setAdapter(recipeAdapter);
        recipeView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (savedInstanceState != null && savedInstanceState.containsKey("recipes")) {
            recipeArrayList = Parcels.unwrap(savedInstanceState.getParcelable("recipes"));
            recipeAdapter.setData(recipeArrayList);
        } else
            getData();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!recipeArrayList.isEmpty())
            outState.putParcelable("recipes", Parcels.wrap(recipeArrayList));
    }

    public void setRecipeDataListener(RecipeDataListener recipeDataListener) {
        this.recipeDataListener = recipeDataListener;
    }

    public void getData() {
        progressBar.setVisibility(View.VISIBLE);
        RecipeApi recipeApi = RecipeClient.createApi(RecipeClient.buildRetrofit());
        recipeApi.getRecipesData().enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Recipe>> call, @NonNull Response<ArrayList<Recipe>> response) {
                if (response.isSuccessful()) {
                    recipeArrayList = response.body();
                    recipeAdapter.setData(response.body());
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Recipe>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getData();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.setMessage("Sorry no internet connection");
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });
    }
}
