/*
 * @(#)PassworCellEditor.java  1.1  2006-05-06
 *
 * Copyright (c) 2003-2006 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * http://www.randelshofer.ch
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.gui.table;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.tree.*;
import java.io.Serializable;

/**
 * PassworCellEditor.
 *
 * @author Werner Randelshofer
 * @version 1.1 2006-05-06 Set a table cell editor border on the JPasswordField.
 * <br>1.0 August 24, 2003  Created.
 */
public class PasswordCellEditor extends DefaultCellEditor {
//
//  Constructors
//

    /**
     * Constructs a new instance.
     */
    public PasswordCellEditor() {
        super(new JPasswordField());
        ((JPasswordField) editorComponent).setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        //new EmptyBorder(0,0,0,0));
    }

//
//  Modifying
//

//
//  Implementing the TreeCellEditor Interface
//

    /** Implements the <code>TreeCellEditor</code> interface. */
    public Component getTreeCellEditorComponent(JTree tree, Object value,
						boolean isSelected,
						boolean expanded,
						boolean leaf, int row) {
	String stringValue = tree.convertValueToText(value, isSelected,
					    expanded, leaf, row, false);

	delegate.setValue(stringValue);
        ((JPasswordField) editorComponent).selectAll();
	return editorComponent;
    }

//
//  Implementing the CellEditor Interface
//
    /** Implements the <code>TableCellEditor</code> interface. */
    public Component getTableCellEditorComponent(JTable table, Object value,
						 boolean isSelected,
						 int row, int column) {
        delegate.setValue(value);
        ((JPasswordField) editorComponent).selectAll();
	return editorComponent;
    }
}
