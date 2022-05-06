// FileAccessServer.java
// This program reads a file of objects sequentially
// and displays each record.

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class FileAccessServer extends JFrame {

    private ObjectInputStream input;
    private JTextArea serverDisplay;
    private JButton clearButton, exitButton;
    private Socket client;

    // Constructor -- initialize the Frame
    public FileAccessServer() throws NoSuchAlgorithmException, CertificateException {
        super("FileAccessServer");

        // create instance of reusable user interface
        serverDisplay = new JTextArea();
        clearButton = new JButton("Clear");
        clearButton.addActionListener(
                // anonymous inner class to handle openButton event
                new ActionListener() {

            // call openFile when button pressed
            public void actionPerformed(ActionEvent event) {
                serverDisplay.setText("");
            }
        }
        );

        exitButton = new JButton("Exit");
        exitButton.addActionListener(
                // anonymous inner class to handle openButton event
                new ActionListener() {

            // call openFile when button pressed
            public void actionPerformed(ActionEvent event) {
                dispose();
                System.exit(0);
            }
        }
        );

        getContentPane().add(new JScrollPane(serverDisplay), BorderLayout.CENTER);
        getContentPane().add(clearButton, BorderLayout.NORTH);
        getContentPane().add(exitButton, BorderLayout.SOUTH);

        setBounds(900, 400, 300, 200);
        setVisible(true);

        // using the pre-defined passwords from "serverkeys" keystore, created by the keytool utility
        String keystore = "serverkeys";
        char keystorepass[] = "letmein".toCharArray();
        char keypassword[] = "letmein".toCharArray();
        SSLServerSocket sock = null;

        try {
            //Set up SSL environment from the Keystore for creating a SSLServerSocket
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(keystore), keystorepass);
            KeyManagerFactory kmf
                    = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, keypassword);
            SSLContext sslcontext = SSLContext.getInstance("SSLv3");
            sslcontext.init(kmf.getKeyManagers(), null, null);
            ServerSocketFactory ssf = sslcontext.getServerSocketFactory();
            sock = (SSLServerSocket) ssf.createServerSocket(6868);

            while (true) {
                /**
                 * now listen for connections and service the connection in a
                 * separate thread.
                 */
                client = (SSLSocket) sock.accept();
                InetAddress ipAddr = client.getInetAddress();
                serverDisplay.append("Connected from -> " + ipAddr.getHostAddress()
                        + ":" + client.getPort() + "\n");
                Thread worker = new Thread(new Connection(client));
                worker.start();
            }

        } catch (java.io.IOException ioe) {
            System.err.println(ioe);
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
            };
        }

    } // end FileAccessServer constructor

    public static void main(String args[]) {
        FileAccessServer app;
        try {
            app = new FileAccessServer();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FileAccessServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(FileAccessServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

} // end class ReadSequentialFile
