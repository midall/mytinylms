/*
 * @(#)Viewer.java 1.0  2001-10-05
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

/**
 * Defines the requirements for a viewer that displays an object. 
 *
 * @author  Werner Randelshofer
 * @version 1.0 2001-10-05
 */
public interface Viewer {
    /**
     * Sets the value of the viewer to value. 
     *
     * @param parent This is the component into which the viewer will be
     * embedded.
     * @param value This is the object to be displayed.
     */
    public Component getComponent(Component parent, Object value);
}

