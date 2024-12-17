package com.example.appproject.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appproject.Domain.CategoryDomain;
import com.example.appproject.Domain.FoodDomain;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "food.db";
    private static final int DATABASE_VERSION = 3;

    // Table and column names for Categories
    private static final String TABLE_CATEGORIES = "Categories";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IMAGE = "image";

    // Table and column names for Foods
    private static final String TABLE_FOODS = "Foods";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";

    // Table and column names for Users
    private static final String TABLE_USERS = "Users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";


    private static final String TABLE_CART = "Cart";
    private static final String COLUMN_USER_ID_FK = "user_id";
    private static final String COLUMN_FOOD_ID_FK = "food_id";
    private static final String COLUMN_QUANTITY = "quantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Categories table
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_IMAGE + " TEXT NOT NULL)";
        db.execSQL(CREATE_CATEGORIES_TABLE);

        // Create Foods table
        String CREATE_FOODS_TABLE = "CREATE TABLE " + TABLE_FOODS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT NOT NULL, "
                + COLUMN_IMAGE + " TEXT NOT NULL, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_PRICE + " REAL NOT NULL)";
        db.execSQL(CREATE_FOODS_TABLE);

        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, "
                + COLUMN_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + COLUMN_USER_ID_FK + " INTEGER, "
                + COLUMN_FOOD_ID_FK + " INTEGER, "
                + COLUMN_QUANTITY + " INTEGER, "
                + "PRIMARY KEY(" + COLUMN_USER_ID_FK + ", " + COLUMN_FOOD_ID_FK + "), "
                + "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "), "
                + "FOREIGN KEY(" + COLUMN_FOOD_ID_FK + ") REFERENCES " + TABLE_FOODS + "(" + COLUMN_ID + "))";
        db.execSQL(CREATE_CART_TABLE);


        // Insert initial data
        insertInitialCategories(db);
        insertInitialFoods(db);
        insertHardcodedUsers(db);
    }

    private void insertInitialCategories(SQLiteDatabase db) {
        insertCategory(db, "Pizza", "cat_1");
        insertCategory(db, "Burger", "cat_2");
        insertCategory(db, "Hotdog", "cat_3");
        insertCategory(db, "Drink", "cat_4");
        insertCategory(db, "Donut", "cat_5");
    }

    private void insertInitialFoods(SQLiteDatabase db) {
        insertFood(db, "Pepperoni Pizza", "pop_1", "Dough, tomato sauce, mozzarella cheese, pepperoni, mushrooms, onions, bell peppers, olives, oregano, basil, and olive oil.", 9.76);
        insertFood(db, "Cheese Burger", "pop_2", "Juicy beef patty, cheese, lettuce, tomato, onion, and special sauce on a sesame bun.", 8.50);
        insertFood(db, "Vegetable Pizza", "pop_3", "Dough, tomato sauce, mozzarella cheese, mushrooms, onions, bell peppers, olives, oregano, and basil.", 7.99);
    }

    private void insertHardcodedUsers(SQLiteDatabase db) {
        insertUser(db, "ghassen", "ghassen1234");
        insertUser(db, "aziz", "aziz1234");
        insertUser(db, "omar", "omar1234");
    }

    private void insertCategory(SQLiteDatabase db, String name, String image) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_IMAGE, image);
        db.insert(TABLE_CATEGORIES, null, values);
    }

    private void insertFood(SQLiteDatabase db, String title, String image, String description, double price) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE, price);
        db.insert(TABLE_FOODS, null, values);
    }

    private void insertUser(SQLiteDatabase db, String username, String password) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    // Validate user login
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null,
                COLUMN_USERNAME + " =? AND " + COLUMN_PASSWORD + " =?",
                new String[]{username, password}, null, null, null);

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;

    }

    // Retrieve all food items
    public ArrayList<FoodDomain> getAllFoods() {
        ArrayList<FoodDomain> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FOODS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));

                foodList.add(new FoodDomain(title, image, description, price));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return foodList;
    }

    // Retrieve all categories
    public ArrayList<CategoryDomain> getAllCategories() {
        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORIES, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                categoryList.add(new CategoryDomain(name, image));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return categoryList;
    }
}
