/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core;

import java.io.File;
import java.util.Date;
import skydata.core.FolderTree.FileNode;
import skydata.core.FolderTree.FolderNode;
import skydata.core.FolderTree.RootFolder;

/**
 *
 * @author ivan
 */
public class FileScanner {

    public static FolderNode scan(String folder) {
        File dir = new File(folder);
        Date d1 = new Date(dir.lastModified());
        FolderNode fn = new FolderNode(d1,dir);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String filename = files[i].getAbsolutePath();
                String name = files[i].getName();
                //System.out.println("scanning "+name);
                if (!name.endsWith("-conflict")) {
                    if (files[i].isFile()) {
                        Date d2 = new Date(files[i].lastModified());
                        FileNode file;
                        file = new FileNode(d2,files[i]);
                        fn.addNode(name, file);
                    } else {
                        FolderNode fn2 = scan(filename);
                        fn.addNode(name, fn2);
                    }
                }
            }
        }
        return fn;
    }
    
    public static RootFolder scanRoot(String folder) {
        FolderNode f = scan(folder);
        RootFolder res = new RootFolder(f.getDate(0), true, f.getMap());
        res.setFile(f.getFile());
        return res;
    }
}
