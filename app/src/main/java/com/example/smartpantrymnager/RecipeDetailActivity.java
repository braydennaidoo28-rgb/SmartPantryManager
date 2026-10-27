
package com.example.smartpantrymnager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView txtRecipeName;
    private TextView txtRecipeDescription;
    private TextView txtRecipeInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        txtRecipeName = findViewById(R.id.txtRecipeName);
        txtRecipeDescription = findViewById(R.id.txtRecipeDescription);
        txtRecipeInstructions = findViewById(R.id.txtRecipeInstructions);

        String name = getIntent().getStringExtra("recipeName");
        String description = getIntent().getStringExtra("recipeDescription");
        String instructions = getIntent().getStringExtra("recipeInstructions");

        txtRecipeName.setText(name != null ? name : "Recipe");
        txtRecipeDescription.setText(
                description != null ? description : "");
        txtRecipeInstructions.setText(
                instructions != null ? instructions : "");
    }
}
