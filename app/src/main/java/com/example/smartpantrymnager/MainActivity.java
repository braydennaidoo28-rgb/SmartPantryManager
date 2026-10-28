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

    private Button btnAddIngredient;
    private Button btnSuggestedRecipes;
    private Button btnSettings;

    private RecyclerView recyclerPantry;
    private TextView txtEmptyPantry;

    private ArrayList<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        txtEmptyPantry = findViewById(R.id.txtEmptyPantry);

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

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
        loadPantry();
    }

    private void loadPantry() {
        ingredients = PantryStorage.loadIngredients(this);

        if (ingredients.isEmpty()) {
            txtEmptyPantry.setVisibility(View.VISIBLE);
            recyclerPantry.setVisibility(View.GONE);
        } else {
            txtEmptyPantry.setVisibility(View.GONE);
            recyclerPantry.setVisibility(View.VISIBLE);

            PantryAdapter adapter = new PantryAdapter(ingredients);
            recyclerPantry.setAdapter(adapter);
        }
    }
}