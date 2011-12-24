package skydata.interfaces;

import java.io.IOException;
import java.io.InputStream;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Handler;
import skydata.android.*;
import skydata.core.FolderTree.RootFolder;
import skydata.core.WebService.DirList;
import skydata.core.WebService.Login;
import skydata.core.threads.SyncQueue;

public class Interface {

	private static Handler handler;
	private static Object c;
	private static ArrayList<Listener> lst;
	private static Context cont;
	
	public static void setHandler(Handler handler) {
		Interface.handler = handler;
	}
	
	public static void setClass(Object c) {
		Interface.c = c;
	}
	
	public static void logIn() {
		handler.post(new Runnable() {
			  @Override
			  public void run() {
			    ((Main)c).logIn();
			  }
		});	
	}

	public static void createListeners(ArrayList<RootFolder> folders,ArrayList<SyncQueue> queues) {
		lst = new ArrayList<Listener>();
		for(int i = 0; i < folders.size(); ++i) {
			String path = folders.get(i).getPath();
			Listener l = new Listener(path,queues.get(i));
			l.startWatching();
			lst.add(l);
		}
	}

	public static void dirListCallback(ArrayList<TreeMap<String, Object>> list) {
		final String[] folders = new String[list.size()];
		for(int i = 0; i < list.size(); ++i) {
			folders[i] = (String) list.get(i).get("name");
		}
		handler.post(new Runnable() {
			  @Override
			  public void run() {
			    //((Folders)c).setFolders(folders);
			  }
		});	
	}

	public static void removeListeners() {
		for(int i = 0; i < lst.size(); ++i) {
			lst.get(i).stopWatching();
		}
		lst.clear();
	}

	public static void dirListChanged() {
		handler.post(new Runnable() {
			  @Override
			  public void run() {
			    ((Folders)c).updateFolders();
			  }
		});
	}
	
	public static void setContext(Context context) {
		cont = context;
	}
	
	public static void loadTrustStore() {
		try {
			InputStream mIS = cont.getResources().openRawResource(R.raw.androidtruststore);
			KeyStore keyStore = KeyStore.getInstance("BKS");
			keyStore.load(mIS, "skypass".toCharArray());        
			
			KeyManagerFactory mKMF = KeyManagerFactory.getInstance("X509");
			mKMF.init(keyStore, "androidtruststore".toCharArray());
			KeyManager[] mKM = mKMF.getKeyManagers();
			SSLContext sc = SSLContext.getInstance("TLS");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
			tmf.init(keyStore);
			sc.init(mKM, tmf.getTrustManagers(), new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
