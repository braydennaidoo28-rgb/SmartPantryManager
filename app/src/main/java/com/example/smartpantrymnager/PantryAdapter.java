
package com.example.smartpantrymnager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.IngredientViewHolder> {

    private final ArrayList<Ingredient> ingredients;

    public PantryAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder, int position) {

        Ingredient ingredient = ingredients.get(position);

        holder.txtIngredientName.setText(ingredient.getName());

        String details = ingredient.getQuantity() + " "
                + ingredient.getUnit();

        if (ingredient.getExpiryDate() != null
                && !ingredient.getExpiryDate().isEmpty()) {
            details += "\nExpires: " + ingredient.getExpiryDate();
        }

        holder.txtIngredientDetails.setText(details);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView txtIngredientName;
        TextView txtIngredientDetails;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIngredientName =
                    itemView.findViewById(R.id.txtIngredientName);

            txtIngredientDetails =
                    itemView.findViewById(R.id.txtIngredientDetails);
        }
    }
}
