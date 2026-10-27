
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
                boolean ingredientFound = false;

                for (Ingredient pantry : pantryIngredients) {

                    if (pantry.getName().trim().equalsIgnoreCase(
                            required.getName().trim())) {

                        double convertedQuantity = convertQuantity(
                                pantry.getQuantity(),
                                pantry.getUnit(),
                                required.getUnit());

                        if (convertedQuantity >= 0) {
                            availableQuantity += convertedQuantity;
                            ingredientFound = true;
                        }
                    }
                }

                if (!ingredientFound
                        || availableQuantity < required.getQuantity()) {
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

    private static double convertQuantity(
            double quantity,
            String pantryUnit,
            String requiredUnit) {

        String pantry = normalizeUnit(pantryUnit);
        String required = normalizeUnit(requiredUnit);

        if (pantry.equals(required)) {
            return quantity;
        }

        // Convert litres to millilitres.
        if (pantry.equals("l") && required.equals("ml")) {
            return quantity * 1000;
        }

        if (pantry.equals("ml") && required.equals("l")) {
            return quantity / 1000;
        }

        // Convert kilograms to grams.
        if (pantry.equals("kg") && required.equals("g")) {
            return quantity * 1000;
        }

        if (pantry.equals("g") && required.equals("kg")) {
            return quantity / 1000;
        }

        // Incompatible units must not count towards the recipe.
        return -1;
    }

    private static String normalizeUnit(String unit) {

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
                return "slices";

            case "clove":
                return "cloves";

            default:
                return normalized;
        }
    }
}
