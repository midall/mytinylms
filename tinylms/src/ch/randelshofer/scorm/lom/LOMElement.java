/*
 * @(#)LOMElement.java  1.1  2006-10-10
 *
 * Copyright (c) 2004-2006 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
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
 * Represents a SCORM 1.2 LOM 'lom' Element.
 * <p>
 * The outermost, root element. This element indicates the beginning of the
 * SCORM Meta-data XML record.
 * <p>
 * A 'lom' element has a structure as shown below.
 * Square brackets [ ] denote zero or one occurences.
 * Braces { } denote zero or more occurences.
 * Text in <b>bold</b> denotes non-terminal symbols.
 * <pre>
 * <b>lom</b> ::= &lt;lom&gt;
 *         [<b>general</b>]
 *         [<b>lifecycle</b>]
 *         [<b>metametadata</b>]
 *         [<b>technical</b>]
 *         [<b>educational</b>]
 *         [<b>rights</b>]
 *         {<b>relation</b>}
 *         {<b>annotation</b>}
 *         {<b>classification</b>}
 *         &lt;/lom&gt;
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
 * <br>1.0  2004-01-05  Created.
 */
public class LOMElement extends AbstractLOMElement {
    
    private GeneralElement generalElement;
    private LifecycleElement lifecycleElement;
    private MetaMetadataElement metaMetadataElement;
    private TechnicalElement technicalElement;
    private EducationalElement educationalElement;
    private RightsElement rightsElement;
    private LinkedList relationList = new LinkedList();
    private LinkedList annotationList = new LinkedList();
    private LinkedList classificationList = new LinkedList();
    
    /** Creates a new instance. */
    public LOMElement() {
    }
    
    /**
     * Parses the specified DOM Element and incorporates its contents into this element.
     * @param elem An XML element with the tag name 'lom'.
     */
    public void parse(Element elem)
    throws IOException, ParserConfigurationException, SAXException {
        if (! DOMs.isElement(elem, LOM.NS, "lom")) {
            throw new IOException("'imsmd:lom' element expected, but found '"+elem.getLocalName()+"' element.");
        }
        
        // Read the child elements
        NodeList nodes = elem.getChildNodes();
        for (int i=0; i < nodes.getLength(); i++) {
            if (nodes.item(i) instanceof Element) {
                Element child = (Element) nodes.item(i);
                
                if (DOMs.isElement(child, LOM.NS, "general")) {
                    if (this.generalElement != null) throw new IOException("'general' element may only be specified once whithin a 'organization' element.");
                    this.generalElement = new GeneralElement();
                    add(generalElement);
                    this.generalElement.parse(child);
                } else if (DOMs.isElement(child, LOM.NS, "lifecycle")) {
                    if (this.lifecycleElement != null) throw new IOException("'lifecycle' element may only be specified once whithin a 'organization' element.");
                    this.lifecycleElement = new LifecycleElement();
                    add(lifecycleElement);
                    this.lifecycleElement.parse(child);
                } else if (DOMs.isElement(child, LOM.NS, "metametadata")) {
                    if (this.metaMetadataElement != null) throw new IOException("'metametadata' element may only be specified once whithin a 'organization' element.");
                    this.metaMetadataElement = new MetaMetadataElement();
                    add(metaMetadataElement);
                    this.metaMetadataElement.parse(child);
                } else if (DOMs.isElement(child, LOM.NS, "technical")) {
                    if (this.technicalElement != null) throw new IOException("'general' element may only be specified once whithin a 'organization' element.");
                    this.technicalElement = new TechnicalElement();
                    add(technicalElement);
                    this.technicalElement.parse(child);
                } else if (DOMs.isElement(child, LOM.NS, "educational")) {
                    if (this.educationalElement != null) throw new IOException("'general' element may only be specified once whithin a 'organization' element.");
                    this.educationalElement = new EducationalElement();
                    add(educationalElement);
                    this.educationalElement.parse(child);
                } else if (DOMs.isElement(child, LOM.NS, "rights")) {
                    if (this.rightsElement != null) throw new IOException("'general' element may only be specified once whithin a 'organization' element.");
                    this.rightsElement = new RightsElement();
                    add(rightsElement);
                    this.rightsElement.parse(child);
                } else if (DOMs.isElement(child, LOM.NS, "relation")) {
                    RelationElement relationElement = new RelationElement();
                    add(relationElement);
                    relationList.add(relationElement);
                    relationElement.parse(child);
                } else if (DOMs.isElement(child, LOM.NS, "annotation")) {
                    AnnotationElement annotationElement = new AnnotationElement();
                    add(annotationElement);
                    annotationList.add(annotationElement);
                    annotationElement.parse(child);
                } else if (DOMs.isElement(child, LOM.NS, "classification")) {
                    ClassificationElement classificationElement = new ClassificationElement();
                    add(classificationElement);
                    classificationList.add(classificationElement);
                    classificationElement.parse(child);
                } 
            }
        }
    }

    public void dump(StringBuffer buf, int depth) {
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<html><font size=-1 face=SansSerif>");
        if (! isValid()) buf.append("<font color=red>* </font>");
        buf.append("<b>LOM</b> ");
        
        buf.append("</font>");
        return buf.toString();
    }
}
