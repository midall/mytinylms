/*
 * @(#)UndoRedoManager.java 1.2.1  2003-11-02
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

package ch.randelshofer.undo;

import ch.randelshofer.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import java.util.*;

/**
 * Same as javax.swing.UndoManager but provides actions for undo and
 * redo operations.
 *
 * @author  Werner Randelshofer
 * @version 1.2.1 2003-11-02 Adapted to changes in ResourceBundleUtil. 
 * <br>1.2 2003-03-16 Texts and accelerator keys are now read from a
 * resource bundle.
 * <br>1.1.2 2003-03-12 Actions use now KeyStroke objects instead of
 * String objects as accelerator keys.
 * <br>1.1.1 2002-05-10 Method addEdit is now smarter when
 * determining whether an edit is significant.
 * <br>1.1 2002-04-08 Method hasSignificantEdits/clearChanged added.
 * <br>1.0 2001-10-09
 */
public class UndoRedoManager extends UndoManager {//javax.swing.undo.UndoManager {
    /**
     * The resource bundle used for internationalisation.
     */
    private ResourceBundleUtil labels;
    /**
     * This flag is set to true when at
     * least one significant UndoableEdit
     * has been added to the manager since the
     * last call to discardAllEdits.
     */
    private boolean hasSignificantEdits = false;
    
    /**
     * This flag is set to true when an undo or redo
     * operation is in progress. The UndoRedoManager
     * ignores all incoming UndoableEdit events while
     * this flag is true.
     */
    private boolean undoOrRedoInProgress;
    
    /**
     * Sending this UndoableEdit event to the UndoRedoManager
     * disables the Undo and Redo functions of the manager.
     */
    public final static UndoableEdit DISCARD_ALL_EDITS = new AbstractUndoableEdit() {
        public boolean canUndo() {
            return false;
        }
        public boolean canRedo() {
            return false;
        }
    };
    
    /**
     * Undo Action for use in a menu bar.
     */
    private class UndoAction
    extends AbstractAction {
        public UndoAction() {
            super(labels.getString("undo"));
            putValue(Action.ACCELERATOR_KEY, labels.getAcc("undo"));
            setEnabled(false);
        }
        
        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent evt) {
            try {
                undo();
            } catch (CannotUndoException e) {
                System.out.println("Cannot undo: "+e);
            }
        }
        
    }
    
    /**
     * Redo Action for use in a menu bar.
     */
    private class RedoAction
    extends AbstractAction {
        public RedoAction() {
            super(labels.getString("redo"));
            putValue(Action.ACCELERATOR_KEY, labels.getAcc("redo"));
            setEnabled(false);
        }
        
        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent evt) {
            try {
                redo();
            } catch (CannotRedoException e) {
                System.out.println("Cannot redo: "+e);
            }
        }
        
    }
    
    /** The undo action instance. */
    private UndoAction undoAction;
    /** The redo action instance. */
    private RedoAction redoAction;
    
    /** Creates new UndoRedoManager */
    public UndoRedoManager() {
        labels = ResourceBundleUtil.getLAFBundle("ch.randelshofer.undo.Labels", Locale.getDefault());
        undoAction = new UndoAction();
        redoAction = new RedoAction();
    }
    
    /**
     * Discards all edits.
     */
    public void discardAllEdits() {
        super.discardAllEdits();
        updateActions();
        hasSignificantEdits = false;
    }
    
    /**
     * Returns true if at least one significant UndoableEdit
     * has been added since the last call to discardAllEdits.
     */
    public boolean hasSignificantEdits() {
        return hasSignificantEdits;
    }
    
    /**
     * If inProgress, inserts anEdit at indexOfNextAdd, and removes
     * any old edits that were at indexOfNextAdd or later. The die
     * method is called on each edit that is removed is sent, in the
     * reverse of the order the edits were added. Updates
     * indexOfNextAdd.
     *
     * <p>If not inProgress, acts as a CompoundEdit</p>
     *
     * <p>Regardless of inProgress, if undoOrRedoInProgress,
     * calls die on each edit that is sent.</p>
     *
     *
     * @see CompoundEdit#end
     * @see CompoundEdit#addEdit
     */
    public boolean addEdit(UndoableEdit anEdit) {
        if (undoOrRedoInProgress) {
            anEdit.die();
            return true;
        }
        boolean success = super.addEdit(anEdit);
        updateActions();
        if (success && anEdit.isSignificant() && editToBeUndone() == anEdit) {
            hasSignificantEdits = true;
        }
        return success;
    }
    /**
     * Gets the undo action for use as an Undo menu item.
     */
    public Action getUndoAction() {
        return undoAction;
    }
    
    /**
     * Gets the redo action for use as a Redo menu item.
     */
    public Action getRedoAction() {
        return redoAction;
    }
    
    /**
     * Updates the properties of the UndoAction
     * and of the RedoAction.
     */
    private void updateActions() {
        if (canUndo()) {
            undoAction.setEnabled(true);
            undoAction.putValue(Action.NAME, getUndoPresentationName());
        } else {
            undoAction.setEnabled(false);
            undoAction.putValue(Action.NAME, "Undo");
        }
        
        if (canRedo()) {
            redoAction.setEnabled(true);
            redoAction.putValue(Action.NAME, getRedoPresentationName());
        } else {
            redoAction.setEnabled(false);
            redoAction.putValue(Action.NAME, "Redo");
        }
    }
    
    /**
     * Undoes the last edit event.
     * The UndoRedoManager ignores all incoming UndoableEdit events,
     * while undo is in progress.
     */
    public void undo()
    throws CannotUndoException {
        undoOrRedoInProgress = true;
        try {
            super.undo();
        } finally {
            undoOrRedoInProgress = false;
            updateActions();
        }
    }
    
    /**
     * Redoes the last undone edit event.
     * The UndoRedoManager ignores all incoming UndoableEdit events,
     * while redo is in progress.
     */
    public void redo()
    throws CannotUndoException {
        undoOrRedoInProgress = true;
        try {
            super.redo();
        } finally {
            undoOrRedoInProgress = false;
            updateActions();
        }
    }
    
    /**
     * Undoes or redoes the last edit event.
     * The UndoRedoManager ignores all incoming UndoableEdit events,
     * while undo or redo is in progress.
     */
    public void undoOrRedo()
    throws CannotUndoException, CannotRedoException {
        undoOrRedoInProgress = true;
        try {
            super.undoOrRedo();
        } finally {
            undoOrRedoInProgress = false;
            updateActions();
        }
    }
}
