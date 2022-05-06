/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;

public class RMIQuoteServer {
    public static void main(String[] args) throws IOException {
        new RMIQuoteServerThread().start();
    }
}

