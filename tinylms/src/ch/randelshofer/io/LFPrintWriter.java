/*
 * LFPrintWriter.java   1.0  2002-03-15
 *
 * Copyright (c) 2002 Werner Randelshofer
 * Hausmatt 10, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.io;

import java.io.*;

/**
 * This is a subclass of java.io.PrintWriter. 
 *
 * <p>Unlike java.io.PrintWriter the println() methods use a configurable
 * line separator rather than the platform own's notion of a line separator. 
 * By default the <code>'\n'</code> character is used, but can be overriden
 * by calling <code>setLineSeparator</code>.
 *
 * @author  Werner Randelshofer
 * @version 1.0 2002-03-15
 */
public class LFPrintWriter extends PrintWriter {
    /** 
     * If this variable is true, the println() methods will flush
     * the output buffer.
     */
     private boolean autoFlush = false;

    /**
     * Line separator string.
     */
    private String lineSeparator = "\n";
    
    /**
     * Create a new PrintWriter, without automatic line flushing, and
     * with <code>'\n'</code> as line separator.
     *
     * @param  out        A character-output stream
     */
    public LFPrintWriter(Writer out) {
        this(out, false);
    }
    
    /**
     * Create a new PrintWriter,
     * with <code>'\n'</code> as line separator.
     *
     * @param  out        A character-output stream
     * @param  autoFlush  A boolean; if true, the println() methods will flush
     *                    the output buffer
     */
    public LFPrintWriter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
        this.autoFlush = autoFlush;
    }
    
    /**
     * Create a new PrintWriter, without automatic line flushing, from an
     * existing OutputStream.  This convenience constructor creates the
     * necessary intermediate OutputStreamWriter, which will convert characters
     * into bytes using the default character encoding, and
     * with <code>'\n'</code> as line separator.
     *
     * @param  out        An output stream
     *
     * @see java.io.OutputStreamWriter#OutputStreamWriter(java.io.OutputStream)
     */
    public LFPrintWriter(OutputStream out) {
        this(out, false);
    }
    
    /**
     * Create a new PrintWriter from an existing OutputStream.  This
     * convenience constructor creates the necessary intermediate
     * OutputStreamWriter, which will convert characters into bytes using the
     * default character encoding, and
     * with <code>'\n'</code> as line separator.
     *
     * @param  out        An output stream
     * @param  autoFlush  A boolean; if true, the println() methods will flush
     *                    the output buffer
     *
     * @see java.io.OutputStreamWriter#OutputStreamWriter(java.io.OutputStream)
     */
    public LFPrintWriter(OutputStream out, boolean autoFlush) {
        this(new BufferedWriter(new OutputStreamWriter(out)), autoFlush);
    }
    
    /**
     * Terminate the current line by writing the line separator string.  The
     * line separator string can be defined by calling
     * <code>setLineSeparator()</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     */
    public void println() {
        print(lineSeparator);
        if (autoFlush) flush();
    }
    
    /**
     * Gets the line separator of the println() methods.
     */
    public String getLineSeparator() {
        return lineSeparator;
    }
    
    /**
     * Sets the line separator for the println() methods.
     */
    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }
}
