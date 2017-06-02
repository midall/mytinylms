/*
 * @(#)CAM.java  1.0  October 10, 2006
 *
 * Copyright (c) 2006 Werner Randelshofer
 * Hausmatt 10, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.scorm.cam;

/**
 * CAM holds constants for the SCORM Content Aggregation Model (CAM).
 *
 * @author Werner Randelshofer
 * @version 1.0 October 10, 2006 Created.
 */
public class CAM {
    /** The Namespace URI's for SCORM CAM XML Elements. */
    public final static String IMSCP_NS = "http://www.imsproject.org/xsd/imscp_rootv1p1p2";
    public final static String ADLCP_NS = "http://www.adlnet.org/xsd/adlcp_rootv1p2";
    public final static String IMSMD_NS = "http://www.imsglobal.org/xsd/imsmd_rootv1p2p1";
    
    /** Prevent instance creation. */
    private CAM() {
    }
    
    
}
