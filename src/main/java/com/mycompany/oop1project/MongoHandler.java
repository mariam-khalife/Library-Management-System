/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oop1project;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author user
 */
public class MongoHandler {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017"; // The address where MongoDB is running on your machine
    private static final String DATABASE_NAME = "libraryDB";// The name of the database
    private static final String COLLECTION_NAME = "books"; // The name of the collection (equivalent to a table) that stores books

    private MongoClient mongoClient;//to open and close the connection 
    private MongoCollection<Document> collection;//Holds a reference to the "books" collection
    
    public void connect(){
        mongoClient = MongoClients.create(CONNECTION_STRING);
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);//access to it or create if doesn't exist
        collection = database.getCollection(COLLECTION_NAME);
        System.out.println("Connected to MongoDB successfully.");
        
    }
    
    public void disconnect() {
         //check if the connection exist before close
         if (mongoClient != null){
             mongoClient.close();
             System.out.println("Disconnected from MongoDB.");
        }
    }
     
    public List<Book> loadAllBooks(){
        List<Book> books = new ArrayList<>();
        for (Document doc : collection.find()){  // collection.find() fetches every document in the "books" collection
             Book book = new Book(
                String.valueOf(doc.getInteger("id")), // MongoDB auto-generated ID converted to String
                doc.getString("title"),            // Get the title field from the document
                doc.getString("author"),           // Get the author field from the document
                doc.getBoolean("borrowed")         // Get the borrowed status from the document
            );
            books.add(book); // Add the Book object to the list
        }
        return books;
    }
    
    public String saveBook(Book book){
        Document existing = collection.find(
        Filters.regex("title", "^" + book.getTitle() + "$", "i")// ^ = start, $ = end, "i" = ignore case
        ).first();//returns the first match found, or null if nothing matches
        
        if (existing != null) {
        System.out.println("A book with the title \"" + book.getTitle() + "\" already exists!");
        return null; // return null to signal that save failed
        }
        // Find the current maximum ID in the collection to avoid duplicates
        Document highestId = collection.find()
            .sort(new Document("id", -1)) // sort descending to get the highest
            .limit(1)
            .first();
        
        int nextId = (highestId != null) ? highestId.getInteger("id") + 1 : 1;
        
        Document doc = new Document()
            .append("id",nextId)
            .append("title",book.getTitle()) 
            .append("author",book.getAuthor()) 
            .append("borrowed",book.isBorrowStatus());
        
         collection.insertOne(doc);//Insert the document into the collection(the id is auto generated )
         
         return String.valueOf(nextId); // Return the ID so the Library can assign it to the Book object
    }
    
    public void updateBook(Book book) {
        collection.updateOne(
                Filters.eq("id", Integer.parseInt(book.getBookID())), // filter by integer id
                Updates.set("borrowed", book.isBorrowStatus())
        );
    }
    
    public void deleteBook(String id ){
        collection.deleteOne(
                Filters.eq("id", Integer.parseInt(id))
        );
    }
    
}
