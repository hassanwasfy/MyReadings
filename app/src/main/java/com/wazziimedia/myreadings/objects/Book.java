package com.wazziimedia.myreadings.objects;

import com.wazziimedia.myreadings.constatnts.Constants;

public class Book {
    private int book_id;
    private String book_name;
    private String book_author;
    private int book_pages;
    private int book_finished;
    private int book_progress;

    /**
     * Empty default constructor.
     */
    public Book() {
    }

    /**
     * Constructor to Enter a new book record to the data base.
     * @param book_name String.
     * @param book_author String.
     * @param book_pages int.
     * @param book_finished int.
     */
    public Book(String book_name, String book_author, int book_pages, int book_finished) {
        this.book_name = book_name;
        this.book_author = book_author;
        this.book_pages = book_pages;
        this.book_finished = book_finished;
    }

    /**
     * Constructor to Get a book record from the data base.
     * @param book_id int.
     * @param book_name String.
     * @param book_author String.
     * @param book_pages int.
     * @param book_finished int.
     * @param book_progress int.
     */
    public Book(int book_id, String book_name, String book_author, int book_pages, int book_finished, int book_progress) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_author = book_author;
        this.book_pages = book_pages;
        this.book_finished = book_finished;
        this.book_progress = book_progress;
    }

    /**
     * Book id Getter
     * @return book_id
     */
    public int getBook_id() {
        return book_id;
    }


    /**
     * @param book_id Setter
     */
    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    /**
     * Book name Getter
     * @return book_name
     */
    public String getBook_name() {
        return book_name;
    }

    /**
     * @param book_name Setter
     */
    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    /**
     * Book author Getter
     * @return book_author
     */
    public String getBook_author() {
        return book_author;
    }

    /**
     * @param book_author Setter
     */
    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    /**
     * Book pages Getter
     * @return book_pages
     */
    public int getBook_pages() {
        return book_pages;
    }

    /**
     * @param book_pages Setter
     */
    public void setBook_pages(int book_pages) {
        this.book_pages = book_pages;
    }

    /**
     * Book finished Getter
     * @return book_finished
     */
    public int getBook_finished() {
        return book_finished;
    }

    /**
     * @param book_finished Setter
     */
    public void setBook_finished(int book_finished) {
        this.book_finished = book_finished;
    }

    /**
     * Book progress Getter
     * @return book_progress
     */
    public int getBook_progress() {
        setBook_progress();
        return book_progress;
    }

    /**
     * Progress Setter is managed with the calculateProgress() method.
     */
    public void setBook_progress() {
        this.book_progress = calculateProgress();
    }

    /**
     * Method that calculates the progress of pages read of the book.
     * @return calculateProgress
     */
    public int calculateProgress(){
        return  (getBook_finished() * Constants.HUNDRED_PERCENT)/getBook_pages();
    }
}
