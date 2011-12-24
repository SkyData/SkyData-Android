/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core.FolderTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import skydata.core.FileScanner;

/**
 *
 * @author ivan.nikolic
 */
public class FileNode extends Node implements Serializable {
    private String hash;

    public FileNode(Date localdate, File file) {
        this.localDate = localdate;
        this.file = file;
    }

    public FileNode(Date localdate) {
        this.localDate = localdate;
    }

    public FileNode() {
        
    }

    public static String md5(File f) {
        String signature = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            try {
                InputStream is = new FileInputStream(f);
                is = new DigestInputStream(is, md5);
                byte[] buffer = new byte[8192];
                while (is.read(buffer) != -1){}
            } catch (IOException ex) {
                Logger.getLogger(FileScanner.class.getName()).log(Level.SEVERE, null, ex);
            }
            signature = new BigInteger(1, md5.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FileScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return signature;
    }

    public String getHash() {
        if(hash == null || hash.equals("")) {
            hash = md5(file);
        }
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
