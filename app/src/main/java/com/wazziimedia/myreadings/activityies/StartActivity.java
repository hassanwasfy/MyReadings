package com.wazziimedia.myreadings.activityies;

import static com.wazziimedia.myreadings.constatnts.Constants.ADDING_BOOK_REQUEST_CODE;
import static com.wazziimedia.myreadings.constatnts.Constants.BOOK_ID_KEY;
import static com.wazziimedia.myreadings.constatnts.Constants.EDITING_BOOK_REQUEST_CODE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wazziimedia.myreadings.objects.Book;
import com.wazziimedia.myreadings.adaptermanagement.BookAdapter;
import com.wazziimedia.myreadings.adaptermanagement.OnBookClicked;
import com.wazziimedia.myreadings.R;
import com.wazziimedia.myreadings.databasemanagement.DataBaseAccess;

import java.util.ArrayList;

/**
 * @author Hassan Wasfy
 * @{@link https://www.github.com/hassanwasfy}
 * @version 1.0
 * */

public class StartActivity extends AppCompatActivity {


    private FloatingActionButton btn_fab_add;
    private BookAdapter bookAdapter;
    private DataBaseAccess dataBaseAccess;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        dataBaseAccess = new DataBaseAccess(getBaseContext());
        ArrayList<Book> books = dataBaseAccess.getAllBooks();
        RecyclerView books_recycler = findViewById(R.id.books_recycler);
        btn_fab_add = findViewById(R.id.btn_fab_add);
        bookAdapter = new BookAdapter(books, new OnBookClicked() {
            @Override
            public void onBookClick(int bookID) {
                Intent intent = new Intent(getBaseContext(),ModifyBookActivity.class);
                intent.putExtra(BOOK_ID_KEY,bookID);
                startActivityForResult(intent,EDITING_BOOK_REQUEST_CODE);
            }
        });
        books_recycler.setHasFixedSize(true);
        books_recycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        books_recycler.setAdapter(bookAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onStart() {
        super.onStart();
        btn_fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),AddingBookActivity.class);
                startActivityForResult(i,ADDING_BOOK_REQUEST_CODE);
            }
        });
        bookAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == ADDING_BOOK_REQUEST_CODE || requestCode == EDITING_BOOK_REQUEST_CODE){
                ArrayList<Book> books = dataBaseAccess.getAllBooks();
                bookAdapter.setBooks(books);
                bookAdapter.notifyDataSetChanged();
            }
        }
    }


}