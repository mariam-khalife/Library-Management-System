/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oop1project;

/**
 *
 * @author user
 */
public class Book {
    private String BookID;
    private String Title;
    private String Author ;
    private boolean BorrowStatus;

    public Book(String BookID, String Title, String Author, boolean BorrowStatus) {
        this.BookID = BookID;
        this.Title = Title;
        this.Author = Author;
        this.BorrowStatus = BorrowStatus;
    }
    
    public String toString(){
        return "ID: " + BookID +
               " | Title: " + Title +
               " | Author: " + Author +
               " | Status: " + (BorrowStatus ? "Borrowed" : "Available");
    }

    public String getBookID() {
        return BookID;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public boolean isBorrowStatus() {
        return BorrowStatus;
    }

    public void setBorrowStatus(boolean BorrowStatus) {
        this.BorrowStatus = BorrowStatus;
    }
    
    
}
