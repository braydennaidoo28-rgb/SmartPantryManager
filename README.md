
# Smart Pantry Manager

## 1. Project Overview

Smart Pantry Manager is an Android application developed in Java using Android Studio. It helps users manage pantry ingredients and discover recipes based on the ingredients they have available.

Users can add, view, edit, and delete pantry ingredients. The application compares pantry ingredients against a collection of 15 predefined recipes and suggests recipes only when all required ingredients are available in sufficient quantities.

## 2. Features

- **Pantry Management:** Add ingredients with a name, quantity, unit, and optional expiry date.
- **View Ingredients:** Display saved pantry ingredients in a list.
- **Edit Ingredients:** Update existing ingredient information.
- **Delete Ingredients:** Remove ingredients from the pantry.
- **Recipe Suggestions:** Find recipes based on available pantry ingredients.
- **Strict Recipe Matching:** Exclude recipes when ingredients are missing or quantities are insufficient.
- **Ingredient Name Matching:** Handle common singular and plural differences, such as `egg` and `eggs`.
- **Unit Conversion:** Convert litres to millilitres and kilograms to grams where supported.
- **Recipe Details:** View recipe descriptions, required ingredients, quantities, and preparation instructions.
- **Settings:** View pantry information, read about the application, and clear pantry ingredients.
- **Persistent Local Storage:** Store pantry ingredients and recipe data in an SQLite database on the device.

## 3. Technologies Used

- Java
- Android Studio
- Android SDK
- XML layouts
- SQLite and `SQLiteOpenHelper`
- RecyclerView and a custom pantry adapter
- ListView and ArrayAdapter
- Android Intents for navigation between activities
- Git and GitHub for version control

## 4. Application Screens

The application includes the following screens:

1. **Main Pantry Screen:** Displays saved pantry ingredients and provides navigation to other features.
2. **Add Ingredient Screen:** Allows users to enter and save ingredient details.
3. **Suggested Recipes Screen:** Displays recipes that meet the ingredient-matching requirements.
4. **Recipe Details Screen:** Displays the recipe description, full ingredient list, quantities, and preparation instructions.
5. **Settings Screen:** Displays pantry information and provides application settings.

## 5. Recipe Matching

The application compares each recipe's required ingredients with the ingredients saved in the pantry.

For a recipe to be suggested:

1. Every required ingredient must be available.
2. The available quantity must meet or exceed the required quantity.
3. Compatible units are converted where supported.
4. Common singular and plural ingredient names are normalised.
5. Recipes with missing ingredients or insufficient quantities are excluded.

For example, the Pancakes recipe requires:

- 200 g flour
- 250 ml milk
- 2 pieces egg

The recipe is suggested only when all three ingredient requirements are satisfied. The matcher also recognises common variations such as `egg` and `eggs`.

## 6. Database Design and Storage

The application uses SQLite through Android's `SQLiteOpenHelper` class. SQLite was selected because it provides local relational data storage without requiring a separate database server or an internet connection.

The database is named `SmartPantry.db` and contains three tables:

- **pantry:** Stores ingredient names, quantities, units, and optional expiry dates.
- **recipes:** Stores recipe names, descriptions, and cooking instructions.
- **recipe_ingredients:** Stores the ingredients and required quantities associated with each recipe.

The `recipe_ingredients` table links to the `recipes` table using a recipe ID. This relationship allows a recipe to contain multiple required ingredients.

The application seeds its initial collection of 15 recipes into the database. Pantry information is stored locally and remains available after the application is closed and reopened.

## 7. How to Run the Project

1. Install Android Studio.
2. Clone or download this public GitHub repository.
3. Open the project folder in Android Studio.
4. Allow Gradle to sync and download the required dependencies.
5. Connect an Android device with USB debugging enabled or start an Android emulator.
6. Build and run the application.

The application's package name is:

`com.example.smartpantrymnager`

The SQLite database is created and managed by the application. No separate database server is required.

## 8. Testing

The following functionality has been tested:

- Adding, editing, and deleting pantry ingredients.
- Clearing pantry ingredients through Settings.
- Saving and loading pantry data after closing and reopening the app.
- Loading recipes and required ingredients from SQLite.
- Suggesting recipes when all required ingredients are available.
- Excluding recipes when an ingredient is missing.
- Excluding recipes when quantities are insufficient.
- Matching common singular and plural ingredient names.
- Converting litres to millilitres and kilograms to grams.
- Opening recipe details and displaying ingredients and cooking instructions.

## 9. Future Improvements

Potential improvements include:

- Ingredient expiry reminders.
- Recipe search and filtering.
- Additional recipes.
- Improved ingredient selection and unit validation.
- Recipe images and nutritional information.
- Optional cloud backup and synchronisation.

## 10. Author

Developed as a Mobile Application Development project using Java, Android Studio, and SQLite.
