package Graphics;

/***************************************************************************
 *   MIT License
 *   
 *   Copyright (c) 2021 Sadig Akhund
 *   
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *   
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *   
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 *
 * 
 **************************************************************************/

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Utils.JHardware;
import Utils.JImages;
import util.HashPoint;

public class MainFrame extends JFrame implements Runnable {
    /**
     * 
     */
    private static final long serialVersionUID = 6187669053453265918L;

    /**
     * The Width of the screen
     */
    public static final int SCREEN_WIDTH = (int) JHardware.getScreenSize().getWidth();
    /**
     * The Height of the screen
     */
    public static final int SCREEN_HEIGHT = (int) JHardware.getScreenSize().getHeight();
    /**
     * The ratio of a screen that the program window will be.<br>
     */
    private static double W_RATIO = 0.7;
    /**
     * The ratio of a screen that the program window will be.<br>
     */
    private static double H_RATIO = 0.8;
    /**
     * The Width of the program window
     */
    public static final int WINDOW_WIDTH = (int) (SCREEN_WIDTH * W_RATIO) - 10;
    /**
     * The Height of the program window
     */
    public static final int WINDOW_HEIGHT = (int) (SCREEN_HEIGHT * H_RATIO) + 2;
    /**
     * The margin by how much that all the components must be separated from each
     * other.
     */
    public static final int COMPONENT_MARGIN = 10;
    /**
     * The delay for the game mechanism
     */
    public static int LATENCY = 1000;

    public static final int BOTTOM_PANEL_HEIGHT = 40;

    public boolean PAUSE = true;

    private Surface surf;

    public MainFrame() {
	try {
	    initUI();
	    initMouseAction();
	} catch (AWTException e) {
	}
    }

    private void initUI() {
	setTitle("Game of Life");
	setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel BOTTOM_PANEL = new JPanel();

	FlowLayout flowLayout = (FlowLayout) BOTTOM_PANEL.getLayout();
	flowLayout.setVgap(0);
	flowLayout.setHgap(0);

	BOTTOM_PANEL.setBorder(UIManager.getBorder("RadioButton.border"));

	surf = new Surface(WINDOW_WIDTH, WINDOW_HEIGHT);
	FlowLayout flowLayout_1 = (FlowLayout) surf.getLayout();
	flowLayout_1.setHgap(0);
	flowLayout_1.setVgap(0);
	GroupLayout groupLayout = new GroupLayout(getContentPane());
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addComponent(BOTTOM_PANEL, GroupLayout.DEFAULT_SIZE, WINDOW_WIDTH, Short.MAX_VALUE)
		.addComponent(surf, GroupLayout.DEFAULT_SIZE, WINDOW_WIDTH, Short.MAX_VALUE));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
		.addGroup(groupLayout.createSequentialGroup()
			.addComponent(surf, GroupLayout.DEFAULT_SIZE, WINDOW_HEIGHT, Short.MAX_VALUE)
			.addComponent(BOTTOM_PANEL, GroupLayout.PREFERRED_SIZE, BOTTOM_PANEL_HEIGHT,
				GroupLayout.PREFERRED_SIZE)));
	getContentPane().setLayout(groupLayout);

	surf.addMouseWheelListener(new MouseWheelListener() {
	    public void mouseWheelMoved(MouseWheelEvent e) {

		int notches = e.getWheelRotation();
		if (notches < 0) {// UP
		    surf.setGridSize(surf.getGridSize() + 1);
		} else {
		    surf.setGridSize(surf.getGridSize() - 1);
		}
		surf.repaint();
	    }
	});

	BOTTOM_PANEL.setLayout(null);
	int margin = 10;
	int component_height = BOTTOM_PANEL_HEIGHT - margin * 2;
	JLabel lblLatency = new JLabel("Latency:");
	lblLatency.setBounds(margin, margin, 50, component_height - 2);
	BOTTOM_PANEL.add(lblLatency);

	JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 3000, 1000);
	slider.setBounds(lblLatency.getX() + lblLatency.getWidth() + margin, margin - 5, 200, component_height + 10);

	// Turn on labels at major tick marks.
	// FIXME: make pretty
	slider.setMajorTickSpacing(1000);
	slider.setMinorTickSpacing(500);
	slider.setPaintTicks(true);
	BOTTOM_PANEL.add(slider);

	JLabel lblMs = new JLabel("1000 ms");
	lblMs.setBounds(slider.getX() + slider.getWidth() + margin, margin, 50, component_height - 2);
	BOTTOM_PANEL.add(lblMs);

	/********************
	 * FIXME: overlapping problem<br>
	 * PLAY BUTTON
	 ********************/
	Rectangle lbl_bounds = new Rectangle(lblMs.getX() + lblMs.getWidth() + margin * 2 + 1, margin - 2, 25, 24);
	Rectangle panel_bounds = new Rectangle(lblMs.getX() + lblMs.getWidth() + margin * 2 - 2, margin - 5, 31,
		25 + 5);
	lbl_bounds.setLocation((WINDOW_WIDTH - lbl_bounds.width) / 2, lbl_bounds.y);
	panel_bounds.setLocation((WINDOW_WIDTH - panel_bounds.width) / 2, panel_bounds.y);

	JLabel label = new JLabel("") {
	    @Override
	    public boolean isOptimizedDrawingEnabled() {
		return false;
	    }
	};
	label.setBounds(lbl_bounds);
	Image smileFaceIcon = JImages.scaleImage(new ImageIcon("./res/play.png").getImage(), label.getWidth(),
		label.getHeight());
	label.setIcon(new ImageIcon(smileFaceIcon));

	label.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		PAUSE = !PAUSE;

		Image smileFaceIcon = JImages.scaleImage(
			new ImageIcon("./res/" + (PAUSE ? "play" : "pause") + ".png").getImage(), label.getWidth(),
			label.getHeight());
		label.setIcon(new ImageIcon(smileFaceIcon));
	    }
	});
	BOTTOM_PANEL.add(label);

	JPanel panel_2 = new JPanel() {
	    @Override
	    public boolean isOptimizedDrawingEnabled() {
		return false;
	    }
	};
	panel_2.setBounds(panel_bounds);
	panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	label.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent e) {
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Image smileFaceIcon = JImages.scaleImage(convertIconToImage(label.getIcon()),
			((int) (label.getWidth() * 0.9)), ((int) (label.getHeight() * 0.9)));
		label.setIcon(new ImageIcon(smileFaceIcon));

	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		Image smileFaceIcon = JImages.scaleImage(convertIconToImage(label.getIcon()), label.getWidth(),
			label.getHeight());
		label.setIcon(new ImageIcon(smileFaceIcon));
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		// FIXME: lowers the quality of icon, create global image twice, instead of
		// creating each time.
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		Image smileFaceIcon = JImages.scaleImage(convertIconToImage(label.getIcon()), label.getWidth(),
			label.getHeight());
		label.setIcon(new ImageIcon(smileFaceIcon));
	    }
	});

	BOTTOM_PANEL.add(panel_2);
	/********************
	 * PLAY BUTTON
	 ********************/
	getContentPane().setLayout(groupLayout);

	slider.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent arg0) {
		lblMs.setText(slider.getValue() + " ms");
		LATENCY = slider.getValue() + 5;
	    }
	});

    }

    private void initMouseAction() throws AWTException {
	int initX = (WINDOW_WIDTH) / 2 + getX();
	int initY = (WINDOW_HEIGHT) / 2 + getY();

	Robot r = new Robot();
	r.mouseMove(initX, initY);
	boolean hold = true;

	surf.addMouseMotionListener(new MouseMotionAdapter() {

	    HashPoint prevCoords = new HashPoint(initX, initY);

	    @Override
	    public void mouseMoved(MouseEvent e) {
		prevCoords = new HashPoint(e.getX(), e.getY());
	    }

	    @Override
	    public void mouseDragged(MouseEvent e) {
		HashPoint currCoords = new HashPoint(e.getX(), e.getY());

		if (hold) {
		    surf.addOffset(currCoords.x - prevCoords.x, currCoords.y - prevCoords.y);
		    surf.repaint();
		}
		prevCoords = currCoords;

	    }
	});
	surf.addComponentListener(new ComponentAdapter() {
	    @Override
	    public void componentResized(ComponentEvent e) {
		surf.repaint();
	    }
	});

    }

    public Image convertIconToImage(Icon icon) {
	if (icon instanceof ImageIcon) {
	    return ((ImageIcon) icon).getImage();
	} else {
	    int width = icon.getIconWidth();
	    int height = icon.getIconHeight();
	    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = (Graphics2D) image.getGraphics();
	    icon.paintIcon(null, g2, 0, 0);
	    return image;
	}
    }

    public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		MainFrame ex = new MainFrame();
		ex.setVisible(true);
		new Thread(ex).start();
	    }
	});
    }

    @Override
    public void run() {
	while (true) {
	    try {
		Thread.sleep(LATENCY);
	    } catch (InterruptedException e) {
	    }
	    if (PAUSE)
		continue;

	    surf.step();
	}
    }

    void println(String str) {
	System.out.println(str);
    }
}
