/*
 * @(#)BinaryModel.java  1.0  1999-10-19
 *
 * Copyright (c) 1999 Werner Randelshofer
 * Hausmatt 10, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */
package ch.randelshofer.media.binary;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.util.Vector;

/**
 * Model for untyped binary data.
 *
 * @author  Werner Randelshofer, Hausmatt 10, CH-6405 Immensee, Switzerland
 * @version  1.0  1999-10-19
 */
public class BinaryModel
  {
  // The data is stored in runs of 256 bytes. So we do not
  // need a contiguous area of memory.

  /** Table of elements. */
  private Vector elemTable_;

  /** Number of bytes in the model. */
  private int len_;

  /** Size of an element. */
  private int elemSize_ = 512;

  public BinaryModel()
    {
    elemTable_ = new Vector();
    len_ = 0;
    }

  public BinaryModel(byte[] data) {
    elemTable_ = new Vector();
    if (data == null || data.length == 0) {
      len_ = 0;
    } else {
      elemTable_.addElement(data);
      len_ = elemSize_ = data.length;
    }
  }

  public BinaryModel(InputStream in)
  throws IOException
    {
    this();

    //in = new BufferedInputStream(in);

    byte[] elem = new byte[elemSize_];
    int elemLen = 0;
    while(true)
      {
      int readLen = in.read(elem,elemLen,elemSize_-elemLen);
      if (readLen == -1)
        {
        elemTable_.addElement(elem);
        len_ += elemLen;
        break;
        }
      elemLen += readLen;
      if (elemLen == elemSize_)
        {
        elemTable_.addElement(elem);
        len_ += elemSize_;
        elem = new byte[elemSize_];
        elemLen = 0;
        }
      }
    }

  public int getLength()
    {
    return len_;
    }

  /**
  Gets a sequence of bytes and copies them into the supplied byte array.

  @param off the starting offset >= 0
  @param len the number of bytes >= 0 && <= size - offset
  @param target the target array to copy into
  @exception ArrayIndexOutOfBoundsException  Thrown if the area covered by
    the arguments is not contained in the model.
  */
  public int getBytes(int off, int len, byte[] target)
    {
    if (len + off > len_)
      { len = len_ - off; }

    // Compute the index of the element
    int index = off / elemSize_;

    // Get the element.
    byte[] elem = (byte[])elemTable_.elementAt(index);

    // Count the number of bytes we transfer
    int count = 0;

    // Current index within the element
    int i = off % elemSize_;
    
    // Copy until we are finished
    while(count < len)
      {
      if (i == elem.length)
        {
        elem = (byte[])elemTable_.elementAt(++index);
        i = 0;
        }
      target[count++] = elem[i++];
      }
    return count;
    }
  }
