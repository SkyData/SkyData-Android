/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core.FolderTree;

import java.io.Serializable;
import java.util.Date;
import java.util.TreeMap;

/**
 *
 * @author ivan
 */
public class RootFolder extends FolderNode implements Serializable {
    private boolean writePermission;
    private String owner;

    public RootFolder() {
        localDate = new Date(0);
        m = new TreeMap<String,Node>();
    }

    public RootFolder(Date localdate, boolean writePermission, TreeMap<String,Node> m) {
        super(localdate,m);
        this.writePermission = writePermission;
        this.m = m;
    }

    public void setWrite(boolean write) {
        writePermission = write;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
}
