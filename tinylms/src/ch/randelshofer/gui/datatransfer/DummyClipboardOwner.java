/*
 * @(#)DummyClipboardOwner.java  1.0  November 1, 2003
 *
 * Copyright (c) 2003 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.gui.datatransfer;

/**
 * DummyClipboardOwner.
 *
 * @author  Werner Randelshofer
 * @version 1.0 November 1, 2003 Created.
 */
public class DummyClipboardOwner implements java.awt.datatransfer.ClipboardOwner {
    private static DummyClipboardOwner instance;
    
    public static DummyClipboardOwner getInstance() {
        if (instance == null) {
            instance = new DummyClipboardOwner();
        }
        return instance;
    }
    
    /** Creates a new instance. */
    private DummyClipboardOwner() {
    }
    
    public void lostOwnership(java.awt.datatransfer.Clipboard clipboard, java.awt.datatransfer.Transferable contents) {
    }
    
}
