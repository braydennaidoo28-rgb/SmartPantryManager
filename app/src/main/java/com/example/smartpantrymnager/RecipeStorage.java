
package com.example.smartpantrymnager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class RecipeStorage {

    // Load all recipes and their ingredients from SQLite
    public static ArrayList<Recipe> getRecipes(Context context) {

        ArrayList<Recipe> recipes = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String recipeQuery =
                "SELECT recipe_id, name, description, instructions " +
                        "FROM recipes ORDER BY name COLLATE NOCASE ASC";

        try (Cursor recipeCursor = db.rawQuery(recipeQuery, null)) {

            while (recipeCursor.moveToNext()) {

                int recipeId = recipeCursor.getInt(
                        recipeCursor.getColumnIndexOrThrow("recipe_id")
                );

                String name = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow("name")
                );

                String description = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow("description")
                );

                String instructions = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow("instructions")
                );

                ArrayList<Ingredient> ingredients =
                        getRecipeIngredients(db, recipeId);

                Recipe recipe = new Recipe(
                        name,
                        description,
                        ingredients,
                        instructions
                );

                recipes.add(recipe);
            }

        } finally {
            helper.close();
        }

        return recipes;
    }

    // Load the required ingredients for one recipe
    private static ArrayList<Ingredient> getRecipeIngredients(
            SQLiteDatabase db,
            int recipeId) {

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        String ingredientQuery =
                "SELECT ingredient_name, quantity, unit " +
                        "FROM recipe_ingredients " +
                        "WHERE recipe_id = ? ORDER BY id ASC";

        try (Cursor cursor = db.rawQuery(
                ingredientQuery,
                new String[]{String.valueOf(recipeId)})) {

            while (cursor.moveToNext()) {

                String ingredientName = cursor.getString(
                        cursor.getColumnIndexOrThrow("ingredient_name")
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity")
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                );

                ingredients.add(new Ingredient(
                        ingredientName,
                        quantity,
                        unit,
                        ""
                ));
            }
        }

        return ingredients;
    }
}
