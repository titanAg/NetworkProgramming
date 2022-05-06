// COSC 318 Lab 4
// Kyle Orcutt 300277486

// A reusable GUI for the examples in this chapter.

import java.awt.*;
import javax.swing.*;

public class BookUI extends JPanel {

   // label text for GUI
   protected final static String names[] = { "Book Title", "Author", "ISBN", "Edition", "Copyright Year", "Price", "Quantity In Stock" };

   // GUI components; protected for future subclass access
   protected JLabel labels[], label;
   protected JTextField fields[];
   protected JButton doTask1, doTask2;
   protected JPanel innerPanelCenter, innerPanelSouth;

   protected int size; // number of text fields in GUI

   // constants representing text fields in GUI
   public static final int BOOK_TITLE = 0, AUTHOR = 1, ISBN = 2,
      EDITION = 3, COPYRIGHT_YEAR = 4, PRICE = 5, QUANTITY_IN_STOCK = 6;

   // Set up GUI. Constructor argument size determines the number of
   // rows of GUI components.
   public BookUI()
   {
      size = names.length;
      labels = new JLabel[ size ];
      fields = new JTextField[ size ];

      // create labels
      for ( int count = 0; count < labels.length; count++ )
         labels[ count ] = new JLabel( names[ count ] );

      // create text fields
      for ( int count = 0; count < fields.length; count++ )
         fields[ count ] = new JTextField();

      // create panel to lay out labels and fields
      innerPanelCenter = new JPanel();
      innerPanelCenter.setLayout( new GridLayout( size, 2 ) );

      // attach labels and fields to innerPanelCenter
      for ( int count = 0; count < size; count++ ) {
         innerPanelCenter.add( labels[ count ] );
         innerPanelCenter.add( fields[ count ] );
      }

      // create generic buttons; no labels or event handlers
      doTask1 = new JButton();
      doTask2 = new JButton();
      
      label = new JLabel();

      // create panel to lay out buttons and attach buttons
      innerPanelSouth = new JPanel();
      innerPanelSouth.add( label );
      innerPanelSouth.add( doTask1 );
      innerPanelSouth.add( doTask2 );

      // set layout of this container and attach panels to it
      setLayout( new BorderLayout() );
      add( innerPanelCenter, BorderLayout.CENTER );
      add( innerPanelSouth, BorderLayout.SOUTH );

      validate(); // validate layout

   } // end constructor

   // return reference to generic task button doTask1
   public JButton getDoTask1Button()
   {
      return doTask1;
   }

   // return reference to generic task button doTask2
   public JButton getDoTask2Button()
   {
      return doTask2;
   }

   // return reference to fields array of JTextFields
   public JTextField[] getFields()
   {
      return fields;
   }
   
   public JLabel getLabel() {
       return label;
   }

   // clear content of text fields
   public void clearFields()
   {
      for ( int count = 0; count < size; count++ )
         fields[ count ].setText( "" );
   }

   // set text field values; throw IllegalArgumentException if
   // incorrect number of Strings in argument
   public void setFieldValues( String strings[] )
      throws IllegalArgumentException
   {
      if ( strings.length != size )
         throw new IllegalArgumentException( "There must be " +
            size + " Strings in the array" );

      for ( int count = 0; count < size; count++ )
         fields[ count ].setText( strings[ count ] );
   }

   // get array of Strings with current text field contents
   public String[] getFieldValues()
   {
      String values[] = new String[ size ];

      for ( int count = 0; count < size; count++ )
         values[ count ] = fields[ count ].getText();

      return values;
   }

} // end class BankUI
