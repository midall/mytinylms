/*
 * @(#)LeftAlignedHeaderRenderer.java  1.0  June 21, 2004
 *
 * Copyright (c) 2004 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.gui.table;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
/**
 * LeftAlignedHeaderRenderer.
 * The header renderer. All this does is make the text left aligned.
 *
 * @author  Werner Randelshofer
 * @version 1.0  June 21, 2004  Created.
 */
public class LeftAlignedHeaderRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table,
    Object value, boolean selected, boolean focused,
    int row, int column) {
        super.getTableCellRendererComponent(table, value,
        selected, focused, row, column);
        setBorder(new CompoundBorder(
        UIManager.getBorder("TableHeader.cellBorder"),
        new EmptyBorder(0,2,0,0)
        ));
        setFont(UIManager.getFont("TableHeader.font"));
        return this;
    }
}
