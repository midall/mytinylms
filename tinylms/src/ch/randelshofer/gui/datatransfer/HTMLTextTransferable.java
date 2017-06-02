/*
 * @(#)HTMLTextTransferable.java  1.0  22. August 2007
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

package ch.randelshofer.gui.datatransfer;

import java.awt.datatransfer.*;

/**
 * HTMLTextTransferable.
 *
 * @author Werner Randelshofer
 * @version 1.0 22. August 2007 Created.
 */
public class HTMLTextTransferable extends PlainTextTransferable {
    
    /** Creates a new instance. */
    public HTMLTextTransferable(String htmlText) {
        this(getDefaultFlavors(), htmlText);
    }
    public HTMLTextTransferable(DataFlavor flavor, String htmlText) {
        this(new DataFlavor[] { flavor }, htmlText);
    }
    public HTMLTextTransferable(DataFlavor[] flavors, String htmlText) {
        super(flavors, htmlText);
    }
    
    protected static DataFlavor[] getDefaultFlavors() {
        try {
            return new DataFlavor[] {
                new DataFlavor("text/html;class=java.lang.String"),
                new DataFlavor("text/html;class=java.io.Reader"),
                new DataFlavor("text/html;charset=unicode;class=java.io.InputStream")
            };
        } catch (ClassNotFoundException cle) {
            InternalError ie = new InternalError(
                    "error initializing PlainTextTransferable");
            ie.initCause(cle);
            throw ie;
        }
    }
}
