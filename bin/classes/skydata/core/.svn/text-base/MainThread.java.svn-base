/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import skydata.core.FolderTree.RootFolder;
import skydata.core.FolderTree.TreeFunctions;
import skydata.core.WebService.DirList;
import skydata.core.threads.SyncQueue;
import skydata.core.threads.SyncThread;
import skydata.interfaces.Interface;
import skydata.core.FolderTree.Node;
import skydata.core.WebService.Login;

/**
 *
 * @author ivan.nikolic
 */
public class MainThread implements Runnable {

    private LocalConf conf;
    private boolean finish;

    private ArrayList<RootFolder> folders;
    private ArrayList<SyncQueue> queues;
    private ArrayList<SyncThread> syncthreads;

    public MainThread(LocalConf conf) {
        this.conf = conf;
        folders = new ArrayList<RootFolder>();
        queues = new ArrayList<SyncQueue>();
        syncthreads = new ArrayList<SyncThread>();
        finish = false;
    }

    public void addFolder(String path) {
        SyncQueue q = new SyncQueue();
        queues.add(q);
        //RootFolder f = FileScanner.scanRoot(path);
        TreeMap<String,TreeMap<String,String>> map = conf.getFoldersMap();
        RootFolder f = TreeFunctions.readTree((new File(path)).getName());
        if(f == null)f = new RootFolder();
        f.setFile(new File(path));
        f.setWrite(map.get(f.getName()).get("write").equals("true"));
        f.setOwner(map.get(f.getName()).get("owner"));
        TreeFunctions.writeTree(f);
        folders.add(f);
        SyncThread sh = new SyncThread(q, f);
        syncthreads.add(sh);
        Thread th = new Thread(sh);
        th.start();
        Interface.removeListeners();
        Interface.createListeners(folders, queues);
    }

    public void removeFolder(int ind) {
        queues.get(ind).prepareDeath();
        queues.remove(ind);
        folders.remove(ind);
        syncthreads.get(ind).finish();
        syncthreads.remove(ind);
        Interface.removeListeners();
        Interface.createListeners(folders, queues);
    }

    public void removeFolder(String path) {
        int i = 0;
        while(i < folders.size() && !folders.get(i).getPath().equals(path)) ++i;
        if (i < folders.size()) removeFolder(i);
    }
    
    public void removeFolderByName(String name) {
        int i = 0;
        while(i < folders.size() && !folders.get(i).getName().equals(name)) ++i;
        if (i < folders.size()) removeFolder(i);
    }

    public void finish() {
        finish = true;
    }
    
    public void run() {

        ArrayList<ArrayList<String>> names = conf.getFolders();

        for (int i = 0; i < names.size(); ++i) {
            if (names.get(i).get(2).equals("1")) {
                RootFolder rf = TreeFunctions.readTree(names.get(i).get(0));
                if (rf == null) {
                    names.get(i).set(2, "0");
                    conf.setFolders(names);
                }
                else folders.add(rf);
            }
        }

        for (int i = 0; i < folders.size(); ++i) {
            queues.add(new SyncQueue());
            syncthreads.add(new SyncThread(queues.get(i), folders.get(i)));
            Thread th = new Thread(syncthreads.get(i));
            th.start();
        }
        
        Interface.createListeners(folders, queues);

        while(!finish) {
            //check folder changes in server
            ArrayList<TreeMap<String,Object>> list = DirList.getList();
            if (list != null) {
                TreeMap<String,TreeMap<String,String>> map = conf.getFoldersMap();
                TreeMap<String,TreeMap<String,String>> map2 = new TreeMap<String,TreeMap<String,String>>();
                boolean save = false;
                for(int i = 0; i < list.size(); ++i) {
                    if(map.get((String)list.get(i).get("name")) == null) {
                        TreeMap<String,String> temp = new TreeMap<String,String>();
                        temp.put("path", "");
                        temp.put("sync", "0");
                        temp.put("write",list.get(i).get("write").toString());
                        temp.put("owner",(String)list.get(i).get("owner"));
                        map2.put((String)list.get(i).get("name"), temp);
                        save = true;
                    }
                    else {
                        map2.put((String)list.get(i).get("name"), map.get((String)list.get(i).get("name")));
                    }
                }
                Iterator it = map.entrySet().iterator();
                while(it.hasNext()) {
                    Map.Entry e = (Map.Entry)it.next();
                    if(map2.get((String)e.getKey()) == null) {
                        save = true;
                        removeFolderByName((String)e.getKey());
                    }
                }
                if (save) {
                    conf.setFoldersMap(map2);
                    Interface.dirListChanged();
                }

                //Folders update
                for (int i = 0; i < folders.size(); ++i) {
                    Date d = null;
                    for(int j = 0; j < list.size(); ++j) {
                        if(list.get(j).get("name").equals(folders.get(i).getName())) {
                            d = (Date) list.get(j).get("date");
                        }
                    }
                    if(folders.get(i).getDate(Node.REMOTE) == null ||
                       !folders.get(i).getDate(Node.REMOTE).equals(d)) {
                        synchronized(queues.get(i)) {
                            //System.out.println("change detected");
                            queues.get(i).setRemoteDate(d);
                            queues.get(i).notifyAll();
                        }
                    }
                }
            }
            
            //sleep
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //matar threads
        for (int i = 0; i < folders.size(); ++i) removeFolder(i);
        //Interface.removeListeners();
    }

}
