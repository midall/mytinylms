/*
 * @(#)DataFromLMSElement.java 1.1  2006-10-30
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

package ch.randelshofer.scorm.cam;

import ch.randelshofer.scorm.AbstractElement;
import ch.randelshofer.xml.DOMs;
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
 * Represents a SCORM CAM 'datafromlms' element.
 * <p>
 * This element describes the datafromlms for a CAM 'item' element.
 * <p>
 * A 'datafromlms' element has a structure as shown below.
 * Square brackets [ ] denote zero or one occurences.
 * Braces { } denote zero or more occurences.
 * <pre>
 * &lt;datafromlms&gt;string (maximum of 255 characters)&lt;/datafromlms&gt;
 * </pre>
 * or 
 * <pre>
 * &lt;adlcp:datafromlms&gt;string (maximum of 255 characters)&lt;/adlcp:datafromlms&gt;
 * </pre>
 *
 * Reference:
 * ADL (2001c). Advanced Distributed Learning.
 * Sharable Content Object Reference Model (SCORM(TM)) Version 1.2.
 * The SCORM Content Aggregation Model. October 1, 2001.
 * Internet (2003-01-20): http://www.adlnet.org
 *
 * @author Werner Randelshofer, Hausmatt 10, Immensee, CH-6405, Switzerland
 * @version 1.1 2006-10-10 Parse with XML namespaces.
 * <br>1.0.1 2003-10-30 HTML output in method toString changed. 
 * <br>0.1 2003-02-02 Created.
 */
public class DataFromLMSElement extends AbstractElement {
    /**
     * The dataFromLMS. Smallest permitted maximum of 255 characters.
     */
    private String dataFromLMS;
    
    /** Creates a new instance of DataFromLMSElement */
    public DataFromLMSElement() {
    }
    
    /**
     * Parses the specified DOM Element and incorporates its contents into this element.
     * @param elem An XML element with the tag name 'datafromlms'.
     */
    public void parse(Element elem)
    throws IOException, ParserConfigurationException, SAXException {
        namespace = DOMs.getNamespaceURI(elem, getRequiredNamespace());
        if (! elem.getLocalName().equals("datafromlms")) {
                throw new IOException("'" + getRequiredNamespace() + ":datafromlms' element expected, but found '"+namespace+":"+elem.getTagName()+"' element.");
        }
        
        // Read the dataFromLMS
        this.dataFromLMS = DOMs.getText(elem);
    }
    
    /**
     * Dumps the contents of this subtree into the provided string buffer.
     */
    public void dump(StringBuffer buf, int depth) {
        for (int i=0; i < depth; i++) buf.append('.');
        buf.append("<datafromlms>"+dataFromLMS+"</datafromlms>\n");
    }
    
    public String getDataFromLMS() {
        return dataFromLMS;
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<html><font size=-1 face=SansSerif>");
        if (! isValid()) buf.append("<font color=red>* </font>");
        buf.append("<b>DataFromLMS:</b> "+dataFromLMS);
        buf.append("</font>");
        return buf.toString();
    }
    @Override
    protected String getRequiredNamespace() {
        return CAM.IMSCP_NS;
    }
}
