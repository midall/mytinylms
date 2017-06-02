/*
 * @(#)EditableComponent.java  2.0  2001-07-18
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
package ch.randelshofer.gui;

/**
 * This interface must be implemented by components
 * which support clipboard access.
 * This interface is used by Actions.EditCutAction, Actions.EditCopyAction,
 * Actions.EditPasteAction and Actions.EditDeleteAction to determine
 * if the action can be performed.
 *
 * @author Werner Randelshofer
 * @version 2.0 2001-07-18
 */

public interface EditableComponent {
	/**
	 * Copies the selected region and place its contents into the system clipboard.
	 */
	public void editCopy();
	/**
	 * Cuts the selected region and place its contents into the system clipboard.
	 */
	public void editCut();
	/**
	 * Deletes the component at (or after) the caret position.
	 */
	public void editDelete();
	/**
	 * Pastes the contents of the system clipboard at the caret position.
	 */
	public void editPaste();
}