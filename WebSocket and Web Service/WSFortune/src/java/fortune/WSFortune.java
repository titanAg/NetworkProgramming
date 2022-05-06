/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fortune;
import java.util.Vector;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author Kyle
 */
@WebService(serviceName = "WSFortune")
@Stateless()
public class WSFortune {
    public static final int SIZE = 3;
    private Vector now = new Vector(SIZE);
    private Vector later = new Vector(SIZE);
    
    public WSFortune() {
        now.addElement("A friend is near");
        now.addElement("Expect a call");
        now.addElement("Someone misses you");
        later.addElement("Wealth awaits -- if you desire it.");
        later.addElement("Climb the hill of effort for high grades.");
        later.addElement("The door to success is open to you.");
  }
    
    private Vector find(String when) {
        if (when.equals("now"))
           return now;
        else 
            return later;
    }
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getFortune")
    public String getFortune(@WebParam(name = "when") String when) {
            int number = (int)(3*Math.random());
            Vector fortunes = find(when);
            return (String)fortunes.elementAt(number);
        //TODO write your implementation code here:    
    }
}
