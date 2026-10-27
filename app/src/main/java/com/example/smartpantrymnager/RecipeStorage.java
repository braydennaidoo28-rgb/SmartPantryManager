
package com.example.smartpantrymnager;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeStorage {

    public static ArrayList<Recipe> getRecipes() {

        ArrayList<Recipe> recipes = new ArrayList<>();

        recipes.add(createRecipe(
                "Pancakes",
                "Fluffy homemade pancakes.",
                new String[]{"flour", "milk", "egg"},
                new double[]{200, 250, 2},
                new String[]{"g", "ml", "pieces"},
                "1. Mix flour, milk and eggs.\n"
                        + "2. Whisk until smooth.\n"
                        + "3. Pour into a lightly oiled pan.\n"
                        + "4. Cook both sides until golden."
        ));

        recipes.add(createRecipe(
                "Cheese Omelette",
                "A simple cheesy breakfast.",
                new String[]{"egg", "cheese"},
                new double[]{2, 50},
                new String[]{"pieces", "g"},
                "1. Beat the eggs.\n"
                        + "2. Pour into a heated pan.\n"
                        + "3. Add cheese.\n"
                        + "4. Fold and cook until set."
        ));

        recipes.add(createRecipe(
                "Tomato Pasta",
                "Pasta with a simple tomato sauce.",
                new String[]{"pasta", "tomato", "onion"},
                new double[]{200, 2, 1},
                new String[]{"g", "pieces", "pieces"},
                "1. Cook the pasta.\n"
                        + "2. Chop the tomatoes and onion.\n"
                        + "3. Cook them in a pan.\n"
                        + "4. Mix with the drained pasta."
        ));

        recipes.add(createRecipe(
                "Garlic Bread",
                "Toasted bread with garlic butter.",
                new String[]{"bread", "butter", "garlic"},
                new double[]{4, 30, 2},
                new String[]{"slices", "g", "cloves"},
                "1. Mix softened butter with crushed garlic.\n"
                        + "2. Spread over bread.\n"
                        + "3. Toast until golden."
        ));

        recipes.add(createRecipe(
                "Banana Smoothie",
                "A creamy banana drink.",
                new String[]{"banana", "milk"},
                new double[]{1, 250},
                new String[]{"pieces", "ml"},
                "1. Peel the banana.\n"
                        + "2. Add banana and milk to a blender.\n"
                        + "3. Blend until smooth."
        ));

        recipes.add(createRecipe(
                "French Toast",
                "Golden egg-coated bread.",
                new String[]{"bread", "egg", "milk"},
                new double[]{2, 1, 50},
                new String[]{"slices", "pieces", "ml"},
                "1. Beat egg with milk.\n"
                        + "2. Dip the bread in the mixture.\n"
                        + "3. Fry both sides until golden."
        ));

        recipes.add(createRecipe(
                "Fried Rice",
                "Quick rice with vegetables and egg.",
                new String[]{"rice", "egg", "carrot"},
                new double[]{200, 1, 1},
                new String[]{"g", "pieces", "pieces"},
                "1. Cook the rice and allow it to cool.\n"
                        + "2. Chop the carrot.\n"
                        + "3. Fry the carrot and egg.\n"
                        + "4. Add rice and stir-fry."
        ));

        recipes.add(createRecipe(
                "Mashed Potatoes",
                "Creamy mashed potatoes.",
                new String[]{"potato", "butter", "milk"},
                new double[]{3, 30, 50},
                new String[]{"pieces", "g", "ml"},
                "1. Peel and boil the potatoes.\n"
                        + "2. Drain and mash them.\n"
                        + "3. Mix in butter and milk."
        ));

        recipes.add(createRecipe(
                "Tomato Sandwich",
                "A fresh tomato sandwich.",
                new String[]{"bread", "tomato"},
                new double[]{2, 1},
                new String[]{"slices", "pieces"},
                "1. Slice the tomato.\n"
                        + "2. Place it on the bread.\n"
                        + "3. Add seasoning if available."
        ));

        recipes.add(createRecipe(
                "Peanut Butter Toast",
                "Quick toast with peanut butter.",
                new String[]{"bread", "peanut butter"},
                new double[]{2, 30},
                new String[]{"slices", "g"},
                "1. Toast the bread.\n"
                        + "2. Spread peanut butter over each slice."
        ));

        recipes.add(createRecipe(
                "Potato and Onion Hash",
                "Pan-fried potatoes and onions.",
                new String[]{"potato", "onion", "oil"},
                new double[]{2, 1, 15},
                new String[]{"pieces", "pieces", "ml"},
                "1. Dice the potatoes and onion.\n"
                        + "2. Heat oil in a pan.\n"
                        + "3. Fry until tender and golden."
        ));

        recipes.add(createRecipe(
                "Apple Milk Smoothie",
                "A fruit smoothie with milk.",
                new String[]{"apple", "milk"},
                new double[]{1, 200},
                new String[]{"pieces", "ml"},
                "1. Wash and chop the apple.\n"
                        + "2. Blend with milk until smooth."
        ));

        recipes.add(createRecipe(
                "Cheese Sandwich",
                "A simple cheese sandwich.",
                new String[]{"bread", "cheese"},
                new double[]{2, 40},
                new String[]{"slices", "g"},
                "1. Place cheese between bread slices.\n"
                        + "2. Serve cold or toast until the cheese melts."
        ));

        recipes.add(createRecipe(
                "Garlic Butter Pasta",
                "Pasta tossed in garlic butter.",
                new String[]{"pasta", "butter", "garlic"},
                new double[]{200, 30, 2},
                new String[]{"g", "g", "cloves"},
                "1. Cook and drain the pasta.\n"
                        + "2. Melt butter in a pan.\n"
                        + "3. Add crushed garlic.\n"
                        + "4. Toss the pasta in the garlic butter."
        ));

        recipes.add(createRecipe(
                "Carrot Soup",
                "A warm, simple carrot soup.",
                new String[]{"carrot", "onion", "potato"},
                new double[]{3, 1, 1},
                new String[]{"pieces", "pieces", "pieces"},
                "1. Chop the vegetables.\n"
                        + "2. Boil them in water until soft.\n"
                        + "3. Blend until smooth.\n"
                        + "4. Season to taste."
        ));

        return recipes;
    }

    private static Recipe createRecipe(
            String name,
            String description,
            String[] ingredientNames,
            double[] quantities,
            String[] units,
            String instructions) {

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; i < ingredientNames.length; i++) {
            ingredients.add(new Ingredient(
                    ingredientNames[i],
                    quantities[i],
                    units[i],
                    ""
            ));
        }

        return new Recipe(
                name,
                description,
                ingredients,
                instructions
        );
    }
}
