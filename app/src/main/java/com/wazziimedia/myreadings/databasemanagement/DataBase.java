package com.wazziimedia.myreadings.databasemanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    //DataBase ref
    private static DataBase dataBase;

    //Data BaseName + Version:
    private static final String DATABASE_NAME = "books_db";
    private static final int DATABASE_VERSION = 1;

    //Tables :
    public static final String TABLE_NAME = "book_table";

    //Tables Columns :
    public static final String C_BOOK_ID = "id";
    public static final String C_BOOK_NAME = "name";
    public static final String C_BOOK_AUTHOR = "author";
    public static final String C_BOOK_PAGES = "pages";
    public static final String C_BOOK_FINISHED = "finished";
    public static final String C_BOOK_PROGRESS = "progress";


    /**
     * Private Constructor Matching Super
     * @param context takes the current base context.
     * */
    private DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Singleton Pattern Object Creation.
     * @param context takes the current base context.
     * @return Database object.
     * */
    public static DataBase getInstance(Context context){
        if(dataBase == null){
            dataBase = new DataBase(context);
        }
        return dataBase;
    }

    /**
     * OnCreate Method that runs one time to Create the data base.
     * @param sqLiteDatabase takes sql ref.
     * */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query ="CREATE TABLE "+TABLE_NAME+" ("
                +C_BOOK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +C_BOOK_NAME+" TEXT UNIQUE, "
                +C_BOOK_AUTHOR+" TEXT, "
                +C_BOOK_PAGES+" INTEGER, "
                +C_BOOK_FINISHED+" INTEGER, "
                +C_BOOK_PROGRESS+" INTEGER )";
        sqLiteDatabase.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
