/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import skydata.core.FolderTree.RootFolder;
import skydata.interfaces.Interface;

/**
 *
 * @author ivan
 */
public class LocalConf {

    private static Properties p;
    private static String name;
    private static String folder;

    public LocalConf(String n) {
        name = n;
        p = new Properties();
        String fileName = name;
        InputStream is = null;
        //Open config file
        try {
            is = new FileInputStream(fileName);
            File f = new File(name);
            folder = f.getParent();
        } catch (FileNotFoundException ex) {
            //create if doesn't exists
       //Logger.getLogger(LocalConf.class.getName()).log(Level.SEVERE, null, ex);
            File f;
            f = new File(name);
            folder = f.getParent();
            try {
                f.createNewFile();
                setDefault();
            } catch (IOException ex1) {
                Logger.getLogger(LocalConf.class.getName()).log(Level.SEVERE, null, ex1);
            }
            //try to open again the new file
            try {
                is = new FileInputStream(fileName);
            } catch (FileNotFoundException ex1) {
                Logger.getLogger(LocalConf.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        //load configurations
        try {
            p.load(is);
        } catch (IOException ex) {
        	Logger.getLogger(LocalConf.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    public LocalConf() {
        this("config");
    }
    
    public static String getFolder() {
    	return folder;
    }

    public void setDefault() {
        p.setProperty("User", "");
        p.setProperty("Pass", "");
        p.setProperty("Folders", "");
        save();
    }

    private void save() {
        try {
            p.store(new FileOutputStream(name), null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LocalConf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LocalConf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getUser() {
        return p.getProperty("User");
    }

    public String getPass() {
    	return p.getProperty("Pass");
    }

    public ArrayList<ArrayList<String>> getFolders() {
        String s = p.getProperty("Folders");
        String folders[] = s.split(";");
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < folders.length; ++i) {
            if (!folders[i].equals("")) {
                String fold[] = folders[i].split("\"");
                ArrayList<String> res2 = new ArrayList<String>();
                res2.add(fold[0]);
                res2.add(fold[1]);
                res2.add(fold[2]);
                res2.add(fold[3]);
                res2.add(fold[4]);
                res.add(res2);
            }
        }
        //System.out.println("getFolders " + res);
        return res;
    }
    
    public TreeMap<String,TreeMap<String,String>> getFoldersMap() {
        String s = p.getProperty("Folders");
        String folders[] = s.split(";");
        TreeMap<String,TreeMap<String,String>> res = new TreeMap<String,TreeMap<String,String>>();
        for(int i = 0; i < folders.length; ++i) {
            s = folders[i];
            if (!folders[i].equals("")) {
                String fold[] = s.split("\"");
                TreeMap<String,String>res2 = new TreeMap<String,String>();
                res2.put("path",fold[1]);
                res2.put("sync",fold[2]);
                res2.put("write",fold[3]);
                res2.put("owner",fold[4]);
                res.put(fold[0],res2);
            }
        }
        //System.out.println("getFoldersMap " + res);
        return res;
    }
    
    public void setUser(String s) {
        p.setProperty("User", s);
        save();
    }
    
    public void setPass(String s) {
    	p.setProperty("Pass", s);
        save();
    }
    
    public void setFolders(ArrayList<ArrayList<String>> list) {
        String s = "";
        for(int i = 0; i < list.size(); ++i) {
            if(i != 0) s = s+";";
            s = s+list.get(i).get(0)+"\""+list.get(i).get(1)+"\""+list.get(i).get(2)+"\""+list.get(i).get(3)+"\""+list.get(i).get(4);
        }
    	p.setProperty("Folders",s);
        //System.out.println("setFolders " + s);
        save();
    }
    
    public void setFoldersMap(TreeMap<String,TreeMap<String,String>> map) {
        String s = "";
        Iterator it = map.entrySet().iterator();
        int i = 0;
        while(it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            if(i != 0) s = s+";";
            TreeMap<String,String> val = (TreeMap<String,String>)e.getValue();
            s = s+e.getKey()+"\""+val.get("path")+"\""+val.get("sync")+"\""+val.get("write")+"\""+val.get("owner");
            i++;
        }
        p.setProperty("Folders",s);
        //System.out.println("setFoldersMap " + s);
        save();
    }

    public boolean exists() {
        File f = new File(name);
        return f.exists();
    }
}
