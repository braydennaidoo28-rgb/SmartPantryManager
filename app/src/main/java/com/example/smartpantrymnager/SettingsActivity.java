package com.example.smartpantrymnager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private Button btnClearPantry;
    private Button btnAboutApp;
    private Button btnBackToPantry;
    private TextView txtSettingsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Connect Java variables to the XML views
        btnClearPantry = findViewById(R.id.btnClearPantry);
        btnAboutApp = findViewById(R.id.btnAboutApp);
        btnBackToPantry = findViewById(R.id.btnBackToPantry);
        txtSettingsInfo = findViewById(R.id.txtSettingsInfo);

        // Display pantry information
        updateSettingsInfo();

        // Clear Pantry button
        btnClearPantry.setOnClickListener(v -> showClearPantryDialog());

        // About App button
        btnAboutApp.setOnClickListener(v -> showAboutDialog());

        // Back button
        btnBackToPantry.setOnClickListener(v -> finish());
    }

    private void updateSettingsInfo() {

        ArrayList<Ingredient> ingredients =
                PantryStorage.loadIngredients(this);

        int ingredientCount = ingredients == null
                ? 0
                : ingredients.size();

        txtSettingsInfo.setText(
                "Smart Pantry Manager\n\n" +
                        "Manage your pantry ingredients and discover recipes.\n\n" +
                        "Ingredients currently stored: " + ingredientCount
        );
    }

    private void showClearPantryDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Clear Pantry")
                .setMessage(
                        "Are you sure you want to delete all pantry ingredients? " +
                                "This action cannot be undone."
                )
                .setPositiveButton("Clear", (dialog, which) -> {

                    // Delete pantry records from SQLite
                    PantryStorage.clearIngredients(
                            SettingsActivity.this
                    );

                    Toast.makeText(
                            SettingsActivity.this,
                            "Pantry cleared successfully.",
                            Toast.LENGTH_SHORT
                    ).show();

                    // Refresh the settings information
                    updateSettingsInfo();
                })
                .setNegativeButton("Cancel", (dialog, which) ->
                        dialog.dismiss())
                .show();
    }

    private void showAboutDialog() {

        new AlertDialog.Builder(this)
                .setTitle("About Smart Pantry Manager")
                .setMessage(
                        "Smart Pantry Manager\n\n" +
                                "This application helps you manage pantry ingredients, " +
                                "track quantities, and find recipes based on the ingredients " +
                                "you have available.\n\n" +
                                "Your pantry data is stored locally using SQLite."
                )
                .setPositiveButton("OK", (dialog, which) ->
                        dialog.dismiss())
                .show();
    }
}