/*
 * @(#)ExtensionFileFilter.java 1.0.1  2003-09-12
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

package ch.randelshofer.io;

import java.io.*;
import javax.swing.filechooser.*;

/**
 * Accepts all directories, and all files which have
 * the given extensions.
 *
 * @author  Werner Randelshofer
 * @version 1.0.1 2003-09-12 Comments update.
 * <br>1.0 2001-10-14
 */
public class ExtensionFileFilter
extends javax.swing.filechooser.FileFilter
implements java.io.FileFilter, java.io.FilenameFilter {
    /** List of accepted extensions. */
    private String[] acceptedExtensions;
    
    /** The description of the filter. */
    private String description;
    
    /** 
     * Creates new ExtensionFileFilter which accepts all directories
     * and all files with the given extension.
     *
     * @param extension The extension which shall be accepted.
     * @param description The description of the FileFilter
     */
    public ExtensionFileFilter(String extension, String description) {
        this.acceptedExtensions = new String[] {extension};
        this.description = description;
    }
    /** 
     * Creates new ExtensionFileFilter which accepts all directories
     * and all files with the given extension.
     *
     * @param extensions The extension which shall be accepted.
     * @param description The description of the FileFilter
     */
    public ExtensionFileFilter(String[] extensions, String description) {
        this.acceptedExtensions = extensions;
        this.description = description;
    }

    /**
     * Whether the given file is accepted by this filter.
     * Accepts all directories, and all files which have the required 
     * extension(s).
     */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        String ext = getExtension(f.getName());
        if (ext != null) {
            for (int i=0; i < acceptedExtensions.length; i++) {
                if (ext.equals(acceptedExtensions[i])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Tests if a specified file should be included in a file list.
     *
     * @param   dir    the directory in which the file was found.
     * @param   name   the name of the file.
     * @return  <code>true</code> if and only if the name should be
     * included in the file list; <code>false</code> otherwise.
     */
    public boolean accept(File dir, String name) {
        String ext = getExtension(name);
        if (ext != null) {
            for (int i=0; i < acceptedExtensions.length; i++) {
                if (ext.equals(acceptedExtensions[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The description of this filter. For example: "JPG and GIF Images"
     * @see FileView#getName
     */
    public String getDescription() {
        return description;
    }
    
    public String getExtension(String filename) {
        String ext = null;
        int i = filename.lastIndexOf('.');
        
        if (i > 0 && i < filename.length() - 1) {
            ext = filename.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    
}
