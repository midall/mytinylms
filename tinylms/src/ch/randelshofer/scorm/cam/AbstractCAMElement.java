/*
 * @(#)AbstractLOMElement.java  1.1  2006-10-11
 *
 * Copyright (c) 2001 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer.
 * You may not use, copy or modify this software, except in
 * accordance with the license agreement you entered into with
 * Werner Randelshofer. For details see accompanying license terms.
 */

package ch.randelshofer.scorm.cam;

import ch.randelshofer.scorm.lom.*;
import ch.randelshofer.scorm.AbstractElement;

/**
 * AbstractLOMElement.
 *
 * @author Werner Randelshofer
 * @version 1.0 2009-09-01 Created.
 */
public abstract class AbstractCAMElement extends AbstractElement {
    @Override
    final protected String getRequiredNamespace() {
        return CAM.IMSCP_NS;
    }

}
