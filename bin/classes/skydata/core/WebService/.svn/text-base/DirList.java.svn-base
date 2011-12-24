/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skydata.core.WebService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import skydata.interfaces.Interface;

/**
 *
 * @author ivan
 */
public class DirList implements Runnable{
    private static final String SOAP_ACTION = "http://example.org/";
    private static final String METHOD_NAME = "getRootFolders";
    private static final String NAMESPACE = "http://example.org";
    private static final String URL = "https://skydata.no-ip.info/skydata/www/ws/serverGetrootfolders.php";
      
    
    
    public static ArrayList<TreeMap<String,Object>> getList() {
        ArrayList<TreeMap<String,Object>> res = null;
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("token", Login.token);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            
            Interface.loadTrustStore();
            
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            
            SoapObject msg = (SoapObject) envelope.bodyIn;
            SoapObject all = (SoapObject) msg.getProperty(0);
            SoapObject folders = null;
            SoapObject folders2 = null;
            //System.out.println(msg);

            res = new ArrayList<TreeMap<String,Object>> ();
            
            for(int i = 0; i < all.getPropertyCount(); ++i) {
                if(((SoapObject)all.getProperty(i)).getProperty("key").equals("folders")) {
                    folders = (SoapObject) all.getProperty(i);
                } else {
                    folders2 = (SoapObject) all.getProperty(i);
                }
            }
            /*
            if(all.getPropertyCount() > 0) {
                folders = (SoapObject) all.getProperty(0);
                if(all.getPropertyCount() > 1) {
                    folders2 = (SoapObject) all.getProperty(1);
                }
            }*/
            
            if(folders != null) {
                Vector v = (Vector)folders.getProperty("value");
                //System.out.println(Login.token);
                //System.out.println(envelope.bodyIn);
                if (v != null) {
                    for(int i = 0; i < v.size(); ++i) {
                        SoapObject map = (SoapObject) v.get(i);
                        TreeMap<String,Object> mapitem = new TreeMap<String,Object>();
                        for(int j = 0; j < map.getPropertyCount(); ++j) {
                            SoapObject item = (SoapObject) map.getProperty(j);
                            String key = (String)item.getProperty("key");
                            if(key.equals("date")) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String strFecha = (String)item.getProperty("value");
                                Date fecha = format.parse(strFecha);
                                mapitem.put(key,fecha);
                            } else {
                                mapitem.put(key,item.getProperty("value"));
                            }
                        }
                        mapitem.put("owner", Login.user);
                        mapitem.put("write", true);
                        res.add(mapitem);
                    }
                }
            }
            if(folders2 == null) return res;
            Vector v = (Vector)folders2.getProperty("value");
            //System.out.println(Login.token);
            //System.out.println(envelope.bodyIn);
            if (v == null) return res;
            for(int i = 0; i < v.size(); ++i) {
                SoapObject map = (SoapObject) v.get(i);
                TreeMap<String,Object> mapitem = new TreeMap<String,Object>();
                for(int j = 0; j < map.getPropertyCount(); ++j) {
                    SoapObject item = (SoapObject) map.getProperty(j);
                    String key = (String)item.getProperty("key");
                    if(key.equals("date")) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String strFecha = (String)item.getProperty("value");
                        Date fecha = format.parse(strFecha);
                        mapitem.put(key,fecha);
                    } else {
                        mapitem.put(key,item.getProperty("value"));
                    }
                }
                if(mapitem.get("type").equals("public")) mapitem.put("write",false);
                else mapitem.put("write",true);
                res.add(mapitem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public void run() {
        Interface.dirListCallback(getList());
    }
}
