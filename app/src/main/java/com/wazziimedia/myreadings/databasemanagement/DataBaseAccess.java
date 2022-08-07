package com.wazziimedia.myreadings.databasemanagement;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wazziimedia.myreadings.objects.Book;

import java.util.ArrayList;

public class DataBaseAccess {

    private SQLiteDatabase sqLiteDatabase;
    private final DataBase dataBase;

    /**
     * Getting Access with the singleton object.
     * @param context the context managing the access
     */
    public DataBaseAccess(Context context){
        dataBase = DataBase.getInstance(context);
    }

    /**
     * To insert A Book in the db.
     * @param book to be inserted.
     * @return boolean to insure that process is done.
     */
    public boolean insertBook(Book book){
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBase.C_BOOK_NAME,book.getBook_name());
        contentValues.put(DataBase.C_BOOK_AUTHOR,book.getBook_author());
        contentValues.put(DataBase.C_BOOK_PAGES,book.getBook_pages());
        contentValues.put(DataBase.C_BOOK_FINISHED,book.getBook_finished());
        long result = sqLiteDatabase.insert(DataBase.TABLE_NAME,null,contentValues);
        close();
        return result != -1;
    }

    /**
     * To update A Book in the db.
     * @param book to be updated.
     * @return boolean to calc affected objects.
     */
    public boolean updateBook(Book book){
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBase.C_BOOK_NAME,book.getBook_name());
        contentValues.put(DataBase.C_BOOK_AUTHOR,book.getBook_author());
        contentValues.put(DataBase.C_BOOK_PAGES,book.getBook_pages());
        contentValues.put(DataBase.C_BOOK_FINISHED,book.getBook_finished());
        String whereClause = "id=?";
        String[] whereArgs = {book.getBook_id()+""};
        long result = sqLiteDatabase.update(DataBase.TABLE_NAME,contentValues,whereClause,whereArgs);
        close();
        return result > 0;
    }


    /**
     * To delete A Book from the db.
     * @param book to be deleted.
     * @return boolean to insure that process is done.
     */
    public boolean deleteBook(Book book){
        open();
        String whereClause = "id=?";
        String[] whereArgs = {book.getBook_id()+""};
        int result = sqLiteDatabase.delete(DataBase.TABLE_NAME,whereClause,whereArgs);
        close();
        return result > 0;
    }

    /**
     * Connection To get a Single Book.
     * @param bookID put the id of the book you want to get.
     * @return Book an object of The Book class.
     */
    @SuppressLint("Range")
    public Book getBook(int bookID){
        open();
        Book book;
        String sql = "SELECT * FROM "+DataBase.TABLE_NAME+" WHERE "+DataBase.C_BOOK_ID+"=?";
        String[] args = {bookID+""};
        Cursor cursor = sqLiteDatabase.rawQuery(sql,args);
        if(cursor != null && cursor.moveToFirst() ){
            int id = cursor.getInt(cursor.getColumnIndex(DataBase.C_BOOK_ID));
            String name = cursor.getString(cursor.getColumnIndex(DataBase.C_BOOK_NAME));
            String author = cursor.getString(cursor.getColumnIndex(DataBase.C_BOOK_AUTHOR));;
            int pages = cursor.getInt(cursor.getColumnIndex(DataBase.C_BOOK_PAGES));
            int finished = cursor.getInt(cursor.getColumnIndex(DataBase.C_BOOK_FINISHED));
            int progress = cursor.getInt(cursor.getColumnIndex(DataBase.C_BOOK_PROGRESS));
            book = new Book(id,name,author,pages,finished,progress);
            cursor.close();
            close();
            return book;
        }
        else {
            return null;
        }
    }

    /**
     * Get All db records to update the recycler view.
     * @return ArrayList<Book> contains full db records
     */
    @SuppressLint("Range")
    public ArrayList<Book> getAllBooks(){
        open();
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM "+DataBase.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if(cursor != null && cursor.moveToFirst() ){
            cursor.moveToFirst();
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DataBase.C_BOOK_ID));
                String name = cursor.getString(cursor.getColumnIndex(DataBase.C_BOOK_NAME));
                String author = cursor.getString(cursor.getColumnIndex(DataBase.C_BOOK_AUTHOR));;
                int pages = cursor.getInt(cursor.getColumnIndex(DataBase.C_BOOK_PAGES));
                int finished = cursor.getInt(cursor.getColumnIndex(DataBase.C_BOOK_FINISHED));
                int progress = cursor.getInt(cursor.getColumnIndex(DataBase.C_BOOK_PROGRESS));
                Book book = new Book(id,name,author,pages,finished,progress);
                books.add(book);
            }while ( cursor.moveToNext());
        }
        assert cursor != null;
        cursor.close();
        close();
        return books;
    }


    /**
     * void method to open data base
     */
    public void open(){
        this.sqLiteDatabase = dataBase.getWritableDatabase();
    }

    /**
     * void method to close data base
     */
    public void close(){
        if(dataBase != null){
            this.dataBase.close();
        }
    }
}
