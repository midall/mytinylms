/*
 * @(#)IdentifierElement.java  1.1  2006-10-11
 *
 * Copyright (c) 2003 Fachhochschule Zentralschweiz (FHZ)
 * Zentralstrasse 18, Postfach 2858, CH-6002 Lucerne, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.scorm.lom;

import ch.randelshofer.scorm.AbstractElement;
import ch.randelshofer.util.*;
import ch.randelshofer.xml.*;
import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import org.w3c.dom.*;
/**
 * Represents a SCORM 1.2 LOM 'identifier 'Element.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2006-10-11 Parse using XML namespaces.
 * <br>1.0 5. Januar 2004  Created.
 */
public class IdentifierElement extends AbstractLOMElement {
    
    /** Creates a new instance. */
    public IdentifierElement() {
    }
    
    /**
     * Parses the specified DOM Element and incorporates its contents into this element.
     * @param elem An XML element with the tag name 'file'.
     */
    public void parse(Element elem)
    throws IOException, ParserConfigurationException, SAXException {
        if (! DOMs.isElement(elem, LOM.NS, "identifier")) {
            throw new IOException("'identifier' element expected, but found '"+elem.getTagName()+"' element.");
        }
        
    }

    public void dump(StringBuffer buf, int depth) {
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<html><font size=-1 face=SansSerif>");
        if (! isValid()) buf.append("<font color=red>* </font>");
        buf.append("<b>Identifier</b> ");
        
        buf.append("</font>");
        return buf.toString();
    }
}
