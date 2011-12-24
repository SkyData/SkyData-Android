package skydata.core.WebService;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import skydata.interfaces.Interface;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ivan.nikolic
 */
public class Login implements Runnable{
    private static final String SOAP_ACTION = "http://example.org/";
    private static final String METHOD_NAME = "logIn";
    private static final String NAMESPACE = "http://example.org";
    private static final String URL = "https://skydata.no-ip.info/skydata/www/ws/serverLogin.php";
    
    public static String user;
    private static String pass;
    public static String token;
      
    public Login(String username, String password) {
        user = username;
        pass = password;
    }
    
    public static String doLogin(String username, String password) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("username", username);
            request.addProperty("password", password);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            
            Interface.loadTrustStore();
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            
            String s = (String) envelope.getResponse();
            if(!s.equals("Wrong Username or Password")) {
                token = s;
                user = username;
                pass = password;
            }
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void run() {
        doLogin(user,pass);
        Interface.logIn();
    }
}
