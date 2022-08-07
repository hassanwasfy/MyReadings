package com.wazziimedia.myreadings.adaptermanagement;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wazziimedia.myreadings.R;
import com.wazziimedia.myreadings.objects.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    /**
     * Adapter Extends RecyclerView.Adapter<INNER CLASS TYPE>
     *      force me to implement [1-onCreateViewHolder && 2-onBindViewHolder && 3-getItemCount]
     */


    private ArrayList<Book> books;
    private final OnBookClicked onBookClicked;

    /**
     *  Adapter Constructor
     * @param books the data Entry that we get from db.
     * @param onBookClicked interface listener deals with cardView clicks.
     */
    public BookAdapter(ArrayList<Book> books,OnBookClicked onBookClicked) {
        this.books = books;
        this.onBookClicked = onBookClicked;
    }

    /**
     * Method to get the books.
     * @return ArrayList<Book>
     */
    public ArrayList<Book> getBooks() {
        return books;
    }

    /**
     * Method to set the ArrayList
     * @param books is an ArrayList of this object.
     */
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }


    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_book_layout,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book currentBook = books.get(position);
        holder.cd_book_name.setText(currentBook.getBook_name());
        holder.cd_book_author.setText(currentBook.getBook_author());
        holder.cd_book_percent.setText(currentBook.getBook_progress()+" %");
        holder.cd_book_name.setTag(currentBook.getBook_id());
        holder.cd_book_name.setTag(currentBook.getBook_id());
    }

    @Override
    public int getItemCount() {
        return this.books.size();
    }

    class BookHolder extends RecyclerView.ViewHolder{
        /**
         * INNER CLASS TYPE extends RecyclerView.ViewHolder
         *      force me to implement [constructor matching super]
         */

        private final TextView cd_book_name;
        private final TextView cd_book_author;
        private final TextView cd_book_percent;


        public BookHolder(@NonNull View itemView) {
            super(itemView);

            cd_book_name    = itemView.findViewById(R.id.cd_book_name);
            cd_book_author  = itemView.findViewById(R.id.cd_book_author);
            cd_book_percent = itemView.findViewById(R.id.cd_book_percent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBookClicked.onBookClick((int) cd_book_name.getTag());
                }
            });
            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            //this lambada exactly the same with anonymous listener
            //itemView.setOnClickListener(view -> onBookClicked.onBookClick((int) cd_book_name.getTag()));
        }
    }
}
