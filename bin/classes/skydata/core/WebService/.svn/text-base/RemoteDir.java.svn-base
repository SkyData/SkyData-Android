package skydata.core.WebService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import skydata.core.FolderTree.FileNode;
import skydata.core.FolderTree.FolderNode;
import skydata.core.FolderTree.Node;
import skydata.interfaces.Interface;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ivan.nikolic
 */
public class RemoteDir{
    private static final String SOAP_ACTION = "http://example.org/";
    private static final String METHOD_NAME = "lsFolder";
    private static final String NAMESPACE = "http://example.org";
    private static final String URL = "https://skydata.no-ip.info/skydata/www/ws/serverLsfolder.php";
      
    public static FolderNode ls(String path,String localpath,String owner) {
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
            
            FolderNode fn = new FolderNode();
            fn.setFile(new File(localpath));
            
            SoapObject msg = (SoapObject) envelope.bodyIn;
            //System.out.println(msg);
            SoapObject fileFolder = (SoapObject) msg.getProperty(0);
            if(fileFolder == null) return fn;
            SoapObject folder = null;
            SoapObject file = null;
            //System.out.println(fileFolder);
            for(int i = 0; i < fileFolder.getPropertyCount(); ++i) {
                if(((SoapObject)fileFolder.getProperty(i)).getProperty("key").equals("folders")) {
                    folder = (SoapObject) fileFolder.getProperty(i);
                } else {
                    file = (SoapObject) fileFolder.getProperty(i);
                }
            }
            //System.out.println(file);
            //System.out.println(folder);
            /*
            if(fileFolder.getPropertyCount() > 0) {
                file = (SoapObject) fileFolder.getProperty(0);
                if(file.getProperty("key").equals("folders")) {
                    folder = file;
                    file = null;
                }
                if(fileFolder.getPropertyCount() > 1) {
                    folder = (SoapObject) fileFolder.getProperty(1);
                }
            }*/

            if(file != null) {
                Vector v = (Vector)file.getProperty("value");
                for(int i = 0; i < v.size(); ++i) {
                    SoapObject map = (SoapObject) v.get(i);
                    FileNode filen = new FileNode();
                    for(int j = 0; j < map.getPropertyCount(); ++j) {
                        SoapObject item = (SoapObject) map.getProperty(j);
                        String key = (String)item.getProperty("key");
                        if(key.equals("date")) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String strFecha = (String)item.getProperty("value");
                            Date fecha = format.parse(strFecha);
                            filen.setRemoteDate(fecha);
                        } else if(key.equals("name")){
                            filen.setFile(new File(localpath+File.separator+((String)item.getProperty("value"))));
                        } else {
                            filen.setHash((String) item.getProperty("value"));
                        }
                    }
                    fn.addNode(filen.getName(),filen);
                }
            }
            if(folder == null) return fn;
            Vector v = (Vector)folder.getProperty("value");
            for(int i = 0; i < v.size(); ++i) {
                SoapObject map = (SoapObject) v.get(i);
                FolderNode foldn = new FolderNode();
                for(int j = 0; j < map.getPropertyCount(); ++j) {
                    SoapObject item = (SoapObject) map.getProperty(j);
                    String key = (String)item.getProperty("key");
                    if(key.equals("date")) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String strFecha = (String)item.getProperty("value");
                        Date fecha = format.parse(strFecha);
                        foldn.setRemoteDate(fecha);
                    } else {
                        foldn.setFile(new File(localpath+File.separator+((String)item.getProperty("value"))));
                    }
                }
                fn.addNode(foldn.getName(),foldn);
            }
            return fn;
            //for(int i = 0; i < s.getPropertyCount(); ++i) System.out.println(s.getProperty(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
