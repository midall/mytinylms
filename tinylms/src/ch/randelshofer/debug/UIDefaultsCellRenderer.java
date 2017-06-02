/*
 * @(#)UIDefaultsCellRenderer.java  1.1  2003-03-16
 *
 * Copyright (c) 2003 Werner Randelshofer
 * Hausmatt 10, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.debug;

import ch.randelshofer.gui.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * ObjectTableCellRenderer.
 *
 * @author  Werner Randelshofer, Hausmatt 10, CH-6405 Immensee, Switzerland
 * @version 1.1 2003-03-16 Some Icon's may generate class cast exceptions in the Aqua LAF.
 * <br>1.0 March 15, 2003 Created.
 */
public class UIDefaultsCellRenderer extends DefaultTableCellRenderer {
    private PolygonIcon colorIcon = new PolygonIcon(
    new Polygon(
    new int[] {0, 10, 10, 0},
    new int[] {0, 0, 10, 10},
    4
    ),
    new Dimension(10, 10)
    );
    
    /** Creates a new instance. */
    public UIDefaultsCellRenderer() {
    }
    
    public void paint(Graphics g) {
        // Work around ClassClastException's in some Icons of the Aqua LAF
        try {
            super.paint(g);
        } catch (ClassCastException e) {
            setIcon(null);
            super.paint(g);
        }
    }
    
    public Component getTableCellRendererComponent(
    JTable table, Object object,
    boolean isSelected, boolean hasFocus,
    int row, int column) {
        Object value = object;
            setIcon(null);
        
        if (object instanceof Color) {
            Color v = (Color) object;
            setIcon(colorIcon);
            colorIcon.setFillColor(v);
            value = "Color ["+v.getRed()+","+v.getGreen()+","+v.getBlue()+"]";
        } else if (object instanceof Insets) {
            Insets v = (Insets) object;
            value = "Insets ["+v.top+","+v.left+","+v.bottom+","+v.right+"]";
        } else if (object instanceof Dimension) {
            Dimension v = (Dimension) object;
            value = "Dimension ["+v.width+","+v.height+"]";
        } else if (object instanceof Font) {
            Font v = (Font) object;
            value = "Font ["+v.getName()+","+v.getSize()+","+(v.isPlain()?"plain":(v.isBold()?"bold":"")+(v.isItalic()?"italic":""))+"]";
        } else if (object instanceof Icon) {
            Icon v = (Icon) object;
            setIcon(v);
            value = "Icon ["+v.getIconWidth()+","+v.getIconHeight()+"]";
        }
        
         super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setFont(new Font("Lucida Grande", Font.PLAIN, 11));
         return this;
    }
}
