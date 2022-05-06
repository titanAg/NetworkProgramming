
/**
 * This is the separate thread that services each
 * incoming echo client request.
 */
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Connection implements Runnable {

    private Socket client;
    private FileSystemView fsview;
    private ObjectInputStream input;

    public Connection(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            process(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void process(Socket client) throws IOException {
        File[] allfiles = null;
        File[] files;
        File current, parent;
        String cmd;
        boolean validfile;
        ObjectInputStream fromClient = null;
        ObjectOutputStream toClient = null;
        boolean filefound = true;

        try {
            /**
             * get the input and output streams associated with the socket.
             */

            fromClient = new ObjectInputStream(client.getInputStream());
            toClient = new ObjectOutputStream(client.getOutputStream());

            // open file
            try {
                fsview = FileSystemView.getFileSystemView();
                current = new File(new File(".").getAbsolutePath());
                // or do this:    current = fsview.getDefaultDirectory();
                files = fsview.getFiles(current, true);
                parent = fsview.getParentDirectory(current);
                allfiles = addparent(files, parent);
                toClient.writeObject(allfiles);

                filefound = false;
                while (!filefound) {
                    current = (File) fromClient.readObject();
                    if (current.isDirectory()) {
                        toClient.writeObject("directory");
                    } else {
                        toClient.writeObject("file");
                    }

                    cmd = (String) fromClient.readObject();

                    while (cmd.equals("sendfiles")) {
                        files = fsview.getFiles(current, true);
                        parent = fsview.getParentDirectory(current);
                        allfiles = addparent(files, parent);
                        toClient.writeObject(allfiles);
                        current = (File) fromClient.readObject();

                        if (current.isDirectory()) {
                            toClient.writeObject("directory");
                        } else {
                            toClient.writeObject("file");
                        }

                        cmd = (String) fromClient.readObject();
                    }
                    validfile = isValidFile(current);
                    if (validfile) {
                        input = new ObjectInputStream(new FileInputStream(current));
                        toClient.writeObject("ready");
                        filefound = true;
                        boolean done = false;
                        while (!done) {
                            cmd = (String) fromClient.readObject();
                            if (cmd.equals("nextrecord")) {
                                Book obj = (Book) input.readObject();
                                if (obj != null) {
                                    System.out.println("Sending book to client: " + obj);
                                    toClient.writeObject(obj);
                                } else {
                                    System.out.println("Error - tried to send null object to client");
                                    done = true;
                                    toClient.writeObject(null);
                                }
                            }
                        }
                        input.close();
                    } else {
                        toClient.writeObject("invaliddatafile");
                        filefound = false;
                    }
                } //file not found? keep looking for it!

                fromClient.close();
                toClient.close();

            } // process exceptions opening file
            catch (IOException ioException) {
                if (!filefound) {
                    JOptionPane.showMessageDialog(null, "Error Opening File",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (ClassNotFoundException e) {
            };

            toClient.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        } finally {
            // close streams and socket
            if (fromClient != null) {
                fromClient.close();
            }
            if (toClient != null) {
                toClient.close();
            }
            if (client != null) {
                client.close();
            }
        }
    }

    public File[] addparent(File[] files, File parent) {
        File[] allfiles = new File[files.length + 1];
        allfiles[0] = parent;
        for (int i = 0; i < files.length; i++) {
            allfiles[i + 1] = files[i];
        }
        return allfiles;
    }

    public boolean isValidFile(File current) {
        Book record;
        try {
            System.out.println("Validating file at: " + current.getPath());
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(current));
            record = (Book) input.readObject();
            input.close();
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            return false;
        } catch (RuntimeException e) {
            System.out.println(e);
            return false;
        }
    }

}
