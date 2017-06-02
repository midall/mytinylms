/*
 * @(#)AbstractElement.java 1.2  2006-10-07
 *
 * Copyright (c) 2003-2006 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */


package ch.randelshofer.scorm;

import ch.randelshofer.scorm.cam.*;
import ch.randelshofer.util.*;
import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import javax.swing.tree.*;

import org.w3c.dom.*;
/**
 * Represents a SCORM CAM element.
 *
 * Reference:
 * ADL (2001c). Advanced Distributed Learning.
 * Sharable Content Object Reference Model (SCORM(TM)) Version 1.2.
 * The SCORM Content Aggregation Model. October 1, 2001.
 * Internet (2003-01-20): http://www.adlnet.org
 *
 * @author Werner Randelshofer, Hausmatt 10, Immensee, CH-6405, Switzerland
 * @version 1.2 2006-10-07 Got rid of HTML output in method getInfo because
 * of bug http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4988885
 * <br>1.1 2003-11-03 Method consumeFileNames added.
 * <br>1.0 2003-10-30 HTML output in method toString changed.
 * <br>0.19.5 2003-05-09 Method validate() did not return false when
 * the identifer was invalid.
 * <br>0.19.4 2003-04-02 Method validateSubtree added.
 * <br>0.1 2003-02-02 Created.
 */
public abstract class AbstractElement extends DefaultMutableTreeNode {
    /**
     * This attribute is set by validate().
     */
    private boolean isIdentifierValid = true;
    private boolean isIdentifierLegal = true;
    protected boolean isValid = false;
    protected static ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("ch.randelshofer.scorm.Labels");

    /** Holds the actual namespace of the underlying XML element for validation. */
    protected String namespace;
    
    /** Creates a new instance of AbstractElement */
    public AbstractElement() {
    }
    
    
    /**
     * Dumps the contents of this subtree into the provided string buffer.
     */
    public abstract void dump(StringBuffer buf, int depth);
    
    public String getIdentifier() {
        return null;
    }
    
    /**
     * Searches this subtree for a AbstractElement with the specified identifier.
     * This node is regarded as part of the subtree.
     * Performance note: this method uses a linear search algorithm!
     */
    public AbstractElement findSubtreeByIdentifier(String identifier) {
        Enumeration enm = preorderEnumeration();
        while (enm.hasMoreElements()) {
            AbstractElement element = (AbstractElement) enm.nextElement();
            if (element.getIdentifier() != null
                    && element.getIdentifier().equals(identifier)) {
                return element;
            }
        }
        return null;
    }
    /**
     * Searches the children of this node for a AbstractElement with the specified
     * identifier. Performance note: this method uses a linear search algorithm!
     */
    public AbstractElement findChildByIdentifier(String identifier) {
        Enumeration enm = children();
        while (enm.hasMoreElements()) {
            AbstractElement element = (AbstractElement) enm.nextElement();
            if (element.getIdentifier() != null
                    && element.getIdentifier().equals(identifier)) {
                return element;
            }
        }
        return null;
    }
    
    public void indent(PrintWriter out, int depth) {
        for (int i=0; i < depth; i++) { out.print("  "); }
    }
    
    /**
     * Validates this CAM element and the subtree starting from this element
     * recursively.
     *
     * This method calls recursively the validate() methods of all nodes in
     * this subtree.
     *
     * @return Returns true if all elements in this subtree are valid.
     * Returns false if one or more elements are invalid.
     */
    public boolean validateSubtree() {
        boolean isValid = validate();
        Enumeration enm = children();
        while (enm.hasMoreElements()) {
            AbstractElement child = (AbstractElement) enm.nextElement();
            if (! child.validateSubtree()) isValid = false;
        }
        return isValid;
    }
    /**
     * Validates this CAM element.
     *
     * @return Returns true if this elements is valid.
     */
    public boolean validate() {
        isIdentifierValid = true;
        isIdentifierLegal = true;
        
        String thisID = this.getIdentifier() ;
        if (thisID == null || thisID.length() == 0) return isValid = true;
        
        IMSManifestDocument root = getIMSManifestDocument();
        Enumeration enm = root.preorderEnumeration();
        while (enm.hasMoreElements()) {
            AbstractElement node = (AbstractElement) enm.nextElement();
            if (node != this && node.getIdentifier() != null
                    && node.getIdentifier().equals(thisID)
                    && thisID != null) {
                isIdentifierValid = false;
                break;
            }
        }
        
        // Verify if ID matches W3C naming rules for XML ID's.
        //
        //
        // "The first character of a Name must be a NameStartChar, and any other 
        // characters must be NameChars; this mechanism is used to prevent names 
        // from beginning with European (ASCII) digits or with basic combining 
        // characters. Almost all characters are permitted in names, except 
        // those which either are or reasonably could be used as delimiters. 
        // The intention is to be inclusive rather than exclusive, so that 
        // writing systems not yet encoded in Unicode can be used in XML names."
        // 
        // NameStartChar ::= ":" | [A-Z] | "_" | [a-z] |
        //                   [#xC0-#xD6] | [#xD8-#xF6] | 
        //                   [#xF8-#x2FF] | [#x370-#x37D] | 
        //                   [#x37F-#x1FFF] | [#x200C-#x200D] |
        //                   [#x2070-#x218F] | [#x2C00-#x2FEF] | 
        //                   [#x3001-#xD7FF] | [#xF900-#xFDCF] | 
        //                   [#xFDF0-#xFFFD] | [#x10000-#xEFFFF]
        //
        // NameChar      ::= NameStartChar | "-" | "." | [0-9] | #xB7 |
        //                   [#x0300-#x036F] | [#x203F-#x2040]
        //
        // Quoted from http://www.w3.org/TR/xml11/#sec-common-syn
        //
        // 
        // "An xml:id processor must assure that the following constraints hold 
        // for all xml:id attributes:
        // The normalized value of the attribute is an NCName according to the 
        // Namespaces in XML Recommendation which has the same version as the 
        // document in which this attribute occurs (NCName for XML 1.0, or 
        // NCName for XML 1.1)."
        //
        // Quoted from http://www.w3.org/TR/xml-id/#syntax
        //
        // NCName         ::= NCNameStartChar NCNameChar*
        //                    /* An XML Name, minus the ":" */
        //
        // NCNameChar     ::= NameChar - ':'
        // NCNameStartChar ::= NameStartChar - ':'
        //
        // Quoted from http://www.w3.org/TR/xml-names11/#NT-NCName

        
        isIdentifierLegal = thisID.matches(
                "\\A"+ // The beginning of the input
                
                // NCNameStartChar without the ":"
                "["+
                "A-Z_a-z"+ // [A-Z] | "_" | [a-z] |
                "\\u00c0-\\u00d6\\u00d8-\\u00f6"+ // [#xC0-#xD6] | [#xD8-#xF6] |
                "\\u00f8-\\u02ff\\u0370-\\u037d"+ // [#xF8-#x2FF] | [#x370-#x37D] |
                "\\u037f-\\u1fff\\u200c-\\u200d"+ // [#x37F-#x1FFF] | [#x200C-#x200D] |
                "\\u2070-\\u218f\\u2c00-\\u2fef"+ // [#x2070-#x218F] | [#x2C00-#x2FEF] | 
                "\\u3001-\\ud7ff\\uf900-\\ufdcf"+ // [#x3001-#xD7FF] | [#xF900-#xFDCF] | 
                "\\ufdf0-\\ufffd"+ // [#xFDF0-#xFFFD] | [#x10000-#xEFFFF]] 
                "]"+
                
                //NCNameChar without the ":"
                "[" +
                "A-Z_a-z"+ // [A-Z] | "_" | [a-z] |
                "\\u00c0-\\u00d6\\u00d8-\\u00f6"+ // [#xC0-#xD6] | [#xD8-#xF6] |
                "\\u00f8-\\u02ff\\u0370-\\u037d"+ // [#xF8-#x2FF] | [#x370-#x37D] |
                "\\u037f-\\u1fff\\u200c-\\u200d"+ // [#x37F-#x1FFF] | [#x200C-#x200D] |
                "\\u2070-\\u218f\\u2c00-\\u2fef"+ // [#x2070-#x218F] | [#x2C00-#x2FEF] | 
                "\\u3001-\\ud7ff\\uf900-\\ufdcf"+ // [#x3001-#xD7FF] | [#xF900-#xFDCF] | 
                "\\ufdf0-\\ufffd"+ // [#xFDF0-#xFFFD] | [#x10000-#xEFFFF]] 
                "\\-.0-9\\u00b7"+ // "-" | "." | [0-9] | #xB7 |
                "\\u0300-\\u036f\u203f-\u2040"+ // [#x0300-#x036F] | [#x203F-#x2040]                
                "]*+"+
                
                "\\z" // The end of the input
                );
        
        
        return isValid = isIdentifierValid && isIdentifierLegal && isNamespaceValid();
    }

    public boolean isValid() {
        return isValid;
    }
    
    public IMSManifestDocument getIMSManifestDocument() {
        return (((TreeNode) getRoot()).getChildCount() == 0) ? null : (IMSManifestDocument) ((TreeNode) getRoot()).getChildAt(0);
    }
    
    /**
     * The return value of this method is unspecified until
     * validate() has been done.
     *
     * @return Returns true if the identifier of this element is unique in
     * this tree.
     */
    public boolean isIdentifierValid() {
        return isIdentifierValid;
    }
    public boolean isIdentifierLegal() {
        return isIdentifierLegal;
    }
    
    public String getInfo() {
        String info;
        if (isValid()) {
            info = labels.getString("cam.elementIsValid");
        } else {
            info = labels.getString("cam.elementIsInvalid");
            
            if (! isIdentifierLegal()) {
                info += "\n" + labels.getString("cam.illegalIdentifier");
                
            }
        }
        return info;
    }
    
    /**
     * Removes all file names in the set, which are referenced by this
     * CAM Element.
     */
    public void consumeFileNames(Set fileNames) {
    }

    /** Returns true if the actual namespace of the underlying XML element
     * matches the required namespace.
     */
    protected boolean isNamespaceValid() {
        return namespace==null||namespace.equals(getRequiredNamespace());
    }

    /** Returns the required XML namespace of the element. */
    abstract protected String getRequiredNamespace();
}
