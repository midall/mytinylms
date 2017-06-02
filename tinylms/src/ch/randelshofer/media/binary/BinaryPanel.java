/*
 * @(#)BinaryPanel.java  1.0.2.1  2001-06-16
 *
 * Copyright (c) 1999 Werner Randelshofer
 * Hausmatt 10, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */
package ch.randelshofer.media.binary;

import java.util.Arrays;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Panel for untyped binary data.
 *
 * @author  Werner Randelshofer, Hausmatt 10, CH-6405 Immensee, Switzerland
 * @version 1.0.2.1 2001-06-16 Upgrade to JKD 1.3 is in progress...
 * <br> 1.0.2   2000-10-08 Set small font when Platinum LAF is active.
 * <br> 1.0.1  2000-06-12
 * <br>1.0 1999-10-19
 */
public class BinaryPanel
extends JComponent {
    private BinaryModel model_;
    private final char[] HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };

    public BinaryPanel() {
        model_ = new BinaryModel();
        updateUI();
    }

    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        return new Dimension(
            fm.charWidth('0')*68,
            fm.getHeight()*(model_.getLength()+15)/16
        );
    }

    public void setModel(BinaryModel m) {
        model_ = m;
        revalidate();
        repaint();
    }

    public BinaryModel getModel() {
        return model_;
    }

    public void paintComponent(Graphics g) {
        Rectangle clipRect = g.getClipBounds();
        FontMetrics fm = getFontMetrics(getFont());

        g.setColor(getBackground());
        g.fillRect(clipRect.x, clipRect.y, clipRect.width, clipRect.height);
        g.setColor(getForeground());

        int startLine = clipRect.y / fm.getHeight();
        int endLine = Math.min(
            (clipRect.y + clipRect.height) / fm.getHeight() + 1,
            (model_.getLength()+15) / 16
        );
        byte[] bytes = new byte[16];
        char[] chars = new char[69];

        // for each visible line
        for ( ; startLine < endLine; startLine++) {
            Arrays.fill(chars, ' ');

            int offset = 1;

            // write the line address
            int address = startLine*16;
            for (int i=0; i < 8; i++) {
                chars[offset++] = HEX[address >>> 28];
                address <<= 4;
            }
            offset += 2;
            chars[9] = '>';

            int len = model_.getBytes(startLine*16,16,bytes);
            for(int i=0; i<len; i++) {
                // write the data as hexadecimal digits
                chars[offset++] = HEX[ (bytes[i] >>> 4) & 0x0f];
                chars[offset++] = HEX[bytes[i] & 0x0f];

                // write the data as characters
                char ch = (char) (bytes[i] & 0xff);
                chars[i + 48 + i / 4] = Character.isISOControl(ch) ? '.' : ch;

                if (i % 4 == 3) {
                    offset++;
                }

            }

            g.drawString(
                new String(chars),
                0,
                startLine*fm.getHeight()+fm.getAscent()
            );
        }
    }

    public void updateUI() {
        super.updateUI();
        setBackground(UIManager.getColor("TextArea.background"));
        setForeground(UIManager.getColor("TextArea.foreground"));
        if (UIManager.getLookAndFeel().getID().equals("MacOS")) {
            setFont(
                new Font("Monospaced",Font.PLAIN,10)
            );
        } else {
            setFont(
                new Font("Monospaced",Font.PLAIN,12)
            );
        }
    }
}
