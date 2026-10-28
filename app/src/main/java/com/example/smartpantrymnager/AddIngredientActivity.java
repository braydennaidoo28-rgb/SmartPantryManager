
package com.example.smartpantrymnager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddIngredientActivity extends AppCompatActivity {

    private EditText editIngredientName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiryDate;

    private Button btnSaveIngredient;
    private Button btnCancel;

    private boolean editMode = false;
    private String originalName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        editIngredientName = findViewById(R.id.editIngredientName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiryDate = findViewById(R.id.editExpiryDate);

        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);
        btnCancel = findViewById(R.id.btnCancelIngredient);

        editMode = getIntent().getBooleanExtra("editMode", false);

        if (editMode) {
            originalName = getIntent().getStringExtra("ingredientName");

            editIngredientName.setText(originalName);

            editQuantity.setText(String.valueOf(
                    getIntent().getDoubleExtra(
                            "ingredientQuantity", 0)));

            editUnit.setText(
                    getIntent().getStringExtra("ingredientUnit"));

            editExpiryDate.setText(
                    getIntent().getStringExtra("ingredientExpiry"));
        }

        btnSaveIngredient.setOnClickListener(v -> saveIngredient());

        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveIngredient() {

        String name = editIngredientName.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiryDate = editExpiryDate.getText().toString().trim();

        if (name.isEmpty() || quantityText.isEmpty() || unit.isEmpty()) {
            Toast.makeText(
                    this,
                    "Please enter the ingredient name, quantity and unit.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            editQuantity.setError("Enter a valid quantity.");
            return;
        }

        if (quantity <= 0) {
            editQuantity.setError("Quantity must be greater than zero.");
            return;
        }

        Ingredient ingredient = new Ingredient(
                name,
                quantity,
                unit,
                expiryDate
        );

        boolean success;

        if (editMode) {
            success = PantryStorage.updateIngredient(
                    this,
                    originalName,
                    ingredient
            );
        } else {
            success = PantryStorage.addIngredient(
                    this,
                    ingredient
            );
        }

        if (success) {
            Toast.makeText(
                    this,
                    editMode
                            ? "Ingredient updated successfully."
                            : "Ingredient saved successfully.",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "Could not save the ingredient.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
