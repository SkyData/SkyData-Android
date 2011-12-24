/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core.FolderTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import skydata.core.LocalConf;
import skydata.core.WebService.TreeService;

/**
 *
 * @author ivan
 */
public class TreeFunctions {

    private TreeService ts;

    public TreeFunctions(TreeService ts) {
        this.ts = ts;
    }

    private ArrayList<Diff> insertRec(FolderNode fn,int location,String owner) {
        TreeMap<String, Node> m = fn.getMap();
        Iterator it = m.entrySet().iterator();
        ArrayList<Diff> actions = new ArrayList<Diff>();
        //ArrayList<Diff> recactions = new ArrayList<Diff>();
        while(it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            if(e.getValue().getClass().equals(FolderNode.class)) {
                //recactions.addAll(insertRec((FolderNode)e.getValue()));
                actions.add(new Diff(Diff.ADD,(FolderNode) e.getValue(),null));
                
                FolderNode next;
                FolderNode fno = (FolderNode) e.getValue();
                if(location == Node.REMOTE) next = ts.getNode(fno.getPath(),owner);
                else next = (FolderNode) e.getValue();
                actions.addAll(insertRec(next,location,owner));
            } else {
                actions.add(new Diff(Diff.ADD,(FileNode) e.getValue(),null));
            }
        }
        //actions.addAll(recactions);
        return actions;
    }

    //Location is FileNode Remote or Local, is used to compare dates
    public ArrayList<Diff> createDiff(FolderNode oldf, FolderNode newf,int location,String owner) {
        TreeMap<String, Node> mo = oldf.getMap();
        TreeMap<String, Node> mn = newf.getMap();
        Iterator ito = mo.entrySet().iterator();
        Iterator itn = mn.entrySet().iterator();
        ArrayList<Diff> actions = new ArrayList<Diff>();
        //ArrayList<Diff> recactions = new ArrayList<Diff>();
        boolean itob = true;
        boolean itnb = true;
        Map.Entry eo = null;
        Map.Entry en = null;
        while((ito.hasNext()||!itob) && (itn.hasNext()|| !itnb)) {
            if(itob) eo = (Map.Entry)ito.next();
            if(itnb) en = (Map.Entry)itn.next();
            itob = true;
            itnb = true;
            String so = (String) eo.getKey();
            String sn = (String) en.getKey();
            //System.out.println(so+" "+sn);
            if(!so.equals(sn)) {
                //System.out.println("Diferentes");
                if(sn.compareTo(so) < 0){
                    //System.out.println("Add");
                    //add
                    if(((Node) en.getValue()).getClass().equals(FolderNode.class)) {
                        //recactions.addAll(insertRec((FolderNode) en.getValue()));
                        actions.add(new Diff(Diff.ADD,(FolderNode) en.getValue(),oldf));
                        actions.addAll(insertRec((FolderNode) en.getValue(),location,owner));
                    } else {
                        actions.add(new Diff(Diff.ADD,(FileNode) en.getValue(),oldf));
                    }
                    itob = false;
                }
                else {
                    //System.out.println("Rm");
                    //rm
                    actions.add(new Diff(Diff.REMOVE, (Node) eo.getValue(),oldf));
                    itnb = false;
                }
            }
            else {
                //System.out.println("iguales");
                if(((Node) eo.getValue()).getClass().equals(FolderNode.class)) {
                    FolderNode fno = (FolderNode) eo.getValue();
                    FolderNode fnn = (FolderNode) en.getValue();
                    //System.out.println("Carpeta");
                    //System.out.println(fno.getName());
                    if(fno.getDate(location).compareTo(fnn.getDate(location)) != 0) {
                        FolderNode next;
                        if(location == Node.REMOTE) next = ts.getNode(fno.getPath(),owner);
                        else next = (FolderNode) en.getValue();
                        //recactions.addAll(createDiff((FolderNode)eo.getValue(), next,location));
                        actions.addAll(createDiff((FolderNode)eo.getValue(), next,location,owner));
                    }
                } else {
                    //System.out.println("Archivo");
                    FileNode fno = (FileNode) eo.getValue();
                    FileNode fnn = (FileNode) en.getValue();
                    if(fno.getDate(location).compareTo(fnn.getDate(location)) < 0) {
                        //System.out.println("feeeeech");
                        if(fno.getHash().compareTo(fnn.getHash()) != 0) {
                            //System.out.println("fuuuuuu");
                            actions.add(new Diff(Diff.ADD,(FileNode) en.getValue(),oldf));
                        }
                    }
                }
            }
        }
        while(ito.hasNext()||!itob) {
            //rm
            //System.out.println("RM2");
            if(itob) eo = (Map.Entry)ito.next();
            actions.add(new Diff(Diff.REMOVE, (Node) eo.getValue(),oldf));
            itob = true;
        }
        while(itn.hasNext()|| !itnb) {
            //add
            //System.out.println("ADD2");
            if(itnb) en = (Map.Entry)itn.next();
            if(((Node) en.getValue()).getClass().equals(FolderNode.class)) {
                FolderNode next;
                FolderNode fno = (FolderNode) en.getValue();
                if(location == Node.REMOTE) next = ts.getNode(fno.getPath(),owner);
                else next = (FolderNode) en.getValue();
                actions.add(new Diff(Diff.ADD,(FolderNode) en.getValue(),oldf));
                //recactions.addAll(insertRec(next));
                actions.addAll(insertRec(next,location,owner));
            } else {
                actions.add(new Diff(Diff.ADD,(FileNode) en.getValue(),oldf));
            }
            itnb = true;
        }
        //actions.addAll(recactions);
        return actions;
    }

    public void treatConflicts(ArrayList<Diff> local,ArrayList<Diff> remote) {
        int i = 0;
        int j = 0;
        //System.out.println("ttttttttttttttttttttttt");
        //System.out.println(local.size());
        //System.out.println(remote.size());
        while(i < local.size() && j < remote.size()) {
            String sl = local.get(i).getParentPath();
            String sr = remote.get(j).getParentPath();
            //System.out.println(sl+" "+sr);
            if(!sl.equals(sr)) {
                if(sl.compareTo(sr) < 0) {
                    ++i;
                }
                else {
                    ++j;
                }
            }
            else {
                String namel = local.get(i).getName();
                String namer = remote.get(j).getName();
                //System.out.println(namel+" "+namer);
                Node nl = local.get(i).getNode();
                Node nr = remote.get(j).getNode();
                //System.out.println(namel + " " + namer);
                if(!namel.equals(namer)) {
                    if(namel.compareTo(namer) < 0) {
                        ++i;
                    }
                    else {
                        ++j;
                    }
                }
                else if(nl.getClass() == nr.getClass()){
                    //System.out.println("Mismo!!!");
                    if (nl.getClass() == FileNode.class &&  
                        ((FileNode)nl).getHash().equals(((FileNode) nr).getHash())) {
                        //System.out.println("bbbbbbbbb");
                        local.remove(i);
                        remote.remove(j);
                    }
                    else {
                        //System.out.println("aaaaaa");
                        if(remote.get(j).getAction() == Diff.ADD) {
                            local.get(i).setConflict();
                        } else {
                            if(local.get(i).getAction() == Diff.ADD) {
                                remote.get(j).setConflict();
                            } else {
                                local.get(i).setConflict();
                            }
                        }
                        ++i;
                        ++j;
                    }
                } else if (nl.getClass() == FolderNode.class) {
                    ++i;
                } else {
                    ++j;
                }
            }
        }
    }

    public void treatConflicts2(ArrayList<Diff> local,ArrayList<Diff> remote) {
        for(int i = 0; i < local.size(); ++i) {
            for(int j = 0; j < remote.size(); ++i) {
                String namel = local.get(i).getName();
                String namer = remote.get(j).getName();
                Node nl = local.get(i).getNode();
                Node nr = remote.get(j).getNode();
                if(namel.equals(namer) && (nl.getClass() == nr.getClass())) {
                    if(remote.get(j).getAction() == Diff.ADD) {
                        local.get(i).setConflict();
                    } else {
                        if(local.get(i).getAction() == Diff.ADD) {
                            remote.get(j).setConflict();
                        } else {
                            local.get(i).setConflict();
                        }
                    }
                }
            }
        }
    }
    
    private void printTree(String name, FolderNode folder,String space,int location,String owner) {
        TreeMap<String, Node> map = folder.getMap();
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            //System.out.println(space+e.getKey());
            //System.out.println(space+((Node) e.getValue()).getDate(Node.LOCAL));
            //System.out.println(space+((Node) e.getValue()).getDate(Node.REMOTE));
            if(e.getValue().getClass().equals(FolderNode.class)) {
                FolderNode next;
                FolderNode fno = (FolderNode) e.getValue();
                //System.out.println("asking server for" + fno.getPath());
                if(location == Node.REMOTE) next = ts.getNode(fno.getPath(),owner);
                else next = (FolderNode) e.getValue();
                printTree((String) e.getKey(), next,space+"  ",location,owner);
            }
        }
    }

    public void printTree(String name, FolderNode folder, int location,String owner) {
        //System.out.println(name);
        printTree(name,folder,"  ",location,owner);
    }
    
    public static void writeTree(RootFolder folder) {
        try {
            FileOutputStream fos = new FileOutputStream(LocalConf.getFolder()+File.separator+folder.getName()+".tree");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(folder);
        } catch (Exception ex) {
            Logger.getLogger(TreeFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static RootFolder readTree(String name) {
        RootFolder folder = null;
        try {
            FileInputStream fos = new FileInputStream(LocalConf.getFolder()+File.separator+name+".tree");
            ObjectInputStream oos = new ObjectInputStream(fos);
            folder = (RootFolder) oos.readObject();
        } catch (Exception ex) {
            return null;
        }
        return folder;
    }
}
