/*
 * @(#)IdentifierGenerator.java 2.0  2009-09-01
 *
 * Copyright (c) 2001-2009 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */
package ch.randelshofer.util;

import java.util.*;

/**
 *
 * @author  werni
 * @version 2.0 2009-09-01 Reworked.
 * <br>1.0 2003-03-05 Revised.
 * <br>0.1 2003-02-09 Created.
 */
public class IdentifierGenerator {

    private HashMap keyValueMap = new HashMap();
    private HashMap valueKeyMap = new HashMap();
    int generator = 0;
    private Object reservedObject = new Object();

    /** Creates a new instance of IdentifierGenerator */
    public IdentifierGenerator() {
    }

    /** Creates a new unique identifier without associating it to a key.
     *
     * @return Returns a new unique identifier.
     */
    public String createIdentifier() {
        return createIdentifier(null);
    }
    /** Creates a new unique identifier without associating it to a key.
     *
     * @param preferredValue The preferred value of the identifier.
     * 
     * @return Returns a new unique identifier. This is only the preferred
     * value, If no identifier with the preferred value already existed,
     */
    public String createIdentifier(String preferredValue) {
        if (preferredValue != null && !valueKeyMap.containsKey(preferredValue)) {
            valueKeyMap.put(preferredValue, reservedObject);
            return preferredValue;
        } else {
            int count = 0;
            String value;
            do {
                value = Integer.toString(generator++, Character.MAX_RADIX);
            } while (valueKeyMap.containsKey(value) && count < 100);
            if (valueKeyMap.containsKey(value)) {
                throw new InternalError();
            }
            valueKeyMap.put(value, reservedObject);
            return value;
        }
    }

    /** Returns a unique identifier for the specified key.
     * A new identifier is created if necessary.
     */
    public String getIdentifier(Object key) {
        return getIdentifier(key, null);
    }

    /** Returns a unique identifier for the specified key.
     * A new identifier is created if necessary. If possible the
     * preferred value is used for the newly created identifier.
     */
    public String getIdentifier(Object key, String preferredValue) {
        if (key == null) {
            return null;
        }
        if (keyValueMap.containsKey(key)) {
            return (String) keyValueMap.get(key);
        } else {
            String value = createIdentifier(preferredValue);
            keyValueMap.put(key, value);
            return value;
        }
    }
}
