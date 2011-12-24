/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core.WebService;

import java.io.IOException;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.ServiceConnectionSE;

/**
 *
 * @author ivan.nikolic
 */


public class HttpTransportBasicAuth extends HttpTransportSE {
     private String username;
        private String password;
        /**
         * Constructor with username and password
         *
         * @param url
         *            The url address of the webservice endpoint
         * @param username
         *            Username for the Basic Authentication challenge RFC 2617
         * @param password
         *            Password for the Basic Authentication challenge RFC 2617
         */
    public HttpTransportBasicAuth(String url, String username, String password) {
        super(url);
        this.username = username;
        this.password = password;
    }

    @Override
    protected ServiceConnection getServiceConnection() throws IOException {
        ServiceConnectionSE midpConnection = new ServiceConnectionSE(url);
        addBasicAuthentication(midpConnection);
        return midpConnection;
    }

    protected void addBasicAuthentication(ServiceConnection midpConnection) throws IOException {
        if (username != null && password != null) {
            StringBuffer buf = new StringBuffer(username);
            buf.append(':').append(password);
            byte[] raw = buf.toString().getBytes();
            buf.setLength(0);
            buf.append("Basic ");
            org.kobjects.base64.Base64.encode(raw, 0, raw.length, buf);
            midpConnection.setRequestProperty("Authorization", buf.toString());
        }
    }
}
