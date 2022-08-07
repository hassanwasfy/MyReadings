package com.wazziimedia.myreadings.activityies;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.wazziimedia.myreadings.objects.Book;
import com.wazziimedia.myreadings.R;
import com.wazziimedia.myreadings.constatnts.Constants;
import com.wazziimedia.myreadings.databasemanagement.DataBaseAccess;

public class AddingBookActivity extends AppCompatActivity {

    private DataBaseAccess dataBaseAccess;
    private EditText enterBookName, enterBookAuthor, enterBookPages, enterBookFinished;
    private MenuItem save,edit,delete,update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_book);

        enterBookName = findViewById(R.id.enter_book_name);
        enterBookAuthor = findViewById(R.id.enter_book_author);
        enterBookPages = findViewById(R.id.enter_book_pages);
        enterBookFinished = findViewById(R.id.enter_book_finished);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(AddingBookActivity.this,permission, Constants.WRITING_DATA_PERMISSION);
        }
        dataBaseAccess = new DataBaseAccess(getBaseContext());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.WRITING_DATA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getBaseContext(), "Database Access Done!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.the_menu_bar,menu);
        save = menu.findItem(R.id.book_menu_save);
        edit = menu.findItem(R.id.book_menu_edit);
        delete = menu.findItem(R.id.book_menu_delete);
        update = menu.findItem(R.id.book_menu_update);

        save.setVisible(true);
        edit.setVisible(false);
        delete.setVisible(false);
        update.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item == save){
            if(enterBookName.getText().toString().isEmpty() || enterBookAuthor.getText().toString().isEmpty() || enterBookPages.getText().toString().isEmpty() || enterBookFinished.getText().toString().isEmpty()){
                Toast.makeText(getBaseContext(), "Fill all Data!", Toast.LENGTH_SHORT).show();
            }else {
                String name = enterBookName.getText().toString();
                String author = enterBookAuthor.getText().toString();
                int pages = Integer.parseInt(enterBookPages.getText().toString());
                int finished = Integer.parseInt(enterBookFinished.getText().toString());
                Book book = new Book(name,author,pages,finished);
                if(finished <= pages){
                    boolean result = dataBaseAccess.insertBook(book);
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
        return true;
    }

}