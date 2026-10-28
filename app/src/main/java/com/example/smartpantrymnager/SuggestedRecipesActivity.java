
package com.example.smartpantrymnager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private ListView listRecipes;
    private TextView txtNoRecipes;

    private ArrayList<Recipe> matchingRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        listRecipes = findViewById(R.id.listRecipes);
        txtNoRecipes = findViewById(R.id.txtNoRecipes);

        loadMatchingRecipes();

        // Open the details of the selected recipe
        listRecipes.setOnItemClickListener(
                (parent, view, position, id) -> {

                    Recipe selectedRecipe = matchingRecipes.get(position);

                    Intent intent = new Intent(
                            SuggestedRecipesActivity.this,
                            RecipeDetailActivity.class
                    );

                    intent.putExtra(
                            "recipeName",
                            selectedRecipe.getName()
                    );

                    intent.putExtra(
                            "recipeDescription",
                            selectedRecipe.getDescription()
                    );

                    intent.putExtra(
                            "recipeInstructions",
                            selectedRecipe.getInstructions()
                    );

                    startActivity(intent);
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh suggestions when returning to this screen
        if (listRecipes != null && txtNoRecipes != null) {
            loadMatchingRecipes();
        }
    }

    private void loadMatchingRecipes() {

        // Load recipes and their ingredients from SQLite
        ArrayList<Recipe> allRecipes =
                RecipeStorage.getRecipes(this);

        // Load pantry ingredients from SQLite
        ArrayList<Ingredient> pantryIngredients =
                PantryStorage.loadIngredients(this);

        // Find recipes with every required ingredient
        // in sufficient quantities
        matchingRecipes = RecipeMatcher.getMatchingRecipes(
                allRecipes,
                pantryIngredients
        );

        // Prepare the names of matching recipes
        ArrayList<String> recipeNames = new ArrayList<>();

        for (Recipe recipe : matchingRecipes) {
            recipeNames.add(recipe.getName());
        }

        // Display matching recipes
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                recipeNames
        );

        listRecipes.setAdapter(adapter);

        // Display a message when there are no matches
        if (matchingRecipes.isEmpty()) {

            txtNoRecipes.setText(
                    "No recipes match your pantry yet. " +
                            "Add more ingredients to see suggestions."
            );

            txtNoRecipes.setVisibility(View.VISIBLE);
            listRecipes.setVisibility(View.GONE);

        } else {

            txtNoRecipes.setVisibility(View.GONE);
            listRecipes.setVisibility(View.VISIBLE);
        }
    }
}
