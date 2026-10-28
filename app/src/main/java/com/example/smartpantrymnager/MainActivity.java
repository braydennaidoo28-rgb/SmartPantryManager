package com.example.smartpantrymnager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private TextView txtEmptyPantry;

    private Button btnAddIngredient;
    private Button btnSuggestedRecipes;
    private Button btnSettings;

    private ArrayList<Ingredient> ingredients;
    private PantryAdapter pantryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect the Java variables to the XML views
        recyclerPantry = findViewById(R.id.recyclerPantry);
        txtEmptyPantry = findViewById(R.id.txtEmptyPantry);

        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        // Set up the RecyclerView
        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        ingredients = new ArrayList<>();

        // Load ingredients from the SQLite database
        loadPantry();

        // Open the Add Ingredient screen
        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddIngredientActivity.class
            );
            startActivity(intent);
        });

        // Open the Suggested Recipes screen
        btnSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );
            startActivity(intent);
        });

        // Open the Settings screen
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the pantry whenever the user returns to this screen
        loadPantry();
    }

    private void loadPantry() {

        // Retrieve the latest ingredients from SQLite
        ingredients = PantryStorage.loadIngredients(this);

        if (ingredients == null || ingredients.isEmpty()) {

            // Display a message when the pantry is empty
            txtEmptyPantry.setVisibility(View.VISIBLE);
            recyclerPantry.setVisibility(View.GONE);

        } else {

            // Show the ingredients list
            txtEmptyPantry.setVisibility(View.GONE);
            recyclerPantry.setVisibility(View.VISIBLE);

            // IMPORTANT: Pass the Activity context to PantryAdapter
            pantryAdapter = new PantryAdapter(
                    MainActivity.this,
                    ingredients
            );

            recyclerPantry.setAdapter(pantryAdapter);
        }
    }
}