/*
 * @(#)TinyLMSApp.java  1.5.1  2008-03-20
 *
 * Copyright (c) 2005-2008 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.scorm;

import java.lang.reflect.*;
import java.io.*;
/**
 * Launcher for the TinyLMS application.
 * If the launcher is invoked without command line parameters, it will start
 * the graphical user interface of TinyLMS: the CourseBuilder.
 * If it is invoked with command line parameters, it will start the batch
 * processor: BatchProcessor.
 *
 * @author  Werner Randelshofer
 * @version 1.5.1 2008-03-20 Get version number from package.
 * <br>1.5 2006-05-26 Read version from a file.
 * <br>1.4.1 2006-05-06 Reworked.
 * <br>1.0 August 2, 2005 Created.
 */
public class TinyLMSApp {
    /**
     * Creates a new instance.
     */
    public TinyLMSApp() {
    }
    public static String getVersion() {
        return TinyLMSApp.class.getPackage().getImplementationVersion();
    }
    
    public static void main(String[] args) {
        /*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.exit(0);
        }*/
        
        // The following code uses reflection to invoke the main method of
        // class CourseBuilder or class BatchProcessor.
        // Reflection is used to avoid unnecessarily loading lots of UI classes
        // just for a batch invocation of TinyLMS.
        
        try {
            Class c;
            if (args.length == 0) {
                c = Class.forName("ch.randelshofer.scorm.CourseBuilder");
                //CourseBuilder.main(args);
            } else {
                c = Class.forName("ch.randelshofer.scorm.BatchProcessor");
                //BatchProcessor.main(args);
            }
            Method m = c.getMethod("main", new Class[] {String[].class});
            m.invoke(null, new Object[] {args});
        } catch (InvocationTargetException e) {
            e.getTargetException().printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
