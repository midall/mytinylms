/*
 * @(#)LangStringElement.java  1.1  2006-10-11
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

package ch.randelshofer.scorm.lom;

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
 * Represents a SCORM 1.2 LOM 'langstring' element.
 * <p>
 * This element describes a language dependent string.
 * <p>
 * A 'langstring' element has a structure as shown below.
 * Square brackets [ ] denote zero or one occurences.
 * Braces { } denote zero or more occurences.
 * Text in <b>bold</b> denotes non-terminal symbols.
 * <pre>
 * <b>langstring</b> ::= &lt;langstring [xml:lang=<b>string</b>]&gt;
 *                <b>string</b>
 *                &lt;/langstring&gt;
 * </pre>
 * Reference:
 * ADL (2001). Advanced Distributed Learning.
 * Sharable Content Object Reference Model (SCORM(TM)) Version 1.2.
 * The SCORM Content Aggregation Model. October 1, 2001.
 * Internet (2003-01-20): http://www.adlnet.org
 *
 * @author  Werner Randelshofer
 * @version 1.1 2006-10-11 Parse using XML namespaces.
 * <br>1.0.1  2004-01-19  Comments updated. 
 * <br>1.0  2004-01-05  Created.
 */
public class LangStringElement extends AbstractLOMElement {
    /**
     * The string.
     */
    private String text;
    /**
     * The language.
     */
    private String language;
    
    /** Creates a new instance of LangStringElement */
    public LangStringElement() {
    }
    
    /**
     * Parses the specified DOM Element and incorporates its contents into this element.
     * @param elem An XML element with the tag name 'title'.
     */
    public void parse(Element elem)
    throws IOException, ParserConfigurationException, SAXException {
        if (! DOMs.isElement(elem, LOM.NS, "langstring")) {
            throw new IOException("'langstring' element expected, but found '"+elem.getTagName()+"' element.");
        }
        
        this.language = DOMs.getAttributeNS(elem, "xml", "lang", null);
        
        // Read the title
        this.text = DOMs.getText(elem);
    }
    
    /**
     * Dumps the contents of this subtree into the provided string buffer.
     */
    public void dump(StringBuffer buf, int depth) {
        for (int i=0; i < depth; i++) buf.append('.');
        buf.append("<langstring xml:lang=\""+language+"\">"+text+"</langstring>\n");
    }
    
    public String getText() {
        return text;
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<html><font size=-1 face=SansSerif>");
        if (! isValid()) buf.append("<font color=red>* </font>");
        buf.append("<b>LangString</b> lang:"+language+" text:"+text);
        buf.append("</font>");
        return buf.toString();
    }

}
