package com.example.ahmed.bakingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    String ingredients_text = "";

    @BindView(R.id.steps_list)
    RecyclerView stepView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        recipe = Parcels.unwrap(getArguments().getParcelable("recipe"));

        int i = 1;
        for (Ingredient ingredient : recipe.getIngredients())
            ingredients_text = ingredients_text.concat(i++ + ") " + ingredient.getIngredient() + "\n");

        stepArrayList = recipe.getSteps();
        ingredientArrayList = recipe.getIngredients();
        stepAdapter = new StepAdapter(ingredients_text, stepArrayList, stepDataListener);
        stepView.setAdapter(stepAdapter);
        stepView.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));

        if (getActivity().findViewById(R.id.step_details_frame_layout) != null
                && savedInstanceState == null)
            stepDataListener.setStepData(stepArrayList, 0);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_to_widget) {
            addIngredientsToWidget(ingredients_text);
            Toast.makeText(getContext(), "Recipe's ingredients added to widget", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
