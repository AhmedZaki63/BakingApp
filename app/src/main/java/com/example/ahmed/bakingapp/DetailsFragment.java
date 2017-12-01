package com.example.ahmed.bakingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.bakingapp.Adapters.StepAdapter;
import com.example.ahmed.bakingapp.Models.Ingredient;
import com.example.ahmed.bakingapp.Models.Recipe;
import com.example.ahmed.bakingapp.Models.Step;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class DetailsFragment extends Fragment {

    Recipe recipe;
    ArrayList<Step> stepArrayList;
    ArrayList<Ingredient> ingredientArrayList;
    StepAdapter stepAdapter;
    StepDataListener stepDataListener;

    @BindView(R.id.steps_list)
    RecyclerView stepView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);

        recipe = Parcels.unwrap(getArguments().getParcelable("recipe"));

        String ingredients_text = "";
        int i = 1;
        for (Ingredient ingredient : recipe.getIngredients())
            ingredients_text = ingredients_text.concat(i++ + ") " + ingredient.getIngredient() + "\n");

        addIngredientsToWidget(ingredients_text);

        stepArrayList = recipe.getSteps();
        ingredientArrayList = recipe.getIngredients();
        stepAdapter = new StepAdapter(ingredients_text, stepArrayList, stepDataListener);
        stepView.setAdapter(stepAdapter);
        stepView.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));

        return view;
    }

    public void setStepDataListener(StepDataListener stepDataListener) {
        this.stepDataListener = stepDataListener;
    }

    public void addIngredientsToWidget(String s) {
        SharedPreferences prefs = getContext().getSharedPreferences("Baking_Prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ingredients", s);
        editor.apply();
    }
}
