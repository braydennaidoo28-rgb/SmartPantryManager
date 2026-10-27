
package com.example.smartpantrymnager;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PantryStorage {

    private static final String PREFS_NAME = "SmartPantryPrefs";
    private static final String KEY_INGREDIENTS = "ingredients";

    public static void saveIngredients(
            Context context, ArrayList<Ingredient> ingredients) {

        SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);

        JSONArray jsonArray = new JSONArray();

        try {
            for (Ingredient ingredient : ingredients) {
                JSONObject object = new JSONObject();

                object.put("name", ingredient.getName());
                object.put("quantity", ingredient.getQuantity());
                object.put("unit", ingredient.getUnit());
                object.put("expiryDate", ingredient.getExpiryDate());

                jsonArray.put(object);
            }

            prefs.edit()
                    .putString(KEY_INGREDIENTS, jsonArray.toString())
                    .apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Ingredient> loadIngredients(Context context) {

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);

        String savedData = prefs.getString(KEY_INGREDIENTS, "[]");

        try {
            JSONArray jsonArray = new JSONArray(savedData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                Ingredient ingredient = new Ingredient(
                        object.getString("name"),
                        object.getDouble("quantity"),
                        object.getString("unit"),
                        object.optString("expiryDate", "")
                );

                ingredients.add(ingredient);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingredients;
    }
}
