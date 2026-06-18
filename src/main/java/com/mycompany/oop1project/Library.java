/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oop1project;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class Library {
    private List<Book> books;       
    private MongoHandler mongoHandler;
    private FileHandler fileHandler;

    public Library() {
        mongoHandler = new MongoHandler(); // create a new MongoHandler instance
        fileHandler  = new FileHandler();
        mongoHandler.connect();            // open the connection to MongoDB
        books = mongoHandler.loadAllBooks(); // load all existing books from MongoDB into the list
        fileHandler.saveAllBooks(books);
        System.out.println("Library loaded with " + books.size() + " book(s).\n");
    }
    
    public void addBook(String title, String author) {
        Book book = new Book(null, title, author, false);
        
        // returns null if a book with the same title already exists
        String generatedId = mongoHandler.saveBook(book);
        
        //the book was not saved (duplicate title), so we stop
        if (generatedId == null) {
            return;
        }
        // create the final Book object with the real generated ID
        book = new Book(generatedId, title, author, false);
        
        books.add(book); 
        System.out.println("Book added successfully!\n" + book + "\n");
    }
    
    public void viewAllBooks() {
        if (books.isEmpty()) {// check if the list is empty 
            System.out.println("No books available in the library.\n");
            return;
        }
        
        System.out.println("\n========== All Books ==========");
        
        int counter = 1; // counter to number each book in the display
        for (Book book : books) {
            System.out.println("Book " + counter + ":");
            System.out.println("  ID     : " + book.getBookID());
            System.out.println("  Title  : " + book.getTitle());
            System.out.println("  Author : " + book.getAuthor());
            System.out.println("  Status : " + (book.isBorrowStatus() ? "Borrowed" : "Available"));
            System.out.println("--------------------------------");
            counter++;
        }
        
        System.out.println("================================\n");
    }
    
    private Book findBookById(String id) {
        
        // loop through the in-memory list looking for a book whose ID matches
        for (Book book : books) {
            if (book.getBookID().equals(id)) {
                return book; // found it, return it immediately
            }
        }
        return null; // no book found with this ID
    }
    
     public void borrowBook(String id) {
        Book book = findBookById(id);// search in-memory list for the book with this ID
        
        // if no book was found with this ID, inform the user and stop
        if (book == null) {
            System.out.println("No book found with ID: " + id + "\n");
            return;
        }
        
        // if the book exists but is already borrowed, inform the user and stop
        if (book.isBorrowStatus()) {
            System.out.println("Sorry, this book is already borrowed.\n");
            return;
        }
        
        book.setBorrowStatus(true); 
        mongoHandler.updateBook(book);   // update the status in MongoDB
        fileHandler.saveAllBooks(books); // sync txt file after update
        System.out.println("You successfully borrowed: " + book.getTitle() + "\n");
    }
    
    public void returnBook(String id) {
        // search the in-memory list for the book with this ID
        Book book = findBookById(id);
        
        // if no book was found with this ID, inform the user and stop
        if (book == null) {
            System.out.println("No book found with ID: " + id + "\n");
            return;
        }
        
        // if the book exists but is not borrowed, there is nothing to return
        if (!book.isBorrowStatus()) {
            System.out.println("This book was not borrowed.\n");
            return;
        }
        
        book.setBorrowStatus(false);         // update the status in memory
        mongoHandler.updateBook(book);   // update the status in MongoDB
        fileHandler.saveAllBooks(books);
        System.out.println("You successfully returned: " + book.getTitle() + "\n");
    }

    public void searchBook(String title) {
        
        // temporary list to collect all books that match the search keyword
        List<Book> results = new ArrayList<>();
        
        // loop through all books and check if the title contains the search keyword
        for (Book book : books) {
            // toLowerCase() on both sides makes the search case-insensitive
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book); // add matching book to results
            }
        }
        
        // if no books matched the search keyword
        if (results.isEmpty()) {
            System.out.println("No books found matching: \"" + title + "\"\n");
        } else {
            System.out.println("\n===== Search Results =====");
            for (Book book : results) {
                System.out.println(book); // print each matching book
            }
            System.out.println("==========================\n");
        }
    }
    
    public void deleteBook(String id) {
        
        // search the in-memory list for the book with this ID
        Book book = findBookById(id);
        
        // if no book was found with this ID, inform the user and stop
        if (book == null) {
            System.out.println("No book found with ID: " + id + "\n");
            return;
        }
        
        mongoHandler.deleteBook(id); // delete the document from MongoDB
        books.remove(book);          // remove the book from the in-memory list
        fileHandler.saveAllBooks(books);
        System.out.println("Book deleted successfully: " + book.getTitle() + "\n");
    }

    // ─── Close the MongoDB connection ─────────────────────────────────
    public void close() {
        mongoHandler.disconnect(); // must be called when the program exits to free resources
    }

}
