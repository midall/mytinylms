/*
 * @(#)OrganizationView.java 1.3 2009-09-01
 *
 * Copyright (c) 2003-2009 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */
package ch.randelshofer.scorm;

import java.beans.*;
import ch.randelshofer.gui.*;
import ch.randelshofer.util.*;
import java.awt.*;
import javax.swing.*;
import ch.randelshofer.scorm.cam.*;
import java.util.LinkedList;
import javax.swing.text.PlainDocument;

/**
 * Organization view for TinyLMS.
 *
 * @author  Werner Randelshofer, Hausmatt 10, CH-6405 Immensee, Switzerland
 * @version 1.3 2009-09-01 Added Quiz structure.
 * <br>1.2 2006-05-26 Internationalized.
 * <br>1.1 2003-11-05 Components for choosing sequencing behaviour added.
 * <br>1.0 2003-08-22 Renamed from OptionsView to OrganizationView. Info
 * labels added.
 * <br>0.20 2003-04-05 Support for wide and small TOC added.
 * <br>0.19.4 2003-04-02 Revised.
 * <br>0.18 2003-03-19 Revised.
 * <br>0.1 2003-03-03 Created.
 */
public class OrganizationView extends JPanel implements PropertyChangeListener, Scrollable {

    private OrganizationComboBoxModel orgModel;
    private ResourceBundleUtil labels;
    private LinkedList<JComponent> quizSettingsFields;

    /** Creates new form OptionsView */
    public OrganizationView() {
        labels = ResourceBundleUtil.getLAFBundle("ch.randelshofer.scorm.Labels");

        initComponents();
        Font f = Fonts.getEmphasizedDialogFont();
        organizationLabel.setFont(f);
        structureLabel.setFont(f);
        quizSettingsLabel.setFont(f);
        sequencingLabel.setFont(f);

        orgModel = new OrganizationComboBoxModel();
        organizationComboBox.setModel(orgModel);
        organizationComboBox.setRenderer(new DefaultListCellRenderer() {

            public Component getListCellRendererComponent(JList list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                if (value != null && (value instanceof OrganizationElement)) {
                    value = ((OrganizationElement) value).getTitle();
                    if (value == null) {
                        value = "no title";
                    }
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        organizationInfoLabel.setText("<html>" + Fonts.smallDialogFontTag(
                labels.getString("organization.organizationInfo")));

        structureInfoLabel.setText("<html>" + Fonts.smallDialogFontTag(
                labels.getString("organization.structureInfo")));

        sequencingInfoLabel.setText("<html>" + Fonts.smallDialogFontTag(
                labels.getString("organization.sequencingInfo")));

        quizExcludePanel.setMinimumSize(quizExcludePanel.getPreferredSize());
        quizItemsPerRoundField.setMinimumSize(quizItemsPerRoundField.getPreferredSize());

        quizSettingsFields = new LinkedList<JComponent>();
        quizSettingsFields.add(quizSettingsLabel);
        quizSettingsFields.add(quizItemsPerRoundField);
        quizSettingsFields.add(quizItemsPerRoundLabel);
        quizSettingsFields.add(quizExcludeLabel1);
        quizSettingsFields.add(quizExcludeLabel2);
        quizSettingsFields.add(quizExcludeLabel3);
        quizSettingsFields.add(quizExcludeFirstField);
        quizSettingsFields.add(quizExcludeLastField);

    }

    public void setModel(CourseModel m) {
        CourseModel oldModel = orgModel.getCourseModel();
        if (oldModel != null) {
            oldModel.removePropertyChangeListener(this);
            quizItemsPerRoundField.setDocument(new PlainDocument());
            quizExcludeFirstField.setDocument(new PlainDocument());
            quizExcludeLastField.setDocument(new PlainDocument());
        }
        orgModel.setCourseModel(m);
        if (m != null) {
            quizItemsPerRoundField.setDocument(m.getQuizItemsPerRoundDocument());
            quizExcludeFirstField.setDocument(m.getQuizExcludeFirstDocument());
            quizExcludeLastField.setDocument(m.getQuizExcludeLastDocument());
        }
        m.addPropertyChangeListener(this);
        propertyChange(null);
    }

    public CourseModel getModel() {
        return orgModel.getCourseModel();
    }

    public void setEnabled(boolean b) {
        super.setEnabled(b);
        Component[] c = getComponents();
        for (int i = 0; i < c.length; i++) {
            c[i].setEnabled(b);
        }
        updateQuizSettingsFields();
    }

    private void updateQuizSettingsFields() {
        boolean b = isEnabled() && //
                orgModel != null && orgModel.getCourseModel() != null &&//
                orgModel.getCourseModel().getStructure() == CourseModel.STRUCTURE_QUIZ;
        for (JComponent c : quizSettingsFields) {
            c.setEnabled(b);
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

        structureRadioGroup = new javax.swing.ButtonGroup();
        sequencingRadioGroup = new javax.swing.ButtonGroup();
        organizationLabel = new javax.swing.JLabel();
        organizationComboBox = new javax.swing.JComboBox();
        organizationInfoLabel = new javax.swing.JLabel();
        structureLabel = new javax.swing.JLabel();
        structureHierarchicalRadioButton = new javax.swing.JRadioButton();
        structureLayeredRadioButton = new javax.swing.JRadioButton();
        structureQuizRadioButton = new javax.swing.JRadioButton();
        structureInfoLabel = new javax.swing.JLabel();
        quizSettingsLabel = new javax.swing.JLabel();
        quizItemsPerRoundLabel = new javax.swing.JLabel();
        quizItemsPerRoundField = new javax.swing.JTextField();
        quizExcludePanel = new javax.swing.JPanel();
        quizExcludeLabel1 = new javax.swing.JLabel();
        quizExcludeFirstField = new javax.swing.JTextField();
        quizExcludeLabel2 = new javax.swing.JLabel();
        quizExcludeLastField = new javax.swing.JTextField();
        quizExcludeLabel3 = new javax.swing.JLabel();
        sequencingLabel = new javax.swing.JLabel();
        sequencingAutomaticRadioButton = new javax.swing.JRadioButton();
        sequencingManualRadioButton = new javax.swing.JRadioButton();
        sequencingInfoLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setLayout(new java.awt.GridBagLayout());

        organizationLabel.setText(labels.getString("organization.organization")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(organizationLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        add(organizationComboBox, gridBagConstraints);

        organizationInfoLabel.setText("organization Info");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 24, 0, 0);
        add(organizationInfoLabel, gridBagConstraints);

        structureLabel.setText(labels.getString("organization.structure")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 0);
        add(structureLabel, gridBagConstraints);

        structureRadioGroup.add(structureHierarchicalRadioButton);
        structureHierarchicalRadioButton.setSelected(true);
        structureHierarchicalRadioButton.setText(labels.getString("organization.hierarchical")); // NOI18N
        structureHierarchicalRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                structureItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        add(structureHierarchicalRadioButton, gridBagConstraints);

        structureRadioGroup.add(structureLayeredRadioButton);
        structureLayeredRadioButton.setText(labels.getString("organization.layered")); // NOI18N
        structureLayeredRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                structureItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        add(structureLayeredRadioButton, gridBagConstraints);

        structureRadioGroup.add(structureQuizRadioButton);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ch/randelshofer/scorm/Labels"); // NOI18N
        structureQuizRadioButton.setText(bundle.getString("organization.quiz")); // NOI18N
        structureQuizRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                structureItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        add(structureQuizRadioButton, gridBagConstraints);

        structureInfoLabel.setText("structure Info");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 24, 0, 0);
        add(structureInfoLabel, gridBagConstraints);

        quizSettingsLabel.setText(labels.getString("organization.quiz")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 0);
        add(quizSettingsLabel, gridBagConstraints);

        quizItemsPerRoundLabel.setText(labels.getString("organization.quiz.itemsPerRound")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        add(quizItemsPerRoundLabel, gridBagConstraints);

        quizItemsPerRoundField.setColumns(3);
        quizItemsPerRoundField.setText("10");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(quizItemsPerRoundField, gridBagConstraints);

        quizExcludePanel.setLayout(new java.awt.GridBagLayout());

        quizExcludeLabel1.setText(labels.getString("organization.quiz.exclude.part1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        quizExcludePanel.add(quizExcludeLabel1, gridBagConstraints);

        quizExcludeFirstField.setColumns(3);
        quizExcludeFirstField.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        quizExcludePanel.add(quizExcludeFirstField, gridBagConstraints);

        quizExcludeLabel2.setText(labels.getString("organization.quiz.exclude.part2")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        quizExcludePanel.add(quizExcludeLabel2, gridBagConstraints);

        quizExcludeLastField.setColumns(3);
        quizExcludeLastField.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        quizExcludePanel.add(quizExcludeLastField, gridBagConstraints);

        quizExcludeLabel3.setText(labels.getString("organization.quiz.exclude.part3")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        quizExcludePanel.add(quizExcludeLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        add(quizExcludePanel, gridBagConstraints);

        sequencingLabel.setText(labels.getString("organization.sequencing")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 0);
        add(sequencingLabel, gridBagConstraints);

        sequencingRadioGroup.add(sequencingAutomaticRadioButton);
        sequencingAutomaticRadioButton.setSelected(true);
        sequencingAutomaticRadioButton.setText(labels.getString("organization.automatic")); // NOI18N
        sequencingAutomaticRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sequencingItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        add(sequencingAutomaticRadioButton, gridBagConstraints);

        sequencingRadioGroup.add(sequencingManualRadioButton);
        sequencingManualRadioButton.setText(labels.getString("organization.manual")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        add(sequencingManualRadioButton, gridBagConstraints);

        sequencingInfoLabel.setText("sequencing Info");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 24, 0, 0);
        add(sequencingInfoLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void sequencingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sequencingItemStateChanged
        // Add your handling code here:
        CourseModel m = orgModel.getCourseModel();
        if (m != null) {
            if (sequencingAutomaticRadioButton.isSelected()) {
                m.setSequencing(CourseModel.SEQUENCING_AUTOMATIC);
            } else {
                m.setSequencing(CourseModel.SEQUENCING_MANUAL);
            }
        }

    }//GEN-LAST:event_sequencingItemStateChanged

    private void structureItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_structureItemStateChanged
        CourseModel m = orgModel.getCourseModel();
        if (m != null) {
            if (structureHierarchicalRadioButton.isSelected()) {
                m.setStructure(CourseModel.STRUCTURE_HIERARCHICAL);
            } else if (structureLayeredRadioButton.isSelected()) {
                m.setStructure(CourseModel.STRUCTURE_LAYERED);
            } else if (structureQuizRadioButton.isSelected()) {
                m.setStructure(CourseModel.STRUCTURE_QUIZ);
            }
            updateQuizSettingsFields();
        }
    }//GEN-LAST:event_structureItemStateChanged

    /** This method gets called when a bound property is changed.
     * @param evt A PropertyChangeEvent object describing the event source
     *   	and the property that has changed.
     *
     */
    public void propertyChange(PropertyChangeEvent evt) {
        CourseModel m = orgModel.getCourseModel();
        String propertyName = (evt == null) ? null : evt.getPropertyName();
        if (m != null) {
            if (propertyName == null || propertyName.equals("structure")) {
                switch (m.getStructure()) {
                    case CourseModel.STRUCTURE_HIERARCHICAL:
                        structureHierarchicalRadioButton.setSelected(true);
                        break;
                    case CourseModel.STRUCTURE_LAYERED:
                        structureLayeredRadioButton.setSelected(true);
                        break;
                    case CourseModel.STRUCTURE_QUIZ:
                        structureQuizRadioButton.setSelected(true);
                        break;
                }
                updateQuizSettingsFields();
            }
            if (propertyName == null || propertyName.equals("sequencing")) {
                switch (m.getSequencing()) {
                    case CourseModel.SEQUENCING_AUTOMATIC:
                        sequencingAutomaticRadioButton.setSelected(true);
                        break;
                    default:
                        sequencingManualRadioButton.setSelected(true);
                        break;
                }
            }
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
    private javax.swing.JComboBox organizationComboBox;
    private javax.swing.JLabel organizationInfoLabel;
    private javax.swing.JLabel organizationLabel;
    private javax.swing.JTextField quizExcludeFirstField;
    private javax.swing.JLabel quizExcludeLabel1;
    private javax.swing.JLabel quizExcludeLabel2;
    private javax.swing.JLabel quizExcludeLabel3;
    private javax.swing.JTextField quizExcludeLastField;
    private javax.swing.JPanel quizExcludePanel;
    private javax.swing.JTextField quizItemsPerRoundField;
    private javax.swing.JLabel quizItemsPerRoundLabel;
    private javax.swing.JLabel quizSettingsLabel;
    private javax.swing.JRadioButton sequencingAutomaticRadioButton;
    private javax.swing.JLabel sequencingInfoLabel;
    private javax.swing.JLabel sequencingLabel;
    private javax.swing.JRadioButton sequencingManualRadioButton;
    private javax.swing.ButtonGroup sequencingRadioGroup;
    private javax.swing.JRadioButton structureHierarchicalRadioButton;
    private javax.swing.JLabel structureInfoLabel;
    private javax.swing.JLabel structureLabel;
    private javax.swing.JRadioButton structureLayeredRadioButton;
    private javax.swing.JRadioButton structureQuizRadioButton;
    private javax.swing.ButtonGroup structureRadioGroup;
    // End of variables declaration//GEN-END:variables
}
