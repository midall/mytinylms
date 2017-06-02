/*
 * @(#)TreeNodeList.java  1.0  2001-10-08
 *
 * Copyright (c) 2001 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.gui.tree;
import java.util.*;
import javax.swing.tree.*;
import ch.randelshofer.util.*;
/**
 * Wraps a default mutable tree node with the List interface.
 *
 * @author  Werni Randelshofer
 * @version 1.0 2001-10-08
 */
public class TreeNodeList extends AbstractList {
    DefaultMutableTreeNode model;

    /** Creates new TreeNodeCollection */
    public TreeNodeList(DefaultMutableTreeNode n) {
        model = n;
    }

    public Object get(int index) {
        return model.getChildAt(index);
    }
    
    public int size() {
        return model.getChildCount();
    }
}
