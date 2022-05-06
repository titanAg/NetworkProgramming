// COSC 318 Lab 3
// Kyle Orcutt

package MyEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.filechooser.FileNameExtensionFilter;


/** An example class used to demonstrate the
  * use of the color and file choosers
  */
public class MyEditor extends JFrame {
   private JTextArea text = new JTextArea();
   private JFileChooser fileChoose
      = new JFileChooser();
   private FileNameExtensionFilter filter 
           = new FileNameExtensionFilter("TXT file", "txt");
   private JDialog colorDlg;
   private JColorChooser colorChoose
      = new JColorChooser();

// Class constructor
   public MyEditor( String titleText ) {
      super( titleText );
      setJMenuBar( buildMenuBar() );
      text.setEditable( false );
      Container cp = getContentPane();
      cp.add( new JScrollPane( text ),
              BorderLayout.CENTER );
      setBounds(350,350, 500, 400 );
      setVisible( true );
      fileChoose.setFileFilter(filter);
   }

   // Present a dialog box to have the user select
   //the file for browsing
   public void loadFile() {
      int result = fileChoose.showOpenDialog(
         this );
      File file = fileChoose.getSelectedFile();
      if ( file != null
           && result == JFileChooser.APPROVE_OPTION ) try {
         FileReader fr = new FileReader( file );
         text.setText( "" );
         char[] charBuffer = new char[4096];
         int charsRead = fr.read( charBuffer, 0,
                                  charBuffer.length );
         while ( charsRead != -1 ) {
            text.append( new String( charBuffer, 0,
                                     charsRead ) );
            charsRead = fr.read( charBuffer, 0,
                                 charBuffer.length );
         }
      } catch( IOException ioe ) {
         ioe.printStackTrace();
      }
   }
   
   public void saveFile() throws IOException {
       int result = fileChoose.showSaveDialog(this);
       if (result == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChoose.getSelectedFile();
            fileToSave.createNewFile();
            FileWriter writer = new FileWriter(fileToSave);
            writer.write(text.getText()); 
            writer.close();
            System.out.println("Saving file: " + fileToSave.getAbsolutePath());

       }
   }
   
   public void printFile() {
       PrinterJob pj = PrinterJob.getPrinterJob();
        if (pj.printDialog()) {
            try {
                pj.setPrintable (new Printable() {    
                    public int print(Graphics pg, PageFormat pf, int pageNum){
                        if (pageNum > 0){
                           return Printable.NO_SUCH_PAGE;
                        }

                        Graphics2D g2 = (Graphics2D) pg;
                        g2.translate(pf.getImageableX(), pf.getImageableY());
                        text.paint(g2);
                       return Printable.PAGE_EXISTS;
                    }
                });
                pj.print();

            }
            catch (PrinterException e) {
                System.out.println("Error...");
                e.printStackTrace();
             }
     }
   }

   // Build the menu bar, menus, and menu items for
   // the file browser
   public JMenuBar buildMenuBar() {
      JMenuBar menuBar = new JMenuBar();
      JMenu fileMenu = new JMenu( "File" );
      JMenu editMenu = new JMenu( "Edit" );
      JMenu colorMenu = new JMenu( "Color" );
      JMenuItem exitItem = new JMenuItem( "Exit" );
      JMenuItem fileOpenItem
         = new JMenuItem( "File Open..." );
      JMenuItem fileNewItem
         = new JMenuItem( "File New..." );
      JMenuItem fileSaveItem
         = new JMenuItem( "File Save..." );
      JMenuItem filePrintItem
         = new JMenuItem( "Print..." );
      
      JMenuItem selectAllItem
         = new JMenuItem( "Select All" );
      JMenuItem deleteItem
         = new JMenuItem( "Delete" );
      JMenuItem copyItem
         = new JMenuItem( "Copy" );
      JMenuItem cutItem
         = new JMenuItem( "Cut" );
      JMenuItem pasteItem
         = new JMenuItem( "Paste" );
      
      JMenuItem colorsItem
         = new JMenuItem( "Change Color..." );

      fileMenu.setMnemonic( KeyEvent.VK_F );
      //editMenu.setMnemonic( KeyEvent.VK_E );
      colorMenu.setMnemonic( KeyEvent.VK_C );
      
      fileOpenItem.setMnemonic( KeyEvent.VK_O );
      fileNewItem.setMnemonic( KeyEvent.VK_N );
      fileSaveItem.setMnemonic( KeyEvent.VK_S );
      filePrintItem.setMnemonic( KeyEvent.VK_P );
      exitItem.setMnemonic( KeyEvent.VK_X );
      
//      selectAllItem.setMnemonic(KeyEvent.VK_A);
//      deleteItem.setMnemonic(KeyEvent.VK_D);
//      copyItem.setMnemonic(KeyEvent.VK_C);
//      cutItem.setMnemonic(KeyEvent.VK_X);
//      pasteItem.setMnemonic(KeyEvent.VK_V);
      
      colorsItem.setMnemonic( KeyEvent.VK_C );

      ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == fileOpenItem) {
                text.setEditable( true );
                loadFile();
            }
            else if (e.getSource() == fileNewItem) {
                text.setText("");
                text.setEditable( true );
            }
            else if (e.getSource() == fileSaveItem) {
                try {
                    saveFile();
                } catch (IOException ex) {
                    Logger.getLogger(MyEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (e.getSource() == filePrintItem) {
                printFile();
            }
            else if (e.getSource() == exitItem) {
                dispose();
                System.exit( 0 );
            }
            else if (e.getSource() == selectAllItem) {
                text.selectAll();
            }
            else if (e.getSource() == deleteItem) {
                String t = text.getSelectedText() == null ? text.getText() :
                        text.getText().replace(text.getSelectedText(),"");
                text.setText(t);
            }
            else if (e.getSource() == copyItem) {
                text.copy();
            }
            else if (e.getSource() == cutItem) {
                text.cut();
            }
            else if (e.getSource() == pasteItem) {
                text.paste();
            }            
            else if (e.getSource() == colorsItem) {
                if ( colorDlg == null ) {
                      colorDlg = JColorChooser.createDialog(MyEditor.this,
                         "Select Text Color",
                         true,
                         colorChoose,
                         new ColorOKListener(),
                         null
                      );
                   }
                   colorChoose.setColor(
                      text.getForeground() );
                   colorDlg.setVisible( true );
                }
              }
      };
      
      
      fileOpenItem.addActionListener(actionListener);
      fileNewItem.addActionListener(actionListener);
      fileSaveItem.addActionListener(actionListener);
      filePrintItem.addActionListener(actionListener);
      exitItem.addActionListener(actionListener);
      selectAllItem.addActionListener(actionListener);
      deleteItem.addActionListener(actionListener);
      copyItem.addActionListener(actionListener);
      cutItem.addActionListener(actionListener);
      pasteItem.addActionListener(actionListener);
      colorsItem.addActionListener(actionListener);

      menuBar.add( fileMenu );
      menuBar.add( editMenu );
      menuBar.add( colorMenu );
      fileMenu.add( fileOpenItem );
      fileMenu.add( fileNewItem );
      fileMenu.add( fileSaveItem );
      fileMenu.add( filePrintItem );
      fileMenu.add( exitItem );
      editMenu.add(selectAllItem);
      editMenu.add(deleteItem);
      editMenu.add(copyItem);
      editMenu.add(cutItem);
      editMenu.add(pasteItem);
      colorMenu.add( colorsItem );

      return menuBar;
   }

   class ColorOKListener implements ActionListener {
      public void actionPerformed( ActionEvent e ) {
         Color c = colorChoose.getColor();
         text.setForeground( c );
         text.repaint();
      }
   }

   // The main method for the class
   public static void main( String[] args ) {
   	 MyEditor app = new MyEditor( "File and Color Choosers" );
     app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
}