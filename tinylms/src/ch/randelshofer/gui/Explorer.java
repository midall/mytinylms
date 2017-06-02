/*
 * @(#)Explorer.java 1.1  2003-11-04
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

package ch.randelshofer.gui;

import ch.randelshofer.gui.event.*;
import ch.randelshofer.gui.tree.*;
import ch.randelshofer.undo.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.undo.*;
/**
 * This panel acts like an Explorer Window as commonly used
 * on Windows.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2003-11-04 Support for vertical and horizontal split pane added.
 * Method setCellRenderer added.
 * <br>1.0 2001-10-05 Created.
 */
public class Explorer extends javax.swing.JPanel {
    /**
     * The viewer supplies components for rendering the
     * selected item(s) of the tree.
     */
    private Viewer viewer = new DefaultViewer();
    /**
     * This variable holds the Object or Object[]-array
     * of the objects being viewed currently.
     */
    private Object viewedObject;
    /**
     * This variable holds the Component which displays
     * the current viewedObject.
     */
    private Component view;
    
    /**
     * Undo Manager for undo/redo support.
     */
    private UndoManager undo;
    
    /** Creates new form Explorer */
    public Explorer() {
        initComponents();
        tree.setSelectionModel(new UndoableTreeSelectionModel());
        tree.addTreeSelectionListener(
        (TreeSelectionListener) GenericListener.create(
        TreeSelectionListener.class, "valueChanged",
        this, "treeSelectionChanged"
        ));
        /*
        new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent evt) {
                treeSelectionChanged(evt);
            }
        }
        );*/
    }
    
    public void setDividerLocation(int location) {
        splitPane.setDividerLocation(location);
    }
    public int getDividerLocation() {
        return splitPane.getDividerLocation();
    }
    
    /**
     * Sets the tree model.
     */
    public void setTreeModel(TreeModel m) {
        tree.setModel(m);
    }
    
    /**
     * Sets the cell renderer for the tree.
     */
    public void setCellRenderer(TreeCellRenderer r) {
        tree.setCellRenderer(r);
    }
    
    /**
     * Expands all tree nodes, up to the
     * specified depth.
     *
     * @param depthLimit The depth limit.
     */
    public void expandAll(int depthLimit) {
        expandAll(depthLimit, Integer.MAX_VALUE);
    }
    
    /**
     * Expands all tree nodes, up to the
     * specified depth and maximal number
     * of children.
     *
     * @param depthLimit The depth limit.
     * @param childLimit The child count limit;
     */
    public void expandAll(int depthLimit, int childLimit) {
        TreeModel treeModel = getTreeModel();
        
        depthLimit += 2;
        for (int i=0; i < tree.getRowCount(); i++) {
            TreePath path = tree.getPathForRow(i);
            if (path.getPathCount() < depthLimit) {
                Object node = path.getLastPathComponent();
                if (! treeModel.isLeaf(node) && treeModel.getChildCount(node) < childLimit) {
                    tree.expandRow(i);
                }
            }
        }
    }
    /**
     * Expands the specified tree path.
     *
     * @param path The tree path.
     */
    public void expandPath(TreePath path) {
        tree.expandPath(path);
    }
    
    /**
     * Gets the tree model.
     */
    public TreeModel getTreeModel() {
        return tree.getModel();
    }
    
    /**
     * Gets the tree component.
     */
    public MutableJTree getTree() {
        return tree;
    }
    
    public void setUndoManager(UndoManager value) {
        if (undo != null) {
            ((UndoableTreeSelectionModel) tree.getSelectionModel()).removeUndoableEditListener(undo);
        }
        undo = value;
        if (undo != null) {
            ((UndoableTreeSelectionModel) tree.getSelectionModel()).addUndoableEditListener(undo);
        }
    }
    
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        tree.setEnabled(b);
        scrollPane.setEnabled(b);
        if (view != null) view.setEnabled(b);
    }
    
    /**
     * Sets the viewer.
     * The viewer renders the currently selected
     * TreeNode or Array of TreeNodes in the
     * right pane of the explorer.
     */
    public void setViewer(Viewer v) {
        viewer = v;
    }
    
    /**
     * Gets the viewer.
     */
    public Viewer getViewer() {
        return viewer;
    }
    
    /**
     * Sets the orientation of the split pane.
     *
     * @param orientation JSplitPane.VERTICAL_SPLIT or JSplitPane.HORIZONTAL_SPLIT.
     */
    public void setOrientation(int orientation) {
        splitPane.setOrientation(orientation);
    }
    /**
     * Gets the orientation of the split pane.
     *
     * @return JSplitPane.VERTICAL_SPLIT or JSplitPane.HORIZONTAL_SPLIT.
     */
    public int getOrientation() {
        return splitPane.getOrientation();
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        splitPane = new javax.swing.JSplitPane();
        rightPane = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        tree = new ch.randelshofer.gui.MutableJTree();

        setLayout(new java.awt.BorderLayout());

        splitPane.setBorder(null);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        rightPane.setLayout(new java.awt.BorderLayout());

        rightPane.setMinimumSize(new java.awt.Dimension(0, 0));
        splitPane.setRightComponent(rightPane);

        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setMinimumSize(new java.awt.Dimension(0, 0));
        tree.setLargeModel(true);
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        scrollPane.setViewportView(tree);

        splitPane.setLeftComponent(scrollPane);

        add(splitPane, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents
    
    public void treeSelectionChanged(TreeSelectionEvent evt) {
        if ((undo != null)
        && (view instanceof Undoable)) {
            ((Undoable) view).removeUndoableEditListener(undo);
        }
        
        Component newView = null;
        
        TreePath[] paths = tree.getSelectionPaths();
        if (paths == null || paths.length == 0) {
            newView = null;
            
        } else if (paths.length == 1) {
            Object value = paths[0].getLastPathComponent();
            viewedObject = value;
            newView = viewer.getComponent(Explorer.this, viewedObject);
        } else {
            Object[] values = new Object[paths.length];
            for (int i=0; i < paths.length; i++) {
                values[i] = paths[i].getLastPathComponent();
            }
            viewedObject = values;
            newView = viewer.getComponent(Explorer.this, viewedObject);
        }
        
        if (newView == null)
            newView = new JPanel();
        if (newView != view) {
            view = newView;
            rightPane.removeAll();
            rightPane.add(view);
            view.invalidate();
            rightPane.validate();
            rightPane.repaint();
            view.setEnabled(isEnabled());
        }
        
        if ((undo != null)
        && (view instanceof Undoable)) {
            ((Undoable) view).addUndoableEditListener(undo);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel rightPane;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSplitPane splitPane;
    private ch.randelshofer.gui.MutableJTree tree;
    // End of variables declaration//GEN-END:variables
}
