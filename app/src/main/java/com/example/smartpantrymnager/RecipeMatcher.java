
package com.example.smartpantrymnager;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeMatcher {

    public static ArrayList<Recipe> getMatchingRecipes(
            ArrayList<Recipe> allRecipes,
            ArrayList<Ingredient> pantryIngredients) {

        ArrayList<Recipe> matchingRecipes = new ArrayList<>();

        for (Recipe recipe : allRecipes) {

            boolean allIngredientsAvailable = true;

            for (Ingredient required : recipe.getRequiredIngredients()) {

                double availableQuantity = 0;

                for (Ingredient pantry : pantryIngredients) {

                    // Match singular/plural names and ignore case
                    if (normalizeIngredientName(pantry.getName())
                            .equals(normalizeIngredientName(
                                    required.getName()))) {

                        double convertedQuantity = convertQuantity(
                                pantry.getQuantity(),
                                pantry.getUnit(),
                                required.getUnit()
                        );

                        // Ignore incompatible measurement units
                        if (convertedQuantity >= 0) {
                            availableQuantity += convertedQuantity;
                        }
                    }
                }

                // Every required ingredient must have enough quantity
                if (availableQuantity < required.getQuantity()) {
                    allIngredientsAvailable = false;
                    break;
                }
            }

            if (allIngredientsAvailable) {
                matchingRecipes.add(recipe);
            }
        }

        return matchingRecipes;
    }

    // Normalize common singular and plural ingredient names
    private static String normalizeIngredientName(String name) {

        if (name == null) {
            return "";
        }

        String normalized = name.trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", " ");

        switch (normalized) {
            case "eggs":
                return "egg";

            case "tomatoes":
                return "tomato";

            case "potatoes":
                return "potato";

            case "onions":
                return "onion";

            case "carrots":
                return "carrot";

            case "bananas":
                return "banana";

            case "apples":
                return "apple";

            case "cloves of garlic":
                return "garlic";

            case "slices of bread":
                return "bread";

            default:
                break;
        }

        // Handle other straightforward plurals.
        if (normalized.length() > 4
                && normalized.endsWith("ies")) {
            return normalized.substring(
                    0, normalized.length() - 3) + "y";
        }

        if (normalized.length() > 3
                && normalized.endsWith("s")
                && !normalized.endsWith("ss")
                && !normalized.endsWith("us")) {
            return normalized.substring(
                    0, normalized.length() - 1);
        }

        return normalized;
    }

    private static double convertQuantity(
            double quantity,
            String pantryUnit,
            String requiredUnit) {

        String pantry = normalizeUnit(pantryUnit);
        String required = normalizeUnit(requiredUnit);

        if (pantry.equals(required)) {
            return quantity;
        }

        // Litres to millilitres
        if (pantry.equals("l") && required.equals("ml")) {
            return quantity * 1000;
        }

        if (pantry.equals("ml") && required.equals("l")) {
            return quantity / 1000;
        }

        // Kilograms to grams
        if (pantry.equals("kg") && required.equals("g")) {
            return quantity * 1000;
        }

        if (pantry.equals("g") && required.equals("kg")) {
            return quantity / 1000;
        }

        // Incompatible units must not count
        return -1;
    }

    private static String normalizeUnit(String unit) {

        if (unit == null) {
            return "";
        }

        String normalized = unit.trim().toLowerCase(Locale.ROOT);

        switch (normalized) {

            case "litre":
            case "litres":
            case "liter":
            case "liters":
                return "l";

            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
                return "ml";

            case "gram":
            case "grams":
                return "g";

            case "kilogram":
            case "kilograms":
                return "kg";

            case "piece":
            case "pieces":
            case "pcs":
                return "pieces";

            case "slice":
            case "slices":
                return "slices";

            case "clove":
            case "cloves":
                return "cloves";

            default:
                return normalized;
        }
    }
}
