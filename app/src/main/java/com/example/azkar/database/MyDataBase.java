package com.example.azkar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.azkar.Moudle.AZKARMoudle;
import com.example.azkar.Moudle.Item;
import com.example.azkar.Moudle.MyAZKARMO;

import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper {
    final static String DB_NAME = "Azkar_db";
    final static int DB_VERSION = 1;

    final static String TABLE_NAME = "Azkar_TB";
    final static String TABLE_ID = "id";
    final static String TABLE_TITLE = "title";
    final static String TABLE_TEXT = "text";
    final static String TABLE_DISCRITION = "discription";
    final static String TABLE_ENDTEXT = "endtext";
    final static String TABLE_COUNT = "count";
    final static String TABLE_COUNT_MINUS = "countminus";
    final static String TABLE_FROM = "fromCloum";

    final static String TABLE_NAME_2 = "Item_TB";
    final static String ITEM_ID = "Item_id";
    final static String ITEM_TITLE = "Item_title";
    final static String ITEM_FAVORITE = "Item_favorite";

    final static String TABLE_NAME_3 = "MyAzkar_TB";
    final static String MyAzkar_ID = "azkar_id";
    final static String MyAzkar_TITLE = "azkar_title";
    final static String MyAzkar_TEXT = "azkar_text";
    final static String MyAzkar_DISCRITION = "azkar_discription";
    final static String MyAzkar_ENDTEXT = "azkar_endtext";
    final static String MyAzkar_COUNT = "azkar_count";
    final static String MyAzkar_COUNT_MINUS = "azkar_countminus";

    public MyDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableAzkar = "CREATE TABLE " + TABLE_NAME + " (" + TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + TABLE_TITLE + " TEXT , " + TABLE_TEXT + " TEXT , " + TABLE_DISCRITION + " TEXT," + TABLE_ENDTEXT + " TEXT," + TABLE_COUNT + " INT," + TABLE_COUNT_MINUS + " INT  ," + TABLE_FROM + " INT)";
        String tableItem = "CREATE TABLE " + TABLE_NAME_2 + " (" + ITEM_ID + " INTEGER PRIMARY KEY , " + ITEM_TITLE + " TEXT , " + ITEM_FAVORITE + " INT )";
        String tabler_MyAzkar = "CREATE TABLE " + TABLE_NAME_3 + " (" + MyAzkar_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + MyAzkar_TITLE + " TEXT , " + MyAzkar_TEXT + " TEXT , " + MyAzkar_DISCRITION + " TEXT," + MyAzkar_ENDTEXT + " TEXT," + MyAzkar_COUNT + " INT," + MyAzkar_COUNT_MINUS + " INT)";

        db.execSQL(tableAzkar);
        db.execSQL(tableItem);
        db.execSQL(tabler_MyAzkar);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2 + "");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3 + "");
        this.onCreate(db);
    }

    ///////////////////////////////////////////////////////////////////////////////
    public boolean INSERT_Zeker(AZKARMoudle car) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_TITLE, car.title);
        values.put(TABLE_TEXT, car.text);
        values.put(TABLE_DISCRITION, car.description);
        values.put(TABLE_ENDTEXT, car.endText);
        values.put(TABLE_COUNT, car.count);
        values.put(TABLE_COUNT_MINUS, car.count);
        values.put(TABLE_FROM, car.From);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public void UPDATE_Count() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String strSQL = "UPDATE " + TABLE_NAME + " SET " + TABLE_COUNT_MINUS + " = " + TABLE_COUNT + "";
        sqLiteDatabase.execSQL(strSQL);
    }

    public boolean UPDATE_CAR(AZKARMoudle car) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_TITLE, car.title);
        values.put(TABLE_TEXT, car.text);
        values.put(TABLE_DISCRITION, car.description);
        values.put(TABLE_ENDTEXT, car.endText);
        values.put(TABLE_COUNT, car.count);
        values.put(TABLE_COUNT_MINUS, car.count);
        values.put(TABLE_FROM, car.From);
        String args[] = {car.id + ""};
        long res = sqLiteDatabase.update(TABLE_NAME, values, "" + TABLE_ID + "=?", args);
        return res > 0;
    }

    public boolean DELETE_ZEKER(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String args[] = {id + ""};
        long res = sqLiteDatabase.delete(TABLE_NAME, "" + TABLE_ID + "=?", args);
        return res != -1;
    }

    public ArrayList<AZKARMoudle> GET_ALL_AZKAR() {
        ArrayList<AZKARMoudle> cars = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + "", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TABLE_ID));
                String Title = cursor.getString(cursor.getColumnIndex(TABLE_TITLE));
                String Text = cursor.getString(cursor.getColumnIndex(TABLE_TEXT));
                String Description = cursor.getString(cursor.getColumnIndex(TABLE_DISCRITION));
                String EndText = cursor.getString(cursor.getColumnIndex(TABLE_ENDTEXT));
                int Count = cursor.getInt(cursor.getColumnIndex(TABLE_COUNT));
                int CountMinus = cursor.getInt(cursor.getColumnIndex(TABLE_COUNT_MINUS));
                int From = cursor.getInt(cursor.getColumnIndex(TABLE_FROM));
                AZKARMoudle car = new AZKARMoudle(id, Title, Text, Description, EndText, CountMinus, Count, From);
                cars.add(car);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }

    public ArrayList<AZKARMoudle> GETAllFromWhere(int from) {
        ArrayList<AZKARMoudle> cars = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String args[] = {from + ""};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_FROM + " = " + from + " ", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TABLE_ID));
                String Title = cursor.getString(cursor.getColumnIndex(TABLE_TITLE));
                String Text = cursor.getString(cursor.getColumnIndex(TABLE_TEXT));
                String Description = cursor.getString(cursor.getColumnIndex(TABLE_DISCRITION));
                String EndText = cursor.getString(cursor.getColumnIndex(TABLE_ENDTEXT));
                int Count = cursor.getInt(cursor.getColumnIndex(TABLE_COUNT));
                int CountMinus = cursor.getInt(cursor.getColumnIndex(TABLE_COUNT_MINUS));
                int From = cursor.getInt(cursor.getColumnIndex(TABLE_FROM));
                AZKARMoudle car = new AZKARMoudle(id, Title, Text, Description, EndText, CountMinus, Count, From);
                cars.add(car);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }

    public AZKARMoudle GET_ZEKER(int carr) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String args[] = {carr + ""};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_ID + "=?", args);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(TABLE_ID));
            String Title = cursor.getString(cursor.getColumnIndex(TABLE_TITLE));
            String Text = cursor.getString(cursor.getColumnIndex(TABLE_TEXT));
            String Description = cursor.getString(cursor.getColumnIndex(TABLE_DISCRITION));
            String EndText = cursor.getString(cursor.getColumnIndex(TABLE_ENDTEXT));
            int Count = cursor.getInt(cursor.getColumnIndex(TABLE_COUNT));
            int CountMinus = cursor.getInt(cursor.getColumnIndex(TABLE_COUNT_MINUS));
            int From = cursor.getInt(cursor.getColumnIndex(TABLE_FROM));
            AZKARMoudle car = new AZKARMoudle(id, Title, Text, Description, EndText, CountMinus, Count, From);
            cursor.close();
            return car;
        }
        return null;
    }

    public ArrayList<AZKARMoudle> SEARCH_ZEKER(String text) {
        ArrayList<AZKARMoudle> cars = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String args[] = {text + "%"};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_TITLE + " LIKE ?", args);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TABLE_ID));
                String Title = cursor.getString(cursor.getColumnIndex(TABLE_TITLE));
                String Text = cursor.getString(cursor.getColumnIndex(TABLE_TEXT));
                String Description = cursor.getString(cursor.getColumnIndex(TABLE_DISCRITION));
                String EndText = cursor.getString(cursor.getColumnIndex(TABLE_ENDTEXT));
                int Count = cursor.getInt(cursor.getColumnIndex(TABLE_COUNT));
                int CountMinus = cursor.getInt(cursor.getColumnIndex(TABLE_COUNT_MINUS));
                int From = cursor.getInt(cursor.getColumnIndex(TABLE_FROM));
                AZKARMoudle car = new AZKARMoudle(id, Title, Text, Description, EndText, CountMinus, Count, From);
                cars.add(car);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }

    ///////////////////////////////////////////////////////////////////////////////
    public boolean INSERT_ITEM(Item item) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_ID, item.id);
        values.put(ITEM_TITLE, item.text);
        values.put(ITEM_FAVORITE, item.favorite);
        long result = sqLiteDatabase.insert(TABLE_NAME_2, null, values);
        return result != -1;
    }

    public void UPDATE_FAVERUITE(int State, int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String strSQL = "UPDATE " + TABLE_NAME_2 + " SET " + ITEM_FAVORITE + " = " + State + " Where " + ITEM_ID + " =  " + id + "";
        sqLiteDatabase.execSQL(strSQL);

    }

    public boolean DELETE_ITEM(Item id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String args[] = {id + ""};
        long res = sqLiteDatabase.delete(TABLE_NAME_2, "" + ITEM_ID + "=?", args);
        return res != -1;
    }

    public ArrayList<Item> GET_ALL_ITEMS() {
        ArrayList<Item> cars = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_2 + "", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ITEM_ID));
                String Title = cursor.getString(cursor.getColumnIndex(ITEM_TITLE));
                int favorite = cursor.getInt(cursor.getColumnIndex(ITEM_FAVORITE));
                Item item = new Item(id, Title, favorite);
                cars.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }

    public ArrayList<Item> GET_ALL_FAVORITE() {
        ArrayList<Item> cars = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE " + ITEM_FAVORITE + " = 1 ", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ITEM_ID));
                String Title = cursor.getString(cursor.getColumnIndex(ITEM_TITLE));
                int favorite = cursor.getInt(cursor.getColumnIndex(ITEM_FAVORITE));
                Item item = new Item(id, Title, favorite);
                cars.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }

    public Item GET_ITEM(int carr) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String args[] = {carr + ""};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE " + ITEM_ID + "=?", args);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(ITEM_ID));
            String Title = cursor.getString(cursor.getColumnIndex(ITEM_TITLE));
            int favorite = cursor.getInt(cursor.getColumnIndex(ITEM_FAVORITE));
            Item item = new Item(id, Title, favorite);
            cursor.close();
            return item;
        }
        return null;
    }

    public ArrayList<Item> SEARCH_ITEM(String text) {
        ArrayList<Item> cars = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String args[] = {"%" + text + "%"};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE " + ITEM_TITLE + " LIKE ?", args);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ITEM_ID));
                String Title = cursor.getString(cursor.getColumnIndex(ITEM_TITLE));
                int favorite = cursor.getInt(cursor.getColumnIndex(ITEM_FAVORITE));
                Item item = new Item(id, Title, favorite);
                cars.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }

    ///////////////////////////////////////////////////////////////////////////////
    public boolean INSERT_My_Azkar(MyAZKARMO car) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyAzkar_TITLE, car.title);
        values.put(MyAzkar_TEXT, car.text);
        values.put(MyAzkar_DISCRITION, car.description);
        values.put(MyAzkar_ENDTEXT, car.endText);
        values.put(MyAzkar_COUNT, car.count);
        values.put(MyAzkar_COUNT_MINUS, car.count);
        long result = sqLiteDatabase.insert(TABLE_NAME_3, null, values);
        return result != -1;
    }

    public void UPDATE_My_Azkar_Count() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String strSQL = "UPDATE " + TABLE_NAME_3 + " SET " + MyAzkar_COUNT_MINUS + " = " + MyAzkar_COUNT + "";
        sqLiteDatabase.execSQL(strSQL);
    }

    public boolean UPDATE_My_Azkar_Object(MyAZKARMO car) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyAzkar_TITLE, car.title);
        values.put(MyAzkar_TEXT, car.text);
        values.put(MyAzkar_DISCRITION, car.description);
        values.put(MyAzkar_ENDTEXT, car.endText);
        values.put(MyAzkar_COUNT, car.count);
        values.put(MyAzkar_COUNT_MINUS, car.count);
        String args[] = {car.id + ""};
        long res = sqLiteDatabase.update(TABLE_NAME_3, values, "" + MyAzkar_ID + "=?", args);
        return res > 0;
    }

    public boolean DELETE_My_Azkar_Object(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String args[] = {id + ""};
        long res = sqLiteDatabase.delete(TABLE_NAME_3, "" + MyAzkar_ID + "=?", args);
        return res != -1;
    }

    public ArrayList<MyAZKARMO> GET_All_My_Azkar() {
        ArrayList<MyAZKARMO> cars = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_3 + "", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MyAzkar_ID));
                String Title = cursor.getString(cursor.getColumnIndex(MyAzkar_TITLE));
                String Text = cursor.getString(cursor.getColumnIndex(MyAzkar_TEXT));
                String Description = cursor.getString(cursor.getColumnIndex(MyAzkar_DISCRITION));
                String EndText = cursor.getString(cursor.getColumnIndex(MyAzkar_ENDTEXT));
                int Count = cursor.getInt(cursor.getColumnIndex(MyAzkar_COUNT));
                int CountMinus = cursor.getInt(cursor.getColumnIndex(MyAzkar_COUNT_MINUS));
                MyAZKARMO car = new MyAZKARMO(id, Title, Text, Description, EndText, CountMinus, Count);
                cars.add(car);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }

    public MyAZKARMO GET_MyAzkar_One_Object(int carr) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String args[] = {carr + ""};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_3 + " WHERE " + MyAzkar_ID + "=?", args);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MyAzkar_ID));
            String Title = cursor.getString(cursor.getColumnIndex(MyAzkar_TITLE));
            String Text = cursor.getString(cursor.getColumnIndex(MyAzkar_TEXT));
            String Description = cursor.getString(cursor.getColumnIndex(MyAzkar_DISCRITION));
            String EndText = cursor.getString(cursor.getColumnIndex(MyAzkar_ENDTEXT));
            int Count = cursor.getInt(cursor.getColumnIndex(MyAzkar_COUNT));
            int CountMinus = cursor.getInt(cursor.getColumnIndex(MyAzkar_COUNT_MINUS));
            MyAZKARMO car = new MyAZKARMO(id, Title, Text, Description, EndText, CountMinus, Count);
            cursor.close();
            return car;
        }
        return null;
    }

    public ArrayList<MyAZKARMO> SEARCH_On_My_Azkar(String text) {
        ArrayList<MyAZKARMO> cars = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String args[] = {text + "%"};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_3 + " WHERE " + MyAzkar_TITLE + " LIKE ?", args);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MyAzkar_ID));
                String Title = cursor.getString(cursor.getColumnIndex(MyAzkar_TITLE));
                String Text = cursor.getString(cursor.getColumnIndex(MyAzkar_TEXT));
                String Description = cursor.getString(cursor.getColumnIndex(MyAzkar_DISCRITION));
                String EndText = cursor.getString(cursor.getColumnIndex(MyAzkar_ENDTEXT));
                int Count = cursor.getInt(cursor.getColumnIndex(MyAzkar_COUNT));
                int CountMinus = cursor.getInt(cursor.getColumnIndex(MyAzkar_COUNT_MINUS));
                MyAZKARMO car = new MyAZKARMO(id, Title, Text, Description, EndText, CountMinus, Count);
                cars.add(car);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }
}
