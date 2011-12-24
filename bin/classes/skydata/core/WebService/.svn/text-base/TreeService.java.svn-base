/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core.WebService;

import java.io.File;
import skydata.core.FolderTree.FolderNode;

/**
 *
 * @author ivan.nikolic
 */
public class TreeService {

    private String rootPath;

    public TreeService(String rootPath) {
        this.rootPath = rootPath;
    }


    public FolderNode getNode(String path,String owner) {
        File f = new File(rootPath);
        String name = f.getName();
        String relative = path.substring(rootPath.length());
        String newPath = "";
        if (relative.startsWith(File.separator)) {
            newPath = name + relative;
        } else {
            newPath = name + File.separator + relative;
        }
        newPath = newPath.replace('\\', '/');
        return RemoteDir.ls(newPath, path,owner);
    }

    public FolderNode getRoot(String owner) {
        File f = new File(rootPath);
        String name = f.getName();
        //System.out.println(name + " " + rootPath);
        return RemoteDir.ls(name, rootPath,owner);
    }

    public void remove(String path,String owner) {
        File f = new File(rootPath);
        String name = f.getName();
        String relative = path.substring(rootPath.length());
        String newPath = "";
        if (relative.startsWith(File.separator)) {
            newPath = name + relative;
        } else {
            newPath = name + File.separator + relative;
        }
        Remove.Remove(newPath,owner);
    }

}
