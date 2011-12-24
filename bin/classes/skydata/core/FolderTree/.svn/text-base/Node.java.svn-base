/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core.FolderTree;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ivan
 */
public abstract class Node implements Serializable {
    protected Date localDate;
    protected Date remoteDate;
    public final static int LOCAL = 0;
    public final static int REMOTE = 1;
    protected File file;

    public Date getDate(int location) {
        if(location == 0) return localDate;
        return remoteDate;
    }

    public void setLocalDate(Date localDate) {
        this.localDate = localDate;
    }

    public void setRemoteDate(Date remoteDate) {
        this.remoteDate = remoteDate;
    }

    public String getName() {
        return file.getName();
    }

    public String getParent() {
        return file.getParent();
    }

    public String getPath() {
        return file.getAbsolutePath();
    }

    public void setFile(File f) {
        file = f;
    }
    
    public File getFile() {
        return file;
    }
}
