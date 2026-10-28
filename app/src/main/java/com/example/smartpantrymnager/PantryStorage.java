
package com.example.smartpantrymnager;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class PantryStorage {

    // CREATE: Save a new ingredient to SQLite
    public static boolean addIngredient(
            Context context,
            Ingredient ingredient) {

        DatabaseHelper dbHelper = new DatabaseHelper(context);

        long result = dbHelper.addIngredient(
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getUnit(),
                ingredient.getExpiryDate()
        );

        dbHelper.close();

        return result != -1;
    }

    // READ: Load all ingredients from SQLite
    public static ArrayList<Ingredient> loadIngredients(
            Context context) {

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        Cursor cursor = dbHelper.getAllIngredients();

        try {
            while (cursor.moveToNext()) {

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_NAME));

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_QUANTITY));

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_UNIT));

                String expiryDate = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_EXPIRY));

                ingredients.add(new Ingredient(
                        name,
                        quantity,
                        unit,
                        expiryDate == null ? "" : expiryDate
                ));
            }
        } finally {
            cursor.close();
            dbHelper.close();
        }

        return ingredients;
    }

    // DELETE ALL: Used by the Settings screen
    public static void clearIngredients(Context context) {

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        android.database.sqlite.SQLiteDatabase db =
                dbHelper.getWritableDatabase();

        db.delete(DatabaseHelper.TABLE_PANTRY, null, null);

        dbHelper.close();
    }

    // UPDATE and DELETE by ingredient name
    // These temporary compatibility methods will be improved
    // to use database IDs in the next step.
    public static boolean updateIngredient(
            Context context,
            String oldName,
            Ingredient updatedIngredient) {

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        android.database.sqlite.SQLiteDatabase db =
                dbHelper.getWritableDatabase();

        android.content.ContentValues values =
                new android.content.ContentValues();

        values.put(DatabaseHelper.COL_NAME,
                updatedIngredient.getName());
        values.put(DatabaseHelper.COL_QUANTITY,
                updatedIngredient.getQuantity());
        values.put(DatabaseHelper.COL_UNIT,
                updatedIngredient.getUnit());
        values.put(DatabaseHelper.COL_EXPIRY,
                updatedIngredient.getExpiryDate());

        int rows = db.update(
                DatabaseHelper.TABLE_PANTRY,
                values,
                DatabaseHelper.COL_NAME + " = ?",
                new String[]{oldName}
        );

        dbHelper.close();

        return rows > 0;
    }

    public static boolean deleteIngredient(
            Context context,
            String name) {

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        android.database.sqlite.SQLiteDatabase db =
                dbHelper.getWritableDatabase();

        int rows = db.delete(
                DatabaseHelper.TABLE_PANTRY,
                DatabaseHelper.COL_NAME + " = ?",
                new String[]{name}
        );

        dbHelper.close();

        return rows > 0;
    }
}
