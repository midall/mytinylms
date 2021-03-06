/*
 * @(#)LocationElement.java  1.1  2006-10-10
 *
 * Copyright (c) 2001-2006 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.scorm.cam;

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
 * Represents a SCORM 1.2 CAM 'adclp:location' element.
 * <p>
 * This element describes the location where the meta-data describing the
 * Content Packaging component may be found. This may be a Universal
 * Resource Indicator (URI). This is an ADL namespaced element extension to
 * the IMS Content Packaging Specification. The meta-data creator has two
 * options for expressing meta-data in a Content Package. The creator can
 * either use the <adlcp:location> element to express the location of the
 * meta-data record or place the meta-data inline within the Manifest file.
 * <p>
 * A 'adlcp:location' element has a structure as shown below.
 * Square brackets [ ] denote zero or one occurences.
 * Braces { } denote zero or more occurences.
 * Text in <b>bold</b> denotes non-terminal symbols.
 * <pre>
 * <b>location</b> ::= &lt;adlcp:location&gt;<b>string</b>&lt;/adlcp:location&gt;
 * </pre>
 *
 * Reference:
 * ADL(2001c). Advanced Distributed Learning.
 * Sharable Content Object Reference Model(SCORM(TM)) Version 1.2.
 * The SCORM Content Aggregation Model. October 1, 2001.
 * Internet(2003-01-20): http://www.adlnet.org
 *
 * @author  Werner Randelshofer
 * @version 1.1 2006-10-10 Parse with XML namespaces. 
 * <br>1.0 5. Januar 2004  Created.
 */
public class LocationElement extends AbstractElement {
    private String uri;
    
    private boolean isLocationValid;
    
    public String getUri() {
        return uri;
    }
    /**
     * Gets a location relative to
     * the content package or an absolute href.
     */
    public String getConsolidatedURI() {
        return Strings.unescapeURL(uri);
    }
    
    /**
     * Parses the specified DOM Element and incorporates its contents into this element.
     * @param elem An XML element with the tag name 'file'.
     */
    public void parse(Element elem)
    throws IOException, ParserConfigurationException, SAXException {
        namespace = DOMs.getNamespaceURI(elem, getRequiredNamespace());
        if (! elem.getLocalName().equals("location")) {
                throw new IOException("'" + getRequiredNamespace() + ":location' element expected, but found '"+namespace+":"+elem.getTagName()+"' element.");
        }
        
        // Read the text of the element
        uri = DOMs.getText(elem);
    }
    
    public void dump(StringBuffer buf, int depth) {
    }
    /**
     * Validates this CAM element.
     *
     * @return Returns true if this elements is valid.
     */
    public boolean validate() {
        isValid = super.validate();
        
        Set fileNames = getIMSManifestDocument().getFileNames();
        isLocationValid = fileNames.contains(getConsolidatedURI());
        
        return isValid = isValid && isLocationValid;
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<html><font size=-1 face=SansSerif>");
        if (! isValid()) buf.append("<font color=red>* </font>");
        buf.append("<b>Location</b> ");
        buf.append(uri);
        
        buf.append("</font>");
        return buf.toString();
    }
    /**
     * Removes all file names in the set, which are referenced by this
     * CAM Element.
     */
    public void consumeFileNames(Set fileNames) {
        fileNames.remove(getConsolidatedURI());
    }
    @Override
    protected String getRequiredNamespace() {
        return CAM.IMSCP_NS;
    }
}
