/*
 * @(#)ZipEntryFilter.java  1.0  13. Januar 2004
 *
 * Copyright (c) 2004 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */


package ch.randelshofer.util;

import java.util.zip.*;
/**
 * ZipEntryFilter.
 *
 * @author  Werner Randelshofer
 * @version 1.0 13. Januar 2004  Created.
 */
public interface ZipEntryFilter {
    /**
     * Tests if a specified file should be included in a file list. 
     * 
     * @param entry the entry.
     * @return true if and only if the entry should be included in the
     * file list; false otherwise.
     */
    public boolean accept(ZipEntry entry);
    
}
