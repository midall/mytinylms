/*
 * @(#)RunnableWorker.java  1.0  2002-05-18
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
package ch.randelshofer.util;

import javax.swing.SwingUtilities;

/**
 * This is an abstract class that you subclass to
 * perform GUI-related work in a dedicated event dispatcher.
 * <p>
 * This class is compatible with SwingWorker where it is reasonable
 * to be so. Unlike a SwingWorker it does not use an internal
 * worker thread but has to be dispatched by a dispatcher which
 * handles java.awt.ActiveEvent's.
 *
 * @author Werner Randelshofer
 * @version 1.0 2002-05-18 Created.
 */
public abstract class RunnableWorker implements Runnable {
    private Object value;  // see getValue(), setValue()
    
    /**
     * Calls #construct on the current thread and invokes
     * #finished on the AWT event dispatcher thread.
     */
    public void run() {
        final Runnable doFinished = new Runnable() {
            public void run() { finished(getValue()); }
        };
        try {
            setValue(construct());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            SwingUtilities.invokeLater(doFinished);
        }
    }
    
    /**
     * Compute the value to be returned by the <code>get</code> method.
     */
    public abstract Object construct();
    /**
     * Called on the event dispatching thread (not on the worker thread)
     * after the <code>construct</code> method has returned.
     */
    public void finished(Object value) {
    }
    /**
     * Get the value produced by the worker thread, or null if it
     * hasn't been constructed yet.
     */
    protected synchronized Object getValue() {
        return value;
    }
    /**
     * Set the value produced by worker thread
     */
    private synchronized void setValue(Object x) {
        value = x;
    }
}