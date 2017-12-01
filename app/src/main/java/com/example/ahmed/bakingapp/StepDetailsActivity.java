package com.example.ahmed.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StepDetailsActivity extends AppCompatActivity {

    StepDetailsFragment stepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, stepDetailsFragment, "step_details_fragment").commit();
        } else {
            stepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager().findFragmentByTag("step_details_fragment");
        }
    }
}
