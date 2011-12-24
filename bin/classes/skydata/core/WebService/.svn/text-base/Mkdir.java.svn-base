package skydata.core.WebService;

import java.text.SimpleDateFormat;
import java.util.Date;
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
public class Mkdir {
    private static final String SOAP_ACTION = "http://example.org/";
    private static final String METHOD_NAME = "mkdir";
    private static final String NAMESPACE = "http://example.org";
    private static final String URL = "https://skydata.no-ip.info/skydata/www/ws/serverMkdir.php";

    
    public static Date mkdir(String path) {
        try {
            path = path.replace('\\', '/');
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("sessionid", Login.token);
            request.addProperty("path", path);
            //System.out.println(path);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            
            Interface.loadTrustStore();
            
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            
            SoapObject msg = (SoapObject) envelope.bodyIn;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = (String) msg.getProperty(0);
            Date date = format.parse(strDate);
            //System.out.println(date);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
