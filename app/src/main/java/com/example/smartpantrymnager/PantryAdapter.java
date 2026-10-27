
package com.example.smartpantrymnager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

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

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();

            String[] options = {"Edit", "Delete"};

            new AlertDialog.Builder(context)
                    .setTitle(ingredient.getName())
                    .setItems(options, (dialog, which) -> {
                        if (which == 0) {
                            Intent intent = new Intent(
                                    context, AddIngredientActivity.class);

                            intent.putExtra("editMode", true);
                            intent.putExtra("ingredientName", ingredient.getName());
                            intent.putExtra("ingredientQuantity", ingredient.getQuantity());
                            intent.putExtra("ingredientUnit", ingredient.getUnit());
                            intent.putExtra("ingredientExpiry", ingredient.getExpiryDate());

                            context.startActivity(intent);

                        } else {
                            new AlertDialog.Builder(context)
                                    .setTitle("Delete ingredient")
                                    .setMessage("Delete " + ingredient.getName() + "?")
                                    .setPositiveButton("Delete", (d, w) -> {
                                        int currentPosition =
                                                holder.getBindingAdapterPosition();

                                        if (currentPosition != RecyclerView.NO_POSITION) {
                                            ingredients.remove(currentPosition);

                                            PantryStorage.saveIngredients(
                                                    context, ingredients);

                                            notifyItemRemoved(currentPosition);

                                            Toast.makeText(context,
                                                    "Ingredient deleted",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
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
