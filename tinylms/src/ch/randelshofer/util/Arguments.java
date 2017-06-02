/*
 * @(#)Arguments.java  1.2  2007-02-01
 *
 * Copyright (c) 2002-2007 Werner Randelshofer
 * Hausmatt 10, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */
package ch.randelshofer.util;

import java.util.*;
import java.io.*;

/**
 * Arguments is a utility for parsing arguments which
 * have been passed to the main method of a Java program.
 * <p>
 * Arguments supports three different argument types:
 * <br>Positional Arguments
 * <br>Named Boolean Arguments (Switches)
 * <br>Named String Arguments
 * <p>
 * You can specify whether an argument is mandatory
 * or optional and whether an argument may be specified
 * only once or multiple times.
 * <p>
 * To parse arguments, you create a new Arguments instance
 * and provide the String array to be parsed and an argument
 * template.
 * <p>
 * The template consists of a list of argument names
 * which are prefixed or enclosed by modifiers to
 * identify the type and use of each argument.
 * <p>
 * Summary of template constructs:
 * <br><code>name</code> - Defines a mandatory positional argument.
 * <br><code>-name</code> - Defines a mandatory named boolean argument.
 * <br><code>-name:</code> - Defines a mandatory named string argument.
 * <br><code>[ ]</code> - Enclosing an argument with square brackets makes
 *                        it optional.
 * <br><code>{ }</code> - Enclosing an argument with braces makes it
 *                        optional and allows multiple occurences.
 * <p>
 * Rules for template constructs:
 * <br>All positional arguments must be specified at the start
 * of the template.
 * <br>Mandatory positional arguments may not be specified after
 * optional arguments.
 * <br>Only the last positional argument may have multiple occurences.
 * <br>Boolean arguments may not have multiple occurences.
 * <p>
 * Example:
 * <p>You want to write a command line tool, which formats
 * the volumes on a specific drive.
 * <br>You want to support the following arguments:
 * <pre>
 * {drives} [-c cylindercount] [-t trackcount] [-v] [-b]
 * </pre>
 * The argument drive is an optional positional argument, which
 * may be specified multiple times.
 * <br>All other arguments are optional.
 * <br>-v, -b are named boolean arguments.
 * <br>-c:, -t: are named string arguments.
 * <p>
 * Here's a code sample which parses the template shown above:
 * <pre>
 * public class Format {
 *   public static void main(String[] args) {
 *     try {
 *       Arguments arguments =
 *         new Arguments(args, "{drives} [-c:] [-t:] [-v] [-b]");
 *       List drives = arguments.getList("drives");
 *       String cylinderCount = arguments.getString("-c:"));
 *       String trackCount = arguments.getString("-t:"));
 *       boolean verifyDisk = arguments.getBoolean("-v");
 *       boolean installBootSectors = arguments.getBoolean("-b");
 *
 *       ...do something with the arguments...
 *
 *     } catch (IllegalArgumentException e) {
 *       System.out.println(e);
 *     }
 *   }
 * }
 * </pre>
 *
 * @author  Werner Randelshofer
 * @version 1.2 2007-02-01 Throw IOException instead of IllegalArgumentException
 * when arguments are wrong.
 * <br>1.1.2 2006-10-10 Added support for "--help" option. 
 * <br>1.1.1 2002-09-25 Comments updated.
 * <br>1.1 2002-09-19 Method <code>getStringAsList(String, String)</code>
 *                         added.
 * <br>1.0 2002-02-17
 */
public class Arguments {
    /**
     * Stores the parsed arguments.
     * <br>The key holds the name of the argument. The prefix '-' and
     * the suffix ':' for named arguments are retained.
     * <br>The value holds the value of the argument.
     * null = argument not specified. Boolean = value of a boolean argument.
     * String = value of a single occurence text argument. LinkedList = value of
     * a multiple occurence text argument.
     */
    private HashMap arguments;
    
    /** Names of the mandatory arguments. */
    private HashSet mandatoryKeys = new HashSet();
    
    /** Maps indexes to names of positional arguments. */
    ArrayList positionToIdentifierMap = new ArrayList();
    
    private String template;
    
    /**
     * Creates a new instance and parses the arguments
     * using the given template.
     *
     * @param args The arguments to be parsed.
     * @param template The template to be used for parsing.
     * @throws IOException Ein-/Ausgabefehler.
     */
    public Arguments(String[] args, String template)
    throws IOException {
        arguments = new HashMap();
        this.template = template;
        parseTemplate(template);
        parseArgs(args);
        verify();
    }
    
    /**
     * Creates a new instance which uses the given
     * template to parse arguments.
     *
     * @param template The template to be used for parsing.
     * @throws IOException Ein-/Ausgabefehler.
     */
    public Arguments(String template)
    throws IOException {
        arguments = new HashMap();
        this.template = template;
        parseTemplate(template);
    }
    
    /**
     * Cleans the arguments map from the remnants of
     * previous calls to parseArguments.
     */
    public void clear() {
        Iterator iter = arguments.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object value = entry.getValue();
            if (value != null) {
                if (value instanceof List) {
                    ((List) value).clear();
                } else if (value instanceof Boolean) {
                    entry.setValue(Boolean.FALSE);
                } else {
                    entry.setValue(null);
                }
            }
        }
    }
    
    /**
     * Verifies if all mandatory arguments have been
     * specified.
     *
     * @throws IllegalArgumentException If mandatory arguments are missing.
     */
    public void verify()
    throws IllegalArgumentException {
        boolean checkFailed = false;
        StringBuffer buf = new StringBuffer("Missing arguments: ");
        Iterator iter = mandatoryKeys.iterator();
        
        while (iter.hasNext()) {
            String key = (String) iter.next();
            
            Object value = arguments.get(key);
            if (value == null
            || value instanceof List && ((List) value).size() == 0
            || value instanceof Boolean && ((Boolean) value).equals(Boolean.FALSE)) {
                if (checkFailed) buf.append(", ");
                buf.append(key);
                checkFailed = true;
            }
        }
        buf.append('.');
        
        if (checkFailed) {
            throw new IllegalArgumentException(buf.toString());
        }
    }
    /**
     * Parses the template.
     */
    private void parseTemplate(String template)
    throws IllegalArgumentException {
        try {
        StreamTokenizer scanner = new StreamTokenizer(new StringReader(template));
        scanner.resetSyntax();
        scanner.wordChars('a','z');
        scanner.wordChars('A','Z');
        scanner.wordChars('0','9');
        scanner.wordChars(128 + 32, 255);
        scanner.whitespaceChars(0,' ');
        
        boolean positionalArgsAllowed = true;
        boolean positionalMandatoryArgsAllowed = true;
        
        while (scanner.nextToken() != StreamTokenizer.TT_EOF) {
            boolean isMultiple = scanner.ttype == '{';
            boolean isOptional = isMultiple || scanner.ttype == '[';
            
            if (isMultiple || isOptional) {
                scanner.nextToken();
            }
            
            boolean isNamedArgument = scanner.ttype == '-';
            if (isNamedArgument) {
                scanner.nextToken();
            }
            
            if (scanner.ttype != StreamTokenizer.TT_WORD) {
                throw new IllegalArgumentException("Illegal Template: Argument name missing.");
            }
            String identifier = scanner.sval;
            
            boolean isNamedStringArgument;
            if (isNamedArgument) {
                isNamedStringArgument = scanner.nextToken() == ':';
                if (! isNamedStringArgument) {
                    scanner.pushBack();
                }
            } else {
                isNamedStringArgument = false;
            }
            
            // Add argument to map
            if (isNamedStringArgument) {
                // It is a named string argument
                arguments.put(identifier, (isMultiple) ? new LinkedList() : null);
                positionalArgsAllowed = false;
                positionalMandatoryArgsAllowed = false;
            } else if (isNamedArgument) {
                // If it is a named argument but not a named string argument
                // it has to be a named boolean argument.
                arguments.put(identifier, (isMultiple) ? (Object) new LinkedList() : (Object) Boolean.FALSE);
                positionalArgsAllowed = false;
                positionalMandatoryArgsAllowed = false;
            } else {
                // If it is not a named argument
                // it's a positinal argument.
                if (! positionalArgsAllowed) {
                    throw new IllegalArgumentException("Illegal Template: Positional arguments must be specified at the start of the template.");
                }
                if (! isOptional && ! positionalMandatoryArgsAllowed) {
                    throw new IllegalArgumentException("Illegal Template: Mandatory positional arguments must be specified at the start of the template.");
                }
                
                arguments.put(identifier, (isMultiple) ? new LinkedList() : null);
                positionToIdentifierMap.add(identifier);
                if (isOptional) {
                    positionalMandatoryArgsAllowed = false;
                }
            }
            
            if (! isOptional) {
                mandatoryKeys.add(identifier);
            }
            
            if (isMultiple) {
                if (scanner.nextToken() != '}') {
                    throw new IllegalArgumentException("Illegal Template: Closing brace missing.");
                }
            } else if (isOptional) {
                if (scanner.nextToken() != ']') {
                    throw new IllegalArgumentException("Illegal Template: Closing square bracket missing.");
                }
            }
        }
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    
    /**
     * This method reads arguments using a java.util.Properties
     * from the given InputStream.
     * You can specify whether the arguments in the property file
     * should override existing values or not.
     * <p>
     * Note: This method does not check for missing mandatory
     * arguments. You must call verify() to make sure that
     * all mandatory arguments are present.
     *
     * @param in The input stream.
     * @param override If true, values from the input stream
     * override existing argument values.
     * @exception IOException if an error occurred when reading
     * from the input stream.
     *
     * @see #verify()
     */
    public void load(InputStream in, boolean override)
    throws IOException {
        Properties p = new Properties();
        p.load(in);
        
        Iterator iter = p.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            if (! arguments.containsKey(key)) {
                throw new IllegalArgumentException("Unknown option '"+key+"'.");
            }
            String newValue = (String) entry.getValue();
            Object oldValue = arguments.get(key);
            if (oldValue == null || oldValue instanceof String) {
                if (override || oldValue == null) {
                    arguments.put(key, newValue);
                }
            } else if (oldValue instanceof List) {
                LinkedList list = (LinkedList) oldValue;
                if (override || list.size() == 0) {
                    list.clear();
                    StreamTokenizer scanner = new StreamTokenizer(new StringReader(newValue));
                    scanner.resetSyntax();
                    scanner.wordChars(33, 255);
                    scanner.whitespaceChars(0, ' ');
                    scanner.quoteChar('"');
                    scanner.quoteChar('\'');
                    while (scanner.nextToken() != StreamTokenizer.TT_EOF) {
                        list.add(scanner.sval);
                    }
                }
            } else if (oldValue instanceof Boolean) {
                if (override || oldValue.equals(Boolean.FALSE)) {
                    arguments.put(key, Boolean.valueOf(newValue));
                }
            }
        }
    }
    
    
    /**
     * Parses the arguments from the Reader.
     * The arguments must match the template.
     * After successful execution of this method, the
     * arguments can be retrieved using the get-Methods.
     * <p>
     * Note: This method does not check for missing mandatory
     * arguments. You must call verify() to make sure that
     * all mandatory arguments are present.
     *
     * @see #verify()
     * @see #getString(String)
     * @see #getString(String, String)
     * @see #getBoolean(String)
     * @see #getList(String)
     * @param in The Reader.
     * @throws IOException If an I/O exception is encountered or if the
     * input does not match the template.
     */
    public void parseArgs(Reader in)
    throws IOException {
        LinkedList list = new LinkedList();
        StreamTokenizer scanner = new StreamTokenizer(in);
        scanner.resetSyntax();
        scanner.quoteChar('\'');
        scanner.quoteChar('"');
        scanner.slashSlashComments(true);
        scanner.slashStarComments(true);
        while (scanner.nextToken() != StreamTokenizer.TT_EOF) {
            list.add(scanner.sval);
        }
        parseArgs((String[]) list.toArray(new String[list.size()]));
    }
    
    /**
     * Parses the arguments.
     * After successful execution of this method, the
     * arguments can be retrieved using the get-Methods.
     * <p>
     * Note: This method does not check for missing mandatory
     * arguments. You must call verify() to make sure that
     * all mandatory arguments are present.
     *
     * @see #verify()
     *
     * @see #getString(String)
     * @see #getString(String, String)
     * @see #getBoolean(String)
     * @see #getList(String)
     * @param args The arguments to be parsed.
     * @throws IOException If the arguments don't match the template.
     */
    public void parseArgs(String[] args)
    throws IOException {
        if (args.length == 1 && 
                (args[0].equals("--help") || args[0].equals("-h")) ||
                args[0].equals("?")) {
            System.out.println("Supported Parameters: "+template);
            System.exit(0);
        }
        
        
        
        
        Iterator iter;
        
        // Parse the arguments
        // -------------------
        int i=0;
        String key;
        Object value;
        
        // Handle positional arguments
        for (; i < args.length && i < positionToIdentifierMap.size() && ! args[i].startsWith("-"); i++) {
            Object oldValue = arguments.get(positionToIdentifierMap.get(i));
            if (oldValue instanceof LinkedList) {
                ((LinkedList) oldValue).add(args[i]);
            } else {
                arguments.put(positionToIdentifierMap.get(i), args[i]);
            }
        }
        
        // Handle a multi-occurence positional argument
        Object lastPositionalValue = arguments.get(positionToIdentifierMap.get(positionToIdentifierMap.size() - 1));
        if (lastPositionalValue instanceof List) {
            for (; i < args.length && ! args[i].startsWith("-"); i++) {
                ((List) lastPositionalValue).add(args[i]);
            }
        }
        
        // Handle named arguments
        for (; i < args.length; i++) {
            if (args[i].charAt(0) != '-') {
                throw new IOException("Argument '"+args[i]+"' not allowed here.");
            }
            
            key = args[i].substring(1);
            
            if (! arguments.containsKey(key)) {
                throw new IOException("Unknown option '"+key+"'.");
            }
            
            Object oldValue = arguments.get(key);
            
            if (oldValue == null) {
                // Its a named string argument
                if (i + 1 >= args.length || args[i + 1].charAt(0) == '-') {
                    throw new IOException("Value for option '"+key+"' missing.");
                }
                arguments.put(key, args[++i]);
                
            } else if (oldValue.equals(Boolean.FALSE)) {
                // Its a named boolean argument
                arguments.put(key, Boolean.TRUE);
                
            } else if (oldValue instanceof List) {
                // Its a list argument
                if (i + 1 >= args.length || args[i + 1].startsWith("-")) {
                    throw new IOException("Value for option '"+key+"' missing.");
                }
                while (i + 1 < args.length && ! args[i + 1].startsWith("-")) {
                    ((List) oldValue).add(args[++i]);
                }
                
            } else {
                throw new IOException("Option '"+key+"' must not be specified multiple times.");
            }
        }
    }
    
    /**
     * Returns the value of a single-occurence positional
     * argument or single-occurence named string argument.
     *
     * @param key The name of the argument. The key of named
     * string arguments must be enclosed in '-' and ':'.
     *
     * @return The value or null if the argument has not
     * been specified.
     *
     * @exception ClassCastException when the value of
     * the argument is not of the required type.
     */
    public String getString(String key) {
        return (String) arguments.get(key);
    }
    /**
     * Returns the value of a single-occurence positional
     * argument or single-occurence named string argument
     * as a list.
     *
     * @param key The name of the argument. The key of named
     * string arguments must be enclosed in '-' and ':'.
     * @param delimiter A String containing delimiter characters
     * used to split up the string into list elements.
     *
     * @return The value or null if the argument has not
     * been specified.
     *
     * @exception ClassCastException when the value of
     * the argument is not of the required type.
     */
    public List getStringAsList(String key, String delimiter) {
        LinkedList list = new LinkedList();
        String value = (String) arguments.get(key);
        if (value != null) {
            StringTokenizer tt = new StringTokenizer(value, delimiter);
            while (tt.hasMoreTokens()) {
                list.add(tt.nextToken());
            }
        }
        return list;
    }
    /**
     * Returns the value of a single-occurence positional
     * argument or single-occurence named string argument.
     *
     * @param key The name of the argument.
     * @param defaultValue The value to be returned when
     * the argument has not been specified.
     *
     * @return The value or the specified default if
     * the argument has not been specified.
     *
     * @exception ClassCastException when the value of
     * the argument is not of the required type.
     */
    public String getString(String key, String defaultValue) {
        String value = (String) arguments.get(key);
        return (value == null) ? defaultValue : value;
    }
    /**
     * Returns the value of a multi-occurence positional
     * argument or multi-occurence named string argument.
     *
     * @param key The name of the argument.
     *
     * @return A list holding the values.
     *
     * @exception ClassCastException when the value of
     * the argument is not of the required type.
     */
    public LinkedList getList(String key) {
        return (LinkedList) arguments.get(key);
    }
    /**
     * Returns the value of a multi-occurence positional
     * argument or multi-occurence named string argument.
     *
     * @param key The name of the argument.
     * @param defaultValue The value to be returned when
     * the argument has not been specified.
     *
     * @return A list holding the values.
     *
     * @exception ClassCastException when the value of
     * the argument is not of the required type.
     */
    public List getList(String key, List defaultValue) {
        List list = (LinkedList) arguments.get(key);
        return (list.size() == 0) ? defaultValue : list;
    }
    /**
     * Returns the value of a single-occurence named
     * boolean argument.
     *
     * @param key The name of the argument. The key
     * must be prefixed with '-'.
     *
     * @return True if the argument has been specified.
     *
     * @exception ClassCastException when the value of
     * the argument is not of the required type.
     */
    public boolean getBoolean(String key) {
        return arguments.get(key) == Boolean.TRUE;
    }
    
    /**
     * Returns a string presentation describing the arguments.
     * @return Human readable description. */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(super.toString());
        buf.append('{');
        Iterator i = arguments.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry) i.next();
            buf.append(entry.getKey());
            buf.append('=');
            
            if (entry.getValue() instanceof List) {
                buf.append('{');
                Iterator j = ((List) entry.getValue()).iterator();
                while (j.hasNext()) {
                    buf.append(j.next());
                    if (j.hasNext()) buf.append(',');
                }
                buf.append('}');
            } else {
                buf.append(entry.getValue());
            }
            
            if (i.hasNext()) buf.append(", ");
        }
        buf.append('}');
        return buf.toString();
    }
}
