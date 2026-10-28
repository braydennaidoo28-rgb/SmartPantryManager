
package com.example.smartpantrymnager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView txtRecipeName;
    private TextView txtRecipeDescription;
    private TextView txtRecipeInstructions;
    private TextView txtRecipeIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        txtRecipeName = findViewById(R.id.txtRecipeName);
        txtRecipeDescription =
                findViewById(R.id.txtRecipeDescription);
        txtRecipeInstructions =
                findViewById(R.id.txtRecipeInstructions);

        // Load the selected recipe information
        String name = getIntent().getStringExtra("recipeName");
        String description =
                getIntent().getStringExtra("recipeDescription");
        String instructions =
                getIntent().getStringExtra("recipeInstructions");

        txtRecipeName.setText(name != null ? name : "Recipe");
        txtRecipeDescription.setText(
                description != null ? description : "");
        txtRecipeInstructions.setText(
                instructions != null ? instructions : "");

        // Find the ingredients TextView in the layout
        txtRecipeIngredients =
                findViewById(R.id.txtRecipeIngredients);

        // Load the required ingredients from SQLite
        if (name != null) {
            loadRecipeIngredients(name);
        } else {
            txtRecipeIngredients.setText(
                    "No ingredients available.");
        }
    }

    private void loadRecipeIngredients(String recipeName) {

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        StringBuilder ingredientList = new StringBuilder();

        String query =
                "SELECT ri.ingredient_name, ri.quantity, ri.unit " +
                        "FROM recipe_ingredients ri " +
                        "INNER JOIN recipes r " +
                        "ON ri.recipe_id = r.recipe_id " +
                        "WHERE r.name = ? " +
                        "ORDER BY ri.id ASC";

        try (Cursor cursor = db.rawQuery(
                query,
                new String[]{recipeName})) {

            while (cursor.moveToNext()) {

                String ingredientName =
                        cursor.getString(0);

                double quantity =
                        cursor.getDouble(1);

                String unit =
                        cursor.getString(2);

                ingredientList.append("\u2022 ")
                        .append(formatQuantity(quantity))
                        .append(" ")
                        .append(unit)
                        .append(" ")
                        .append(ingredientName)
                        .append("\n");
            }
        } finally {
            helper.close();
        }

        if (ingredientList.length() == 0) {
            txtRecipeIngredients.setText(
                    "No ingredients found for this recipe.");
        } else {
            txtRecipeIngredients.setText(
                    ingredientList.toString().trim());
        }
    }

    private String formatQuantity(double quantity) {

        if (quantity == Math.floor(quantity)) {
            return String.valueOf((long) quantity);
        }

        return String.valueOf(quantity);
    }
}
