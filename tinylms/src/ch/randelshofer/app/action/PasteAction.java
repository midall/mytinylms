/*
 * @(#)PasteAction.java  2.0  2007-04-13
 *
 * Copyright (c) 2007 Werner Randelshofer
 * Hausmatt 10, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.app.action;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
import ch.randelshofer.util.*;
/**
 * Pastes the contents of the system clipboard at the caret position.
 * Acts on the EditableComponent or JTextComponent which had the focus when
 * the ActionEvent was generated.
 *
 * @author Werner Randelshofer
 * @version 2.0 2007-04-13 Use javax.swing.TransferHandler instead of
 * interface EditableComponent.
 * <br>1.0 October 9, 2005 Created.
 */
public class PasteAction extends AbstractAction {
    public final static String ID = "paste";
    
    /** Creates a new instance. */
    public PasteAction() {
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("ch.randelshofer.app.Labels");
        labels.configureAction(this, ID);
    }
    
    public void actionPerformed(ActionEvent evt) {
        Component focusOwner = KeyboardFocusManager.
                getCurrentKeyboardFocusManager().
                getPermanentFocusOwner();
        if (focusOwner != null && focusOwner instanceof JComponent) {
            JComponent component = (JComponent) focusOwner;
            Transferable t = component.getToolkit().getSystemClipboard().getContents(component);
            if (t != null && component.getTransferHandler() != null) {
                component.getTransferHandler().importData(
                        component,
                        t
                        );
            }
        }
    }
}