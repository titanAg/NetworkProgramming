/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.ws;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.*;
import javax.sql.DataSource;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocketendpoint")
public class WsServer {
	Connection connection;
        ArrayList<String> quotes;
        String out = "";
	@OnOpen
	public void onOpen(){
            try {
                System.out.println("Open Connection ...");
                Context ctx = (Context) (new InitialContext().lookup("java:comp/env"));
                DataSource ds = (DataSource) ctx.lookup("jdbc/quotesDB");
                connection = ds.getConnection();
                System.out.println("Connection: " + connection.toString());
                
                String query = "SELECT * FROM quotesDB.quotes";
                Statement st = connection.createStatement();
                ResultSet res = st.executeQuery(query);
                quotes = new ArrayList<>();
                while(res.next()) {
                    quotes.add(res.getString("message"));
                }
                
                
            } catch (NamingException ex) {
                Logger.getLogger(WsServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(WsServer.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	
	@OnClose
	public void onClose(){
		System.out.println("Close Connection ...");
	}
	
	@OnMessage
	public String onMessage(String message) {
            System.out.println("Message from the client: " + message);
            Random rand = new Random();
            int select = rand.nextInt(quotes.size());
            
            String outMessage = message.equals("quote") ? "Quote: " + quotes.get(select) : "Date: " + new Date().toString();
            System.out.println("Out message to client: " + outMessage);
            return outMessage;
	}

	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
	}

}

