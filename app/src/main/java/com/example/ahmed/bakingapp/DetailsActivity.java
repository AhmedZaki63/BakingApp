package com.example.ahmed.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ahmed.bakingapp.Models.Step;

import org.parceler.Parcels;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements StepDataListener {

    DetailsFragment detailsFragment;
    public boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.details_frame_layout, detailsFragment, "details_fragment").commit();
        } else {
            detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentByTag("details_fragment");
        }
        detailsFragment.setStepDataListener(this);
        if (findViewById(R.id.step_details_frame_layout) != null)
            isTwoPane = true;
    }

    @Override
    public void setStepData(ArrayList<Step> stepArrayList, int position) {
        Bundle b = new Bundle();
        b.putParcelable("steps_list", Parcels.wrap(stepArrayList));
        b.putInt("position", position);
        if (!isTwoPane) {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        } else {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.step_details_frame_layout, stepDetailsFragment).commit();
        }
    }
}
