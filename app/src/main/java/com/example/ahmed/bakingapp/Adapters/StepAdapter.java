package com.example.ahmed.bakingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.bakingapp.Models.Step;
import com.example.ahmed.bakingapp.R;
import com.example.ahmed.bakingapp.StepDataListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.MyViewHolder> {

    private String ingredients;
    private ArrayList<Step> stepArrayList;
    private StepDataListener stepDataListener;

    public StepAdapter(String ingredients, ArrayList<Step> stepArrayList, StepDataListener stepDataListener) {
        this.ingredients = ingredients;
        this.stepArrayList = stepArrayList;
        this.stepDataListener = stepDataListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0 || viewType == 2)
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.title_item, parent, false);
        else
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                holder.item_text.setText(R.string.ingredients_title);
                break;
            case 1:
                holder.item_text.setText(ingredients);
                break;
            case 2:
                holder.item_text.setText(R.string.steps_title);
                break;
            case 3:
                holder.item_text.setText(stepArrayList.get(position - 3).getShortDescription());
                holder.item_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stepDataListener.setStepData(stepArrayList, holder.getAdapterPosition() - 3);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= 3) ? 3 : position;
    }

    @Override
    public int getItemCount() {
        return stepArrayList.size() + 3;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_item_text)
        TextView item_text;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
