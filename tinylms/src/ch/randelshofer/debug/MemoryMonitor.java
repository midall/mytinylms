
/*
 * @(#)MemoryMonitor.java	1.27 99/11/05
 *
 * Copyright (c) 1998, 1999 by Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */
package ch.randelshofer.debug;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


/**
 * Tracks Memory allocated & used, displayed in graph form.
 */
public class MemoryMonitor extends JPanel {

    static JCheckBox dateStampCB = new JCheckBox("Output Date Stamp");
    public Surface surf;
    JPanel controls;
    boolean doControls;
    JTextField tf;

    public MemoryMonitor() {
        initComponents();
        
        //setLayout(new BorderLayout());
        add(surf = new Surface(), BorderLayout.CENTER);
        /*
        controls = new JPanel();
        controls.setPreferredSize(new Dimension(135,80));
        //Font font = new Font("serif", Font.PLAIN, 10);
        Font font = new Font("Dialog", Font.PLAIN, 10);
        JLabel label = new JLabel("Sample Rate");
        label.setFont(font);
        label.setForeground(Color.red);
        controls.add(label);
        tf = new JTextField("1000");
        tf.setPreferredSize(new Dimension(45,20));
        controls.add(tf);
        controls.add(label = new JLabel("ms"));
        label.setFont(font);
        label.setForeground(Color.red);
        controls.add(dateStampCB);
        dateStampCB.setFont(font);
        MouseListener ml = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
               if (doControls = !doControls) {
                   remove(surf);
                   add(controls, BorderLayout.CENTER);
                   tf.requestFocus();
               } else {
                   remove(controls);
                   try { 
                       surf.sleepAmount = Long.parseLong(tf.getText().trim());
                   } catch (Exception ex) {}
                   add(surf, BorderLayout.CENTER);
               }
               invalidate();
               validate();
               repaint();
            }
        };
        addMouseListener(ml);
        controls.addMouseListener(ml);
        */
    }

    public void start() {
        surf.start();
    }
    public void stop() {
        surf.stop();
    }

    public class Surface extends JPanel implements Runnable {

        public Thread thread;
        public long sleepAmount = 1000;
        private int w, h;
        private BufferedImage bimg;
        private Graphics2D big;
        //private Font font = new Font("Times New Roman", Font.PLAIN, 11);
        private Font font = new Font("Dialog", Font.PLAIN, 11);
        private Runtime r = Runtime.getRuntime();
        private int columnInc;
        private int pts[];
        private int tpts[];
        private int ptNum;
        private int ascent, descent;
        private float freeMemory, totalMemory;
        private Rectangle graphOutlineRect = new Rectangle();
        private Rectangle2D mfRect = new Rectangle2D.Float();
        private Rectangle2D muRect = new Rectangle2D.Float();
        private Line2D graphLine = new Line2D.Float();
        private Color graphColor = new Color(46, 139, 87);
        private Color mfColor = new Color(0, 100, 0);
        private String usedStr;
      
        private float oldTotalMemory = -1;
        
        public Surface() {
            setBackground(Color.black);
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (thread == null) start(); else stop();
                }
            });
        }

        public Dimension getMinimumSize() {
            return getPreferredSize();
        }

        public Dimension getMaximumSize() {
            return getPreferredSize();
        }

        public Dimension getPreferredSize() {
            return new Dimension(135,80);
        }

        public void setSize(int width, int height) {
            super.setSize(width, height);
            
            // clear graph
            ptNum = 0;
        }
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            
            // clear graph
            ptNum = 0;
        }
            
        public void paint(Graphics g) {
            if (big == null) {
                return;
            }

            big.setBackground(getBackground());
            big.clearRect(0,0,w,h);

            float freeMemory = (float) r.freeMemory();
            float totalMemory = (float) r.totalMemory();
            
            // .. Draw allocated and used strings ..
            big.setColor(Color.green);
            big.drawString(String.valueOf((int) totalMemory/1024) + "K allocated",  4.0f, (float) ascent+0.5f);
            usedStr = String.valueOf(((int) (totalMemory - freeMemory))/1024) 
                + "K used";
            big.drawString(usedStr, 4, h-descent);

            // Calculate remaining size
            float ssH = ascent + descent;
            float remainingHeight = (float) (h - (ssH*2) - 0.5f);
            float blockHeight = remainingHeight/10;
            float blockWidth = 20.0f;
            float remainingWidth = (float) (w - blockWidth - 10);

            // .. Memory Free ..
            big.setColor(mfColor);
            int MemUsage = (int) ((freeMemory / totalMemory) * 10);
            int i = 0;
            for ( ; i < MemUsage ; i++) { 
                mfRect.setRect(5,(float) ssH+i*blockHeight,
                                blockWidth,(float) blockHeight-1);
                big.fill(mfRect);
            }

            // .. Memory Used ..
            big.setColor(Color.green);
            for ( ; i < 10; i++)  {
                muRect.setRect(5,(float) ssH+i*blockHeight,
                                blockWidth,(float) blockHeight-1);
                big.fill(muRect);
            }

            // .. Draw History Graph ..
            big.setColor(graphColor);
            int graphX = 30;
            int graphY = (int) ssH;
            int graphW = w - graphX - 5;
            int graphH = (int) remainingHeight;
            graphOutlineRect.setRect(graphX, graphY, graphW, graphH);
            big.draw(graphOutlineRect);


            // .. Draw row ..
            for (int j = 1; j < 10; j++) {
                float graphRow = graphY + graphH / 10f*j;
                graphLine.setLine(graphX,graphRow,graphX+graphW,graphRow);
                big.draw(graphLine);
            }
        
            // .. Draw animated column movement ..
            int graphColumn = graphW/15;

            if (columnInc == 0) {
                columnInc = graphColumn;
            }

            for (int j = graphX+columnInc; j < graphW+graphX; j+=graphColumn) {
                graphLine.setLine(j,graphY,j,graphY+graphH);
                big.draw(graphLine);
            }

            --columnInc;

            // rescale graph if total memory has changed
            if (oldTotalMemory != totalMemory) {
                if (oldTotalMemory > 0 && pts != null) {
                    for (i=0; i < ptNum; i++) {
                        pts[i] = graphY+graphH-(int)((graphY+graphH-pts[i])*(oldTotalMemory/totalMemory));
                        tpts[i] = graphY+graphH-(int)((graphY+graphH-tpts[i])*(oldTotalMemory/totalMemory));
                    }
                }
                oldTotalMemory = totalMemory;
            }

            // Alloc array if there arent any points
            if (pts == null) {
                pts = new int[graphW];
                tpts = new int[graphW];
                ptNum = 0;
                
            // Resize array if window width has changed    
            } else if (pts.length != graphW) {
                int tmp[] = null;
                int tmp2[] = null;
                if (ptNum < graphW) {     
                    tmp = new int[ptNum];
                    tmp2 = new int[ptNum];
                    System.arraycopy(pts, 0, tmp, 0, tmp.length);
                    System.arraycopy(tpts, 0, tmp2, 0, tmp2.length);
                } else {        
                    tmp = new int[graphW];
                    tmp2 = new int[graphW];
                    System.arraycopy(pts, pts.length-tmp.length, tmp, 0, tmp.length);
                    System.arraycopy(tpts, tpts.length-tmp2.length, tmp2, 0, tmp2.length);
                    ptNum = tmp.length - 2;
                }
                pts = new int[graphW];
                tpts = new int[graphW];
                System.arraycopy(tmp, 0, pts, 0, tmp.length);
                System.arraycopy(tmp, 0, tpts, 0, tmp2.length);
                
            // draw data    
            } else {
                tpts[ptNum] = graphY;
                pts[ptNum] = (int)(graphY+graphH*(freeMemory/totalMemory));

                // draw the total memory graph
                big.setColor(Color.green);
                for (int j=graphX+graphW-ptNum, k=0;k < ptNum; k++, j++) {
                    if (k != 0) {
                        if (tpts[k] != tpts[k-1]) {
                            big.drawLine(j-1, tpts[k-1], j, tpts[k]);
                        } else {
                            big.fillRect(j, tpts[k], 1, 1);
                        }
                    }
                }

                // draw the used memory graph
                big.setColor(Color.yellow);
                for (int j=graphX+graphW-ptNum, k=0;k < ptNum; k++, j++) {
                    if (k != 0) {
                        if (pts[k] != pts[k-1]) {
                            big.drawLine(j-1, pts[k-1], j, pts[k]);
                        } else {
                            big.fillRect(j, pts[k], 1, 1);
                        }
                    }
                }
                if (ptNum+2 == pts.length) {
                    // throw out oldest point
                    /*
                    for (int j = 1;j < ptNum; j++) {
                        pts[j-1] = pts[j];
                        tpts[j-1] = tpts[j];
                    }
                    --ptNum;
                     */
                    System.arraycopy(pts, 1, pts, 0, ptNum);
                    System.arraycopy(tpts, 1, tpts, 0, ptNum);
                } else {
                    ptNum++;
                }
            }
            g.drawImage(bimg, 0, 0, this);
        }


        public void start() {
            thread = new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setName("MemoryMonitor");
            thread.start();
        }


        public synchronized void stop() {
            thread = null;
            notify();
        }


        public void run() {
            Thread me = Thread.currentThread();

            while (thread == me && !isShowing() || getSize().width == 0) {
                try {
                    thread.sleep(500);
                } catch (InterruptedException e) { return; }
            }
    
            while (thread == me && isShowing()) {
                Dimension d = getSize();
                if (d.width != w || d.height != h) {
                    w = d.width;
                    h = d.height;
                    bimg = (BufferedImage) createImage(w, h);
                    big = bimg.createGraphics();
                    big.setFont(font);
                    FontMetrics fm = big.getFontMetrics(font);
                    ascent = (int) fm.getAscent();
                    descent = (int) fm.getDescent();
                }
                repaint();
                try {
                    thread.sleep(sleepAmount);
                } catch (InterruptedException e) { break; }
                if (MemoryMonitor.dateStampCB.isSelected()) {
                     System.out.println(new Date().toString() + " " + usedStr);
                }
            }
            thread = null;
        }
    }


    public static void main(String s[]) {
        final MemoryMonitor demo = new MemoryMonitor();
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
            public void windowDeiconified(WindowEvent e) { demo.surf.start(); }
            public void windowIconified(WindowEvent e) { demo.surf.stop(); }
        };
        JFrame f = new JFrame("Java2D Demo - MemoryMonitor");
        f.addWindowListener(l);
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(200,200));
        f.setVisible(true);
        demo.surf.start();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        gcButton = new javax.swing.JButton();
        
        setLayout(new java.awt.BorderLayout());
        
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));
        
        gcButton.setText("GC");
        gcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gc(evt);
            }
        });
        
        jPanel1.add(gcButton);
        
        add(jPanel1, java.awt.BorderLayout.SOUTH);
        
    }//GEN-END:initComponents

    private void gc(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gc
        Runtime.getRuntime().gc();
    }//GEN-LAST:event_gc


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton gcButton;
    // End of variables declaration//GEN-END:variables

}
