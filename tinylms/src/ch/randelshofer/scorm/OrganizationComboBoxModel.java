/*
 * @(#)OrganizationComboBoxModel.java  1.0  March 3, 2003
 *
 * Copyright (c) 2003 Werner Randelshofer
 * Hausmatt 10, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.scorm;

import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;
import ch.randelshofer.scorm.cam.*;
/**
 * OrganizationComboBoxModel.
 *
 * @author  Werner Randelshofer, Hausmatt 10, CH-6405 Immensee, Switzerland
 * @version 1.0 March 3, 2003 Created.
 */
public class OrganizationComboBoxModel extends AbstractListModel
implements ComboBoxModel, TreeModelListener, PropertyChangeListener {
    private CourseModel model;
    
    /** Creates a new instance. */
    public OrganizationComboBoxModel() {
    }
    
    public void setCourseModel(CourseModel m) {
        if (model != null) {
            model.removeTreeModelListener(this);
            model.removePropertyChangeListener(this);
            int oldSize = getSize();
            model = null;
            if (oldSize > 0) fireIntervalRemoved(this, 0, oldSize -1);
        }
        model = m;
        if (model != null) {
            model.addTreeModelListener(this);
            model.addPropertyChangeListener(this);
            if (getSize() > 0) fireIntervalAdded(this, 0, getSize() -1);
        }
        if (getOrganizations() != null) setSelectedItem(getOrganizations().getDefaultOrganizationElement());
    }
    public CourseModel getCourseModel() {
        return model;
    }
    public OrganizationsElement getOrganizations() {
        if (model != null) {
            IMSManifestDocument mani = model.getIMSManifestDocument();
            if (mani != null) {
                return mani.getOrganizationsElement();
            }
        }
        return null;
    }
    
    /** Returns the value at the specified index.
     * @param index the requested index
     * @return the value at <code>index</code>
     *
     */
    public Object getElementAt(int index) {
        OrganizationsElement orgs = getOrganizations();
        if (orgs != null) {
            return orgs.getChildAt(index);
        }
        return null;
    }
    
    /**
     * Returns the selected item
     * @return The selected item or <code>null</code> if there is no selection
     *
     */
    public Object getSelectedItem() {
        return (model == null) ? null : model.getSelectedOrganization();
    }
    
    /**
     * Returns the length of the list.
     * @return the length of the list
     *
     */
    public int getSize() {
        OrganizationsElement orgs = getOrganizations();
        if (orgs != null) {
            return orgs.getChildCount();
        }
        return 0;
    }
    
    /**
     * Set the selected item. The implementation of this  method should notify
     * all registered <code>ListDataListener</code>s that the contents
     * have changed.
     *
     * @param anItem the list object to select or <code>null</code>
     *        to clear the selection
     *
     */
    public void setSelectedItem(Object anItem) {
        if (model != null) {
            model.setSelectedOrganization((OrganizationElement) anItem);
        }
    }
    
    /** <p>Invoked after a node (or a set of siblings) has changed in some
     * way. The node(s) have not changed locations in the tree or
     * altered their children arrays, but other attributes have
     * changed and may affect presentation. Example: the name of a
     * file has changed, but it is in the same location in the file
     * system.</p>
     * <p>To indicate the root has changed, childIndices and children
     * will be null. </p>
     *
     * <p>Use <code>e.getPath()</code>
     * to get the parent of the changed node(s).
     * <code>e.getChildIndices()</code>
     * returns the index(es) of the changed node(s).</p>
     *
     */
    public void treeNodesChanged(TreeModelEvent e) {
        fireContentsChanged(this, 0, getSize());
    }
    
    /** <p>Invoked after nodes have been inserted into the tree.</p>
     *
     * <p>Use <code>e.getPath()</code>
     * to get the parent of the new node(s).
     * <code>e.getChildIndices()</code>
     * returns the index(es) of the new node(s)
     * in ascending order.</p>
     *
     */
    public void treeNodesInserted(TreeModelEvent e) {
        setCourseModel(model);
    }
    
    /** <p>Invoked after nodes have been removed from the tree.  Note that
     * if a subtree is removed from the tree, this method may only be
     * invoked once for the root of the removed subtree, not once for
     * each individual set of siblings removed.</p>
     *
     * <p>Use <code>e.getPath()</code>
     * to get the former parent of the deleted node(s).
     * <code>e.getChildIndices()</code>
     * returns, in ascending order, the index(es)
     * the node(s) had before being deleted.</p>
     *
     */
    public void treeNodesRemoved(TreeModelEvent e) {
        setCourseModel(model);
    }
    
    /** <p>Invoked after the tree has drastically changed structure from a
     * given node down.  If the path returned by e.getPath() is of length
     * one and the first element does not identify the current root node
     * the first element should become the new root of the tree.<p>
     *
     * <p>Use <code>e.getPath()</code>
     * to get the path to the node.
     * <code>e.getChildIndices()</code>
     * returns null.</p>
     *
     */
    public void treeStructureChanged(TreeModelEvent e) {
        setCourseModel(model);
    }
    
    /** This method gets called when a bound property is changed.
     * @param evt A PropertyChangeEvent object describing the event source
     *   	and the property that has changed.
     *
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("selectedOrganization")) {
            fireContentsChanged(this, 0, getSize());
        }
    }
    
}
