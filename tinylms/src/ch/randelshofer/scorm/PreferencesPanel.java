/*
 * @(#)PreferencesPanel.java  1.0  20. August 2007
 *
 * Copyright (c) 2007 Werner Randelshofer
 * Hausmatt 10, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

package ch.randelshofer.scorm;

import ch.randelshofer.io.ExtensionFileFilter;
import ch.randelshofer.quaqua.util.Worker;
import ch.randelshofer.util.*;
import ch.randelshofer.gui.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.prefs.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicTextUI;

/**
 * PreferencesPanel.
 *
 * @author Werner Randelshofer
 * @version 1.0 20. August 2007 Created.
 */
public class PreferencesPanel extends javax.swing.JPanel {
    private Preferences userPrefs = Preferences.userNodeForPackage(CourseBuilder.class);
    private ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("ch.randelshofer.scorm.Labels");
    
    private JFileChooser importFileChooser;
    private JFileChooser exportFileChooser;
    
    /** Creates new instance. */
    public PreferencesPanel() {
        initComponents();
        
        DefaultComboBoxModel cbm = new DefaultComboBoxModel();
        cbm.addElement(labels.getString("preferences.templateUseInternal"));
        cbm.addElement(labels.getString("preferences.templateUseExternal"));
        templateChoice.setModel(cbm);
        
        templateChoice.setSelectedIndex(
                userPrefs.getBoolean("courseBuilder.useInternalTemplate", true) ?
                    0 : 1
                );
        templateChoice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                updateTemplateChoice();
            }
        });
        updateTemplateChoice();
        
        externalTemplateField.setText(
                userPrefs.get("courseBuilder.externalTemplate", "")
                );
        
        
        externalTemplateField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateExternalTemplate();
            }
            
            public void removeUpdate(DocumentEvent e) {
                updateExternalTemplate();
            }
            
            public void changedUpdate(DocumentEvent e) {
                updateExternalTemplate();
            }
            
        });
        
        externalTemplateField.setTransferHandler(new FileTextFieldTransferHandler());
    }
    
    private void updateTemplateChoice() {
        boolean b = templateChoice.getSelectedIndex() == 0;
        userPrefs.putBoolean("courseBuilder.useInternalTemplate", b);
        externalTemplateField.setEnabled(!b);
        templateChooseButton.setEnabled(!b);
    }
    
    private void updateExternalTemplate() {
        userPrefs.put("courseBuilder.externalTemplate", externalTemplateField.getText());
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        templateChoiceLabel = new javax.swing.JLabel();
        templateChoice = new javax.swing.JComboBox();
        externalTemplateLabel = new javax.swing.JLabel();
        externalTemplateField = new javax.swing.JTextField();
        templateChooseButton = new javax.swing.JButton();
        templateExportButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        templateChoiceLabel.setText(labels.getString("preferences.templateChoiceLabel"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(templateChoiceLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        add(templateChoice, gridBagConstraints);

        externalTemplateLabel.setText(labels.getString("preferences.externalTemplateLabel"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(externalTemplateLabel, gridBagConstraints);

        externalTemplateField.setColumns(16);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        add(externalTemplateField, gridBagConstraints);

        templateChooseButton.setText(labels.getString("preferences.chooseTemplate"));
        templateChooseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseExternalTemplate(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        add(templateChooseButton, gridBagConstraints);

        templateExportButton.setText(labels.getString("preferences.exportTemplate"));
        templateExportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportInternalTemplate(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        add(templateExportButton, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents
    
    private void exportInternalTemplate(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportInternalTemplate
        if (exportFileChooser == null) {
            exportFileChooser = new JFileChooser();
            exportFileChooser.setFileFilter(new ExtensionFileFilter("zip", "Zip Archive"));
            exportFileChooser.setSelectedFile(new File(userPrefs.get("courseBuilder.exportedTemplate", "TinyLMS Template.zip")));
            exportFileChooser.setApproveButtonText(labels.getString("filechooser.export"));
        }
        if (JFileChooser.APPROVE_OPTION == exportFileChooser.showSaveDialog(this)) {
            userPrefs.put("courseBuilder.exportedTemplate", exportFileChooser.getSelectedFile().getPath());
            final File target = exportFileChooser.getSelectedFile();
            new Worker() {
                public Object construct() {
                    Object result = null;
                    if (! target.getParentFile().exists()) {
                        target.getParentFile().mkdirs();
                    }
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = getClass().getResourceAsStream("/lmshtml.zip");
                        out = new FileOutputStream(target);
                        Files.copyStream(in, out);
                    } catch (IOException e) {
                        result = e;
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException ex) {
                                result = (result == null) ? ex : result;
                            }
                        }
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException ex) {
                                result = (result == null) ? ex : result;
                            }
                        }
                    }
                    return result;
                }
                public void finished(Object result) {
                    
                }
            }.start();
        }
    }//GEN-LAST:event_exportInternalTemplate
    
    private void chooseExternalTemplate(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseExternalTemplate
        if (importFileChooser == null) {
            importFileChooser = new JFileChooser();
            importFileChooser.setFileFilter(new ExtensionFileFilter("zip", "Zip Archive"));
            importFileChooser.setSelectedFile(new File(userPrefs.get("courseBuilder.externalTemplate", "TinyLMS Template.zip")));
            importFileChooser.setApproveButtonText(labels.getString("filechooser.choose"));
        }
        if (JFileChooser.APPROVE_OPTION == importFileChooser.showOpenDialog(this)) {
            userPrefs.put("courseBuilder.externalTemplate", importFileChooser.getSelectedFile().getPath());
            externalTemplateField.setText(
                    userPrefs.get("courseBuilder.externalTemplate", "")
                    );
        }
    }//GEN-LAST:event_chooseExternalTemplate
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField externalTemplateField;
    private javax.swing.JLabel externalTemplateLabel;
    private javax.swing.JComboBox templateChoice;
    private javax.swing.JLabel templateChoiceLabel;
    private javax.swing.JButton templateChooseButton;
    private javax.swing.JButton templateExportButton;
    // End of variables declaration//GEN-END:variables
    
}
