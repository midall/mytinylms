/*
 * @(#)JVMLocalObjectTransferable.java  1.1  2007-09-08
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

import java.awt.datatransfer.*;
import java.io.IOException;
/**
 *
 * @author  werni
 * @version 1.1 2007-09-08 Constructor with data flavor added.
 * <br>1.0 2002-03-08
 */
public class JVMLocalObjectTransferable
        implements Transferable {
    private DataFlavor[] flavors;
    private Object data;
    
    /** Creates new JVMLocalObjectTransferable */
    public JVMLocalObjectTransferable(Class transferClass, Object data) {
        this.data = data;
        flavors = new DataFlavor[] {
            new DataFlavor(transferClass, "Object")
        };
    }
    public JVMLocalObjectTransferable(DataFlavor flavor, Object data) {
        this.data = data;
        flavors = new DataFlavor[] {
            flavor
        };
    }
    public JVMLocalObjectTransferable(DataFlavor[] flavors, Object data) {
        this.data = data;
        this.flavors = flavors;
    }
    
    public Object getTransferData(DataFlavor dataFlavor)
    throws UnsupportedFlavorException, IOException {
        if (! isDataFlavorSupported(dataFlavor)) {
            throw new UnsupportedFlavorException(dataFlavor);
        }
        return data;
    }
    
    public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
        for (int i=0; i < flavors.length; i++) {
            if (dataFlavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }
    
    public DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }
}
