/*
 * @(#)ZipOutDirectory.java  1.1  2009-01-08
 *
 * Copyright (c) 2004-2009 Werner Randelshofer
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

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * ZipOutDirectory writes zip entries as regular files into a directory on the
 * file system.
 *
 * @author Werner Randelshofer
 * @version 1.1 2009-01-08 If a file already exists, but does not have the
 * right case, delete it instead of just overwriting its contents.
 * <br>1.0 26. Juli 2006 Created.
 */
public class ZipOutDirectory extends OutputStream implements ZipOut {

    private OutputStream out;
    private File dir;
    private ZipEntry entry;

    /** Creates a new instance. */
    public ZipOutDirectory(File directory) {
        this.dir = directory;
    }

    public void putNextEntry(ZipEntry zipEntry) throws IOException {
        if (out != null) {
            closeEntry();
        }
        entry = zipEntry;
    }

    private void ensureOpen() throws IOException {
        if (out == null) {
            File file = new File(dir, entry.getName().replace('/', File.separatorChar));
            if (file.exists() &&
                    file.getCanonicalFile().getName().equals(file.getName())) {
                // If the filename does not match, we have to delete the
                // file.
                file.delete();
            } else {
                file.getParentFile().mkdirs();
            }
            if (!file.isDirectory()) {
                out = new BufferedOutputStream(new FileOutputStream(file));
            }
        }
    }

    public void closeEntry() throws IOException {
        if (out != null) {
            out.close();
            out = null;
        }
        entry = null;
    }

    public void write(int b) throws IOException {
        ensureOpen();
        out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        ensureOpen();
        out.write(b, off, len);
    }

    @Override
    public void write(byte[] b) throws IOException {
        ensureOpen();
        out.write(b);
    }

    @Override
    public void close() throws IOException {
        if (out != null) {
            closeEntry();
        }
    }

    @Override
    public void flush() throws IOException {
        if (out != null) {
            out.flush();
        }
    }

    public OutputStream getOutputStream() throws IOException {
        ensureOpen();
        return out;
    }
}
