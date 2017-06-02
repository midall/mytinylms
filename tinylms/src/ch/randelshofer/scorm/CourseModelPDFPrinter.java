/*
 * @(#)CourseModelPDFPrinter.java  1.0.7  2008-12-16
 *
 * Copyright (c) 2007-2008 Werner Randelshofer
 * Hausmatt 10, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */
package ch.randelshofer.scorm;

import ch.randelshofer.gui.ProgressIndicator;
import ch.randelshofer.scorm.cam.*;
import ch.randelshofer.util.ResourceBundleUtil;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.image.*;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.SAXParserFactory;
import org.ccil.cowan.tagsoup.Parser;
import org.ccil.cowan.tagsoup.jaxp.*;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.simple.*;
import org.xhtmlrenderer.util.XRLog;
import org.xml.sax.SAXException;

/**
 * Prints a CourseModel as a PDF document.
 *
 * @author Werner Randelshofer
 * @version 1.0.7 2008-12-16 Fixed encoding of baseURI.
 * <br>1.0 6. August 2007 Created.
 */
public class CourseModelPDFPrinter {

    private CourseModel model;
    private URI baseURI;
    private File pdfFile;
    private ResourceBundleUtil labels;
    /**
     * If this is set to true, renders a page first as an image, and inserts
     * it into the PDF page.
     * <p>
     * If this is set to false, renders a page directly into PDF page.
     */
    private boolean isRenderAsImage = false;
    private String mediaType = "screen";

    /** Creates a new instance. */
    public CourseModelPDFPrinter(CourseModel model) {
        this.model = model;
        baseURI = model.getIMSManifestDocument().getManifestURI();
        labels = ResourceBundleUtil.getLAFBundle("ch.randelshofer.scorm.Labels");
    }

    public void setMediaType(String newValue) {
        mediaType = newValue;
    }

    public void setRenderAsImage(boolean newValue) {
        isRenderAsImage = newValue;
    }

    public void print(File pdfFile, ProgressIndicator p) throws IOException {
        if (isRenderAsImage) {
            printAsImage(pdfFile, p);
        } else {
            printAsText(pdfFile, p);
        }
    }

    public void printAsImage(File pdfFile, ProgressIndicator p) throws IOException {
        System.setProperty("xr.load.xml-reader", "org.ccil.cowan.tagsoup.Parser");
        XRLog.setLoggingEnabled(false);

        this.pdfFile = pdfFile;

        ArrayList pageList = model.createPageList(model.getSelectedOrganization());
        p.setMaximum(pageList.size());

        String printDate = DateFormat.getInstance().format(new Date());


        Document pdf = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer;
        try {
            writer = PdfWriter.getInstance(pdf, new FileOutputStream(pdfFile));


            // Print each page
            // -------------------------------------------
            for (int i = 0; i < pageList.size() && !p.isCanceled(); i++) {
                ItemElement item = (ItemElement) pageList.get(i);

                p.setProgress(i);
                p.setNote(item.getTitle());

                Enumeration enm = model.getIMSManifestDocument().getResourcesElement().preorderEnumeration();
                ResourceElement resource = null;
                while (enm.hasMoreElements()) {
                    Object o = enm.nextElement();
                    if (o instanceof ResourceElement) {
                        resource = (ResourceElement) o;
                        if (item.getIdentifierref().equals(resource.getIdentifier())) {
                            break;
                        }
                    }
                }

                Phrase phrase = new Phrase(model.getSelectedOrganization().getTitle());
                HeaderFooter header = new MyHeaderFooter(
                        new Phrase(model.getSelectedOrganization().getTitle()),
                        new Phrase(item.getTitle()));
                pdf.setHeader(header);
                HeaderFooter footer = new MyHeaderFooter(
                        new Phrase(printDate),
                        new Phrase(labels.getFormatted("pdf.pagina", Integer.toString(i + 1))));
                footer.setAlignment(HeaderFooter.ALIGN_RIGHT);
                pdf.setHeader(header);
                pdf.setFooter(footer);


                if (i == 0) {
                    pdf.open();
                } else {
                    pdf.newPage();
                }
                printPageAsImage(pdf, writer, baseURI.toURL(), resource.getHRef());
            }
            p.setProgress(pageList.size());

        } catch (DocumentException ex) {
            IOException ioe = new IOException(ex.getMessage());
            ioe.initCause(ex);
            throw ioe;
        } finally {
            try {
                pdf.close();
            } catch (Throwable e) {
            }
            p.close();
        }
    }

    /**
     * Exports a document to pdf.
     * @param pdf The PDF output document
     * @param baseURI the base URL of the input document
     * @param href the relative URL of the input document.
     */
    private void printPageAsImage(Document pdf, PdfWriter writer, URL baseURL, String href) throws IOException, DocumentException {

        File outDir = pdfFile.getParentFile();
        File outFile = new File(outDir, href);
        URL url = new URL(baseURL, href);
        URI uri;
        try {
            uri = url.toURI();
        } catch (URISyntaxException ex) {
            IOException ioe = new IOException("Couldn't convert "+url+" to URI");
            ioe.initCause(ex);
            throw ioe;
        }

        if (isRenderAsImage) {
            // Width and height of an A4 page (210x297 mm) in 300 DPI;
            int width = (int) (210 * 300 / 254);
            int height = (int) (297 * 300 / 254);
            try {
                width = Integer.decode(model.getFramesetPageWidth());
            } catch (NumberFormatException ex) {
            //ex.printStackTrace();
            }
            try {
                height = Integer.decode(model.getFramesetPageHeight());
            } catch (NumberFormatException ex) {
            //ex.printStackTrace();
            }

            ImageRenderer renderer = new ImageRenderer();
            BufferedImage bufferedImg = renderer.renderToImage(
                    uri.toString(),
                    null,
                    width, height);
            //ImageIO.write(img, "PNG", new File(outFile.getPath()+".png"));

            com.lowagie.text.Image img = com.lowagie.text.Image.getInstance(
                    bufferedImg, null);
            float absWidth = pdf.getPageSize().getWidth() - pdf.leftMargin() - pdf.rightMargin();
            float absHeight = absWidth * bufferedImg.getHeight() / bufferedImg.getWidth();

            // if the image is too high, scale it down
            float oversizeFactor = (pdf.getPageSize().getHeight() -
                    pdf.leftMargin() -
                    pdf.rightMargin()) / absHeight;
            if (oversizeFactor < 1f) {
                absWidth *= oversizeFactor;
                absHeight *= oversizeFactor;
            }

            img.scaleAbsolute(absWidth, absHeight);
            img.setAbsolutePosition(
                    (pdf.getPageSize().getWidth() - absWidth) / 2f,
                    (pdf.getPageSize().getHeight() - absHeight) / 2f + pdf.bottomMargin());
            PdfContentByte cb = writer.getDirectContent();
            cb.addImage(img);
        } else {
            /* Unfortunately, the PDF Renderer only renders the text of an HTML page* /
            PDFRenderer renderer = new PDFRenderer();
            try {
            renderer.renderToPDF(url.toString(), outFile.getPath()+".pdf");
            } catch (DocumentException e) {
            IOException ioe = new IOException("Couldn't render "+url);
            ioe.initCause(e);
            throw ioe;
            }*/

            // This might work better:
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url.toString());
            renderer.getSharedContext().setPrint(false); // Explicitly switch print mode off
            OutputStream os = null;
            try {
                os = new FileOutputStream(outFile.getPath() + ".pdf");
                renderer.layout();
                renderer.createPDF(os);

                os.close();
                os = null;
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                    // ignore
                    }
                }
            }
        }
    }

    public void printAsText(File pdfFile, ProgressIndicator p) throws IOException {
        System.setProperty("xr.load.xml-reader", "org.ccil.cowan.tagsoup.Parser");
        XRLog.setLoggingEnabled(false);

        this.pdfFile = pdfFile;

        ArrayList pageList = model.createPageList(model.getSelectedOrganization());
        p.setMaximum(pageList.size());

        String printDate = DateFormat.getInstance().format(new Date());

        // Width and height of an A4 page (210x297 mm) in 72 DPI;
        int width = (int) (210 * 254 / 72);
        int height = (int) (297 * 254 / 72);
        try {
            width = Integer.decode(model.getFramesetPageWidth());
        } catch (NumberFormatException ex) {
        //ex.printStackTrace();
        }
        try {
            height = Integer.decode(model.getFramesetPageHeight());
        } catch (NumberFormatException ex) {
        //ex.printStackTrace();
        }
        
        float dpi = width / (190f / 25.4f * 3.5f);
        OutputStream os = null;
        try {
            os = new FileOutputStream(pdfFile);

            // ITextRenderer renderer = new ITextRenderer();
            // ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
            ITextRenderer renderer = new ITextRenderer(dpi, 20);
            renderer.getSharedContext().setMedia(mediaType);

            // Print each page
            // -------------------------------------------
            for (int i = 0; i < pageList.size() && !p.isCanceled(); i++) {
                ItemElement item = (ItemElement) pageList.get(i);

                p.setProgress(i);
                p.setNote(item.getTitle());

                Enumeration enm = model.getIMSManifestDocument().getResourcesElement().preorderEnumeration();
                ResourceElement resource = null;
                while (enm.hasMoreElements()) {
                    Object o = enm.nextElement();
                    if (o instanceof ResourceElement) {
                        resource = (ResourceElement) o;
                        if (item.getIdentifierref().equals(resource.getIdentifier())) {
                            break;
                        }
                    }
                }


                URL url = new URL(baseURI.toURL(), resource.getHRef());
                renderer.setDocument(url.toString());
                renderer.layout();
                if (i == 0) {
                    renderer.createPDF(os, false);
                } else {
                    renderer.writeNextDocument();
                }
            }

            renderer.finishPDF();

            os.close();
            os = null;
        } catch (DocumentException de) {
            IOException e = new IOException();
            e.initCause(de);
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                // ignore
                }
            }
        }
    }

    public static class MyHeaderFooter extends HeaderFooter {

        public MyHeaderFooter(Phrase before, Phrase after) {
            super(before, after);
        }

        public Paragraph paragraph() {
            Paragraph paragraph = new Paragraph(getBefore().getLeading());
            paragraph.add(getBefore());
            paragraph.addSpecial(new Phrase(" – "));
            /*
            if (numbered) {
            paragraph.addSpecial(new Chunk(String.valueOf(pageN), before.font()));
            }*/
            if (getAfter() != null) {
                paragraph.addSpecial(getAfter());
            }
            paragraph.setAlignment(alignment());
            return paragraph;
        }
    }
}
