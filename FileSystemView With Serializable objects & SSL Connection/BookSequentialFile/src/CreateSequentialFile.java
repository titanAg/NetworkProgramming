// Kyle Orcutt 

// CreateSequentialFile.java
// Writing objecst sequentially to a file with class ObjectOutputStream.
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CreateSequentialFile extends JFrame {
   private ObjectOutputStream output;
   private BookUI userInterface;
   private JButton enterButton, openButton;

   // set up GUI
   public CreateSequentialFile()
   {
      super( "Creating a Sequential File of Objects" );

      // create instance of reusable user interface
      userInterface = new BookUI();  // four textfields
      getContentPane().add( userInterface, BorderLayout.CENTER );

      // configure button doTask1 for use in this program
      openButton = userInterface.getDoTask1Button();
      openButton.setText( "Save into File ..." );

      // register listener to call openFile when button pressed
      openButton.addActionListener(

         // anonymous inner class to handle openButton event
         new ActionListener() {

            // call openFile when button pressed
            public void actionPerformed( ActionEvent event )
            {
               openFile();
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // configure button doTask2 for use in this program
      enterButton = userInterface.getDoTask2Button();
      enterButton.setText( "Enter" );
      enterButton.setEnabled( false );  // disable button

      // register listener to call addRecord when button pressed
      enterButton.addActionListener(

         // anonymous inner class to handle enterButton event
         new ActionListener() {

            // call addRecord when button pressed
            public void actionPerformed( ActionEvent event )
            {
                addRecord();
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // register window listener to handle window closing event
      addWindowListener(

         // anonymous inner class to handle windowClosing event
         new WindowAdapter() {

            // add current record in GUI to file, then close file
            public void windowClosing( WindowEvent event )
            {
               if ( output != null )
                  addRecord();

               closeFile();
            }

         } // end anonymous inner class

      ); // end call to addWindowListener

      setBounds(400,400, 500, 250 );
      setVisible( true );

   } // end CreateSequentialFile constructor

   // allow user to specify file name
   private void openFile()
   {
      // display file dialog, so user can choose file to open
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );

      int result = fileChooser.showSaveDialog( this );

      // if user clicked Cancel button on dialog, return
      if ( result == JFileChooser.CANCEL_OPTION )
         return;

      File fileName = fileChooser.getSelectedFile(); // get selected file

      // display error if invalid
      if ( fileName == null || fileName.getName().equals( "" ) )
         JOptionPane.showMessageDialog( this, "Invalid File Name",
            "Invalid File Name", JOptionPane.ERROR_MESSAGE );

      else {

         // open file
         try {
            output = new ObjectOutputStream(
               new FileOutputStream( fileName ) );

            openButton.setEnabled( false );
            enterButton.setEnabled( true );
         }

         // process exceptions from opening file
         catch ( IOException ioException ) {
            JOptionPane.showMessageDialog( this, "Error Opening File",
               "Error", JOptionPane.ERROR_MESSAGE );
         }

      } // end else

   } // end method openFile

   // close file and terminate application
   private void closeFile()
   {
      // close file
      try {
          if (output != null)
            output.close();
         System.exit( 0 );
      }

      // process exceptions from closing file
      catch( IOException ioException ) {
         JOptionPane.showMessageDialog( this, "Error closing file",
            "Error", JOptionPane.ERROR_MESSAGE );
         System.exit( 1 );
      }

   } // end method closeFile

   // add record to file
   public void addRecord()
   {
      Book record;
      String fieldValues[] = userInterface.getFieldValues();

      // if book title field value is not empty
      if ( ! fieldValues[ BookUI.BOOK_TITLE ].equals( "" ) ) {
          
          boolean hasEmptyField = false;
          for (int i = 0; i < fieldValues.length; i++) {
              if (fieldValues[i].equals(""))
                  hasEmptyField = true;
          }

         // output values to file
         try {
             
             if (!hasEmptyField) {
                // create new record
                record = new Book( fieldValues[ BookUI.BOOK_TITLE ],
                fieldValues[ BookUI.AUTHOR ],
                fieldValues[ BookUI.ISBN ],
                Integer.parseInt(fieldValues[ BookUI.EDITION ]),
                Integer.parseInt(fieldValues[ BookUI.COPYRIGHT_YEAR ]),
                Double.parseDouble(fieldValues[ BookUI.PRICE ]),
                Integer.parseInt(fieldValues[ BookUI.QUANTITY_IN_STOCK ]) );

                // output record and flush buffer
                output.writeObject( record );
                output.flush();

                // clear textfields
                userInterface.clearFields();
             }
             else {
                JOptionPane.showMessageDialog( this,
               "All fields must have a value",
               "Bad entry", JOptionPane.ERROR_MESSAGE );
             }
         } // end try

         // process invalid Edition, Copyright Year, price, or quantity format
         catch ( NumberFormatException formatException ) {
            JOptionPane.showMessageDialog( this,
               "Bad inputs - Edition, Copyright Year, price, and quantity must be number values", "Invalid Number Format",
               JOptionPane.ERROR_MESSAGE );
         }

         // process exceptions from file output
         catch ( IOException ioException ) {
             JOptionPane.showMessageDialog( this, "Error writing to file",
                "IO Exception", JOptionPane.ERROR_MESSAGE );
            closeFile();
         }
      } // end if
   } // end method addRecord

   public static void main( String args[] )
   {
      new CreateSequentialFile();
   }

} // end class CreateSequentialFile

