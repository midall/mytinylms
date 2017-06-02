/*
 * @(#)StudentModel.java  1.0  August 24, 2003
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

package ch.randelshofer.scorm;

import ch.randelshofer.beans.*;
import java.beans.*;
import java.security.*;
import ch.randelshofer.util.*;
import gnu.java.security.provider.*;
import java.io.*;
/**
 * StudentModel.
 *
 * @author Werner Randelshofer
 * @version 1.0 August 24, 2003  Created.
 */
public class StudentModel extends AbstractBean implements Cloneable {
    private String id, password, lastName, firstName, middleInitial;
    /**
     * The electronic fingerprint of the password.
     * 32 bit hex encoded salt concatenated with the password
     * and munched with SHA1.
     */
    private String passwordDigest;
    private static SecureRandom random;
    
    /** Creates a new instance. */
    public StudentModel() {
    }
    
    public StudentModel(String id, String lastName, String firstName, String middleInitial) {
        this.id = id;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
    }
    
    public void setID(String newValue) {
        String oldValue = id;
        id = newValue;
        propertySupport.firePropertyChange("id", oldValue, newValue);
    }
    
    public void setPassword(String newValue) {
        String oldValue = password;
        password = newValue;
        propertySupport.firePropertyChange("password", oldValue, newValue);
        passwordDigest = null;
    }
    public void setLastName(String newValue) {
        String oldValue = lastName;
        lastName = newValue;
        propertySupport.firePropertyChange("lastName", oldValue, newValue);
    }
    public void setFirstName(String newValue) {
        String oldValue = firstName;
        firstName = newValue;
        propertySupport.firePropertyChange("firstName", oldValue, newValue);
    }
    public void setMiddleInitial(String newValue) {
        String oldValue = middleInitial;
        middleInitial = newValue;
        propertySupport.firePropertyChange("middleInitial", oldValue, newValue);
    }
    public String getID() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getMiddleInitial() {
        return middleInitial;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPasswordDigest(String newValue) {
        String oldValue = passwordDigest;
        passwordDigest = newValue;
        // For security reasons we erase the password if we have a digest.
        if (newValue != null) {
            password = "******";
        }
    }
    public String getPasswordDigest() {
        try {
            if (passwordDigest == null && password != null && password.trim().length() != 0) {
                // Lazily create the random generator
                if (random == null) {
                    random = new SecureRandom();
                }
                
                // Create 32 bits of salt
                String salt = Integer.toString(random.nextInt(), 16);
                
                // Compute the message digest for the password
                // We use Unicode encoding because this encoding is supported
                // by the String.charCodeAt(index) method of JavaScript.
                SHA md = new SHA();
                //md.update(salt.getBytes("UTF-16BE"));
                //md.update(password.getBytes("UTF-16BE"));
                md.update(salt.getBytes("ASCII"));
                md.update(password.getBytes("ASCII"));
                
                passwordDigest = salt + "." + ArrayUtil.toHexString(md.digest());
                //password = null;
            }
            return passwordDigest;
            
        } catch (UnsupportedEncodingException e) {
            InternalError t = new InternalError(e.toString());
            t.initCause(e);
            throw t;
        }
    }
    
    public Object clone() {
        try {
        StudentModel that = (StudentModel) super.clone();
        that.propertySupport = new PropertyChangeSupport(that);
        return that;
        } catch (CloneNotSupportedException e) {
            InternalError t = new InternalError(e.toString());
            t.initCause(e);
            throw t;
        }
    }
}
