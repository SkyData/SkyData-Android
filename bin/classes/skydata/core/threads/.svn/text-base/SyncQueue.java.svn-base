/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core.threads;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import skydata.core.FolderTree.Diff;
import skydata.core.FolderTree.FolderNode;

/**
 *
 * @author ivan.nikolic
 */
public class SyncQueue {
    TreeMap<String,Diff> waitMap;
    private boolean finish;
    private Date remote;

    public SyncQueue() {
        finish = false;
        waitMap = new TreeMap<String,Diff>();
    }

    public synchronized void prepareDeath() {
        finish = true;
        notifyAll();
    }

    public synchronized  ArrayList<Diff> getNext() {
        if (waitMap.isEmpty() && !finish) {
            try {
                //System.out.println("zzzz");
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(SyncQueue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ArrayList<Diff> res = new ArrayList<Diff>();
        Iterator it = waitMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            res.add((Diff)e.getValue());
        }
        waitMap = new TreeMap<String,Diff>();
        return res;
    }

    public synchronized void addDiff(Diff d) {
        waitMap.put(d.getPath(), d);
        notifyAll();
    }

    public synchronized void setRemoteDate(Date date) {
        remote = date;
    }
    
    public synchronized Date getRemoteDate() {
        return remote;
    }
}
