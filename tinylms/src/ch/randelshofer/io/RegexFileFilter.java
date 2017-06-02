/*
 * @(#)RegexFileFilter.java  1.0  September 12, 2003
 *
 * Copyright (c) 2003 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * http://www.randelshofer.ch
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
import java.util.regex.*;
/**
 * RegexFileFilter.
 *
 * @author Werner Randelshofer
 * @version 1.0 September 12, 2003  Created.
 */
public class RegexFileFilter extends javax.swing.filechooser.FileFilter implements java.io.FileFilter, java.io.FilenameFilter {
    private Pattern pattern;
    private String description;
    
    /** 
     * Creates new ExtensionFileFilter which accepts all directories
     * and all files which mach the given regular expression.
     *
     * @param expression The regular expression.
     * @param description The description of the FileFilter
     */
    public RegexFileFilter(String expression, String description) {
        this.pattern = Pattern.compile(expression);
        this.description = description;
    }
    
    /**
     * Whether the given file is accepted by this filter.
     * Accepts all directories, and all files of which their filename matches
     * the regular expression.
     */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        return pattern.matcher(f.getName()).matches();
    }
    
    /**
     * Tests if a specified file should be included in a file list.
     * Accepts all names which match the regular expression.
     *
     * @param   dir    the directory in which the file was found.
     * @param   name   the name of the file.
     * @return  <code>true</code> if and only if the name should be
     * included in the file list; <code>false</code> otherwise.
     */
    public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
    }

    /**
     * The description of this filter. For example: "JPG and GIF Images"
     * @see FileView#getName
     */
    public String getDescription() {
        return description;
    }
}
