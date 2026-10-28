
package com.example.smartpantrymnager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 2;

    // Pantry table
    public static final String TABLE_PANTRY = "pantry";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY = "expiry_date";

    // Recipe tables
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create pantry table
        String createPantryTable =
                "CREATE TABLE " + TABLE_PANTRY + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME + " TEXT NOT NULL, " +
                        COL_QUANTITY + " REAL NOT NULL, " +
                        COL_UNIT + " TEXT NOT NULL, " +
                        COL_EXPIRY + " TEXT)";

        db.execSQL(createPantryTable);

        // Create recipes table
        String createRecipesTable =
                "CREATE TABLE " + TABLE_RECIPES + " (" +
                        "recipe_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL UNIQUE, " +
                        "description TEXT, " +
                        "instructions TEXT)";

        db.execSQL(createRecipesTable);

        // Create recipe ingredients table
        String createRecipeIngredientsTable =
                "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "recipe_id INTEGER NOT NULL, " +
                        "ingredient_name TEXT NOT NULL, " +
                        "quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "FOREIGN KEY(recipe_id) REFERENCES " +
                        TABLE_RECIPES +
                        "(recipe_id) ON DELETE CASCADE)";

        db.execSQL(createRecipeIngredientsTable);

        // Add the initial collection of recipes
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        // The tables already exist in version 1.
        // Seed recipes without deleting pantry records.
        if (oldVersion < 2) {
            seedRecipes(db);
        }
    }

    // Add a pantry ingredient
    public long addIngredient(
            String name,
            double quantity,
            String unit,
            String expiryDate) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_UNIT, unit);
        values.put(COL_EXPIRY, expiryDate);

        return db.insert(TABLE_PANTRY, null, values);
    }

    // Read all pantry ingredients
    public Cursor getAllIngredients() {

        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                TABLE_PANTRY,
                null,
                null,
                null,
                null,
                null,
                COL_NAME + " COLLATE NOCASE ASC"
        );
    }

    // Update a pantry ingredient
    public int updateIngredient(
            int id,
            String name,
            double quantity,
            String unit,
            String expiryDate) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_UNIT, unit);
        values.put(COL_EXPIRY, expiryDate);

        return db.update(
                TABLE_PANTRY,
                values,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    // Delete a pantry ingredient
    public int deleteIngredient(int id) {

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                TABLE_PANTRY,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    // Insert the initial recipe collection into SQLite
    private void seedRecipes(SQLiteDatabase db) {

        db.beginTransaction();

        try {

            addRecipe(db, "Pancakes",
                    "Classic pancakes for breakfast.",
                    "Mix the flour, milk and eggs into a batter. " +
                            "Cook portions in a lightly greased pan until " +
                            "golden on both sides.",
                    new String[]{"flour", "milk", "egg"},
                    new double[]{200, 250, 2},
                    new String[]{"g", "ml", "pieces"});

            addRecipe(db, "Cheese Omelette",
                    "A simple cheese omelette.",
                    "Beat the eggs. Cook them in a pan and add the cheese. " +
                            "Fold the omelette and cook until done.",
                    new String[]{"egg", "cheese"},
                    new double[]{2, 50},
                    new String[]{"pieces", "g"});

            addRecipe(db, "Tomato Pasta",
                    "Pasta with tomato and onion.",
                    "Cook the pasta. Chop and cook the tomato and onion. " +
                            "Combine everything and serve.",
                    new String[]{"pasta", "tomato", "onion"},
                    new double[]{200, 2, 1},
                    new String[]{"g", "pieces", "pieces"});

            addRecipe(db, "Garlic Bread",
                    "Crispy garlic bread.",
                    "Mix softened butter with crushed garlic. Spread over " +
                            "the bread and bake until golden.",
                    new String[]{"bread", "butter", "garlic"},
                    new double[]{4, 30, 2},
                    new String[]{"slices", "g", "cloves"});

            addRecipe(db, "Banana Smoothie",
                    "A creamy banana smoothie.",
                    "Blend the banana and milk until smooth. Serve chilled.",
                    new String[]{"banana", "milk"},
                    new double[]{1, 250},
                    new String[]{"pieces", "ml"});

            addRecipe(db, "French Toast",
                    "Golden French toast.",
                    "Beat the egg with the milk. Dip the bread into the " +
                            "mixture and fry until golden on both sides.",
                    new String[]{"bread", "egg", "milk"},
                    new double[]{2, 1, 50},
                    new String[]{"slices", "pieces", "ml"});

            addRecipe(db, "Fried Rice",
                    "Rice with egg and carrot.",
                    "Cook the rice. Cook the carrot and egg in a pan, " +
                            "then mix with the rice and heat through.",
                    new String[]{"rice", "egg", "carrot"},
                    new double[]{200, 1, 1},
                    new String[]{"g", "pieces", "pieces"});

            addRecipe(db, "Mashed Potatoes",
                    "Creamy mashed potatoes.",
                    "Boil the potatoes until soft. Mash with the butter " +
                            "and milk until smooth.",
                    new String[]{"potato", "butter", "milk"},
                    new double[]{3, 30, 50},
                    new String[]{"pieces", "g", "ml"});

            addRecipe(db, "Tomato Sandwich",
                    "A fresh tomato sandwich.",
                    "Slice the tomato, place it between the bread slices, " +
                            "and serve.",
                    new String[]{"bread", "tomato"},
                    new double[]{2, 1},
                    new String[]{"slices", "pieces"});

            addRecipe(db, "Peanut Butter Toast",
                    "Toast with peanut butter.",
                    "Toast the bread and spread peanut butter over it.",
                    new String[]{"bread", "peanut butter"},
                    new double[]{2, 30},
                    new String[]{"slices", "g"});

            addRecipe(db, "Potato and Onion Hash",
                    "A potato and onion pan-fried dish.",
                    "Dice the potatoes and onion. Heat the oil and fry " +
                            "everything until cooked and lightly browned.",
                    new String[]{"potato", "onion", "oil"},
                    new double[]{2, 1, 15},
                    new String[]{"pieces", "pieces", "ml"});

            addRecipe(db, "Apple Milk Smoothie",
                    "A refreshing apple smoothie.",
                    "Core and chop the apple. Blend with the milk until smooth.",
                    new String[]{"apple", "milk"},
                    new double[]{1, 200},
                    new String[]{"pieces", "ml"});

            addRecipe(db, "Cheese Sandwich",
                    "A simple cheese sandwich.",
                    "Place the cheese between the bread slices and serve.",
                    new String[]{"bread", "cheese"},
                    new double[]{2, 40},
                    new String[]{"slices", "g"});

            addRecipe(db, "Garlic Butter Pasta",
                    "Pasta with garlic butter sauce.",
                    "Cook the pasta. Melt the butter, add crushed garlic, " +
                            "then mix with the drained pasta.",
                    new String[]{"pasta", "butter", "garlic"},
                    new double[]{200, 30, 2},
                    new String[]{"g", "g", "cloves"});

            addRecipe(db, "Carrot Soup",
                    "A comforting vegetable soup.",
                    "Chop the carrot, onion and potato. Boil until soft, " +
                            "then blend until smooth.",
                    new String[]{"carrot", "onion", "potato"},
                    new double[]{3, 1, 1},
                    new String[]{"pieces", "pieces", "pieces"});

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }

    // Insert a recipe and its ingredients without creating duplicates
    private void addRecipe(
            SQLiteDatabase db,
            String name,
            String description,
            String instructions,
            String[] ingredientNames,
            double[] quantities,
            String[] units) {

        ContentValues recipeValues = new ContentValues();
        recipeValues.put("name", name);
        recipeValues.put("description", description);
        recipeValues.put("instructions", instructions);

        db.insertWithOnConflict(
                TABLE_RECIPES,
                null,
                recipeValues,
                SQLiteDatabase.CONFLICT_IGNORE
        );

        long recipeId = -1;

        try (Cursor cursor = db.query(
                TABLE_RECIPES,
                new String[]{"recipe_id"},
                "name = ?",
                new String[]{name},
                null,
                null,
                null)) {

            if (cursor.moveToFirst()) {
                recipeId = cursor.getLong(0);
            }
        }

        if (recipeId == -1) {
            return;
        }

        // If ingredients already exist, do not insert duplicates.
        try (Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_RECIPE_INGREDIENTS +
                        " WHERE recipe_id = ?",
                new String[]{String.valueOf(recipeId)})) {

            if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
                return;
            }
        }

        for (int i = 0; i < ingredientNames.length; i++) {

            ContentValues ingredientValues = new ContentValues();
            ingredientValues.put("recipe_id", recipeId);
            ingredientValues.put("ingredient_name", ingredientNames[i]);
            ingredientValues.put("quantity", quantities[i]);
            ingredientValues.put("unit", units[i]);

            db.insert(
                    TABLE_RECIPE_INGREDIENTS,
                    null,
                    ingredientValues
            );
        }
    }
}
