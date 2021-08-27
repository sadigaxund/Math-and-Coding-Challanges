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
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.SystemColor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Utils.JHardware;

/**
 * TODO: FUNCTIONALITY TO DRAW TODO: FUNCTIONALITY TO IMPORT or TYPE TODO: SOME
 * GUI FEATURES
 * 
 * @author sadig
 *
 */
public class MainFrame extends JFrame implements Runnable {
    /**
     * 
     */
    private static final long serialVersionUID = 6187669053453265918L;

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

	BOTTOM_PANEL = new JPanel();
	BOTTOM_PANEL.setBackground(SystemColor.controlHighlight);
	BOTTOM_PANEL.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	surf = new Surface(WINDOW_WIDTH, WINDOW_HEIGHT);

	SpringLayout springLayout = new SpringLayout();
	springLayout.putConstraint(SpringLayout.NORTH, surf, 0, SpringLayout.NORTH, getContentPane());
	springLayout.putConstraint(SpringLayout.SOUTH, surf, 0, SpringLayout.NORTH, BOTTOM_PANEL);
	springLayout.putConstraint(SpringLayout.WEST, surf, 0, SpringLayout.WEST, getContentPane());
	springLayout.putConstraint(SpringLayout.EAST, surf, 0, SpringLayout.EAST, getContentPane());

	springLayout.putConstraint(SpringLayout.NORTH, BOTTOM_PANEL, -BOTTOM_PANEL_HEIGHT, SpringLayout.SOUTH,
		getContentPane());
	springLayout.putConstraint(SpringLayout.WEST, BOTTOM_PANEL, 0, SpringLayout.WEST, getContentPane());
	springLayout.putConstraint(SpringLayout.EAST, BOTTOM_PANEL, 0, SpringLayout.EAST, getContentPane());
	springLayout.putConstraint(SpringLayout.SOUTH, BOTTOM_PANEL, 0, SpringLayout.SOUTH, getContentPane());
	getContentPane().setLayout(springLayout);

	lblLatency = new JLabel("DELAY");
	lblLatency.setHorizontalAlignment(SwingConstants.CENTER);
	lblLatency.setFont(new Font("Arial", Font.BOLD, 12));
	slider = new JSlider(JSlider.HORIZONTAL, 0, 3000, 1000);
	slider.setOpaque(false);
	slider.setMajorTickSpacing(1000);
	slider.setMinorTickSpacing(500);
	slider.setPaintTicks(true);
	lblMs = new JLabel("1000 ms");
	lblMs.setHorizontalAlignment(SwingConstants.LEADING);
	lblMs.setFont(new Font("Arial", Font.BOLD, 12));
	buttonPause = new BiStateButton("pause", "play", BUTTON_ICON_SIZE);
	buttonPause.setState(true);
	drawButton = new BiStateButton("select", "pencil", toInt(BUTTON_SIZE * 0.8));
	drawModeButton = new MultiStateButton(BUTTON_ICON_SIZE, "white", "darkestBlue", "inverted");

	SpringLayout sl_BOTTOM_PANEL = new SpringLayout();
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, lblLatency, X_MARGIN, SpringLayout.WEST, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, lblLatency, LATENCY_LABEL_WIDTH + X_MARGIN, SpringLayout.WEST,
		BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, lblLatency, Y_MARGIN - 3, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, lblLatency, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, slider, X_MARGIN, SpringLayout.EAST, lblLatency);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, slider, SLIDER_WIDTH + X_MARGIN, SpringLayout.WEST,
		lblLatency);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, slider, Y_MARGIN + 2, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, slider, 0, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, lblMs, X_MARGIN, SpringLayout.EAST, slider);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, lblMs, MS_LABEL_WIDTH + X_MARGIN, SpringLayout.EAST, slider);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, lblMs, Y_MARGIN - 3, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, lblMs, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, buttonPause, X_MARGIN, SpringLayout.EAST, lblMs);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, buttonPause, BUTTON_SIZE + X_MARGIN, SpringLayout.EAST, lblMs);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, buttonPause, Y_MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, buttonPause, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, drawButton, X_MARGIN, SpringLayout.EAST, buttonPause);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, drawButton, BUTTON_SIZE + X_MARGIN, SpringLayout.EAST,
		buttonPause);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, drawButton, Y_MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, drawButton, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, drawModeButton, X_MARGIN, SpringLayout.EAST, drawButton);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, drawModeButton, BUTTON_SIZE + X_MARGIN, SpringLayout.EAST,
		drawButton);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, drawModeButton, Y_MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, drawModeButton, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	BOTTOM_PANEL.setLayout(sl_BOTTOM_PANEL);
	BOTTOM_PANEL.add(lblLatency);
	BOTTOM_PANEL.add(slider);
	BOTTOM_PANEL.add(lblMs);
	BOTTOM_PANEL.add(buttonPause);
	BOTTOM_PANEL.add(drawButton);
	BOTTOM_PANEL.add(drawModeButton);

	getContentPane().add(BOTTOM_PANEL);
	getContentPane().add(surf);

    }

    private void initMouseAction() throws AWTException {
	int initX = (WINDOW_WIDTH) / 2 + getX();
	int initY = (WINDOW_HEIGHT) / 2 + getY();

	Robot r = new Robot();
	r.mouseMove(initX, initY);

	surf.addMouseMotionListener(new MouseMotionAdapter() {

	    Point prevCoords = Surface.convert2Point(initX, initY);

	    @Override
	    public void mouseMoved(MouseEvent e) {
		prevCoords = Surface.convert2Point(e.getX(), e.getY());
	    }

	    @Override
	    public void mouseDragged(MouseEvent e) {
		/* DRAWING... */
		if (drawButton.getState()) {
		    buttonPause.setState(true);
		    surf.clickCell(Surface.convert2Point(e.getX(), e.getY()), drawModeButton.getIndex());
		    surf.repaint();
		    return;
		}

		/* DRAGGING... */
		surf.addOffset(e.getX() - prevCoords.x, e.getY() - prevCoords.y);
		surf.repaint();
		prevCoords = Surface.convert2Point(e.getX(), e.getY());

	    }
	});
	surf.addComponentListener(new ComponentAdapter() {
	    @Override
	    public void componentResized(ComponentEvent e) {
		surf.repaint();
	    }
	});
	surf.addMouseWheelListener(new MouseWheelListener() {
	    public void mouseWheelMoved(MouseWheelEvent e) {

		int notches = e.getWheelRotation();

		int zoom = Math.abs(notches) / notches * ((ZOOM_IN_WHEN_SCROLL_UP) ? -1 : 1);
		surf.setGridSize(surf.getGridSize() + zoom);
		surf.repaint();
	    }
	});
	surf.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseReleased(MouseEvent e) {
		surf.resetForbidList();
	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
		// if (drawButton.getState())
		// surf.clickCell(e.getX(), e.getY());
	    }
	});
	slider.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent arg0) {
		lblMs.setText(slider.getValue() + " ms");
		LATENCY = slider.getValue() + 5;
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

    public static int toInt(double a) {
	return ((int) a);
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
	    if (buttonPause.getState())
		continue;

	    surf.step();
	    surf.repaint();
	}
    }

    void println(String str) {
	System.out.println(str);
    }

    /**************************************************************************************
     * VARIABLE DECLARATION
     **************************************************************************************/
    /****************************
     * STATIC SETTINGS VARIABLES
     ****************************/

    /**
     * The ratio of a screen that the program window will be.<br>
     */
    private static double W_RATIO = 0.7;
    /**
     * The ratio of a screen that the program window will be.<br>
     */
    private static double H_RATIO = 0.8;
    public static final boolean ZOOM_IN_WHEN_SCROLL_UP = true;
    public static final int BOTTOM_PANEL_HEIGHT = 50;
    public static final int X_MARGIN = 5;
    public static final int Y_MARGIN = 5;
    public static final int LATENCY_LABEL_WIDTH = 50;
    public static final int MS_LABEL_WIDTH = 60;
    public static final int SLIDER_WIDTH = 250;

    /****************************
     * FINAL VARIABLES
     ****************************/

    /**
     * The Width of the screen
     */
    public static final int SCREEN_WIDTH = (int) toInt(JHardware.getScreenSize().getWidth());
    /**
     * The Height of the screen
     */
    public static final int SCREEN_HEIGHT = (int) JHardware.getScreenSize().getHeight();

    /**
     * The Width of the program window
     */
    public static final int WINDOW_WIDTH = (int) (SCREEN_WIDTH * W_RATIO) - 10;
    /**
     * The Height of the program window
     */
    public static final int WINDOW_HEIGHT = (int) (SCREEN_HEIGHT * H_RATIO) + 2;

    /**
     * The delay for the game mechanism
     */
    public static int LATENCY = 1000;

    public static final int BUTTON_SIZE = BOTTOM_PANEL_HEIGHT - Y_MARGIN * 2;

    public static final int BUTTON_ICON_SIZE = BUTTON_SIZE - X_MARGIN * 3;

    /****************************
     * AWT & SWING COMPONENTS
     ****************************/
    private Surface surf;
    private JLabel lblLatency;
    private JSlider slider;
    private JPanel BOTTOM_PANEL;
    private JLabel lblMs;
    private BiStateButton buttonPause;
    private BiStateButton drawButton;
    private MultiStateButton drawModeButton;
}
