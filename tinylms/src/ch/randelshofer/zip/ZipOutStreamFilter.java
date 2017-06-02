/*
 * @(#)ZipOutStream.java  1.0  11 January 2005
 *
 * Copyright (c) 2004 Werner Randelshofer
 * Staldenmattweg 2, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Werner Randelshofer. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Werner Randelshofer.
 */
package ch.randelshofer.zip;

import java.util.zip.*;
import java.io.*;

/**
 * This is a ZipOutputStream which implemements the ZipOut interface to make
 * this stream interchangeable with ZipOutputFile objects.
 *
 * @author  Werner Randelshofer
 * @version 1.0  11 January 2005  Created.
 */
public class ZipOutStreamFilter implements ZipOut {

    private ZipOutputStream out;

    /**
     * Creates a new ZIP output stream.
     * @param out the actual output stream
     */
    public ZipOutStreamFilter(ZipOutputStream out) {
        this.out = out;
    }

    public OutputStream getOutputStream() {
        return out;
    }

    public void putNextEntry(ZipEntry zipEntry) throws IOException {
        out.putNextEntry(zipEntry);
    }

    public void closeEntry() throws IOException {
        out.closeEntry();
    }

    public void write(int b) throws IOException {
        out.write(b);
    }

    public void write(byte[] b) throws IOException {
        out.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
    }

    public void close() throws IOException {
        out.close();
    }

    public void flush() throws IOException {
        out.flush();
    }
}
