/*
 * @(#)AbstractStateModel.java  1.0  2002-05-09
 *
 * Copyright (c) 1998-2000 Werner Randelshofer
 * Hausmatt 10, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.beans;

import javax.swing.event.*;
/**
 * Abstract superclass of models that fire state change
 * events to registered ChangeListener's, when their
 * state changes.
 *
 * @author Werner Randelshofer
 */
public class AbstractStateModel {
    protected EventListenerList listenerList;
    protected ChangeEvent changeEvent;
    
    /** Creates a new instance of AbstractChangeModel */
    public AbstractStateModel() {
    }
    
    public void addChangeListener(ChangeListener l) {
        if (listenerList == null) {
            listenerList = new EventListenerList();
        }
        listenerList.add(ChangeListener.class, l);
    }
    
    public void removeChangeListener(ChangeListener l) {
        if (listenerList == null) {
            listenerList = new EventListenerList();
        }
        listenerList.remove(ChangeListener.class, l);
    }
    /**
     * Notify all listeners that have registered interest for
     * notification on this event type.
     */
    protected void fireStateChanged() {
        if (listenerList != null) {
            // Guaranteed to return a non-null array
            Object[] listeners = listenerList.getListenerList();
            // Process the listeners last to first, notifying
            // those that are interested in this event
            for (int i = listeners.length-2; i>=0; i-=2) {
                if (listeners[i]==ChangeListener.class) {
                    // Lazily create the event:
                    if (changeEvent == null) {
                        changeEvent = new ChangeEvent(this);
                    }
                    ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
                }
            }
        }
    }
}
