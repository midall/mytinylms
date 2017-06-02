/*
 * @(#)LayoutView.java 1.2  2005-05-26
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
package ch.randelshofer.scorm;

import ch.randelshofer.gui.*;
import ch.randelshofer.util.*;
import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * Layout view for TinyLMS.
 *
 * @author  Werner Randelshofer, Hausmatt 10, Immensee, CH-6405, Switzerland
 * @version 1.2 2006-05-26 Internationalized.
 * <br>1.1.1 2005-05-26 Reordered the layout of some components.
 * <br>1.1 2004-04-16 Checkbox for property "enforceTOC" added.
 * <br>1.0 2003-05-07 Created.
 */
public class LayoutView extends javax.swing.JPanel
        implements PropertyChangeListener, Scrollable {

    private CourseModel model;
    private ResourceBundleUtil labels;

    /** Creates new form LayoutView */
    public LayoutView() {
        labels = ResourceBundleUtil.getLAFBundle("ch.randelshofer.scorm.Labels");
        initComponents();

        Font f = Fonts.getEmphasizedDialogFont();
        pageLabel.setFont(f);
        tocLabel.setFont(f);
        navLabel.setFont(f);

        navHeightTextField.setMinimumSize(navHeightTextField.getPreferredSize());
        pageHeightTextField.setMinimumSize(pageHeightTextField.getPreferredSize());
        pageWidthTextField.setMinimumSize(pageWidthTextField.getPreferredSize());
        tocWidthTextField.setMinimumSize(tocWidthTextField.getPreferredSize());

        pageInfoLabel.setText("<html>" + Fonts.smallDialogFontTag(
                labels.getString("layout.contentAreaInfo")));
        tocInfoLabel.setText("<html>" + Fonts.smallDialogFontTag(
                labels.getString("layout.tocSizeInfo")));
        tocVisibleInfoLabel.setText("<html>" + Fonts.smallDialogFontTag(
                labels.getString("layout.tocVisibilityInfo")));
        navBarVisibleInfoLabel.setText("<html>" + Fonts.smallDialogFontTag(
                labels.getString("layout.navVisibilityInfo")));
        navInfoLabel.setText("<html>" + Fonts.smallDialogFontTag(
                labels.getString("layout.navSizeInfo")));
    }

    public void setModel(CourseModel m) {
        if (model != null && m == null) {
            pageWidthTextField.setDocument(new PlainDocument());
            pageHeightTextField.setDocument(new PlainDocument());
            tocWidthTextField.setDocument(new PlainDocument());
            navHeightTextField.setDocument(new PlainDocument());
        }
        if (model != null) {
            model.removePropertyChangeListener(this);

        }
        model = m;
        if (model != null) {
            pageWidthTextField.setDocument(model.getFramesetPageWidthDocument());
            pageHeightTextField.setDocument(model.getFramesetPageHeightDocument());
            tocWidthTextField.setDocument(model.getFramesetTocWidthDocument());
            navHeightTextField.setDocument(model.getFramesetNavHeightDocument());
            tocVisibleCheckBox.setSelected(model.isTOCVisible());
            switch (model.getNavigationButtons()) {
                case BUTTONS_BELOW_TOC:
                    navBarRadio.setSelected(true);
                    navHeightTextField.setEnabled(false);
                    break;
                case BUTTONS_IN_NAVBAR:
                    navTOCRadio.setSelected(true);
                    navHeightTextField.setEnabled(true);
                    break;
                case NO_BUTTONS:
                    navNoneRadio.setSelected(true);
                    navHeightTextField.setEnabled(false);
                    break;
            }
            updateEnabled();
            model.addPropertyChangeListener(this);
        }
    }

    public CourseModel getModel() {
        return model;
    }

    public void setEnabled(boolean b) {
        super.setEnabled(b);
        updateEnabled();
    }

    private void updateEnabled() {
        boolean b = isEnabled();

        Component[] c = getComponents();
        for (int i = 0; i < c.length; i++) {
            c[i].setEnabled(b);
        }


        b = b && model != null && model.getStructure() == CourseModel.STRUCTURE_HIERARCHICAL;
        tocVisibleCheckBox.setEnabled(b);
        navBarRadio.setEnabled(b);
        navTOCRadio.setEnabled(b);
        navNoneRadio.setEnabled(b);


        switch (model.getNavigationButtons()) {
            case BUTTONS_BELOW_TOC:
                navHeightTextField.setEnabled(false);
                break;
            case BUTTONS_IN_NAVBAR:
                navHeightTextField.setEnabled(b && true);
                break;
            case NO_BUTTONS:
                navHeightTextField.setEnabled(false);
                break;
            }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        navigationButtonsGroup = new javax.swing.ButtonGroup();
        pageLabel = new javax.swing.JLabel();
        pageWidthLabel = new javax.swing.JLabel();
        pageWidthTextField = new javax.swing.JTextField();
        pageHeightLabel = new javax.swing.JLabel();
        pageHeightTextField = new javax.swing.JTextField();
        pageInfoLabel = new javax.swing.JLabel();
        tocLabel = new javax.swing.JLabel();
        tocWidthTextField = new javax.swing.JTextField();
        tocWidthLabel = new javax.swing.JLabel();
        tocInfoLabel = new javax.swing.JLabel();
        tocVisibleCheckBox = new javax.swing.JCheckBox();
        tocVisibleInfoLabel = new javax.swing.JLabel();
        navLabel = new javax.swing.JLabel();
        navBarRadio = new javax.swing.JRadioButton();
        navTOCRadio = new javax.swing.JRadioButton();
        navNoneRadio = new javax.swing.JRadioButton();
        navHeightLabel = new javax.swing.JLabel();
        navHeightTextField = new javax.swing.JTextField();
        navInfoLabel = new javax.swing.JLabel();
        navBarVisibleInfoLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setLayout(new java.awt.GridBagLayout());

        pageLabel.setText(labels.getString("layout.contentArea")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(pageLabel, gridBagConstraints);

        pageWidthLabel.setText(labels.getString("layout.width")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 24, 0, 0);
        add(pageWidthLabel, gridBagConstraints);

        pageWidthTextField.setColumns(5);
        pageWidthTextField.setText("*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 0, 0);
        add(pageWidthTextField, gridBagConstraints);

        pageHeightLabel.setText(labels.getString("layout.height")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 24, 0, 0);
        add(pageHeightLabel, gridBagConstraints);

        pageHeightTextField.setColumns(5);
        pageHeightTextField.setText("*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 0, 0);
        add(pageHeightTextField, gridBagConstraints);

        pageInfoLabel.setText("Info");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 24, 0, 0);
        add(pageInfoLabel, gridBagConstraints);

        tocLabel.setText(labels.getString("layout.toc")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 0);
        add(tocLabel, gridBagConstraints);

        tocWidthTextField.setColumns(5);
        tocWidthTextField.setText("192");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 0, 0);
        add(tocWidthTextField, gridBagConstraints);

        tocWidthLabel.setText(labels.getString("layout.width")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 24, 0, 0);
        add(tocWidthLabel, gridBagConstraints);

        tocInfoLabel.setText("Info");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 24, 0, 0);
        add(tocInfoLabel, gridBagConstraints);

        tocVisibleCheckBox.setText(labels.getString("layout.displayTOC")); // NOI18N
        tocVisibleCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                enforceTOCchanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 24, 0, 0);
        add(tocVisibleCheckBox, gridBagConstraints);

        tocVisibleInfoLabel.setText("Info");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 24, 0, 0);
        add(tocVisibleInfoLabel, gridBagConstraints);

        navLabel.setText(labels.getString("layout.navigationBar")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 0);
        add(navLabel, gridBagConstraints);

        navigationButtonsGroup.add(navBarRadio);
        navBarRadio.setText("Display buttons in navigation bar");
        navBarRadio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                navRadioChanged(evt);
            }
        });
        navBarRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navRadioPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 24, 0, 0);
        add(navBarRadio, gridBagConstraints);

        navigationButtonsGroup.add(navTOCRadio);
        navTOCRadio.setText("Display buttons below table of contents");
        navTOCRadio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                navRadioChanged(evt);
            }
        });
        navTOCRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navRadioPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        add(navTOCRadio, gridBagConstraints);

        navigationButtonsGroup.add(navNoneRadio);
        navNoneRadio.setSelected(true);
        navNoneRadio.setText("Don't display navigation buttons");
        navNoneRadio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                navRadioChanged(evt);
            }
        });
        navNoneRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navRadioPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        add(navNoneRadio, gridBagConstraints);

        navHeightLabel.setText(labels.getString("layout.height")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 24, 0, 0);
        add(navHeightLabel, gridBagConstraints);

        navHeightTextField.setColumns(5);
        navHeightTextField.setText("18");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 0, 0);
        add(navHeightTextField, gridBagConstraints);

        navInfoLabel.setText("Info");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 24, 0, 0);
        add(navInfoLabel, gridBagConstraints);

        navBarVisibleInfoLabel.setText("Info");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 24, 0, 0);
        add(navBarVisibleInfoLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    private void enforceTOCchanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_enforceTOCchanged
        if (model != null) {
            model.setTOCVisible(tocVisibleCheckBox.isSelected());
        }
    }//GEN-LAST:event_enforceTOCchanged

    private void navRadioChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_navRadioChanged
        if (model != null) {

            if (navBarRadio.isSelected()) {
                model.setNavigationButtons(CourseModel.NavigationButtons.BUTTONS_IN_NAVBAR);
            } else if (navTOCRadio.isSelected()) {
                model.setNavigationButtons(CourseModel.NavigationButtons.BUTTONS_BELOW_TOC);
            } else {
                model.setNavigationButtons(CourseModel.NavigationButtons.NO_BUTTONS);
            }
        }
    }//GEN-LAST:event_navRadioChanged

    private void navRadioPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navRadioPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_navRadioPerformed

    public void propertyChange(PropertyChangeEvent event) {
        String name = event.getPropertyName();
        if (name.equals("enforceTOC")) {
            tocVisibleCheckBox.setSelected(((Boolean) event.getNewValue()).booleanValue());
        }
        if (name.equals("navigationButtons")) {
            switch (model.getNavigationButtons()) {
                case BUTTONS_BELOW_TOC:
                    navTOCRadio.setSelected(true);
                    break;
                case BUTTONS_IN_NAVBAR:
                    navBarRadio.setSelected(true);
                    break;
                case NO_BUTTONS:
                    navNoneRadio.setSelected(true);
                    break;
            }
            updateEnabled();
        }
        if (name.equals("structure")) {
            updateEnabled();
        }
    }

    public java.awt.Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableBlockIncrement(java.awt.Rectangle rectangle, int param, int param2) {
        return 10;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    public int getScrollableUnitIncrement(java.awt.Rectangle rectangle, int param, int param2) {
        return 10;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton navBarRadio;
    private javax.swing.JLabel navBarVisibleInfoLabel;
    private javax.swing.JLabel navHeightLabel;
    private javax.swing.JTextField navHeightTextField;
    private javax.swing.JLabel navInfoLabel;
    private javax.swing.JLabel navLabel;
    private javax.swing.JRadioButton navNoneRadio;
    private javax.swing.JRadioButton navTOCRadio;
    private javax.swing.ButtonGroup navigationButtonsGroup;
    private javax.swing.JLabel pageHeightLabel;
    private javax.swing.JTextField pageHeightTextField;
    private javax.swing.JLabel pageInfoLabel;
    private javax.swing.JLabel pageLabel;
    private javax.swing.JLabel pageWidthLabel;
    private javax.swing.JTextField pageWidthTextField;
    private javax.swing.JLabel tocInfoLabel;
    private javax.swing.JLabel tocLabel;
    private javax.swing.JCheckBox tocVisibleCheckBox;
    private javax.swing.JLabel tocVisibleInfoLabel;
    private javax.swing.JLabel tocWidthLabel;
    private javax.swing.JTextField tocWidthTextField;
    // End of variables declaration//GEN-END:variables
}
