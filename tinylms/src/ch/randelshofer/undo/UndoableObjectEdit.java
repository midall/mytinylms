/*
 * @(#)UndoableObjectEdit.java 1.0  2001-10-12
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

import javax.swing.undo.*;

/**
 * This is an abstract class for undoable Object properties.
 *
 * @author  Werner Randelshofer
 * @version 1.0 2001-10-12
 */
public abstract class UndoableObjectEdit extends javax.swing.undo.AbstractUndoableEdit {
    protected Object source;
    protected String propertyName;
    protected Object oldValue;
    protected Object newValue;
    
    /** 
     * Creates new IntPropertyEdit 
     * @param source The Object to which the property belongs.
     * @param propertyName The name of the property.
     * @param oldValue The old value of the property.
     * @param newValue The new value of the property.
     */
    public UndoableObjectEdit(Object source, String propertyName, Object oldValue, Object newValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Re-apply the edit, assuming that it has been undone.
     */
    public void redo() {
        super.redo();
        revert(oldValue, newValue);
    }
    
    /**
     * Undo the edit that was made.
     */
    public void undo() {
        super.undo();
        revert(newValue, oldValue);
    }
    
    /**
     * The name to be displayed in the undo/redo menu.
     */
    public String getPresentationName() {
        return propertyName;
    }
    
    /**
     * This UndoableEdit should absorb anEdit if it can. Return true
     * if anEdit has been incoporated, false if it has not.
     *
     * <p>Typically the receiver is already in the queue of a
     * UndoManager (or other UndoableEditListener), and is being
     * given a chance to incorporate anEdit rather than letting it be
     * added to the queue in turn.</p>
     *
     * <p>If true is returned, from now on anEdit must return false from
     * canUndo() and canRedo(), and must throw the appropriate
     * exception on undo() or redo().</p>
     */
    public boolean addEdit(UndoableEdit anEdit) {
        /*
        if (anEdit instanceof UndoableObjectEdit) {
            UndoableObjectEdit that = (UndoableObjectEdit) anEdit;
            if (that.source == this.source 
            && that.propertyName.equals(this.propertyName)) {
                this.newValue = that.newValue;
                that.die();
                return true;
            }
        }
         */
        return false;
    }
    
    /**
     * Revert the property from the
     * oldValue to the newValue.
     */
    public abstract void revert(Object oldValue, Object newValue);
}

