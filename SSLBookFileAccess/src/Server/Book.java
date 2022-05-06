// Kyle Orcutt 

import java.io.Serializable;


public class Book implements Serializable {
   private String book_title;
   private String author;
   private String isbn;
   private Integer edition;
   private Integer copyright_year;
   private Double price;
   private Integer quantity_in_stock;

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
   public Integer getEdition() { return edition; }
   public Integer getCopyrightYear() { return copyright_year; }
   public Double getPrice() { return price; }
   public Integer getQuantityInStock() { return quantity_in_stock; }
   
   @Override
   public String toString() {
       return "title=" + book_title + " author=" + author + " isbn=" + isbn +
               " edition=" + edition + "copyright_year=" + copyright_year + " price=" + price + " quantity=" + quantity_in_stock;
              
   }
   


} // end class AccountRecord

