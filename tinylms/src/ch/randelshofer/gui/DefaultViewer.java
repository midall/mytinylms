/*
 * @(#)DefaultViewer.java 1.0  2001-10-05
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

import java.awt.*;

/**
 * Calls object.toString() and displays this in a JLabel.
 *
 * @author  Werner Randelshofer
 * @version 1.0 2001-10-05
 */
public class DefaultViewer 
extends javax.swing.JPanel implements Viewer {

    /** Creates new form DefaultViewer */
    public DefaultViewer() {
        initComponents();
    }

    public Component getComponent(Component parent, Object value) {
        if (value instanceof Object[]) {
            return getComponent(parent, (Object[]) value);
        } else {
            label.setText((value == null) ? "" : value.toString());
        }
        return this;
    }
    public Component getComponent(Component parent, Object[] values) {
        StringBuffer buf = new StringBuffer();
        for (int i=0; i < values.length; i++) {
            if (i > 0) buf.append(' ');
            buf.append((values[i] == null) ? "" : values[i].toString());
        }
        label.setText(buf.toString());
        return this;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        label = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        label.setText("jLabel1");
        add(label, java.awt.BorderLayout.NORTH);

    }//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel label;
    // End of variables declaration//GEN-END:variables

}
