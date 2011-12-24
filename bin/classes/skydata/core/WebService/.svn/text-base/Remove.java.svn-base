package skydata.core.WebService;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import skydata.interfaces.Interface;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ivan.nikolic
 */
public class Remove{
    private static final String SOAP_ACTION = "http://example.org/";
    private static final String METHOD_NAME = "remove";
    private static final String NAMESPACE = "http://example.org";
    private static final String URL = "https://skydata.no-ip.info/skydata/www/ws/serverRemove.php";
    
    public static void Remove(String path,String owner) {
        try {
            path = path.replace('\\', '/');
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("sessionid", Login.token);
            request.addProperty("path", path);
            request.addProperty("owner", owner);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            
            Interface.loadTrustStore();
            
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            
            String s = (String) envelope.getResponse();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
