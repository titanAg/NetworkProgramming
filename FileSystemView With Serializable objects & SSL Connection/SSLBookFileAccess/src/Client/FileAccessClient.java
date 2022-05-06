// COSC 318 Lab 4
// Kyle Orcutt 300277486

// FileAccessClient.java
// Accessing objects sequentially from a file across a network connection.
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;

public class FileAccessClient extends JFrame {
   private ObjectOutputStream toServer;
   private ObjectInputStream fromServer;
   private String [] values;
   private File [] files;
   private InetAddress host;
   private String response;
   private JTextField ip;
   private JLabel iplabel, label;
   private JPanel ippane;
   private JList filelist;
   private SSLSocket sock;
   private BookUI userInterface;
   private JButton nextButton, connectButton, openButton;
   String [] recordValues;

   // set up GUI
   public FileAccessClient()
   {
      super( "Reading a Sequential File across a network connection" );

      // create instance of reusable user interface
      userInterface = new BookUI();  // four textfields
      ip = new JTextField("localhost",32);
      iplabel = new JLabel("Host IP ");
      filelist = new JList();
      ippane = new JPanel();
      ippane.setLayout(new GridLayout(1,1));
      ippane.add(iplabel);
      ippane.add(ip);
      getContentPane().add(ippane, BorderLayout.NORTH);
      getContentPane().add( userInterface, BorderLayout.CENTER );
      getContentPane().add(new JScrollPane(filelist), BorderLayout.SOUTH);


      label = userInterface.getLabel();
      label.setText("");

      // configure button doTask1 for use in this program
      connectButton = userInterface.getDoTask1Button();
      connectButton.setText( "Connect" );




      // register listener to call openFile when button pressed
      connectButton.addActionListener(

         // anonymous inner class to handle openButton event
         new ActionListener() {

            // when button pressed
            public void actionPerformed( ActionEvent event )
            {
             try {      
                 System.setProperty("javax.net.ssl.trustStore", "trustedcerts");              
                    SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
	    	    sock = (SSLSocket) factory.createSocket(InetAddress.getByName(ip.getText()), 6868);
                    
		    toServer = new ObjectOutputStream (sock.getOutputStream());
	    	    fromServer = new ObjectInputStream (sock.getInputStream());

                    files = (File []) fromServer.readObject();
                    filelist.setListData(files);
                    connectButton.setEnabled(false);
                    nextButton.setEnabled(false);
                    label.setText("<html><Font Color=blue>" +
                    "Please double-click one of the data files below:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp" +
                    "&nbsp;&nbsp;&nbsp</Font></html>");


              }
              catch (IOException ioe) {}
              catch (ClassNotFoundException cnfe) {};
            }

         } // end anonymous inner class

      ); // end call to addActionListener



      MouseListener mouseListener = new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
         if (e.getClickCount() == 2) {
             int i = filelist.locationToIndex(e.getPoint());
             try {
		 if (i < 0) return;

		 toServer.writeObject(files[i]);
		 response = (String) fromServer.readObject();

		 if (response.equals("directory")) {
			toServer.writeObject("sendfiles");
			files = (File []) fromServer.readObject();
			filelist.setListData(files);
		  }
		  else {
	              toServer.writeObject("openfile");
                      response = (String) fromServer.readObject();
                        if (response.equals("ready")) {
                            
                            
                            // configure button doTask2 for use in this program
                            nextButton = userInterface.getDoTask2Button();
                            nextButton.setText( "Next Record" );
                            nextButton.setEnabled( true);
                            connectButton.setEnabled (false);
                            label.setText("");
                        }
                        else if (response.equals("invaliddatafile")){
                                JOptionPane.showMessageDialog( null, "Invalid data file, try again!",
                                                 "Invalid data file", JOptionPane.ERROR_MESSAGE );
                         }
              	 }
              }
              catch (IOException ioe) {}
              catch (ClassNotFoundException cnfe) {};
            }

          }

      };

      filelist.addMouseListener(mouseListener);

      nextButton = userInterface.getDoTask2Button();
      nextButton.setText( "Next Record" );
      nextButton.setEnabled(false);

      // register listener to call addRecord when button pressed
      nextButton.addActionListener(

         // anonymous inner class to handle enterButton event
         new ActionListener() {

            // call addRecord when button pressed
            public void actionPerformed( ActionEvent event )
            {
              try {
	           toServer.writeObject("nextrecord");
    	           //values = (String [])fromServer.readObject();
                   values = readRecord();

                    if (values != null)
                          // display record contents
                          userInterface.setFieldValues( values );
               	   else {
               	   	  connectButton.setEnabled(true);
               	   	  nextButton.setEnabled( false );
               	   	  userInterface.clearFields();

               	   	  Vector  emptyfiles = new Vector();

               	   	  filelist.setListData(emptyfiles);



                   	  JOptionPane.showMessageDialog( null, "No more records in file",
                                       "End of File", JOptionPane.ERROR_MESSAGE );
                   	  toServer.close();
                   	  fromServer.close();
                   	  sock.close();
                	}
                }
                catch (IOException ioe) {}
;
            }

         } // end anonymous inner class

      ); // end call to addActionListener

            // register window listener for window closing event
      addWindowListener(

         // anonymous inner class to handle windowClosing event
         new WindowAdapter() {

            // close file and terminate application
            public void windowClosing( WindowEvent event )
            {
                  dispose();
                  System.exit(0);
            }

         } // end anonymous inner class

      ); // end call to addWindowListener



      setBounds(350, 350, 500, 390 );
      setVisible( true );
      setResizable(false);

   } // end FileAccessClient constructor
   



   // read record from file
   public String [] readRecord()
   {
      Book record;

      // input the values from the file
      try {
          //String s = (Book) fromServer.readObject();
          //System.out.println("Msg from server: " + s);
         record = (Book)fromServer.readObject();

         // create array of Strings to display in GUI
         String values[] = { record.getBookTitle(),
                             record.getAuthor(),
         record.getIsbn(),
         String.valueOf(record.getEdition()),
         String.valueOf(record.getCopyrightYear()),
         String.valueOf(record.getPrice()),
         String.valueOf(record.getQuantityInStock())};

   	return values;
      }

      // display message when end-of-file reached
      catch ( EOFException endOfFileException ) {

      }

      // display error message if class is not found
      catch ( ClassNotFoundException classNotFoundException ) {
         JOptionPane.showMessageDialog( null, "Unable to create object",
            "Class Not Found", JOptionPane.ERROR_MESSAGE );
      }

      // display error message if cannot read due to problem with file
      catch ( IOException ioException ) {
         JOptionPane.showMessageDialog( null,
            "Error during read from file",
            "Read Error", JOptionPane.ERROR_MESSAGE );
    }
    catch (java.lang.ClassCastException e) {
        System.out.println(e);
        JOptionPane.showMessageDialog( null,
        "Error during serialization of Book object",
        "Read Error", JOptionPane.ERROR_MESSAGE );
    }
    return null;
  } //readRecord


   public static void main( String args[] )
   {
      FileAccessClient app = new FileAccessClient();
      app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

} // end class FileAccessClient