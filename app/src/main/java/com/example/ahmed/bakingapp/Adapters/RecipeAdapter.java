package com.example.ahmed.bakingapp.Adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.bakingapp.RecipeDataListener;
import com.example.ahmed.bakingapp.Models.Recipe;
import com.example.ahmed.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Recipe> recipeArrayList;
    private RecipeDataListener recipeDataListener;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeArrayList, RecipeDataListener recipeDataListener) {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
        this.recipeDataListener = recipeDataListener;
    }

    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new RecipeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeAdapter.MyViewHolder holder, int position) {
        holder.title.setText(recipeArrayList.get(position).getName());
        if (!recipeArrayList.get(position).getImage().isEmpty())
            Picasso.with(context)
                    .load(recipeArrayList.get(position).getImage())
                    .placeholder(R.drawable.cake)
                    .into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeDataListener.setRecipeData(recipeArrayList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }

    public void setData(ArrayList<Recipe> recipes) {
        recipeArrayList = recipes;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_item_title)
        TextView title;
        @BindView(R.id.recipe_item_image)
        ImageView image;
        @BindView(R.id.recipe_card_view)
        CardView cardView;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
