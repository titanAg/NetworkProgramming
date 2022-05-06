/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsfortuneclient;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Kyle
 */
public class WsFortuneClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        try {
            String selection = "";
            int count = 0;
            do {
                count++;
                System.out.print("'now' or 'later' fortune message? ");
                selection = in.next();
                while (!selection.equals("later") && !selection.equals("now")) {
                    System.out.print("Bad selection, must enter 'now' or 'later' - try again: ");
                    selection = in.next();
                }
                System.out.println(selection + " message ==> " + getFortune(selection));
                System.out.print("Continue? [y or n]: ");
                selection = in.next();
                while (!selection.equals("y") && !selection.equals("n")) {
                    System.out.print("Bad selection, must enter 'n' or 'y' - try again: ");
                    selection = in.next();
                }
            }while(!selection.equals("n"));
            System.out.println("You received " + count + " fortunes, spooky...");
        }
        catch (InputMismatchException e) {
            System.out.println(e.getStackTrace());
        }
    }

    private static String getFortune(java.lang.String when) {
        fortune.WSFortune_Service service = new fortune.WSFortune_Service();
        fortune.WSFortune port = service.getWSFortunePort();
        return port.getFortune(when);
    }
    
}
