/*
 * @(#)PlainDocumentBean.java 1.0  2003-05-07
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
package ch.randelshofer.text;

import javax.swing.text.*;
import java.beans.*;
/**
 * A plain document that can be used like a java bean.
 *
 * @author  Werner Randelshofer, Hausmatt 10, CH-6405 Immensee, Switzerland
 * @version 1.0 2003-05-07 Created.
 */
public class PlainDocumentBean extends PlainDocument {
    /**
     * Property change support.
     */
    protected PropertyChangeSupport propertySupport;
    
    private String propertyName;
    
    /** Creates a new instance of PlainDocumentBean */
    public PlainDocumentBean() {
        this(null, "text", "");
    }
    /** Creates a new instance of PlainDocumentBean */
    public PlainDocumentBean(PropertyChangeSupport pcs, String propertyName) {
        this(null, propertyName, "");
    }
    /** Creates a new instance of PlainDocumentBean */
    public PlainDocumentBean(PropertyChangeSupport pcs, String propertyName, String initialText) {
        propertySupport = (pcs == null) ? new PropertyChangeSupport(this) : pcs;
        this.propertyName = propertyName;
        setText(initialText);
    }
    
    
    public void remove(int offs, int len) throws BadLocationException {
        String oldValue = getText();
        super.remove(offs, len);
        String newValue = getText();
        propertySupport.firePropertyChange(propertyName, oldValue, newValue);
        
    }
    
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        String oldValue = getText();
        super.insertString(offset, str, a);
        String newValue = getText();
        propertySupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    public void setText(String newValue) {
        try {
            String oldValue = getText();
            super.remove(0, getLength());
            super.insertString(0, newValue, null);
            propertySupport.firePropertyChange(propertyName, oldValue, newValue);
        } catch (BadLocationException e) {
            InternalError ie = new InternalError(e.toString());
            //ie.initCause(e);
            throw ie;
        }
    }
    public String getText() {
        try {
            return getText(0, getLength());
        } catch (BadLocationException e) {
            InternalError ie = new InternalError(e.toString());
            //ie.initCause(e);
            throw ie;
        }
    }
    
    
    /**
     * @see java.beans.PropertyChangeListener#addPropertyChangeListener(PropertyChangeListener)
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    /**
     * @see java.beans.PropertyChangeListener#removePropertyChangeListener(PropertyChangeListener)
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
    public String toString() {
        return getText();
    }
}
