/*
 * @(#)Undoable.java 1.0  2001-10-09
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

package ch.randelshofer.undo;

import javax.swing.event.*;

/**
 * This interface is implemented by components, which support undo
 * and redo.
 *
 * @author  Werner Randelshofer
 * @version 1.0 2001-10-09
 */
public interface Undoable {

    /**
     * Adds an UndoableEditListener.
     */
    public void addUndoableEditListener(UndoableEditListener l);
    
    /**
     * Removes an UndoableEditListener.
     */
    public void removeUndoableEditListener(UndoableEditListener l);
    
}

