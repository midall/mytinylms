/*
 * @(#)BatchProcessor.java  1.0  August 2, 2005
 *
 * Copyright (c) 2005 Werner Randelshofer
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
import ch.randelshofer.debug.*;
import ch.randelshofer.gui.*;
import ch.randelshofer.io.*;
import ch.randelshofer.util.*;
import java.io.*;
import java.util.*;
import java.util.prefs.*;
import java.util.zip.*;
import java.beans.*;
import org.xml.sax.*;
/**
 * BatchProcessor.
 *
 * @author  Werner Randelshofer
 * @version 1.0 August 2, 2005 Created.
 */
public class BatchProcessor {
    
    /**
     * Creates a new instance.
     */
    public BatchProcessor() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Arguments a = null;
        String argTemplate = "contentpackage [-html:] [-wrap:] [-settings:] [-verify]";
        try {
            a = new Arguments(args, argTemplate);
        } catch (IOException e) {
            System.err.println("TinyLMS failed due to "+e.getMessage());
            System.err.println("Usage:\n  "+argTemplate);
           // e.printStackTrace();
            System.err.flush();
            System.exit(10);
        }
        
        CourseModel cm = new CourseModel();
        
        try {
            File scorm = new File(a.getString("contentpackage"));
            if (scorm.isDirectory()) {
                cm.importContentPackage(scorm);
            } else {
                cm.importPIF(scorm);
            }
        } catch (javax.xml.parsers.ParserConfigurationException e) {
            System.err.println("TinyLMS failed to read content package:"+a.getString("scorm"));
            e.printStackTrace();
            System.err.flush();
            System.exit(10);
        } catch (SAXException e) {
            System.err.println("TinyLMS failed to read content package:"+a.getString("scorm"));
            e.printStackTrace();
            System.err.flush();
            System.exit(10);
        } catch (IOException e) {
            System.err.println("TinyLMS failed to read content package:"+a.getString("scorm"));
            e.printStackTrace();
            System.err.flush();
            System.exit(10);
        }
        
        try {
            if (a.getString("settings") != null) {
                InputStream in = null;
                try {
                    in = new FileInputStream(new File(a.getString("settings")));
                    cm.loadProperties(in);
                } finally {
                    if (in != null) in.close();
                }
            }
        } catch (IOException e) {
            System.err.println("TinyLMS failed to read settings: "+a.getString("settings"));
            e.printStackTrace();
            System.err.flush();
            System.exit(10);
        }
        
        try {
            if (a.getString("html") != null) {
                File html = new File(a.getString("html"));
                cm.createCourse(html, new SilentProgressIndicator());
            }
        } catch (IOException e) {
            System.err.println("TinyLMS failed to create HTML course: "+a.getString("html"));
            e.printStackTrace();
            System.err.flush();
            System.exit(10);
        }
        
        try {
            if (a.getString("wrap") != null) {
                File html = new File(a.getString("html"));
                cm.createPIF(html, new SilentProgressIndicator());
            }
        } catch (IOException e) {
            System.err.println("TinyLMS failed to create wrapped SCORM course: "+a.getString("wrap"));
            e.printStackTrace();
            System.err.flush();
            System.exit(10);
        }
    }
}
