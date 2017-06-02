/*
 * @(#)ScrollPaneLayout2.java  1.0  2002-12-21
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
import javax.swing.*;
/**
 * Objects of this class behave essentially like
 * javax.swing.ScrollPaneLayout but treats the top
 * right corner specially.
 * <p>
 * If there is a component in the top right corner, it
 * is granted to be visible when the vertical scroll bar
 * is visible.
 *
 * @author  werni
 */
public class ScrollPaneLayout2 extends ScrollPaneLayout
implements javax.swing.plaf.UIResource  {
    
    /** Creates a new instance of CornerLayout2 */
    public ScrollPaneLayout2() {
    }
    
    /**
     * Same as ScrollPaneLayout.layoutContainer but the top right
     * corner is treated specially.
     *
     * @param parent the <code>Container</code> to lay out
     */
    public void layoutContainer(Container parent) {
        super.layoutContainer(parent);
        
        if (upperRight != null && colHead == null) {
            if (vsb != null && vsb.isVisible()) {
                Rectangle vsbR = vsb.getBounds();
                
                upperRight.setBounds(vsbR.x, vsbR.y,
                vsbR.width, vsbR.width);
                
                vsb.setBounds(vsbR.x, vsbR.y + vsbR.width,
                vsbR.width, vsbR.height - vsbR.width);
            }
        }
         
    }
}
