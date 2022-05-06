
// COSC 318 Lab 4
// Kyle Orcutt 300277486

import java.io.Serializable;


public class Book implements Serializable {
   private String book_title;
   private String author;
   private String isbn;
   private int edition;                      //Change from Integer
   private int copyright_year;               //Change from Integer
   private double price;                     //Change from Double 
   private int quantity_in_stock;           //change from Integer

   // no-argument constructor calls other constructor with default values
   public Book()
   {
      this( "", "", "", 0, 0, 0.0, 0 );
   }

   // initialize a record
   public Book(String book_title, String author, String isbn, Integer edition, Integer copyright_year, Double price, Integer quantity_in_stock )
   {
      this.book_title = book_title;
      this.author = author;
      this.isbn = isbn;
      this.edition = edition;
      this.copyright_year = copyright_year;
      this.price = price;
      this.quantity_in_stock = quantity_in_stock;
   }

   public String getBookTitle() { return book_title; }
   public String getAuthor() { return author; }
   public String getIsbn() { return isbn; }
   public int getEdition() { return edition; }
   public int getCopyrightYear() { return copyright_year; }
   public double getPrice() { return price; }
   public int getQuantityInStock() { return quantity_in_stock; }

   @Override
   public String toString() {
       return "title=" + book_title + " author=" + author + " isbn=" + isbn +
               " edition=" + edition + "copyright_year=" + copyright_year + " price=" + price + " quantity=" + quantity_in_stock;

   }



} // end class AccountRecord

