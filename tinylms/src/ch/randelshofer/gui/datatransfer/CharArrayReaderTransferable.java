/*
 * @(#)CharArrayReaderTransferable.java  1.0  2002-04-07
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

package ch.randelshofer.gui.datatransfer;

import java.io.*;
import java.awt.datatransfer.*;
/**
 *
 * @author  Werner Randelshofer
 */
public class CharArrayReaderTransferable implements java.awt.datatransfer.Transferable {
    private char[] data;
    private DataFlavor flavor;
    
    /** Creates a new instance of ArrayReaderTransferable */
    public CharArrayReaderTransferable(char[] data, String mimetype, String description) {
        this.data = data;
        
        if (mimetype.indexOf("; class=") == -1) {
            mimetype += "; class=java.io.Reader";
        }
        
        
        this.flavor = new DataFlavor(mimetype, description);
    }
    
    /**
     * Returns an object which represents the data to be transferred.  The class
     * of the object returned is defined by the representation class of the flavor.
     *
     * @param flavor the requested flavor for the data
     * @see DataFlavor#getRepresentationClass
     * @exception IOException                if the data is no longer available
     *             in the requested flavor.
     * @exception UnsupportedFlavorException if the requested data flavor is
     *             not supported.
     */
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(this.flavor)) {
            return new CharArrayReader(data);
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
    
    /**
     * Returns an array of DataFlavor objects indicating the flavors the data
     * can be provided in.  The array should be ordered according to preference
     * for providing the data (from most richly descriptive to least descriptive).
     * @return an array of data flavors in which this data can be transferred
     */
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] {flavor};
    }
    
    /**
     * Returns whether or not the specified data flavor is supported for
     * this object.
     * @param flavor the requested flavor for the data
     * @return boolean indicating wjether or not the data flavor is supported
     */
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(this.flavor);
    }
    
}
