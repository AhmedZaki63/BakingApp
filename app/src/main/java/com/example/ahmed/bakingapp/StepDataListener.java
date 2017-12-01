package com.example.ahmed.bakingapp;


import com.example.ahmed.bakingapp.Models.Step;

import java.util.ArrayList;

public interface StepDataListener {
    void setStepData(ArrayList<Step> stepArrayList, int position);
}
