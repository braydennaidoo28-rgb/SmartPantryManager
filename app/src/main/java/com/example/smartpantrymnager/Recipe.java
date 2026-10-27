
package com.example.smartpantrymnager;

import java.util.ArrayList;

public class Recipe {

    private String name;
    private String description;
    private ArrayList<Ingredient> requiredIngredients;
    private String instructions;

    public Recipe(String name, String description,
                  ArrayList<Ingredient> requiredIngredients,
                  String instructions) {

        this.name = name;
        this.description = description;
        this.requiredIngredients = requiredIngredients;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Ingredient> getRequiredIngredients() {
        return requiredIngredients;
    }

    public String getInstructions() {
        return instructions;
    }
}


