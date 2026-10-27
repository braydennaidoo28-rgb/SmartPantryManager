
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

        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));

        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddIngredientActivity.class
            );
            startActivity(intent);
        });

        btnSuggestedRecipes.setOnClickListener(v ->
                android.widget.Toast.makeText(
                        MainActivity.this,
                        "Suggested Recipes screen coming next",
                        android.widget.Toast.LENGTH_SHORT
                ).show()
        );

        btnSettings.setOnClickListener(v ->
                android.widget.Toast.makeText(
                        MainActivity.this,
                        "Settings screen coming next",
                        android.widget.Toast.LENGTH_SHORT
                ).show()
        );
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

            recyclerPantry.setAdapter(
                    new PantryAdapter(ingredients)
            );
        }
    }
}
