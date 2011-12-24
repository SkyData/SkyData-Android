/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core.WebService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import skydata.interfaces.Interface;

/**
 *
 * @author ivan.nikolic
 */
public class Download {

    public static void download (File destFile,String folder,String path,String owner) {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        path = path.replace('\\', '/');
        //System.out.println("name: "+destFile.getName()+ "   folder: " + folder + "   path: " + path);
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        //Interface.loadTrustStore();
        String upLoadServerUri = "http://skydata.no-ip.info/skydata/www/ws/download.php";

        String fileName = destFile.getName();
        int serverResponseCode = 0;
        try {
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
            dos.writeBytes("Content-Disposition: form-data; name=\"" + "name" + "\""+ lineEnd+lineEnd+ fileName);
            dos.writeBytes(lineEnd);
            
            
            // create a buffer of maximum size
            //bytesAvailable = fileOutputStream.available();

            //int streamSize = (int) sourceFile.length();
            /*
            bufferSize = streamSize / 10;

            buffer = new byte[streamSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            int count = 0;
            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                //bytesAvailable = fileInputStream.available();
                // bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                count += 10;

            }

            // send multipart form data necesssary after file data...
*/          //dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            //System.out.println("Download serverHTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            // close streams
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            //System.out.println("error in upload");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // this block will give the response of download link
        try {
            //destFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            //BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            InputStream is = new BufferedInputStream(conn.getInputStream());
            ByteArrayOutputStream bout = new ByteArrayOutputStream(10000);
            //String line;
            buffer = new byte[1024];
            int b;
            while ((b = is.read()) != -1) {
                //System.out.println(b);
                //fileOutputStream.write(buffer);
                bout.write(b);
            }
            fileOutputStream.write(bout.toByteArray());
            //rd.close();
            fileOutputStream.close();
        } catch (IOException ioex) {
            //System.out.println("erroooooooooor");
        }
    }
}
