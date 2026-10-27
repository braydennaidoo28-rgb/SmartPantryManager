
package com.example.smartpantrymnager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddIngredientActivity extends AppCompatActivity {

    private EditText editIngredientName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        editIngredientName = findViewById(R.id.editIngredientName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiryDate = findViewById(R.id.editExpiryDate);

        Button btnSaveIngredient = findViewById(R.id.btnSaveIngredient);
        Button btnCancelIngredient = findViewById(R.id.btnCancelIngredient);

        btnSaveIngredient.setOnClickListener(v -> saveIngredient());

        btnCancelIngredient.setOnClickListener(v -> finish());
    }

    private void saveIngredient() {

        String name = editIngredientName.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiryDate = editExpiryDate.getText().toString().trim();

        if (name.isEmpty() || quantityText.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this,
                    "Please fill in the ingredient name, quantity and unit.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Enter a valid quantity.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (quantity <= 0) {
            Toast.makeText(this,
                    "Quantity must be greater than zero.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Ingredient> ingredients =
                PantryStorage.loadIngredients(this);

        Ingredient ingredient = new Ingredient(
                name, quantity, unit, expiryDate);

        ingredients.add(ingredient);

        PantryStorage.saveIngredients(this, ingredients);

        Toast.makeText(this,
                name + " saved successfully!",
                Toast.LENGTH_SHORT).show();

        finish();
    }
}
