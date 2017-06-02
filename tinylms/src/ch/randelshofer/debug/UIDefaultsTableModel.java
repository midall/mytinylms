/*
 * @(#)UIDefaultsTableModel.java 1.1  2003-03-16
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

package ch.randelshofer.debug;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author  werni
 */
public class UIDefaultsTableModel extends AbstractTableModel {
    private Object[][] data;
    private final static String[] columnNames = { "Key", "Value" };
    
    public UIDefaultsTableModel(UIDefaults p) {
        setUIDefaults(p);
    }
    public void setUIDefaults(UIDefaults p) {
        data = new Object[p.size()][2];
        int i = 0;
        Iterator iter = p.keySet().iterator();
        while (iter.hasNext()) {
            data[i++][0] = iter.next();
        }
        for (i=0; i < data.length; i++) {
            data[i][1] = p.get(data[i][0]);
        }
        Arrays.sort(data, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((String) ((Object[]) o1)[0]).compareTo((String) ((Object[]) o2)[0]);
            }
        });
        fireTableDataChanged();
    }
    
    public int getColumnCount() {
        return 2;
    }
    
    public int getRowCount() {
        return data.length;
    }
    
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }
    
    public String getColumnName(int col) {
        return columnNames[col];
    }    
}