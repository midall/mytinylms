/*
 * @(#)CourseBuilder.java 2.2.1 2008-12-16
 *
 * Copyright (c) 2003-2008 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */
package ch.randelshofer.scorm;

import ch.randelshofer.app.action.*;
import ch.randelshofer.debug.*;
import ch.randelshofer.gui.*;
import ch.randelshofer.quaqua.*;
import ch.randelshofer.io.*;
import ch.randelshofer.util.*;
import ch.randelshofer.util.pref.PreferencesUtil;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.util.prefs.*;
import javax.swing.*;
import java.beans.*;
import org.xml.sax.*;
/**
 * The course builder application.
 *
 * @author  Werner Randelshofer, Hausmatt 10, CH-6405 Immensee, Switzerland
 * @version 2.2.1 2008-12-16 Fixed encoding of baseURI.
 * <br>2.2 2008-12-02 Content packages can be dropped on the CourseBuilder
 * window.
 * <br>2.1 2007-09-11 Provide a Preferences panel.
 * <br>2.0.2 2006-10-07 Provide a separate menu item for debugging.
 * <br>2.0.1 2006-06-25 Use Quaqua L&F only on Mac OS X.
 * <br>2.0 2005-08-01 Renamed from TinyLMSApp to CourseBuilder.
 * <br>1.3.3 2005-07-05 Enforce Jaguar tabbed pane for Quaqua L&F.
 * Use Screen Menu Bar on Mac.
 * <br>1.3.2 2004-06-20 Learning content consisting of a single SCO did
 * not work. Stylesheet added for logger.js. Hardcoded path for learning content
 * in lmsapi.js replaced by an instance variable.
 * <br>1.3.1 2004-06-14 Fixed bugs related to log out.
 * <br>1.3 2004-06-14 Improved debugging. Fixed bugs related to framesets.
 * <br>1.2.5 2004-05-26 Fixed a bug in generation of the index.html file.
 * <br>1.2.4 2004-05-02 Fixed bugs in generation of a course from a pif.
 * <br>1.2.2 2004-04-16 Property 'enforceTOC' to CourseModel added.
 * <br>1.2.1 2004-01-15 Fixed a bug in CourseModel. It unnecessarily exported
 * the file ims_xml.xsd when a course was generated.
 * <br>1.2 2004-01-06 TinyLMS can be used as an SCO.
 * <br>1.1.8 2003-12-01 Automatic sequencing after login must take the
 * differences between hierarchical and layered structuring into account.
 * Attempt to fix a problem with IE: The menu panel and the navigation panel
 * are sometimes not updated.
 * <br>1.1.7 2003-11-06 Support for debugging added.
 * <br>1.1.5 2003-11-05 The file dialog used to open a content package
 * accepts now directories as well. Support for automatic login added. Hiding of
 * navigation elements added, when the selected organization only contains one
 * item.
 * <br>1.0.3 2003-07-29 Menu items "Import PIF" and "Import Content Package"
 * replaced by one menu item "Import SCORM Content"
 * <br>1.0.2 2003-06-30 Debug function in generated tutorials added.
 * <br>1.0.1 2003-06-24 Login panel of generated courses did not
 * show the title of the course.
 * <br>1.0 2003-06-22 First public release of TinyLMS.
 * <br>0.30 2003-06-16 Pressing ENTER in Login page messed up the navigation.
 * <br>0.29 2003-06-11 Automatic sequencing did not work.
 * <br>0.28 2003-06-05 Wrong title was generated for the index.html file.
 * <br>0.27 2003-05-22 Provide support for fixed width pages.
 * <br>0.25 2003-05-09 Bug fixed in method validate() of class CAMElement.
 * <br>0.24 2003-05-07 New tab pane added: "Dimensions".
 * <br>0.23 2003-04-14 Revised.
 * <br>0.21 2003-04-08 Layered Navigation bar prints 'page i of n'.
 * <br>0.20 2003-04-07 Revised.
 * <br>0.19.4 2003-04-04 Revised.
 * <br>0.18.2 2003-03-21 Revised.
 * <br>0.18 2003-03-19 Revised.
 * <br>0.15.1 2003-03-11 Revised.
 * <br>0.15 2003-03-10 Revised.
 * <br>0.13 2003-03-06 Revised.
 * <br>0.12 2003-03-05 Revised.
 * <br>0.10 2003-02-24 Reworked to improve browser compatibility of the generated
 *                     course.
 */
public class CourseBuilder extends javax.swing.JFrame {
    private Preferences userPrefs = Preferences.userNodeForPackage(CourseBuilder.class);
    
    /**
     * The file chooser used for property files.
     */
    private JFileChooser propertiesFileChooser;
    /**
     * The chooser used for imports of SCORM content.
     */
    private JFileChooser importFileChooser;
    
    /**
     * The file chooser used for the "Create Course" dialog.
     */
    private JFileChooser courseDirChooser;
    /**
     * The file chooser used for the "Export as PDF" dialog.
     */
    private JFileChooser printPDFChooser;
    
    private javax.swing.filechooser.FileFilter pdfPrintMediaFilter;
    private javax.swing.filechooser.FileFilter pdfScreenMediaFilter;
    /**
     * The file chooser used for the "Create Content Package" dialog.
     */
    private JFileChooser contentPackageChooser;
    
    private CourseModel model = new CourseModel();
    private OrganizationComboBoxModel organizationModel = new OrganizationComboBoxModel();
    
    private ResourceBundleUtil labels;
    
    private Debugger debugger;
    
    private JDialog preferencesDialog;

    private class Handler implements PropertyChangeListener, DropTargetListener {
    /** This method gets called when a bound property is changed.
     * @param evt A PropertyChangeEvent object describing the event source
     *   	and the property that has changed.
     *
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("enabled")) {
            updateEnabled();
        }
    }

        /**
         * Called when a drag operation has
         * encountered the <code>DropTarget</code>.
         * <P>
         * @param dtde the <code>DropTargetDragEvent</code>
         */
        public void dragEnter(DropTargetDragEvent event) {
            if (event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                event.acceptDrag(DnDConstants.ACTION_COPY);
            } else {
                event.rejectDrag();
            }
        }

        /**
         * The drag operation has departed
         * the <code>DropTarget</code> without dropping.
         * <P>
         * @param dte the <code>DropTargetEvent</code>
         */
        public void dragExit(DropTargetEvent event) {
            // Nothing to do
        }

        /**
         * Called when a drag operation is ongoing
         * on the <code>DropTarget</code>.
         * <P>
         * @param dtde the <code>DropTargetDragEvent</code>
         */
        public void dragOver(DropTargetDragEvent event) {
            if (event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                event.acceptDrag(DnDConstants.ACTION_COPY);
            } else {
                event.rejectDrag();
            }
        }

        /**
         * The drag operation has terminated
         * with a drop on this <code>DropTarget</code>.
         * This method is responsible for undertaking
         * the transfer of the data associated with the
         * gesture. The <code>DropTargetDropEvent</code>
         * provides a means to obtain a <code>Transferable</code>
         * object that represents the data object(s) to
         * be transfered.<P>
         * From this method, the <code>DropTargetListener</code>
         * shall accept or reject the drop via the
         * acceptDrop(int dropAction) or rejectDrop() methods of the
         * <code>DropTargetDropEvent</code> parameter.
         * <P>
         * Subsequent to acceptDrop(), but not before,
         * <code>DropTargetDropEvent</code>'s getTransferable()
         * method may be invoked, and data transfer may be
         * performed via the returned <code>Transferable</code>'s
         * getTransferData() method.
         * <P>
         * At the completion of a drop, an implementation
         * of this method is required to signal the success/failure
         * of the drop by passing an appropriate
         * <code>boolean</code> to the <code>DropTargetDropEvent</code>'s
         * dropComplete(boolean success) method.
         * <P>
         * Note: The actual processing of the data transfer is not
         * required to finish before this method returns. It may be
         * deferred until later.
         * <P>
         * @param dtde the <code>DropTargetDropEvent</code>
         */
        public void drop(DropTargetDropEvent event) {
            if (event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                event.acceptDrop(DnDConstants.ACTION_COPY);

                try {
                    java.util.List<File> files = (java.util.List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (files.size() == 1) {
                        for (File f : files) {
                            String lcName = f.getName().toLowerCase();
                            doImportSCORMContent(f);
                        }
                    } else {
                    JOptionPane.showConfirmDialog(
                            CourseBuilder.this,
                            "Please only drop one file at a time.",
                            "CourseBuilder: Drop Failed",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException e) {
                    JOptionPane.showConfirmDialog(
                            CourseBuilder.this,
                            "Could not access the dropped data.",
                            "CourseBuilder: Drop Failed",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } catch (UnsupportedFlavorException e) {
                    JOptionPane.showConfirmDialog(
                            CourseBuilder.this,
                            "Unsupported data flavor.",
                            "CourseBuilder: Drop Failed",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                event.rejectDrop();
            }
        }

        /**
         * Called if the user has modified
         * the current drop gesture.
         * <P>
         * @param dtde the <code>DropTargetDragEvent</code>
         */
        public void dropActionChanged(DropTargetDragEvent event) {
            // Nothing to do
        }
    }
    private Handler handler;

    /** Creates new form CourseBuilder. */
    public CourseBuilder() {
        labels = ResourceBundleUtil.getLAFBundle("ch.randelshofer.scorm.Labels");
        
        // Get the preferred size from user preferences
        /*
        int width = userPrefs.getInt("frame.width", 500);
        int height = userPrefs.getInt("frame.height", 500);
         */
        
        initComponents();
        
        // Create a platform specific menu bar for Mac OS X.
        // TODO: Implement with Apple EAWT
        /*
        if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            Application.getInstance().getAboutJMenuItem().addActionListener(
                    new ActionListener() { public void actionPerformed(ActionEvent evt) {
                        about(evt);
                    }
            });
            Application.getInstance().getPreferencesJMenuItem().addActionListener(
                    new ActionListener() { public void actionPerformed(ActionEvent evt) {
                        preferences(evt);
                    }
            });
            aboutMenuItem.getParent().remove(aboutMenuItem);
            preferencesSeparator.getParent().remove(preferencesSeparator);
            preferencesMenuItem.getParent().remove(preferencesMenuItem);
        }*/
        
        tabbedPane.putClientProperty("Quaqua.Component.visualMargin", new Insets(-3,-3,4,-3));
        
        Color panelColor = UIManager.getColor("Panel.background");
        organizationScrollPane.getViewport().setBackground(panelColor);
        layoutScrollPane.getViewport().setBackground(panelColor);
        languageScrollPane.getViewport().setBackground(panelColor);
        debugScrollPane.getViewport().setBackground(panelColor);
        
        cutMenuItem.addActionListener(new CutAction());
        copyMenuItem.addActionListener(new CopyAction());
        pasteMenuItem.addActionListener(new PasteAction());
        
        manifestView.setModel(model);
        organizationView.setModel(model);
        layoutView.setModel(model);
        loginView.setModel(model);
        debugView.setModel(model);
        languageView.setModel(model);
        handler = new Handler();
        model.addPropertyChangeListener(handler);
        model.setEnabled(false);
        updateEnabled();
        
        // Set the size according user preferences
        //setSize(width, height);
        //setSize(500,500);
        
        PreferencesUtil.installFramePrefsHandler(userPrefs,"courseBuilderWindow", this);
        
        /*
        // Listen for size changes
        addComponentListener((ComponentListener)
        GenericListener.create(
                ComponentListener.class, "componentResized",
                this, "frameResized"
                )
                );
         */
        handler = new Handler();
        new DropTarget(this, handler);
        new DropTarget(getContentPane(), handler);
        
    }
    
    public void setEnabled(boolean b) {
        if (b != isEnabled()) {
            super.setEnabled(b);
            organizationView.setEnabled(b);
            layoutView.setEnabled(b);
            loginView.setEnabled(b);
            languageView.setEnabled(b);
            manifestView.setEnabled(b);
            debugView.setEnabled(b);
            updateEnabled();
        }
    }
    private void updateEnabled() {
        boolean b = isEnabled();
        boolean modelB = b && model.isEnabled();
        
        menuBar.setEnabled(b);
        fileMenu.setEnabled(b);
        openMenuItem.setEnabled(modelB);
        saveMenuItem.setEnabled(
                b
                && model != null
                && model.getIMSManifestDocument() != null
                && model.getIMSManifestDocument().getContentPackage() != null
                );
        saveAsMenuItem.setEnabled(modelB);
        importMenuItem.setEnabled(b);
        exitMenuItem.setEnabled(b);
        aboutMenuItem.setEnabled(b);
        helpMenu.setEnabled(b);
        
        tabbedPane.setEnabled(modelB);
        organizationView.setEnabled(modelB);
        manifestView.setEnabled(modelB);
        createContentPackageMenuItem.setEnabled(modelB);
        createCourseMenuItem.setEnabled(modelB);
        printCourseMenuItem.setEnabled(modelB);
        printToPDFMenuItem.setEnabled(modelB);
    }
    
    /**
     * This method is invoked by our anonymous ComponentListener
     * when the size of the frame changed.
     * /
     * public void frameResized() {
     * if (userPrefs != null) {
     * userPrefs.putInt("frame.width", getWidth());
     * userPrefs.putInt("frame.height", getHeight());
     * }
     * }*/
    
    private JFileChooser getImportFileChooser() {
        if (importFileChooser == null) {
            importFileChooser = new JFileChooser();
            importFileChooser.addChoosableFileFilter(
                    new RegexFileFilter(".*\\.zip|.*\\.ZIP|imsmanifest\\.xml", labels.getString("builder.scormContentPackage"))
                    );
            importFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            File importFile = new File(userPrefs.get("importFile", System.getProperty("user.home")));
            importFileChooser.setSelectedFile(importFile);
        }
        
        // Set to preferred size:
        int width = userPrefs.getInt("filechooser.width", importFileChooser.getPreferredSize().width);
        int height = userPrefs.getInt("filechooser.height", importFileChooser.getPreferredSize().height);
        importFileChooser.setSize(width, height);
        
        return importFileChooser;
    }
    private JFileChooser getPropertiesFileChooser() {
        if (propertiesFileChooser == null) {
            propertiesFileChooser = new JFileChooser();
            propertiesFileChooser.addChoosableFileFilter(new ExtensionFileFilter(new String[] {"xml"}, labels.getString("builder.properties")));
            propertiesFileChooser.setSelectedFile(new File(userPrefs.get("propertiesFile", System.getProperty("user.home"))));
        }
        // Set to preferred size:
        int width = userPrefs.getInt("filechooser.width", propertiesFileChooser.getPreferredSize().width);
        int height = userPrefs.getInt("filechooser.height", propertiesFileChooser.getPreferredSize().height);
        propertiesFileChooser.setSize(width, height);
        return propertiesFileChooser;
    }
    private JFileChooser getCourseDirChooser() {
        if (courseDirChooser == null) {
            courseDirChooser = new JFileChooser();
            courseDirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            courseDirChooser.setSelectedFile(new File(userPrefs.get("courseDir", System.getProperty("user.home"))));
            courseDirChooser.setAccessory(new ExportChooserAccessory());

        }
        // Set to preferred size:
        int width = userPrefs.getInt("filechooser.width", courseDirChooser.getPreferredSize().width);
        int height = userPrefs.getInt("filechooser.height", courseDirChooser.getPreferredSize().height);
        courseDirChooser.setSize(width, height);

        ExportChooserAccessory eca = (ExportChooserAccessory) courseDirChooser.getAccessory();
        eca.setModel(model);

        return courseDirChooser;
    }
    private JFileChooser getExportPDFChooser() {
        if (printPDFChooser == null) {
            printPDFChooser = new JFileChooser();
            printPDFChooser.setSelectedFile(new File(userPrefs.get("pdfFile", System.getProperty("user.home"))));
            printPDFChooser.setAcceptAllFileFilterUsed(false);
            printPDFChooser.addChoosableFileFilter(pdfScreenMediaFilter = new ExtensionFileFilter("pdf","PDF screen media"));
            printPDFChooser.addChoosableFileFilter(pdfPrintMediaFilter = new ExtensionFileFilter("pdf","PDF print media"));
            printPDFChooser.setFileFilter(userPrefs.get("pdfMediaType", "screen").equals("screen") ? 
                pdfScreenMediaFilter : pdfPrintMediaFilter);
        }
        // Set to preferred size:
        int width = userPrefs.getInt("filechooser.width", printPDFChooser.getPreferredSize().width);
        int height = userPrefs.getInt("filechooser.height", printPDFChooser.getPreferredSize().height);
        printPDFChooser.setSize(width, height);
        return printPDFChooser;
    }
    private JFileChooser getContentPackageChooser() {
        if (contentPackageChooser == null) {
            contentPackageChooser = new JFileChooser();
            contentPackageChooser.setSelectedFile(new File(userPrefs.get("contentPackage", System.getProperty("user.home"))));
            contentPackageChooser.setAccessory(new ExportChooserAccessory());
        }
        // Set to preferred size:
        int width = userPrefs.getInt("filechooser.width", contentPackageChooser.getPreferredSize().width);
        int height = userPrefs.getInt("filechooser.height", contentPackageChooser.getPreferredSize().height);
        contentPackageChooser.setSize(width, height);

        ExportChooserAccessory eca = (ExportChooserAccessory) contentPackageChooser.getAccessory();
        eca.setModel(model);


        return contentPackageChooser;
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        organizationPanel = new javax.swing.JPanel();
        organizationScrollPane = new javax.swing.JScrollPane();
        organizationView = new ch.randelshofer.scorm.OrganizationView();
        layoutPanel = new javax.swing.JPanel();
        layoutScrollPane = new javax.swing.JScrollPane();
        layoutView = new ch.randelshofer.scorm.LayoutView();
        languagePanel = new javax.swing.JPanel();
        languageScrollPane = new javax.swing.JScrollPane();
        languageView = new ch.randelshofer.scorm.LanguageView();
        usersPanel = new javax.swing.JPanel();
        loginView = new ch.randelshofer.scorm.LoginView();
        debuggingPanel = new javax.swing.JPanel();
        debugScrollPane = new javax.swing.JScrollPane();
        debugView = new ch.randelshofer.scorm.DebugView();
        manifestPanel = new javax.swing.JPanel();
        manifestView = new ch.randelshofer.scorm.ManifestView();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        importMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        createCourseMenuItem = new javax.swing.JMenuItem();
        createContentPackageMenuItem = new javax.swing.JMenuItem();
        printCourseMenuItem = new javax.swing.JMenuItem();
        printToPDFMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        preferencesSeparator = new javax.swing.JSeparator();
        preferencesMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();
        debugMenuItem = new javax.swing.JMenuItem();

        setTitle("TinyLMS");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        tabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        organizationPanel.setLayout(new java.awt.BorderLayout());

        organizationScrollPane.setBorder(null);
        organizationScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        organizationScrollPane.setViewportView(organizationView);

        organizationPanel.add(organizationScrollPane, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab(labels.getString("organization.organization"), organizationPanel); // NOI18N

        layoutPanel.setLayout(new java.awt.BorderLayout());

        layoutScrollPane.setBorder(null);
        layoutScrollPane.setViewportView(layoutView);

        layoutPanel.add(layoutScrollPane, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab(labels.getString("layout.layout"), layoutPanel); // NOI18N

        languagePanel.setLayout(new java.awt.BorderLayout());

        languageScrollPane.setBorder(null);
        languageScrollPane.setViewportView(languageView);

        languagePanel.add(languageScrollPane, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab(labels.getString("language.language"), languagePanel); // NOI18N

        usersPanel.setLayout(new java.awt.BorderLayout());
        usersPanel.add(loginView, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab(labels.getString("login.login"), usersPanel); // NOI18N

        debuggingPanel.setLayout(new java.awt.BorderLayout());

        debugScrollPane.setBorder(null);
        debugScrollPane.setViewportView(debugView);

        debuggingPanel.add(debugScrollPane, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab(labels.getString("debug.debug"), debuggingPanel); // NOI18N

        manifestPanel.setLayout(new java.awt.BorderLayout());
        manifestPanel.add(manifestView, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab(labels.getString("manifest.imsManifest"), manifestPanel); // NOI18N

        getContentPane().add(tabbedPane, java.awt.BorderLayout.CENTER);

        fileMenu.setText(labels.getString("file.file")); // NOI18N
        fileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printCourse(evt);
            }
        });

        importMenuItem.setText(labels.getString("file.openContentPackage")); // NOI18N
        importMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importSCORMContent(evt);
            }
        });
        fileMenu.add(importMenuItem);
        fileMenu.add(jSeparator2);

        createCourseMenuItem.setText(labels.getString("file.createCourse")); // NOI18N
        createCourseMenuItem.setEnabled(false);
        createCourseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCourse(evt);
            }
        });
        fileMenu.add(createCourseMenuItem);

        createContentPackageMenuItem.setText(labels.getString("file.createWrapped")); // NOI18N
        createContentPackageMenuItem.setEnabled(false);
        createContentPackageMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createContentPackage(evt);
            }
        });
        fileMenu.add(createContentPackageMenuItem);

        printCourseMenuItem.setText(labels.getString("file.printCourse")); // NOI18N
        printCourseMenuItem.setEnabled(false);
        printCourseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printCourse(evt);
            }
        });
        fileMenu.add(printCourseMenuItem);

        printToPDFMenuItem.setText(labels.getString("file.printToPDF")); // NOI18N
        printToPDFMenuItem.setEnabled(false);
        printToPDFMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printToPDF(evt);
            }
        });
        fileMenu.add(printToPDFMenuItem);
        fileMenu.add(jSeparator1);

        openMenuItem.setText(labels.getString("file.loadSettings")); // NOI18N
        openMenuItem.setEnabled(false);
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                open(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setText(labels.getString("file.saveSettingsIntoContentPackage")); // NOI18N
        saveMenuItem.setEnabled(false);
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText(labels.getString("file.saveSettingsAs")); // NOI18N
        saveAsMenuItem.setEnabled(false);
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAs(evt);
            }
        });
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(jSeparator3);

        exitMenuItem.setText(labels.getString("application.exit")); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(labels.getString("edit")); // NOI18N

        cutMenuItem.setText(labels.getString("edit.cut")); // NOI18N
        editMenu.add(cutMenuItem);

        copyMenuItem.setText(labels.getString("edit.copy")); // NOI18N
        editMenu.add(copyMenuItem);

        pasteMenuItem.setText(labels.getString("edit.paste")); // NOI18N
        editMenu.add(pasteMenuItem);
        editMenu.add(preferencesSeparator);

        preferencesMenuItem.setText(labels.getString("application.preferences")); // NOI18N
        preferencesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preferences(evt);
            }
        });
        editMenu.add(preferencesMenuItem);

        menuBar.add(editMenu);

        helpMenu.setText(labels.getString("application.help")); // NOI18N

        aboutMenuItem.setText(labels.getString("application.about")); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                about(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ch/randelshofer/scorm/Labels"); // NOI18N
        debugMenuItem.setText(bundle.getString("debug")); // NOI18N
        debugMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debug(evt);
            }
        });
        helpMenu.add(debugMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void printToPDF(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printToPDF
        setEnabled(false);
        
        JFileChooser fc = getExportPDFChooser();
        fc.setDialogTitle("TinyLMS: "+labels.getString("builder.printToPDF"));
        
        if (fc.showSaveDialog(CourseBuilder.this) != JFileChooser.APPROVE_OPTION) {
            setEnabled(true);
            return;
        }
        
        final File pdfFile;
        if (! fc.getSelectedFile().getName().toLowerCase().endsWith(".pdf")) {
            pdfFile = new File(fc.getSelectedFile().getPath()+".pdf");
        } else {
            pdfFile = fc.getSelectedFile();
        }
        userPrefs.put("pdfFile", pdfFile.toString());
        userPrefs.putInt("filechooser.height", fc.getHeight());
        userPrefs.putInt("filechooser.width", fc.getWidth());
        
        final ProgressView p = new ProgressView(this,
                labels.getString("builder.exportingPDF"),
                labels.getString("preparing"), 0, 100);
        
        final String mediaType = (fc.getFileFilter() == pdfPrintMediaFilter) ? "print" : "screen";
userPrefs.put("pdfMediaType", "mediaType");
        
        RunnableWorker worker = new RunnableWorker() {
            public Object construct() {
                Object result =  null;
                try {
                    CourseModelPDFPrinter printer = new CourseModelPDFPrinter(model);
                    printer.setMediaType(mediaType);
                    printer.print(pdfFile, p);
                } catch (Throwable e) {
                    result = e;
                }
                return result;
            }
            public void finished(Object result) {
                if (result instanceof Throwable) {
                    Throwable t = (Throwable) result;
                    t.printStackTrace();
                    String message = t.getMessage();
                    if (message == null) message = t.toString()+"\nat "+t.getStackTrace()[0];
                    
                    JOptionPane.showMessageDialog(
                            CourseBuilder.this,
                            "<html>"
                            +Fonts.emphasizedDialogFontTag(labels.getString("builder.couldntExportPDF"))
                            +Fonts.dialogFontTag("<br>"+Strings.escapeHTML(message)),
                            "TinyLMS: "+labels.getString("builder.exportPDF"),
                            JOptionPane.INFORMATION_MESSAGE
                            );
                    model.setEnabled(true);
                } else {
                    model.setEnabled(true);
                }
                p.close();
                setEnabled(true);
            }
        };
        
        model.dispatch(worker);
    }//GEN-LAST:event_printToPDF
    
    private void preferences(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferences
        if (preferencesDialog == null) {
            preferencesDialog = new JDialog();
            preferencesDialog.setTitle("TinyLMS: "+labels.getString("application.preferences"));
            preferencesDialog.getContentPane().add(new PreferencesPanel());
            ((JComponent) preferencesDialog.getContentPane()).setBorder(
                    new EmptyBorder(14,20,20,20)
                    );
            preferencesDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            PreferencesUtil.installFramePrefsHandler(userPrefs, "preferencesDialog", preferencesDialog);
            preferencesDialog.pack();
            preferencesDialog.setResizable(false);
        }
        preferencesDialog.setVisible(true);
        
    }//GEN-LAST:event_preferences
        
    private void debug(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debug
        if (debugger == null) {
            debugger = new Debugger();
        }
        debugger.setVisible(true);
    }//GEN-LAST:event_debug
    
    private void createContentPackage(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createContentPackage
        setEnabled(false);
        
        JFileChooser fc = getContentPackageChooser();
        
        fc.setDialogTitle("TinyLMS: "+labels.getString("builder.createWrappedContentPackage"));
        
        if (fc.showSaveDialog(CourseBuilder.this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            if (!selectedFile.getName().endsWith(".zip")) {
                selectedFile = new File(selectedFile.getPath()+".zip");
            }
            
            final File outputContentPackage = selectedFile;


            userPrefs.put("contentPackage", outputContentPackage.toString());
            userPrefs.putInt("filechooser.height", fc.getHeight());
            userPrefs.putInt("filechooser.width", fc.getWidth());
            final ProgressView p = new ProgressView(this,
                    labels.getString("builder.creatingContentPackage"),
                    labels.getString("builder.preparing"), 0, 100);
            RunnableWorker worker = new RunnableWorker() {
                public Object construct() {
                    Object result =  null;
                    try {
                        model.createPIF(outputContentPackage, p);
                    } catch (Throwable e) {
                        result = e;
                    }
                    return result;
                }
                public void finished(Object result) {
                    p.close();
                    if (result instanceof Throwable) {
                        Throwable t = (Throwable) result;
                        t.printStackTrace();
                        String message = t.getMessage();
                        if (message == null) message = t.toString()+"\nat "+t.getStackTrace()[0];
                        
                        JOptionPane.showMessageDialog(
                                CourseBuilder.this,
                                "<html>"
                                +Fonts.emphasizedDialogFontTag(labels.getString("builder.couldntCreateWrappedContentPackage"))
                                +Fonts.dialogFontTag("<br>"+Strings.escapeHTML(message)),
                                "TinyLMS: "+labels.getString("builder.createWrappedContentPackage"),
                                JOptionPane.INFORMATION_MESSAGE
                                );
                    }
                    setEnabled(true);
                }
            };
            model.dispatch(worker);
        } else {
            setEnabled(true);
        }
    }//GEN-LAST:event_createContentPackage
    /**
     * This operation is invoked, when a user selects the
     * File/Open SCORM Content... menu.
     */
    private void importSCORMContent(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importSCORMContent
        setEnabled(false);
        
        JFileChooser fc = getImportFileChooser();
        
        fc.setDialogTitle("TinyLMS: "+labels.getString("builder.openContentPackage"));
        
        if (fc.showOpenDialog(CourseBuilder.this) == JFileChooser.APPROVE_OPTION) {
            final File importFile = fc.getSelectedFile();

            // Store preferences
            userPrefs.put("importFile", importFile.toString());
            userPrefs.putInt("filechooser.height", fc.getHeight());
            userPrefs.putInt("filechooser.width", fc.getWidth());

            doImportSCORMContent(importFile);

        } else {
            setEnabled(true);
        }
    }//GEN-LAST:event_importSCORMContent
    
    /**
     * Imports SCORM content on a worker thread.
     * @param file
     */
    private void doImportSCORMContent(final File importFile) {
    setEnabled(false);


            RunnableWorker worker = new RunnableWorker() {
                /**
                 * @return
                 * Returns a Throwable if the import failed.<br>
                 * Returns Boolean.TRUE if the import is successfull.
                 */
                public Object construct() {
                    Object result =  null;
                    try {
                        if (importFile.getName().toLowerCase().endsWith(".zip")) {
                            model.importPIF(importFile);
                        } else {
                            if (importFile.isDirectory()) {
                                model.importContentPackage(importFile);
                            } else {
                                model.importContentPackage(importFile.getParentFile());
                            }
                        }
                        //result = Boolean.valueOf(model.validate());
                        result = (model.validate()) ? Boolean.TRUE :  Boolean.FALSE;
                    } catch (Throwable e) {
                        result = e;
                    }
                    return result;
                }
                public void finished(Object result) {
                    if (result instanceof Throwable) {
                        Throwable t = (Throwable) result;
                        t.printStackTrace();
                        String message = t.getMessage();
                        if (t instanceof SAXParseException) {
                            SAXParseException se = (SAXParseException) t;
                            message += "\nline:"+se.getLineNumber()+" col:"+se.getColumnNumber();
                        }
                        if (message == null) message = t.toString()+"\nat "+t.getStackTrace()[0];

                        JOptionPane.showMessageDialog(
                                CourseBuilder.this,
                                "<html>"+Fonts.emphasizedDialogFontTag(labels.getString("builder.couldntOpenContentPackage"))
                                +Fonts.dialogFontTag("<br>"+Strings.escapeHTML(message)),
                                "TinyLMS: "+labels.getString("builder.openContentPackage"),
                                JOptionPane.INFORMATION_MESSAGE
                                );
                        setTitle("TinyLMS");
                        model.setEnabled(false);
                    } else if (result == Boolean.FALSE) {
                        // The imsmanifest.xml is not ok.
                        JOptionPane.showMessageDialog(
                                CourseBuilder.this,
                                "<html>"+Fonts.emphasizedDialogFontTag(labels.getString("builder.manifestIsInvalid"))
                                +Fonts.dialogFontTag("<br>"+labels.getString("builder.pleaseCorrectManifest")+
                                "<br>"+labels.getString("builder.courseMayStillBeCreatable")),
                                "TinyLMS: "+labels.getString("builder.openContentPackage"),
                                JOptionPane.WARNING_MESSAGE
                                );
                        manifestView.expandInvalidCAMElements();
                        setTitle(model.getContentPackageName()+" - TinyLMS");
                        model.setEnabled(true);
                        tabbedPane.setSelectedComponent(manifestPanel);
                    } else  {
                        // Everything is fine.
                        // We expand does elements that have warnings.
                        manifestView.expandInvalidCAMElements();
                        //expandTree();
                        setTitle(model.getContentPackageName()+" - TinyLMS");
                        model.setEnabled(true);
                    }
                    setEnabled(true);
                }
            };

            model.dispatch(worker);
    }

    private void printCourse(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printCourse
        // Add your handling code here:
        setEnabled(false);
        RunnableWorker worker = new RunnableWorker() {
            public Object construct() {
                Object result =  null;
                try {
                    new CourseModelPrinter(model).print();
                } catch (Throwable e) {
                    result = e;
                }
                return result;
            }
            public void finished(Object result) {
                if (result instanceof Throwable) {
                    Throwable t = (Throwable) result;
                    t.printStackTrace();
                    String message = t.getMessage();
                    if (message == null) message = t.toString()+"\nat "+t.getStackTrace()[0];
                    
                    JOptionPane.showMessageDialog(
                            CourseBuilder.this,
                            "<html>"
                            +Fonts.emphasizedDialogFontTag(labels.getString("builder.couldntPrintCourse"))
                            +Fonts.dialogFontTag("<br>"+Strings.escapeHTML(message)),
                            "TinyLMS: "+labels.getString("builder.printCourse"),
                            JOptionPane.INFORMATION_MESSAGE
                            );
                    model.setEnabled(true);
                } else {
                    model.setEnabled(true);
                }
                setEnabled(true);
            }
        };
        
        model.dispatch(worker);
    }//GEN-LAST:event_printCourse
    
    private void createCourse(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCourse
        setEnabled(false);
        
        JFileChooser fc = getCourseDirChooser();
        fc.setDialogTitle("TinyLMS: "+labels.getString("builder.createCourse"));
        
        if (fc.showSaveDialog(CourseBuilder.this) == JFileChooser.APPROVE_OPTION) {
            final File outputContentPackage = fc.getSelectedFile();
            userPrefs.put("courseDir", outputContentPackage.toString());
            userPrefs.putInt("filechooser.height", fc.getHeight());
            userPrefs.putInt("filechooser.width", fc.getWidth());
            final ProgressView p = new ProgressView(this,
                    labels.getString("builder.creatingCourse"),
                    labels.getString("preparing"), 0, 100);
            RunnableWorker worker = new RunnableWorker() {
                public Object construct() {
                    Object result =  null;
                    try {
                        model.createCourse(outputContentPackage, p);
                    } catch (Throwable e) {
                        result = e;
                    }
                    return result;
                }
                public void finished(Object result) {
                    p.close();
                    
                    if (result instanceof Throwable) {
                        Throwable t = (Throwable) result;
                        t.printStackTrace();
                        String message = t.getMessage();
                        if (message == null) message = t.toString()+"\nat "+t.getStackTrace()[0];
                        
                        JOptionPane.showMessageDialog(
                                CourseBuilder.this,
                                "<html>"
                                +Fonts.emphasizedDialogFontTag(labels.getString("builder.couldntCreateCourse"))
                                +Fonts.dialogFontTag("<br>"+Strings.escapeHTML(message)),
                                "TinyLMS: "+labels.getString("builder.createCourse"),
                                JOptionPane.INFORMATION_MESSAGE
                                );
                    }
                    setEnabled(true);
                }
            };
            model.dispatch(worker);
        } else {
            setEnabled(true);
        }
    }//GEN-LAST:event_createCourse
    
    private void saveAs(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAs
        setEnabled(false);
        
        JFileChooser fc = getPropertiesFileChooser();
        
        fc.setDialogTitle("TinyLMS: "+labels.getString("builder.saveSettings"));
        if (fc.showSaveDialog(CourseBuilder.this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            if (! selectedFile.getName().endsWith(".xml")) selectedFile = new File(selectedFile.getParent(), selectedFile.getName()+".xml");
            final File file = selectedFile;
            userPrefs.put("propertiesFile", file.toString());
            userPrefs.putInt("filechooser.height", fc.getHeight());
            userPrefs.putInt("filechooser.width", fc.getWidth());
            RunnableWorker worker = new RunnableWorker() {
                /**
                 * @return
                 * Returns a Throwable if saving the file failed.<br>
                 * Returns Boolean.TRUE if the file was successfully saved.
                 */
                public Object construct() {
                    Object result =  null;
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        model.saveProperties(out);
                        result = Boolean.TRUE;
                    } catch (Throwable e) {
                        result = e;
                    } finally {
                        if (out != null) {
                            try { out.close(); } catch (IOException e) {
                                if (result == null) result = e;
                            }
                        }
                    }
                    return result;
                }
                public void finished(Object result) {
                    if (result instanceof Throwable) {
                        Throwable t = (Throwable) result;
                        t.printStackTrace();
                        String message = t.getMessage();
                        if (message == null) message = t.toString()+"\nat "+t.getStackTrace()[0];
                        
                        JOptionPane.showMessageDialog(
                                CourseBuilder.this,
                                "<html>"+Fonts.emphasizedDialogFontTag(labels.getString("builder.couldntSaveSettings"))
                                +Fonts.dialogFontTag("<br>"+Strings.escapeHTML(message)),
                                "TinyLMS: "+labels.getString("builder.saveSettings"),
                                JOptionPane.INFORMATION_MESSAGE
                                );
                    }
                    setEnabled(true);
                }
            };
            
            model.dispatch(worker);
            
        } else {
            setEnabled(true);
        }
    }//GEN-LAST:event_saveAs
    
    private void save(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save
        setEnabled(false);
        
        if (model.getIMSManifestDocument().getContentPackage() != null) {
            final File file = new File(model.getIMSManifestDocument().getContentPackage(),"tinylms.xml");
            RunnableWorker worker = new RunnableWorker() {
                /**
                 * @return
                 * Returns a Throwable if saving the file failed.<br>
                 * Returns Boolean.TRUE if the file was successfully saved.
                 */
                public Object construct() {
                    Object result =  null;
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        model.saveProperties(out);
                        result = Boolean.TRUE;
                    } catch (Throwable e) {
                        result = e;
                    } finally {
                        if (out != null) {
                            try { out.close(); } catch (IOException e) {
                                if (result == null) result = e;
                            }
                        }
                    }
                    return result;
                }
                public void finished(Object result) {
                    if (result instanceof Throwable) {
                        Throwable t = (Throwable) result;
                        t.printStackTrace();
                        String message = t.getMessage();
                        if (message == null) message = t.toString()+"\nat "+t.getStackTrace()[0];
                        
                        // Open failed.
                        JOptionPane.showMessageDialog(
                                CourseBuilder.this,
                                "<html>"+Fonts.emphasizedDialogFontTag(labels.getString("builder.couldntSaveSettings"))
                                +Fonts.dialogFontTag("<br>"+Strings.escapeHTML(message)),
                                "TinyLMS: "+labels.getString("builder.saveSettings"),
                                JOptionPane.INFORMATION_MESSAGE
                                );
                    }
                    setEnabled(true);
                }
            };
            
            model.dispatch(worker);
            
        } else {
            setEnabled(true);
        }
    }//GEN-LAST:event_save
    
    private void open(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_open
        setEnabled(false);
        
        JFileChooser fc = getPropertiesFileChooser();
        
        fc.setDialogTitle("TinyLMS: "+labels.getString("builder.loadSettings"));
        
        if (fc.showOpenDialog(CourseBuilder.this) == JFileChooser.APPROVE_OPTION) {
            final File file = fc.getSelectedFile();
            userPrefs.put("propertiesFile", file.toString());
            userPrefs.putInt("filechooser.height", fc.getHeight());
            userPrefs.putInt("filechooser.width", fc.getWidth());
            RunnableWorker worker = new RunnableWorker() {
                /**
                 * @return
                 * Returns a Throwable if reading the file failed.<br>
                 * Returns Boolean.TRUE if the file was successfully read.
                 */
                public Object construct() {
                    Object result =  null;
                    FileInputStream in = null;
                    try {
                        in = new FileInputStream(file);
                        model.loadProperties(in);
                        result = Boolean.TRUE;
                    } catch (Throwable e) {
                        result = e;
                    } finally {
                        if (in != null) {
                            try { in.close(); } catch (IOException e) {
                                if (result == null) result = e;
                            }
                        }
                    }
                    return result;
                }
                public void finished(Object result) {
                    if (result instanceof Throwable) {
                        Throwable t = (Throwable) result;
                        t.printStackTrace();
                        String message = t.getMessage();
                        if (message == null) message = t.toString()+"\nat "+t.getStackTrace()[0];
                        
                        JOptionPane.showMessageDialog(
                                CourseBuilder.this,
                                "<html>"+Fonts.emphasizedDialogFontTag(labels.getString("builder.couldntLoadSettings"))
                                +Fonts.dialogFontTag("<br>"+Strings.escapeHTML(message)),
                                "TinyLMS: "+labels.getString("builder.loadSettings"),
                                JOptionPane.INFORMATION_MESSAGE
                                );
                    }
                    setEnabled(true);
                }
            };
            
            model.dispatch(worker);
            
        } else {
            setEnabled(true);
        }
        
    }//GEN-LAST:event_open
    
    private void about(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_about
        JOptionPane.showConfirmDialog(
                this,
                "<html>"
                +Fonts.emphasizedDialogFontTag("TinyLMS")
                +Fonts.smallDialogFontTag("<br>"+
                "Version "+TinyLMSApp.getVersion()+"<br><br>"+
                "Copyright © 2003-2009<br>"+
                "Werner Randelshofer<br>"+
                "Hausmatt 10<br>"+
                "CH-6405 Immensee<br>"+
                "Switzerland<br><br>"+
                "All rights reserved.<br><br>"+
                "TinyLMS comes with <b>absolutely no warranty</b>.<br>"+
                "This is free software, and you are welcome<br>"+
                "to redistribute it under certain conditions.<br><br>"+
                "mailto:werner.randelshofer@bluewin.ch<br>"+
                "http://www.randelshofer.ch/<br><br>"+
                "Java VM: "+System.getProperty("java.vm.version")+", "+System.getProperty("java.vendor")+"<br>"+
                "Operating System: "+System.getProperty("os.name")+" "+System.getProperty("os.version")+", "+System.getProperty("os.arch")
                ),
                "About TinyLMS",
                JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE
                );
        
    }//GEN-LAST:event_about
    /**
     * This operation is invoked, when a user selects the
     * File/Import PIF... menu.
     */
    
    
    private void exit(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit
        if (isEnabled()) { System.exit(0); }
    }//GEN-LAST:event_exit
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        if (isEnabled()) { System.exit(0); }
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    System.setProperty("apple.laf.useScreenMenuBar","true");
                    System.setProperty("Quaqua.TabbedPane.design","jaguar");
                    boolean isMacOSX = System.getProperty("os.name").toLowerCase().
                            startsWith("mac os x");
                    UIManager.setLookAndFeel(
                            (isMacOSX) ?
                                QuaquaManager.getLookAndFeelClassName() :
                                UIManager.getSystemLookAndFeelClassName()
                                );
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                JFrame f = new CourseBuilder();
                //f.setSize(400, 400);
                //f.pack();
                f.setVisible(true);
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem createContentPackageMenuItem;
    private javax.swing.JMenuItem createCourseMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem debugMenuItem;
    private javax.swing.JScrollPane debugScrollPane;
    private ch.randelshofer.scorm.DebugView debugView;
    private javax.swing.JPanel debuggingPanel;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem importMenuItem;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel languagePanel;
    private javax.swing.JScrollPane languageScrollPane;
    private ch.randelshofer.scorm.LanguageView languageView;
    private javax.swing.JPanel layoutPanel;
    private javax.swing.JScrollPane layoutScrollPane;
    private ch.randelshofer.scorm.LayoutView layoutView;
    private ch.randelshofer.scorm.LoginView loginView;
    private javax.swing.JPanel manifestPanel;
    private ch.randelshofer.scorm.ManifestView manifestView;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JPanel organizationPanel;
    private javax.swing.JScrollPane organizationScrollPane;
    private ch.randelshofer.scorm.OrganizationView organizationView;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem preferencesMenuItem;
    private javax.swing.JSeparator preferencesSeparator;
    private javax.swing.JMenuItem printCourseMenuItem;
    private javax.swing.JMenuItem printToPDFMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JPanel usersPanel;
    // End of variables declaration//GEN-END:variables
}
