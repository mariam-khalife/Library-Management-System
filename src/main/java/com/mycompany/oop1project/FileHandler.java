/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oop1project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class FileHandler {
    private static final String FILE_PATH = "books.txt"; // the file where all books are stored

    // ─── Load all books from the file ─────────────────────────────────
    public List<Book> loadAllBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(FILE_PATH);

        // if the file doesn't exist yet, return an empty list (first run)
        if (!file.exists()) {
            return books;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // read the file line by line — each line is one book
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // skip empty lines

                // each line format: id,title,author,borrowed
                String[] parts = line.split(",", 4); // split into max 4 parts

                if (parts.length == 4) {
                    Book book = new Book(
                        parts[0].trim(),                          // id
                        parts[1].trim(),                          // title
                        parts[2].trim(),                          // author
                        Boolean.parseBoolean(parts[3].trim())     // borrowed (true/false)
                    );
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return books;
    }

    // ─── Save all books to the file (rewrites the entire file) ────────
    public void saveAllBooks(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            // false = overwrite mode, not append mode
            for (Book book : books) {
                // write each book as one line: id,title,author,borrowed
                writer.write(book.getBookID() + "," +
                             book.getTitle()  + "," +
                             book.getAuthor() + "," +
                             book.isBorrowStatus());
                writer.newLine(); // move to the next line
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // ─── Generate next ID based on existing books ──────────────────────
    public int getNextId(List<Book> books) {
        int maxId = 0;

        // loop through all books to find the highest ID
        for (Book book : books) {
            int id = Integer.parseInt(book.getBookID());
            if (id > maxId) {
                maxId = id; // update maxId if this book's ID is higher
            }
        }

        return maxId + 1; // next ID is always highest + 1
    }

    // ─── Check if a book with the same title already exists ───────────
    public boolean titleExists(List<Book> books, String title) {
        for (Book book : books) {
            // equalsIgnoreCase makes the check case-insensitive
            if (book.getTitle().equalsIgnoreCase(title)) {
                return true; // duplicate found
            }
        }
        return false; // no duplicate
    }
}
