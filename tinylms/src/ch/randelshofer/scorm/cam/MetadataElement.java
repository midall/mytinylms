/*
 * @(#)MetadataElement.java 1.1  2006-10-10
 *
 * Copyright (c) 2003 Werner Randelshofer
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
import ch.randelshofer.scorm.lom.*;
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
 * Represents a SCORM 1.2 CAM 'metadata' Element.
 * <p>
 * This element contains context specific meta-data that is used to describe the
 * content of the overall package (Package level meta-data). Implementers are
 * free to choose from any of the meta-data elements defined in the IMS Learning
 * Resource Metadata Specification Version 1.2.
 * <p>
 * A 'metadata' element has a structure as shown below.
 * Square brackets [ ] denote zero or one occurences.
 * Braces { } denote zero or more occurences.
 * Text in <b>bold</b> denotes non-terminal symbols.
 * <pre>
 * <b>metadata</b> ::= &lt;metadata&gt;
 *         [<b>schema</b>]
 *         [<b>schemaversion</b>]
 *         [<b>location</b>]
 *         &lt;/metadata&gt;
 * </pre>
 * Reference:
 * ADL (2001). Advanced Distributed Learning.
 * Sharable Content Object Reference Model (SCORM(TM)) Version 1.2.
 * The SCORM Content Aggregation Model. October 1, 2001.
 * Internet (2003-01-20): http://www.adlnet.org
 *
 * @author  Werner Randelshofer
 * @version 1.1 2006-10-10 Parse with XML namespaces.
 * <br>1.0.1  2004-01-19  Comments updated.
 * <br>1.0 2003-11-03 Created.
 */
public class MetadataElement extends AbstractElement {
    
    /** This element describes the schema that defines the meta-data. */
    private SchemaElement schemaElement;
    
    /**
     * This element describes the version of the schema that defines the
     * meta-data. This element is optional, however if present it must contain
     * the value of 1.2
     */
    private SchemaVersionElement schemaVersionElement;
    
    /**
     * This element describes the location where the meta-data describing the
     * Content Packaging component may be found. This may be a Universal
     * Resource Indicator (URI). This is an ADL namespaced element extension to
     * the IMS Content Packaging Specification. The meta-data creator has two
     * options for expressing meta-data in a Content Package. The creator can
     * either use the <adlcp:location> element to express the location of the
     * meta-data record or place the meta-data inline within the Manifest file.
     */
    private LocationElement locationElement;
    
    /**
     * This element inidicaste the SCORM Meta-data XML record.
     */
    private LOMElement lomElement;
    
    private boolean isLocationValid;
    private boolean isSchemaversionValid;
    
    /** Creates a new instance of MetadataElement */
    public MetadataElement() {
    }
    
    public void dump(StringBuffer buf, int depth) {
    }
    
    /**
     * Parses the specified DOM Element and incorporates its contents into this element.
     * @param elem An XML element with the tag name 'file'.
     */
    public void parse(Element elem)
    throws IOException, ParserConfigurationException, SAXException {
        namespace = DOMs.getNamespaceURI(elem, getRequiredNamespace());
        if (! elem.getLocalName().equals("metadata")) {
                throw new IOException("'" + getRequiredNamespace() + ":metadata' element expected, but found '"+namespace+":"+elem.getTagName()+"' element.");
        }
        // Read the child elements
        NodeList nodes = elem.getChildNodes();
        for (int i=0; i < nodes.getLength(); i++) {
            if (nodes.item(i) instanceof Element) {
                Element child = (Element) nodes.item(i);
                if (DOMs.isElement(child, namespace, "schema")) {
                    if (this.schemaElement != null) throw new IOException("'schema' element may only be specified once whithin a 'organization' element.");
                    this.schemaElement = new SchemaElement();
                    add(schemaElement);
                    this.schemaElement.parse(child);
                } else if (DOMs.isElement(child, namespace, "schemaversion")) {
                    if (this.schemaVersionElement != null) throw new IOException("'schemaversion' element may only be specified once whithin a 'organization' element.");
                    this.schemaVersionElement = new SchemaVersionElement();
                    add(schemaVersionElement);
                    this.schemaVersionElement.parse(child);
                } else if (DOMs.isElement(child, namespace, "location")) {
                    if (this.locationElement != null) throw new IOException("'adlcp:location' element may only be specified once whithin a 'organization' element.");
                    this.locationElement = new LocationElement();
                    add(locationElement);
                    this.locationElement.parse(child);
                } else if (DOMs.isElement(child, LOM.NS, "lom")) {
                    if (this.lomElement != null) throw new IOException("'imsmd:lom' element may only be specified once whithin a 'organization' element.");
                    this.lomElement = new LOMElement();
                    add(lomElement);
                    this.lomElement.parse(child);
                }
            }
        }
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<html><font size=-1 face=SansSerif>");
        if (! isValid()) buf.append("<font color=red>* </font>");
        buf.append("<b>Metadata</b>");
        buf.append("</font>");
        return buf.toString();
    }
    @Override
    protected String getRequiredNamespace() {
        return CAM.IMSCP_NS;
    }
    
}
