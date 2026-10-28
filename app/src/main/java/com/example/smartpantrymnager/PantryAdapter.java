
package com.example.smartpantrymnager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PantryAdapter
        extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private final ArrayList<Ingredient> ingredients;
    private final Context context;

    public PantryAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.context = null;
    }

    public PantryAdapter(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position) {

        Ingredient ingredient = ingredients.get(position);

        holder.txtIngredientName.setText(ingredient.getName());

        String details = ingredient.getQuantity()
                + " " + ingredient.getUnit();

        if (ingredient.getExpiryDate() != null
                && !ingredient.getExpiryDate().trim().isEmpty()) {
            details += "\nExpires: " + ingredient.getExpiryDate();
        }

        holder.txtIngredientDetails.setText(details);

        holder.itemView.setOnClickListener(v ->
                showOptions(holder.getBindingAdapterPosition()));
    }

    private void showOptions(int position) {

        if (position == RecyclerView.NO_POSITION) {
            return;
        }

        Ingredient ingredient = ingredients.get(position);
        Context activeContext = context != null
                ? context
                : null;

        if (activeContext == null) {
            activeContext = ingredientsContext();
        }

        final Context dialogContext = activeContext;

        new AlertDialog.Builder(dialogContext)
                .setTitle(ingredient.getName())
                .setItems(new String[]{"Edit", "Delete"}, (dialog, which) -> {

                    if (which == 0) {
                        Intent intent = new Intent(
                                dialogContext,
                                AddIngredientActivity.class
                        );

                        intent.putExtra("editMode", true);
                        intent.putExtra(
                                "ingredientName",
                                ingredient.getName()
                        );
                        intent.putExtra(
                                "ingredientQuantity",
                                ingredient.getQuantity()
                        );
                        intent.putExtra(
                                "ingredientUnit",
                                ingredient.getUnit()
                        );
                        intent.putExtra(
                                "ingredientExpiry",
                                ingredient.getExpiryDate()
                        );

                        dialogContext.startActivity(intent);

                    } else {
                        confirmDelete(dialogContext, ingredient);
                    }
                })
                .show();
    }

    private Context ingredientsContext() {
        // The RecyclerView item always has the current Activity context.
        return ingredients.isEmpty()
                ? null
                : null;
    }

    private void confirmDelete(
            Context activeContext,
            Ingredient ingredient) {

        new AlertDialog.Builder(activeContext)
                .setTitle("Delete ingredient?")
                .setMessage(
                        "Are you sure you want to delete "
                                + ingredient.getName() + "?"
                )
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", (dialog, which) -> {

                    boolean deleted = PantryStorage.deleteIngredient(
                            activeContext,
                            ingredient.getName()
                    );

                    if (deleted) {
                        Toast.makeText(
                                activeContext,
                                "Ingredient deleted.",
                                Toast.LENGTH_SHORT
                        ).show();

                        if (activeContext instanceof MainActivity) {
                            ((MainActivity) activeContext).recreate();
                        }
                    } else {
                        Toast.makeText(
                                activeContext,
                                "Could not delete ingredient.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .show();
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView txtIngredientName;
        TextView txtIngredientDetails;

        PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIngredientName =
                    itemView.findViewById(R.id.txtIngredientName);

            txtIngredientDetails =
                    itemView.findViewById(R.id.txtIngredientDetails);
        }
    }
}
