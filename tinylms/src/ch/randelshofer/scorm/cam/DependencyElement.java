/*
 * @(#)DependencyElement.java  1.2  2006-10-10
 *
 * Copyright (c) 2003-2006 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * http://www.randelshofer.ch
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
 * Represents a SCORM 1.2 CAM 'dependency' element.
 * <p>
 * This element contains a reference to a single resource that can act as a
 * container for multiple files that resources may be dependent on.
 * <p>
 * The 'dependency' element may occur 0 or more times within a 'resource'
 * element.
 * <p>
 * A 'dependency' element has a structure as shown below.
 * Square brackets [ ] denote zero or one occurences.
 * Braces { } denote zero or more occurences.
 * <pre>
 * &lt;dependency identifierref="identifier"/&gt;
 * </pre>
 *
 * Reference:
 * ADL (2001c). Advanced Distributed Learning.
 * Sharable Content Object Reference Model (SCORM(TM)) Version 1.2.
 * The SCORM Content Aggregation Model. October 1, 2001.
 * Internet (2003-01-20): http://www.adlnet.org
 *
 * @author Werner Randelshofer
 * @version 1.2 2006-10-10 Parse with XML namespaces. 
 * <br>1.1 2003-10-29 Method getResource added. HTML output in method 
 * toString changed. 
 * <br>1.0 August 22, 2003  Created.
 */
public class DependencyElement extends AbstractElement{
    /**
     * This attribute is set by validate().
     */
    private boolean isIdentifierrefValid = true;
    /**
     * identifierref (required). An identifier for other resources to reference.
     * This attribute is null if no identifierref was supplied in the XML document.
     */
    private String identifierref;
    
    /** Creates a new instance. */
    public DependencyElement() {
    }
    
    /**
     * Parses the specified DOM Element and incorporates its contents into this element.
     * @param elem An XML element with the tag name 'dependency'.
     */
    public void parse(Element elem)
    throws IOException, ParserConfigurationException, SAXException {
        namespace = DOMs.getNamespaceURI(elem, getRequiredNamespace());
        if (! elem.getLocalName().equals("dependency")) {
                throw new IOException("'" + getRequiredNamespace() + ":dependency' element expected, but found '"+namespace+":"+elem.getTagName()+"' element.");
        }
        this.identifierref = DOMs.getAttributeNS(elem, CAM.IMSCP_NS, "identifierref", "");
        
        // Read the child elements
        NodeList nodes = elem.getChildNodes();
        // FIXME - Make this a warning
        if (nodes.getLength() > 0) {
            throw new IOException("'dependency' is a leaf element, but found '"+nodes.getLength()+"' children.");
        }
    }
    
    /**
     * Dumps the contents of this subtree into the provided string buffer.
     */
    public void dump(StringBuffer buf, int depth) {
        for (int i=0; i < depth; i++) buf.append('.');
        buf.append("<dependency identifierref=\""+identifierref+"\"/>\n");
    }
    
    /**
     * Exports this CAM subtree to JavaScript using the specified PrintWriter.
     *
     * @param out The output stream.
     * @param depth The current depth of the tree (used for indention).
     * @param gen The identifier generator used to generate short(er) identifiers
     *  whithin the JavaScript.
     */
    public void exportToJavaScript(PrintWriter out, int depth, IdentifierGenerator gen)
    throws IOException {
    }
    
    public String getIdentifierref() {
        return identifierref;
    }
    public ResourceElement getResource() {
        return (getIdentifierref() == null)
        ? null 
        : getIMSManifestDocument().getResourcesElement().findResource(getIdentifierref()); 
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<html><font size=-1 face=SansSerif>");
        if (! isValid()) buf.append("<font color=red>* </font>");
        buf.append("<b>Dependency</b> identifierref:");
        if (! isIdentifierrefValid()) buf.append("<font color=red>");
        buf.append(identifierref);
        if (! isIdentifierrefValid()) buf.append(" <b>NO RESOURCE</b></font>");
        buf.append("</font>");
        return buf.toString();
    }
    
    /**
     * Validates this CAM element.
     *
     * @return Returns true if this elements is valid.
     */
    public boolean validate() {
        isValid = super.validate();
        if (identifierref == null) isIdentifierrefValid = false;
        if (identifierref != null) {
            isIdentifierrefValid = false;
            Enumeration enm = getIMSManifestDocument().getResourcesElement().preorderEnumeration();
            while (enm.hasMoreElements()) {
                AbstractElement node = (AbstractElement) enm.nextElement();
                if (node.getIdentifier() != null && node.getIdentifier().equals(identifierref)) {
                    isIdentifierrefValid = true;
                    break;
                }
            }
            if (! isIdentifierrefValid) isValid = false;
        }
        return isValid;
    }
    
    /**
     * The return value of this method is unspecified until
     * validate() has been done.
     *
     * @return Returns true if the identifierref of this element references
     * a ResourceElement in this tree.
     */
    public boolean isIdentifierrefValid() {
        return isIdentifierrefValid;
    }
    @Override
    protected String getRequiredNamespace() {
        return CAM.IMSCP_NS;
    }
}
