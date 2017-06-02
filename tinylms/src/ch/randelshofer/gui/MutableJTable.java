/*
 * @(#)MutableJTable.java  2.4  2006-01-04
 *
 * Copyright (c) 2001-2006 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */
package ch.randelshofer.gui;

import ch.randelshofer.gui.table.*;
import ch.randelshofer.util.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


/**
 * A JTable that uses a MutableTableModel. Users can add and remove rows
 * using a popup menu. MutableJTree also supports the standard clipboard
 * operations cut, copy and paste.
 *
 * @author Werner Randelshofer
 * @version 2.4 2006-01-04 Specifying Quaqua "tableHeader" button style for 
 * popup button.
 * <br>2.3.1 2004-02-04 Fixed a null pointer exception in removeNotify,
 * which was caused by method unconfigureEnclosingScrollPane.
 * <br>2.3 2002-11-26 Reworked due to changes in MutableTableModel.
 * <br>2.2.1 2002-04-07 Methods added in MutableTableModel.
 * <br>2.2 2001-08-27 Actions provided by the MutableTableModel are
 *           shown in the popup menu.
 * <br>2.1 2001-07-27 Duplicate function added and Accelerater
 * for Delete Row changed from 'Delete'-Key to Control-Delete.
 * <br>2.0 2001-07-18
 */
public class MutableJTable
extends JTable
implements EditableComponent {
    //private boolean isStriped;
    //private Color alternateColor = new Color(237, 243, 254);
    /**
     * This inner class is used to prevent the API from being cluttered
     * by internal listeners.
     */
    private class EventHandler implements ClipboardOwner {
        /**
         * Notifies this object that it is no longer the owner of the contents
         * of the clipboard.
         */
        public void lostOwnership(Clipboard clipboard, Transferable contents) {
            
        }
    }
    private EventHandler eventHandler = new EventHandler();
    
    
    private final static boolean VERBOSE = true;
    /**
     * Holds locale specific resources.
     */
    private ResourceBundleUtil labels;
    
    /**
     * Listener for popup mouse events.
     */
    private MouseAdapter popupListener;
    
    /**
     * Popup button at the top right corner
     * of the enclosing scroll pane.
     */
    private JButton popupButton;
    
    /**
     * Creates a MutableJTable with a sample model.
     */
    public MutableJTable() {
        super(new DefaultMutableTableModel());
        init();
    }
    /**
     * Creates a MutableJTable with the provided model.
     */
    public MutableJTable(MutableTableModel model) {
        super(model);
        init();
    }
    
    /**
     * This method is called from the constructor to initialize the Object.
     */
    private void init() {
        initComponents();
        
        // The popup button will be placed on the top right corner
        // of the parent JScrollPane when the MutableJList is
        // added to a JScrollPane.
        popupButton = new JButton();
        popupButton.setIcon(Icons.POPUP_ICON);
        popupButton.putClientProperty("Quaqua.Button.style", "tableHeader");
        popupButton.addMouseListener(
        new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (isEnabled())
                    createPopup().show(popupButton, 0, popupButton.getHeight());
            }
        }
        );
        
        // The popup listener provides an alternative way for
        // opening the popup menu.
        popupListener = new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (isEnabled() && evt.isPopupTrigger()) {
                    createPopup().show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
            public void mouseReleased(MouseEvent evt) {
                if (isEnabled() && evt.isPopupTrigger()) {
                    createPopup().show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        };
        addMouseListener(popupListener);
        
        // All locale specific and LAF specific
        // labels are read from a resource bundle.
        initLabels(Locale.getDefault());
    }
    
    public JButton getPopupButton() {
        return popupButton;
    }
    
    /*
    public void setStriped(boolean b) {
        if (b != isStriped) {
            isStriped = b;
            if (b) {
                getTableHeader().setDefaultRenderer(new LeftAlignedHeaderRenderer());
                setDefaultRenderer(Object.class, new AlternatingCellRenderer());
                setIntercellSpacing(new Dimension(1, 1));
                setShowHorizontalLines(false);
                setShowVerticalLines(true);
                setGridColor(Color.lightGray);
            } else {
                //getTableHeader().setDefaultRenderer(new DefaultTableHeaderRenderer());
                setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
                setIntercellSpacing(new Dimension(1, 1));
                setShowHorizontalLines(false);
                setShowVerticalLines(false);
                setGridColor(Color.white);
            }
            firePropertyChange("striped", ! b, b);
        }
    }
    
    public boolean isStriped() {
        return isStriped;
    }
    
    public Color getAlternateColor() {
        return alternateColor;
    }
    
    public void paintComponent(Graphics g) {
        // Let the table paint the real rows
        super.paintComponent(g);
        
        if (isStriped()) {
            // Now check if we need to paint some empty ones
            Dimension s = getSize();
            int rh = getRowHeight();
            int n = getRowCount();
            int th = n * rh;
            if (th < s.height) {
                // Paint the empty rows
                int y = th;
                while (y < s.height) {
                    g.setColor(n % 2 == 0 ? getAlternateColor() : getBackground());
                    g.fillRect(0, y, s.width, rh);
                    y += rh;
                    n++;
                }
                
                // Paint the vertical grid lines
                g.setColor(Color.lightGray);
                TableColumnModel cm = getColumnModel();
                n = cm.getColumnCount();
                y = th;
                int x = 0;
                for (int i = 0; i < n; i++) {
                    TableColumn col = cm.getColumn(i);
                    x += col.getWidth();
                    g.drawLine(x - 1, y, x - 1, s.height);
                }
            }
        }
    }*/
    /**
     * Initializes the labels in a locale specific way.
     */
    private void initLabels(Locale locale) {
        // remove previously installed key strokes
        KeyStroke keyStroke;
        if (labels != null) {
            if (null != (keyStroke = labels.getKeyStroke("editNewAcc"))) {
                unregisterKeyboardAction(keyStroke);
            }
            if (null != (keyStroke = labels.getKeyStroke("editDuplicateAcc"))) {
                unregisterKeyboardAction(keyStroke);
            }
            if (null != (keyStroke = labels.getKeyStroke("editCutAcc"))) {
                unregisterKeyboardAction(keyStroke);
            }
            if (null != (keyStroke = labels.getKeyStroke("editCopyAcc"))) {
                unregisterKeyboardAction(keyStroke);
            }
            if (null != (keyStroke = labels.getKeyStroke("editPasteAcc"))) {
                unregisterKeyboardAction(keyStroke);
            }
            if (null != (keyStroke = labels.getKeyStroke("editDeleteAcc"))) {
                unregisterKeyboardAction(keyStroke);
            }
        }
        
        // get the locale and LAF specific resources
        labels = ResourceBundleUtil.getLAFBundle(
        "ch.randelshofer.gui.Labels", locale
        );
        
        // install key strokes
        if (labels != null) {
            if (null != (keyStroke = labels.getKeyStroke("editNewAcc"))) {
                registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (isEnabled()) editNew();
                    }
                },
                keyStroke,
                WHEN_FOCUSED
                );
            }
            
            if (null != (keyStroke = labels.getKeyStroke("editDuplicateAcc"))) {
                registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (isEnabled()) editDuplicate();
                    }
                },
                keyStroke,
                WHEN_FOCUSED
                );
            }
            
            if (null != (keyStroke = labels.getKeyStroke("editCutAcc"))) {
                registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (isEnabled()) editCut();
                    }
                },
                keyStroke,
                WHEN_FOCUSED
                );
            }
            
            if (null != (keyStroke = labels.getKeyStroke("editCopyAcc"))) {
                registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (isEnabled()) editCopy();
                    }
                },
                keyStroke,
                WHEN_FOCUSED
                );
            }
            
            if (null != (keyStroke = labels.getKeyStroke("editPasteAcc"))) {
                registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (isEnabled()) editPaste();
                    }
                },
                keyStroke,
                WHEN_FOCUSED
                );
            }
            
            if (null != (keyStroke = labels.getKeyStroke("editDeleteAcc"))) {
                registerKeyboardAction(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (isEnabled()) editDelete();
                    }
                },
                keyStroke,
                WHEN_FOCUSED
                );
            }
        }
    }
    /**
     * Configures the enclosing scroll pane.
     */
    protected void configureEnclosingScrollPane() {
        super.configureEnclosingScrollPane();
        
        Container p = getParent();
        if (p instanceof JViewport) {
            Container gp = p.getParent();
            if (gp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane)gp;
                // Make certain we are the viewPort's view and not, for
                // example, the rowHeaderView of the scrollPane -
                // an implementor of fixed columns might do this.
                JViewport viewport = scrollPane.getViewport();
                if (viewport != null && viewport.getView() == this) {
                    // Install a mouse listener for the popup menu
                    viewport.addMouseListener(popupListener);
                    
                    // Install a popup button at the top right corner
                    // of the JScrollPane
                    scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, popupButton);
                }
            }
        }
    }
    /**
     * This method creates a new popup menu.
     *
     * @return The popup menu.
     */
    protected JPopupMenu createPopup() {
        final int[] selectedRows = getSelectedRows();
        int leadSelectionRow = (selectedRows.length == 0) ? -1 : getSelectionModel().getLeadSelectionIndex();
        
        
        final MutableTableModel model = (MutableTableModel) getModel();
        JPopupMenu popup = new JPopupMenu();
        JMenuItem item;
        
        // add the "New Row" menu item.
        final int newRow = (leadSelectionRow == -1) ? getRowCount() : leadSelectionRow + 1;
        Object[] types = model.getCreatableRowTypes(newRow);
        for (int i = 0; i < types.length; i++) {
            final Object newRowType = types[i];
            item = new JMenuItem(labels.getFormatted("editNewMenu", new Object[] {newRowType}));
            item.setMnemonic(labels.getMnemonic("editNewMnem"));
            if (labels.getKeyStroke("editNewAcc") != null)
                item.setAccelerator(labels.getKeyStroke("editNewAcc"));
            item.setIcon(labels.getImageIcon("editNewIcon", getClass()));
            item.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    if (isEditing())
                        getCellEditor().stopCellEditing();
                    model.createRow(newRow, newRowType);
                }
            }
            );
            //item.setEnabled(model.isRowAddable(newRow));
            popup.add(item);
        }
        if (popup.getComponentCount() > 0) popup.addSeparator();
        
        // add the "Cut" menu item.
        item = new JMenuItem(labels.getString("editCutMenu"));
        item.setMnemonic(labels.getMnemonic("editCutMnem"));
        if (labels.getKeyStroke("editCutAcc") != null)
            item.setAccelerator(labels.getKeyStroke("editCutAcc"));
        item.setIcon(labels.getImageIcon("editCutIcon", getClass()));
        item.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                editCut();
            }
        }
        );
        boolean b = selectedRows.length > 0;
        for (int i = 0; i < selectedRows.length; i++) {
            if (! model.isRowRemovable(selectedRows[i])) {
                b = false;
                break;
            }
        }
        item.setEnabled(b);
        popup.add(item);
        
        
        // add the "Copy" menu item.
        item = new JMenuItem(labels.getString("editCopyMenu"));
        item.setMnemonic(labels.getMnemonic("editCopyMnem"));
        if (labels.getKeyStroke("editCopyAcc") != null)
            item.setAccelerator(labels.getKeyStroke("editCopyAcc"));
        item.setIcon(labels.getImageIcon("editCopyIcon", getClass()));
        item.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                editCopy();
            }
        }
        );
        item.setEnabled(selectedRows.length > 0);
        popup.add(item);
        
        
        // add the "Paste" menu item.
        item = new JMenuItem(labels.getString("editPasteMenu"));
        item.setMnemonic(labels.getMnemonic("editPasteMnem"));
        if (labels.getKeyStroke("editPasteAcc") != null)
            item.setAccelerator(labels.getKeyStroke("editPasteAcc"));
        item.setIcon(labels.getImageIcon("editPasteIcon", getClass()));
        item.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                editPaste();
            }
        }
        );
        b = model.isRowAddable(leadSelectionRow);
        for (int i = 0; i < selectedRows.length; i++) {
            if (! model.isRowRemovable(selectedRows[i])) {
                b = false;
                break;
            }
        }
        item.setEnabled(b);
        popup.add(item);
        
        
        if (leadSelectionRow != -1) {
            // Add the duplicate row menu item
            item = new JMenuItem(labels.getString("editDuplicateMenu"));
            item.setMnemonic(labels.getMnemonic("editDuplicateMnem"));
            if (labels.getKeyStroke("editDuplicateAcc") != null)
                item.setAccelerator(labels.getKeyStroke("editDuplicateAcc"));
            item.setIcon(labels.getImageIcon("editDuplicateIcon", getClass()));
            boolean allowed = true;
            for (int i=0; i < selectedRows.length; i++) {
                if (! model.isRowRemovable(i) || ! model.isRowAddable(i)) {
                    allowed = false;
                    break;
                }
            }
            item.setEnabled(allowed);
            item.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    //if (isEditing())
                    //    getCellEditor().stopCellEditing();
                    Transferable t = model.exportRowTransferable(selectedRows);
                    try {
                        model.importRowTransferable(t, DnDConstants.ACTION_COPY, (selectedRows.length == 0) ? -1 : selectedRows[0], false);
                    } catch (Exception e) {
                        throw new InternalError(e.getMessage()); // should never happen
                    }
                }
            }
            );
            popup.add(item);
            
            
            // add the "Delete" menu item.
            item = new JMenuItem(labels.getString("editDeleteMenu"));
            item.setMnemonic(labels.getMnemonic("editDeleteMnem"));
            if (labels.getKeyStroke("editDeleteAcc") != null)
                item.setAccelerator(labels.getKeyStroke("editDeleteAcc"));
            item.setIcon(labels.getImageIcon("editDeleteIcon", getClass()));
            allowed = true;
            for (int i=0; i < selectedRows.length; i++) {
                if (! model.isRowRemovable(i)) {
                    allowed = false;
                    break;
                }
            }
            item.setEnabled(allowed);
            item.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    //if (isEditing())
                    //    getCellEditor().stopCellEditing();
                    for (int i=0; i < selectedRows.length; i++) {
                        model.removeRow(selectedRows[i] - i);
                    }
                }
            }
            );
            popup.add(item);
        }
        
        // add the "Select All" menu item
        item = new JMenuItem(labels.getString("editSelectAllMenu"));
        item.setMnemonic(labels.getMnemonic("editSelectAllMnem"));
        if (labels.getKeyStroke("editSelectAllAcc") != null)
            item.setAccelerator(labels.getKeyStroke("editSelectAllAcc"));
        item.setIcon(labels.getImageIcon("editSelectAllIcon", getClass()));
        item.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (getModel().getRowCount() > 0) {
                    setRowSelectionInterval(0, getModel().getRowCount() - 1);
                }
            }
        }
        );
        item.setEnabled(true);
        popup.add(item);
        
        
        if (leadSelectionRow != -1) {
            // Add actions provided by the MutableTableModel
            HashMap menus = new HashMap();
            Action[] actions = model.getRowActions(selectedRows);
            if (actions != null) {
                for (int j = 0; j < actions.length; j++) {
                    String menuName = (String) actions[j].getValue("Menu");
                    if (menuName != null) {
                        if (menus.get(menuName) == null) {
                            JMenu m = new JMenu(menuName);
                            popup.add(m);
                            menus.put(menuName, m);
                        }
                        ((JMenu) menus.get(actions[j].getValue("Menu"))).add(actions[j]);
                    } else {
                        popup.add(actions[j]);
                    }
                }
            }
        }
        
        return popup;
    }
    
    /**
     * Unconfigures the enclosing scroll pane.
     */
    protected void unconfigureEnclosingScrollPane() {
        Container p = getParent();
        if (p instanceof JViewport) {
            Container gp = p.getParent();
            if (gp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane)gp;
                // Make certain we are the viewPort's view and not, for
                // example, the rowHeaderView of the scrollPane -
                // an implementor of fixed columns might do this.
                JViewport viewport = scrollPane.getViewport();
                if (viewport != null && viewport.getView() == this) {
                    // Remove the previously installed mouse listener for the popup menu
                    viewport.removeMouseListener(popupListener);
                    
                    // Remove the popup button from the top right corner
                    // of the JScrollPane
                    try {
                        // I would like to set the corner to null. Unfortunately
                        // we are called from removeNotify. Removing a component
                        // during removeNotify leads to a NPE.
                        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JPanel());
                        //scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, null);
                    } catch (NullPointerException e) {
                        // This try/catch block is a workaround for
                        // bug 4247092 which is present in JDK 1.3.1 and prior
                        // versions
                    }
                }
            }
        }
        
        super.unconfigureEnclosingScrollPane();
    }
    /**
     * Sets the locale of this component.
     * @param l The locale to become this component's locale.
     */
    public void setLocale(Locale l) {
        super.setLocale(l);
        initLabels(l);
    }
    
    public void updateUI() {
        super.updateUI();
        if (UIManager.getLookAndFeel().getName().equals("MacOS")) {
            getTableHeader().setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

    }//GEN-END:initComponents
    
    /**
     * Inserts a new row after the lead selection row,
     * if the model allows it.
     */
    protected void editNew() {
        if (isEditing())
            getCellEditor().stopCellEditing();
        MutableTableModel model = (MutableTableModel) getModel();
        int row = getSelectionModel().getLeadSelectionIndex() + 1;
        if (row == 0 || row > model.getRowCount()) {
            row = model.getRowCount();
        }
        if (model.getCreatableRowType(row) != null) {
            if (model.isRowAddable(row)) {
                Object rowType = model.getCreatableRowType(row);
                model.createRow(row, rowType);
                getSelectionModel().setSelectionInterval(row, row);
            }
        }
    }
    /** Cuts the selected region and place its contents into the system clipboard.
     */
    public void editCut() {
        final int[] selectedRows = getSelectedRows();
        MutableTableModel m = (MutableTableModel) getModel();
        for (int i=0; i < selectedRows.length; i++) {
            if (! m.isRowRemovable(selectedRows[i])) {
                getToolkit().beep();
                return;
            }
        }
        
        getToolkit().getSystemClipboard().setContents(
        m.exportRowTransferable(selectedRows),
        eventHandler
        );
        
        for (int i=selectedRows.length - 1; i > -1; i--) {
            m.removeRow(selectedRows[i]);
        }
    }
    /**
     * Copies the selected region and place its contents into
     * the system clipboard.
     */
    public void editCopy() {
        if (isEditing())
            getCellEditor().stopCellEditing();
        final int[] selectedRows = getSelectedRows();
        MutableTableModel m = (MutableTableModel) getModel();
        getToolkit().getSystemClipboard().setContents(
        m.exportRowTransferable(selectedRows),
        eventHandler
        );
    }
    /** Pastes the contents of the system clipboard at the caret position.
     */
    public void editPaste() {
        if (isEditing())
            getCellEditor().stopCellEditing();
        int row = getSelectionModel().getLeadSelectionIndex() + 1;
        MutableTableModel m = (MutableTableModel) getModel();
        if (row == 0 || row > m.getRowCount()) {
            row = m.getRowCount();
        }
        
        if (m.isRowAddable(row)) {
            try {
                int count = m.importRowTransferable(
                getToolkit().getSystemClipboard().getContents(this),
                DnDConstants.ACTION_COPY,
                row,
                false
                );
                if (VERBOSE) System.out.println("number of rows imported:"+count);
            } catch (Exception e) {
                if (VERBOSE) {
                    System.out.println("BEEP (Exception)");
                    e.printStackTrace();
                }
                getToolkit().beep();
            }
        } else {
            if (VERBOSE) System.out.println("BEEP (not insertable)");
            getToolkit().beep();
        }
    }
    /** Deletes the component at (or after) the caret position.
     */
    public void editDelete() {
        if (isEditing())
            getCellEditor().stopCellEditing();
        MutableTableModel model = (MutableTableModel) getModel();
        int rows[] = getSelectedRows();
        int i;
        for (i = 0; i < rows.length; i++) {
            if (! model.isRowRemovable(rows[i])) {
                break;
            }
        }
        if (i == rows.length) {
            for (i = 0; i < rows.length; i++) {
                model.removeRow(rows[i] - i);
            }
        }
    }
    
    /** Duplicates the selected region.
     */
    public void editDuplicate() {
        int[] selectedRows = getSelectedRows();
        if (selectedRows.length > 0) {
            MutableTableModel m = (MutableTableModel) getModel();
            
            int row = getSelectionModel().getLeadSelectionIndex() + 1;
            if (m.isRowAddable(row)) {
                try {
                    m.importRowTransferable(
                    m.exportRowTransferable(selectedRows),
                    DnDConstants.ACTION_COPY,
                    row,
                    false
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    getToolkit().beep();
                }
            } else {
                getToolkit().beep();
            }
        }
    }
    
    /**
     * Sets the enabled state of the component.
     */
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        popupButton.setEnabled(b);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
