package com.example.ahmed.bakingapp.Network;

import com.example.ahmed.bakingapp.Models.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeApi {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getRecipesData();
}
