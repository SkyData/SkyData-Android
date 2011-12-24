/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core.FolderTree;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 *
 * @author ivan.nikolic
 */
public class FolderNode extends Node implements Serializable {
    protected TreeMap<String,Node> m;

    public FolderNode() {
        m = new TreeMap<String,Node>();
    }

    public FolderNode(Date localdate,File folder) {
        this(localdate,new TreeMap<String,Node>());
        this.file = folder;
    }

    public FolderNode(Date localdate, TreeMap<String,Node> m) {
        this.m = m;
        this.localDate = localdate;
    }

    public void addRecursive(Node node, String path) {
        String p[] = null;
        String p2[] = null;
        if (File.separator.equals("\\")) {
            Pattern pt = Pattern.compile("\\\\|\\/");
            p = pt.split(path);
            p2 = pt.split(getPath());
        }
        else {
            p =path.split(File.separator);
            p2 = getPath().split(File.separator);
        }
        int i = p.length-p2.length;
        if(i > 0) {
            FolderNode n = ((FolderNode)m.get(p[p2.length]));
            if(n == null) {
                int index = path.indexOf(File.separator, getPath().length()+1);
                String subp = "";
                if(index == -1) subp = path;
                else path.substring(0,index);
                n = new FolderNode(node.getDate(FileNode.LOCAL),new File(subp));
                addNode(p[p2.length],n);
            }
            n.addRecursive(node,path);
        }
        else {
            addNode(node.getName(),node);
        }
    }
    
    public FolderNode getParentRec(Node node, String path) {
        String p[] = null;
        String p2[] = null;
        if (File.separator.equals("\\")) {
            Pattern pt = Pattern.compile("\\\\|\\/");
            p = pt.split(path);
            p2 = pt.split(getPath());
        }
        else {
            p =path.split(File.separator);
            p2 = getPath().split(File.separator);
        }
        int i = p.length-p2.length;
        if(i > 0) {
            FolderNode n = ((FolderNode)m.get(p[p2.length]));
            if (n == null) return null;
            return n.getParentRec(node,path);
        }
        else {
            return this;
        }
    }
    
    public boolean isDuplicate(Node node) {
        String p[] = null;
        String p2[] = null;
        if (File.separator.equals("\\")) {
            Pattern pt = Pattern.compile("\\\\|\\/");
            p = pt.split(node.getPath());
            p2 = pt.split(getPath());
        }
        else {
            p = node.getPath().split(File.separator);
            p2 = getPath().split(File.separator);
        }
        int i = p.length-p2.length;
        if(i > 1) {
            FolderNode n = ((FolderNode)m.get(p[p2.length]));
            if(n == null) {
                return false;
            }
            return ((FolderNode)m.get(p[p2.length])).isDuplicate(node);
        }
        else if(i == 1){
            if(m.get(p[p2.length]) != null) {
                return ((Node)m.get(p[p2.length])).getDate(Node.LOCAL).equals(node.getDate(Node.LOCAL));
            } else return false;
        }
        else {
            return getDate(Node.LOCAL).equals(node.getDate(Node.LOCAL));
        }
    }

    public TreeMap<String, Node> getMap() {
        return m;
    }

    public ArrayList<Node> getList() {
        return (ArrayList<Node>) m.values();
    }

    public FolderNode getFolder(String folder) {
        return (FolderNode) m.get(folder);
    }

    public FileNode getFile(String file) {
        return (FileNode) m.get(file);
    }
    
    public Class getClass(String name) {
        return m.get(name).getClass();
    }

    public void addNode(String name, Node node) {
        m.put(name, node);
    }

    public void removeNode(String name) {
        m.remove(name);
    }

}
