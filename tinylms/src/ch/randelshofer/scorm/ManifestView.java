/*
 * @(#)ManifestView.java  1.0  2003-11-01
 *
 * Copyright (c) 2003 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * http://www.randelshofer.ch
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.scorm;

import ch.randelshofer.gui.tree.*;
import ch.randelshofer.scorm.cam.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
/**
 * ManifestView.
 *
 * @author  Werner Randelshofer
 * @version 1.0 2003-11-01 Created.
 */
public class ManifestView extends javax.swing.JPanel {
    private CAMElementViewer viewer;
    private CourseModel model;
    /** Creates new form ManifestView */
    public ManifestView() {
        initComponents();
        explorer.setCellRenderer(new HTMLTreeCellRenderer());
        viewer = new CAMElementViewer();
        explorer.setViewer(viewer);
    }
    
    public void setModel(CourseModel m) {
        model = m;
        explorer.setTreeModel(m);
    }
    
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        viewer.setEnabled(b);
        explorer.setEnabled(b);
    }

    public void expandInvalidCAMElements() {
        Enumeration enm = ((DefaultMutableTreeNode) model.getRoot()).preorderEnumeration();
        while (enm.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enm.nextElement();
            if (node instanceof AbstractElement) {
                AbstractElement element = (AbstractElement) node;
                if (! element.isValid()) {
                    explorer.expandPath(new TreePath(((DefaultMutableTreeNode) element.getParent()).getPath()));
                }
            }
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        explorer = new ch.randelshofer.gui.Explorer();

        setLayout(new java.awt.BorderLayout());

        explorer.setDividerLocation(320);
        explorer.setOrientation(0);
        add(explorer, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ch.randelshofer.gui.Explorer explorer;
    // End of variables declaration//GEN-END:variables
    
}
