/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skydata.core.FolderTree;

/**
 *
 * @author ivan
 */
public class Diff {
    public final static int ADD=0;
    public final static int REMOVE=1;
    private int action;
    private Node node;
    private FolderNode parent;
    private boolean conflict;

    public Diff(int action,Node node, FolderNode parent) {
        this.action = action;
        this.node = node;
        this.parent = parent;
    }

    public Diff(int action,Node node) {
        this.action = action;
        this.node = node;
    }

    public int getAction() {
        return action;
    }

    public String getName() {
        return node.getName();
    }
    
    public String getPath() {
        return node.getPath();
    }

    public Node getNode() {
        return node;
    }

    public String getParentPath() {
        return node.getParent();
    }

    public FolderNode getParent() {
        return parent;
    }

    public void setConflict() {
        conflict = true;
    }

    public boolean getConflict() {
        return conflict;
    }

}
