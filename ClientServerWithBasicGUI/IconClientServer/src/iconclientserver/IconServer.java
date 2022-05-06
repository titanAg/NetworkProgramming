// Kyle Orcutt 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iconclientserver;

/**
 *
 * @author Kyle
 */
public class IconServer {
    public static void main(String[] args) throws java.io.IOException {
        new IconServerThread().start();
    }
}
