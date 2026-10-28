# Smart Pantry Manager

## 1. Project Overview

Smart Pantry Manager is an Android application developed in Java that helps users manage pantry ingredients and discover recipes based on the ingredients they have available.

The application allows users to add, view, edit, and delete pantry ingredients. It also compares available ingredients with predefined recipes and suggests recipes only when all required ingredients are available in sufficient quantities.

## 2. Features

- **Pantry Management:** Add ingredients with a name, quantity, unit, and optional expiry date.
- **View Ingredients:** Display saved pantry ingredients in a list.
- **Edit Ingredients:** Update existing ingredient information.
- **Delete Ingredients:** Remove ingredients from the pantry.
- **Recipe Suggestions:** Find recipes based on available pantry ingredients.
- **Strict Recipe Matching:** Only suggest recipes when every required ingredient is available in a sufficient quantity.
- **Unit Conversion:** Support conversions between litres and millilitres and between kilograms and grams.
- **Recipe Details:** View a recipe's description and preparation instructions.
- **Settings / Profile:** View pantry information, read about the application, and clear saved pantry ingredients.
- **Local Data Storage:** Save pantry ingredients on the device using SharedPreferences and JSON.

## 3. Technologies Used

- Java
- Android Studio
- XML layouts
- Android SDK
- RecyclerView
- ListView and ArrayAdapter
- Intents for navigation between activities
- SharedPreferences and JSON for local storage
- Git and GitHub for version control

## 4. Application Screens

The application includes the following screens:

1. **Main Pantry Screen:** Displays pantry ingredients and provides navigation to other features.
2. **Add Ingredient Screen:** Allows users to enter and save ingredient details.
3. **Suggested Recipes Screen:** Displays recipes that meet the strict ingredient-matching requirements.
4. **Recipe Details Screen:** Displays recipe descriptions and preparation instructions.
5. **Settings / Profile Screen:** Displays pantry information and provides application settings.

## 5. Recipe Matching

The application uses a recipe-matching algorithm to compare the ingredients required by each recipe with the ingredients saved in the pantry.

For a recipe to be suggested:

1. Every required ingredient must exist in the pantry.
2. The available quantity must meet or exceed the recipe's required quantity.
3. Compatible units are converted where supported.
4. Recipes with missing ingredients or insufficient quantities are excluded.

For example, the Pancakes recipe requires 200 g of flour, 250 ml of milk, and 2 pieces of egg. The recipe is suggested only when all three requirements are satisfied.

## 6. Data Storage

Pantry ingredients are stored locally on the device using Android SharedPreferences with JSON serialization. This allows ingredient information to remain available when the application is closed and reopened on the same device.

The predefined recipe collection is provided by the application's recipe storage class.

## 7. How to Run the Project

1. Install Android Studio.
2. Clone or download this repository.
3. Open the project folder in Android Studio.
4. Allow Gradle to sync and download the required dependencies.
5. Connect an Android device with USB debugging enabled, or start an Android emulator.
6. Build and run the application.

The application's package name is `com.example.smartpantrymnager`.

## 8. Testing

The following functionality has been tested:

- Adding, editing, and deleting pantry ingredients.
- Saving and loading pantry information.
- Suggesting recipes when all required ingredients are available.
- Excluding recipes when a required ingredient is missing.
- Excluding recipes when ingredient quantities are insufficient.
- Converting compatible measurement units.
- Opening recipe details.
- Accessing the Settings / Profile screen.

## 9. Future Improvements

Potential improvements include:

- Ingredient expiry reminders.
- Recipe search and filtering.
- More predefined recipes.
- Improved ingredient selection and unit validation.
- Recipe images and nutritional information.
- Optional cloud backup and synchronisation.

## 10. Author

Developed as an Android application project using Java and Android Studio.
