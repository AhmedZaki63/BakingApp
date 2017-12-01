package com.example.ahmed.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ahmed.bakingapp.Models.Recipe;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity implements RecipeDataListener {

    MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, mainFragment, "main_fragment").commit();
        } else {
            mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("main_fragment");
        }
        mainFragment.setRecipeDataListener(this);
    }

    @Override
    public void setRecipeData(Recipe recipe) {
        Bundle b = new Bundle();
        b.putParcelable("recipe", Parcels.wrap(recipe));
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
