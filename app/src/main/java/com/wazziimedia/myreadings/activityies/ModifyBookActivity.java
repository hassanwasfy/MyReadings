package com.wazziimedia.myreadings.activityies;

import static com.wazziimedia.myreadings.constatnts.Constants.BOOK_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.wazziimedia.myreadings.objects.Book;
import com.wazziimedia.myreadings.R;
import com.wazziimedia.myreadings.constatnts.Constants;
import com.wazziimedia.myreadings.databasemanagement.DataBaseAccess;

public class ModifyBookActivity extends AppCompatActivity{


    private DataBaseAccess dataBaseAccess;
    private EditText enterBookName, enterBookAuthor, enterBookPages, enterBookFinished;
    private MenuItem save,edit,delete,update;
    private int sentBook;
    private Book currentBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_book);

        enterBookName = findViewById(R.id.modify_book_name);
        enterBookAuthor = findViewById(R.id.modify_book_author);
        enterBookPages = findViewById(R.id.modify_book_pages);
        enterBookFinished = findViewById(R.id.modify_book_finished);

        lockFields();

        Intent intent = getIntent();
        sentBook = intent.getIntExtra(BOOK_ID_KEY,-1);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(ModifyBookActivity.this,permission, Constants.WRITING_DATA_PERMISSION);
        }
        dataBaseAccess = new DataBaseAccess(getBaseContext());
        currentBook = dataBaseAccess.getBook(sentBook);
        enterBookName.setText(currentBook.getBook_name());
        enterBookAuthor.setText(currentBook.getBook_author());
        enterBookPages.setText(currentBook.getBook_pages()+"");
        enterBookFinished.setText(currentBook.getBook_finished()+"");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.the_menu_bar,menu);
        save = menu.findItem(R.id.book_menu_save);
        edit = menu.findItem(R.id.book_menu_edit);
        delete = menu.findItem(R.id.book_menu_delete);
        update = menu.findItem(R.id.book_menu_update);

        save.setVisible(false);
        edit.setVisible(true);
        delete.setVisible(false);
        update.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item == update){
            if(enterBookName.getText().toString().isEmpty() || enterBookAuthor.getText().toString().isEmpty() || enterBookPages.getText().toString().isEmpty() || enterBookFinished.getText().toString().isEmpty()){
                Toast.makeText(getBaseContext(), "Fill all Data!", Toast.LENGTH_SHORT).show();
            }else {

                String name = enterBookName.getText().toString();
                String author = enterBookAuthor.getText().toString();
                int pages = Integer.parseInt(enterBookPages.getText().toString());
                int finished = Integer.parseInt(enterBookFinished.getText().toString());

                Book book = new Book(name,author,pages,finished);
                book.setBook_id(sentBook);
                if(finished <= pages){
                    boolean result = dataBaseAccess.updateBook(book);
                    setResult(RESULT_OK);
                    if (result){
                        Toast.makeText(getBaseContext(), "Edited!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    Toast.makeText(getBaseContext(), "Finished Exceeded Limit!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(item == edit){
            unLockFields();
            save.setVisible(false);
            edit.setVisible(false);
            delete.setVisible(true);
            update.setVisible(true);
        }
        if(item == delete){
            boolean result = dataBaseAccess.deleteBook(currentBook);
            if (result){
                Toast.makeText(getBaseContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        return true;
    }


    private void lockFields(){
        enterBookName.setEnabled(false);
        enterBookAuthor.setEnabled(false);
        enterBookPages.setEnabled(false);
        enterBookFinished.setEnabled(false);
    }

    private void unLockFields(){
        enterBookName.setEnabled(true);
        enterBookAuthor.setEnabled(true);
        enterBookPages.setEnabled(true);
        enterBookFinished.setEnabled(true);
    }

}