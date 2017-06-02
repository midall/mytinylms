/*
 * @(#)UndoCoalescingBoundedRangeModel.java 1.0  2001-10-04
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
import javax.swing.undo.*;

/**
 *
 * @author  werni
 */
public class UndoCoalescingBoundedRangeModel extends javax.swing.DefaultBoundedRangeModel {
    private CompositeEdit compositeEdit;
    protected EventListenerList listenerList = new EventListenerList();
    
    /** Creates a new instance of UndoCoalescingBoundedRangeModel */
    public UndoCoalescingBoundedRangeModel() {
    }
    public UndoCoalescingBoundedRangeModel(int value, int extent, int min, int max) {
        super(value, extent, min, max);
    }
    
    public void setValueIsAdjusting(boolean b) {
        //System.out.println("setValueIsAdjusting("+b+") "+this);
        if (b) {
            if (compositeEdit != null) {
                System.out.println("zweimal setValueIsAdjusting(true)!! "+this);
            }
            compositeEdit = new CompositeEdit("Slider");
            fireUndoableEditEvent(compositeEdit);

        } else {
            if (compositeEdit == null) {
                System.out.println("zweimal setValueIsAdjusting(false)!! "+this);
            }
            fireUndoableEditEvent(compositeEdit);
            compositeEdit = null;
        }
    }
    /**
     * Removes an UndoableEditListener.
     */
    public void removeUndoableEditListener(UndoableEditListener l) {
        listenerList.remove(UndoableEditListener.class, l);
    }
    
    /**
     * Adds an UndoableEditListener.
     */
    public void addUndoableEditListener(UndoableEditListener l) {
        listenerList.add(UndoableEditListener.class, l);
    }
    
    /**
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     */
    public void fireUndoableEditEvent(UndoableEdit edit) {
        UndoableEditEvent evt = null;
        
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==UndoableEditListener.class) {
                // Lazily create the event
                if (evt == null)
                    evt = new UndoableEditEvent(this, edit);
                ((UndoableEditListener)listeners[i+1]).undoableEditHappened(evt);
            }
        }
    }
}
