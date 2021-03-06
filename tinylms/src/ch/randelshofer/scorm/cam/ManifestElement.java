/*
 * @(#)ManifestElement.java 1.2  2006-10-10
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

import ch.randelshofer.scorm.*;
import ch.randelshofer.xml.*;
import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.xml.sax.*;

import org.w3c.dom.*;

/**
 * Represents a nested SCORM 1.2 CAM 'manifest' element.
 * <p>
 * The 'manifest' element is the top-level element of a content package.
 * <p>
 * A 'manifest' element has a structure as shown below.
 * Square brackets [ ] denote zero or one occurences.
 * Braces { } denote zero or more occurences.
 * Text in <b>bold</b> denotes non-terminal symbols.
 * <pre>
 * <b>manifest</b> ::= &lt;manifest identifier=<b>ID</b> 
 *                       [version=<b>string</b>] 
 *                       [xml:base=<b>string</b>]&gt;
 *              [<b>metadata</b>]
 *              <b>organizations</b>
 *              <b>resources</b>
 *              {<b>manifest</b>}
 *              &lt;/manifest&gt;
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
 * <br>1.1 2003-11-03 Support for metadata element implemented.
 * <br>1.0.1 2003-10-30 HTML output in method toString changed. 
 * <br>0.1 2003-02-02 Created.
 */
public class ManifestElement extends AbstractElement {

    /**
     * identifier (required) - An identifier provided by an author or
     * authoring tool, that is unique within the Manifest.
     */
    protected String identifier;
    /**
     * version (optional) - Identifies the version of the Manifest. It is used
     * to distinguish between manifests with the same identifier.
     * This attribute is null if the version property is not present in the
     * Manifest.
     */
    protected String version;
    /**
     * xml:base (optional) - This provides a relative path offset for the file
     * contained in the manifest. The usage of this element is defined in the
     * XML Base Working Draft from W3C.
     */
    protected String xmlBase;
    protected OrganizationsElement organizationsElement;
    protected ResourcesElement resourcesElement;
    protected MetadataElement metadataElement;
    protected LinkedList manifestList = new LinkedList();

    /** Creates a new instance of ManifestElement */
    public ManifestElement() {
    }

    /**
     * Parses the specified DOM Element and incorporates its contents into this element.
     * @param elem An XML element with the tag name 'manifest'.
     */
    public void parse(Element elem)
            throws IOException, ParserConfigurationException, SAXException {
        namespace = DOMs.getNamespaceURI(elem, getRequiredNamespace());
        if (! elem.getLocalName().equals("manifest")) {
                throw new IOException("'" + getRequiredNamespace() + ":manifest' element expected, but found '" + elem.getNamespaceURI() + ":" + elem.getTagName() + "'.");
        }

        this.identifier = DOMs.getAttributeNS(elem, CAM.IMSCP_NS, "identifier", null);
        this.version = DOMs.getAttributeNS(elem, namespace, "version", null);
        this.xmlBase = DOMs.getAttributeNS(elem, "xml", "base", null);

        // Read the child elements
        NodeList nodes = elem.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i) instanceof Element) {
                Element child = (Element) nodes.item(i);

                if (DOMs.isElement(child, namespace, "metadata")) {
                    if (this.metadataElement != null) {
                        throw new IOException("'metadata' may only be specified once whithin a 'manifest'");
                    }
                    this.metadataElement = new MetadataElement();
                    add(metadataElement);
                    this.metadataElement.parse(child);
                } else if (DOMs.isElement(child, namespace, "organizations")) {
                    if (this.organizationsElement != null) {
                        throw new IOException("The 'organizations' element may only be specified once whithin a 'manifest' element.");
                    }
                    this.organizationsElement = new OrganizationsElement();
                    add(organizationsElement);
                    this.organizationsElement.parse(child);
                } else if (DOMs.isElement(child, namespace, "resources")) {
                    if (this.resourcesElement != null) {
                        throw new IOException("The 'resources' element may only be specified once whithin a 'manifest' element.");
                    }
                    this.resourcesElement = new ResourcesElement();
                    add(resourcesElement);
                    this.resourcesElement.parse(child);
                } else if (DOMs.isElement(child, namespace, "manifest")) {
                    ManifestElement mani = new ManifestElement();
                    add(mani);
                    this.manifestList.add(mani);
                    mani.parse(child);
                }
            }
        }
    }

    /**
     * Dumps the contents of this subtree into the provided string buffer.
     */
    public void dump(StringBuffer buf, int depth) {
        for (int i = 0; i < depth; i++) {
            buf.append('.');
        }
        buf.append("<manifest identifier=\"" + identifier + "\" version=\"" + version + "\" xml:base=\"" + xmlBase + "\">\n");
        organizationsElement.dump(buf, depth + 1);
        resourcesElement.dump(buf, depth + 1);
        Iterator iter = manifestList.iterator();
        while (iter.hasNext()) {
            ((AbstractElement) iter.next()).dump(buf, depth + 1);
        }
        for (int i = 0; i < depth; i++) {
            buf.append('.');
        }
        buf.append("</manifest>\n");
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getVersion() {
        return version;
    }

    public String getXMLBase() {
        return xmlBase;
    }

    public OrganizationsElement getOrganizationsElement() {
        return organizationsElement;
    }

    public ResourcesElement getResourcesElement() {
        return resourcesElement;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<html><font size=-1 face=SansSerif>");
        if (!isValid()) {
            buf.append("<font color=red>* </font>");
        }
        buf.append("<b>Manifest</b> id:");
        if (!isIdentifierValid()) {
            buf.append("<font color=red>");
        }
        buf.append(identifier);
        if (!isIdentifierValid()) {
            buf.append("</font>");
        }
        buf.append(" version:");
        buf.append(version);
        if (!isNamespaceValid()) {
            buf.append(" <font color=blue><b>ILLEGAL NAMESPACE</b></font>");
        }
        buf.append("</font>");
        return buf.toString();
    }

        public String getInfo() {
            String info = super.getInfo();
        if (!isNamespaceValid()) {
            info+= "\nWarning: the \"manifest\" element must be in XML namespace \""+getRequiredNamespace()+"\", but found \""+namespace+"\".";
        }
            return info;
        }

    @Override
    protected String getRequiredNamespace() {
        return CAM.IMSCP_NS;
    }
}
