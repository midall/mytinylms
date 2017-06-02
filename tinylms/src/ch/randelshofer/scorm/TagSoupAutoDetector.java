/*
 * @(#)TagSoupAutoDetector.java  1.0  August 18, 2007
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

package ch.randelshofer.scorm;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.ccil.cowan.tagsoup.AutoDetector;

/**
 * TagSoupAutoDetector.
 * 
 * @author Werner Randelshofer
 * @version 1.0 August 18, 2007 Created.
 */
public class TagSoupAutoDetector implements AutoDetector {
    
    /** Creates a new instance. */
    public TagSoupAutoDetector() {
    }

    public Reader autoDetectingReader(InputStream i) {
        try {
            return new InputStreamReader(i, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return new InputStreamReader(i);
        }
    }
    
}
