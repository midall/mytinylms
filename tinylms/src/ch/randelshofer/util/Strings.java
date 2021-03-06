/*
 * @(#)Strings.java  1.6  2006-06-01
 *
 * Copyright (c) 1999-2006 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland
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
 * This class contains various methods for manipulating strings.
 *
 * @author Werner Randelshofer, Hausmatt 10, Immensee, CH-6405, Switzerland
 * @version 1.6 2006-06-01 Method escapeUnicodeWithHTMLEntities added.
 * <br>1.5 2003-11-03 Method  unescapeURL added.
 * <br>1.4 2003-06-20 Methods with name 'translate' renamed into 'replace'.
 * <br>1.3.2 2003-04-04 Revised.
 * <br>1.3.1 2003-03-18 escapeHTMLMap updated.
 * <br>1.3 2003-02-10 Method escapeHTML(String).
 * <br>1.2 2002-05-10 Method replace(String, String, String) added.
 * <br>1.1 2002-02-05
 */
public class Strings extends Object {
    /**
     * Replaces all occurences of characters in the
     * char array 'from' to the characters in 'to'.
     *
     * @param str The string to be translated.
     * @param from The characters to be replaced.
     * @param to   The replacement characters.
     *
     * @return The translated String.
     */
    public static String replace(String s, char[] from, char[] to) {
        char[] map = new char[256];
        
        int i;
        
        for (i=0; i < 256; i++) {
            map[i] = (char) i;
        }
        
        for (i=0; i < from.length; i++) {
            map[from[i]] = to[i];
        }
        
        
        char[] result = new char[s.length()];
        for (i=0; i < result.length; i++) {
            result[i] = map[s.charAt(i)];
        }
        
        return new String(result);
    }
    /**
     * Replace all occurences of char 'from' to char 'to'.
     *
     * @param str The string to be translated.
     * @param from The character to be replaced.
     * @param to   The replacement character.
     *
     * @return The translated String.
     */
    public static String replace(String str, char from, char to) {
        char[] chars = str.toCharArray();
        for (int i=0; i < chars.length; i++) {
            if (chars[i] == from) {
                chars[i] = to;
            }
        }
        return new String(chars);
    }
    
    /**
     * Replaces all occurences of 'from' by 'to'.
     *
     * @param str The string to be processed.
     * @param from The text to be replaced.
     * @param to   The replacement text.
     *
     * @return The translated String.
     */
    public static String replace(String str, String from, String to) {
        int p0 = 0, p1 = 0;
        StringBuffer buf = new StringBuffer();
        
        while ((p1 = str.indexOf(from, p0)) != -1) {
            buf.append(str.substring(p0, p1));
            buf.append(to);
            p0 = p1 + from.length();
        }
        buf.append(str.substring(p0, str.length()));
        
        return buf.toString();
    }

    /**
     * Escape charactes for method escapeHTML.
     */
    private final static HashMap escapeHTMLMap = new HashMap();
    static {
        HashMap m = escapeHTMLMap;
        m.put(new Character('\u00e4'), "&auml;");
        m.put(new Character('\u00f6'), "&ouml;");
        m.put(new Character('\u00fc'), "&uuml;");
        m.put(new Character('\u00c4'), "&Auml;");
        m.put(new Character('\u00d6'), "&Ouml;");
        m.put(new Character('\u00dc'), "&Uuml;");
        m.put(new Character('"'), "&quot;");
        m.put(new Character('<'), "&lt;");
        m.put(new Character('>'), "&gt;");
        m.put(new Character('\n'), "<br>");
    }
    
    /**
     * Replaces all occurences of special characters with the
     * HTML escape symbols.
     *
     * @param str The string to be processed.
     * @return The escaped String.
     */
    public static String escapeHTML(String str) {
        if (str == null) return null;
        char[] c = str.toCharArray();
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < c.length; i++) {
            String esc = (String) escapeHTMLMap.get(new Character(c[i]));
            if (esc == null){
                buf.append(c[i]);
            } else {
                buf.append(esc);
            }
        }
        return buf.toString();
    }
    /**
     * Replaces all occurences of Unicode characters with the
     * HTML entities symbols.
     *
     * @param str The string to be processed.
     * @return The escaped String.
     */
    public static String escapeUnicodeWithHTMLEntities(String str) {
        if (str == null) return null;
        char[] c = str.toCharArray();
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < c.length; i++) {
            if (c[i] <= 127) {
                buf.append(c[i]);
            } else {
                buf.append("&#x");
                String hex = Integer.toHexString(c[i]);
                for (int j=hex.length(); j < 4; j++) {
                buf.append('0');
                }
                buf.append(hex);
                buf.append(';');
            }
        }
        return buf.toString();
    }
    /**
     * Replaces all occurences of <code>%xx</code> sequences by 
     * the corresponding ASCII character.
     *
     * @param str The string to be processed.
     * @return The unescaped String.
     */
    public static String unescapeURL(String str) {
        if (str == null) return null;
        char[] chars = str.toCharArray();
        StringBuffer buf = new StringBuffer();
        
        int i=0; 
        while (i < chars.length) {
            char c = chars[i++];
            if (c == '%') {
                char high = chars[i++];
                char low  = chars[i++];
                buf.append((char) Integer.valueOf(""+high+low, 16).intValue());
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }
}
