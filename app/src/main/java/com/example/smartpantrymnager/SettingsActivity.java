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

        btnClearPantry = findViewById(R.id.btnClearPantry);
        btnAboutApp = findViewById(R.id.btnAboutApp);
        btnBackToPantry = findViewById(R.id.btnBackToPantry);
        txtSettingsInfo = findViewById(R.id.txtSettingsInfo);

        updatePantryInfo();

        // Clear pantry with confirmation
        btnClearPantry.setOnClickListener(v ->
                showClearPantryConfirmation()
        );

        // Display information about the app
        btnAboutApp.setOnClickListener(v ->
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle("About Smart Pantry Manager")
                        .setMessage(
                                "Smart Pantry Manager helps you manage "
                                        + "your pantry ingredients and find "
                                        + "recipes based on what you have."
                        )
                        .setPositiveButton("OK", null)
                        .show()
        );

        // Return to the previous screen
        btnBackToPantry.setOnClickListener(v -> finish());
    }

    private void updatePantryInfo() {
        ArrayList<Ingredient> ingredients =
                PantryStorage.loadIngredients(this);

        txtSettingsInfo.setText(
                "Smart Pantry Manager\n\n"
                        + "Ingredients currently saved: "
                        + ingredients.size()
                        + "\n\nYour pantry data is stored locally "
                        + "on this device."
        );
    }

    private void showClearPantryConfirmation() {
        new AlertDialog.Builder(SettingsActivity.this)
                .setTitle("Clear pantry?")
                .setMessage(
                        "This will permanently delete all saved pantry "
                                + "ingredients. Your recipe collection "
                                + "will not be deleted."
                )
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Clear pantry", (dialog, which) -> {

                    PantryStorage.saveIngredients(
                            SettingsActivity.this,
                            new ArrayList<>()
                    );

                    updatePantryInfo();

                    Toast.makeText(
                            SettingsActivity.this,
                            "Pantry cleared successfully.",
                            Toast.LENGTH_SHORT
                    ).show();
                })
                .show();
    }
}