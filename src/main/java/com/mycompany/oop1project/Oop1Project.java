/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.oop1project;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class Oop1Project {

    public static void main(String[] args) {
        Library library = new Library();  // connects to MongoDB and loads all books
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        
        while (choice != 0) {
            showMenu();
            System.out.print("Enter your choice: ");

            // ── Read and validate user input ───────────────────────
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                // user typed something that is not a number
                System.out.println("Invalid input. Please enter a number.\n");
                continue; // go back to the top of the loop
            }

            switch (choice) {

                case 1: // Add a book
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine().trim();
                    library.addBook(title, author);
                    break;

                case 2: // View all books
                    library.viewAllBooks();
                    break;

                case 3: // Borrow a book
                    library.viewAllBooks(); 
                    System.out.print("Enter Book ID to borrow: ");
                    String borrowId = scanner.nextLine().trim();
                    library.borrowBook(borrowId);
                    break;

                case 4: // Return a book
                    library.viewAllBooks(); 
                    System.out.print("Enter Book ID to return: ");
                    String returnId = scanner.nextLine().trim();
                    library.returnBook(returnId);
                    break;

                case 5: // Search for a book
                    System.out.print("Enter title to search: ");
                    String searchTitle = scanner.nextLine().trim();
                    library.searchBook(searchTitle);
                    break;

                case 6: // Delete a book
                    library.viewAllBooks();
                    System.out.print("Enter Book ID to delete: ");
                    String deleteId = scanner.nextLine().trim();
                    library.deleteBook(deleteId);
                    break;

                case 0: // Exit
                    System.out.println("Goodbye!");
                    break;

                default: // anything else
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }

        library.close();   // disconnect from MongoDB properly
        scanner.close();   // release the scanner resource
    }

    // ─── Menu Display ─────────────────────────────────────────────────
    private static void showMenu() {
        System.out.println("========== Library Menu ==========");
        System.out.println("1. Add a book");
        System.out.println("2. View all books");
        System.out.println("3. Borrow a book");
        System.out.println("4. Return a book");
        System.out.println("5. Search for a book");
        System.out.println("6. Delete a book");
        System.out.println("0. Exit");
        System.out.println("==================================");
    }

}
