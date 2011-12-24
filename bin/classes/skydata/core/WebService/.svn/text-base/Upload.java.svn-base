/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core.WebService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import skydata.interfaces.Interface;

/**
 *
 * @author ivan.nikolic
 */
public class Upload {

    public static Date upload (File sourceFile,String folder,String path, String owner) {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        path = path.replace('\\', '/');
            
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        Interface.loadTrustStore();
        
        String upLoadServerUri = "https://skydata.no-ip.info/skydata/www/ws/upload.php";

        String fileName = sourceFile.getName();
        if (!sourceFile.isFile()) {
            //System.out.println("not file!");
            return null;
        }
        int serverResponseCode = 0;
        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(upLoadServerUri);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy

            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            


            // conn.setFixedLengthStreamingMode(1024);
            // conn.setChunkedStreamingMode(1);

            dos = new DataOutputStream(conn.getOutputStream());
    
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + "token" + "\""+ lineEnd+lineEnd + Login.token);
            dos.writeBytes(lineEnd);
            
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + "path" + "\""+ lineEnd+lineEnd+ path);
            dos.writeBytes(lineEnd);
            
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + "folder" + "\""+ lineEnd+lineEnd+ folder);
            dos.writeBytes(lineEnd);
            
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + "owner" + "\""+ lineEnd+lineEnd+ owner);
            dos.writeBytes(lineEnd);
            
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + "newfile" + "\"; filename=\"" + fileName+ "\""+ lineEnd);
            dos.writeBytes(lineEnd);
            
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();

            int streamSize = (int) sourceFile.length();
            bufferSize = streamSize / 10;

            //System.out.println("streamSize" + streamSize);

            buffer = new byte[streamSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            int count = 0;
            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                // bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                count += 10;

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            //System.out.println("Upload file to serverHTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            // close streams
            //System.out.println("Upload file to server" + fileName + " File is written");
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            //System.out.println("error in upload");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // this block will give the response of upload link
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String strDate = null;
            while ((line = rd.readLine()) != null) {
                //System.out.println("RESULT Message: " + line);
                strDate = line;
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha = format.parse(strDate);
            rd.close();
            return fecha;
        } catch (ParseException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioex) {
            //System.out.println("erroooooooooor");
        }
        return null;
    }
}
