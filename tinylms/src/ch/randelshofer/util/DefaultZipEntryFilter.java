/*
 * @(#)DefaultZipEntryFilter.java  1.0  13. Januar 2004
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

import java.util.*;
import java.util.zip.*;

/**
 * DefaultZipEntryFilter.
 *
 * @author  Werner Randelshofer
 * @version 1.0 13. Januar 2004  Created.
 */
public class DefaultZipEntryFilter implements ZipEntryFilter {
    private Set filters;
    private boolean accept;
    
    /** Creates a new instance which accepts or rejects zip entries specified
     * in the filter set.
     * 
     * @param filters This set must contain zip entry names (Strings).
     * The names must follow the naming conventions for zip entries. That is
     * slash characters '/' as separators and a trailing slash for directories.
     * @param accept If this parameter is true, only entries which are in the
     * filters set are accepted. If this parameter is false, only entries which
     * are not in the filters set are accepted.
     */
    public DefaultZipEntryFilter(Set filters, boolean accept) {
        this.filters = filters;
        this.accept = accept;
    }
    
    /**
     * Creates a new zip entry filter which accepts all entries.
     */
    public DefaultZipEntryFilter() {
        this.filters = new HashSet(0);
        this.accept = false;
    }
    
    
    public boolean accept(ZipEntry entry) {
        return filters.contains(entry.getName()) == accept;
    }    
}
