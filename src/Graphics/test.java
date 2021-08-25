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
import java.awt.Component;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Utils.JHardware;
import Utils.JImages;
import util.HashPoint;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class test extends JFrame implements Runnable {
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

    public test() {

	initUI();

    }

    private void initUI() {
	setTitle("Game of Life");
	setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel BOTTOM_PANEL = new JPanel();
	BOTTOM_PANEL.setBackground(SystemColor.controlHighlight);
	BOTTOM_PANEL.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	surf = new Surface(WINDOW_WIDTH, WINDOW_HEIGHT);

	SpringLayout springLayout = new SpringLayout();
	springLayout.putConstraint(SpringLayout.SOUTH, surf, 0, SpringLayout.NORTH, BOTTOM_PANEL);
	springLayout.putConstraint(SpringLayout.NORTH, BOTTOM_PANEL, 537, SpringLayout.NORTH, getContentPane());
	springLayout.putConstraint(SpringLayout.WEST, BOTTOM_PANEL, 0, SpringLayout.WEST, getContentPane());
	springLayout.putConstraint(SpringLayout.EAST, BOTTOM_PANEL, 0, SpringLayout.EAST, getContentPane());
	springLayout.putConstraint(SpringLayout.SOUTH, BOTTOM_PANEL, 577, SpringLayout.NORTH, getContentPane());

	springLayout.putConstraint(SpringLayout.NORTH, surf, 0, SpringLayout.NORTH, getContentPane());
	springLayout.putConstraint(SpringLayout.WEST, surf, 0, SpringLayout.WEST, getContentPane());
	springLayout.putConstraint(SpringLayout.EAST, surf, 0, SpringLayout.EAST, getContentPane());
	getContentPane().setLayout(springLayout);

	int MARGIN = 5;
	int latencyLblW = 50;
	int msLblW = 60;
	int sliderW = 250;
	int pauseSize = 25;

	JLabel lblLatency = new JLabel("DELAY");
	lblLatency.setHorizontalAlignment(SwingConstants.CENTER);
	lblLatency.setFont(new Font("Arial", Font.BOLD, 12));
	JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 3000, 1000);
	slider.setOpaque(false);
	slider.setMajorTickSpacing(1000);
	slider.setMinorTickSpacing(500);
	slider.setPaintTicks(true);
	JLabel lblMs = new JLabel("1000 ms");
	lblMs.setHorizontalAlignment(SwingConstants.LEADING);
	lblMs.setFont(new Font("Arial", Font.BOLD, 12));
	MyButton buttonPause = new MyButton("play", "pause", pauseSize);
	MyButton buttonDraw = new MyButton("pencil", "xpencil", pauseSize + 8);
	SpringLayout sl_BOTTOM_PANEL = new SpringLayout();

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, lblLatency, latencyLblW, SpringLayout.WEST, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, lblLatency, MARGIN * 2, SpringLayout.WEST, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, lblLatency, MARGIN - 2, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, lblLatency, -MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, slider, -913 + sliderW, SpringLayout.EAST, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, slider, MARGIN, SpringLayout.EAST, lblLatency);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, slider, MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, slider, 0, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, lblMs, -663 + msLblW, SpringLayout.EAST, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, lblMs, MARGIN, SpringLayout.EAST, slider);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, lblMs, MARGIN - 2, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, lblMs, -MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, buttonPause, 0, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, buttonPause, 0, SpringLayout.SOUTH, slider);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, buttonDraw, 0, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, buttonDraw, 0, SpringLayout.SOUTH, slider);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, buttonPause, 6, SpringLayout.EAST, lblMs);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, buttonPause, -556, SpringLayout.EAST, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, buttonDraw, 6, SpringLayout.EAST, buttonPause);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, buttonDraw, -511, SpringLayout.EAST, BOTTOM_PANEL);

	BOTTOM_PANEL.setLayout(sl_BOTTOM_PANEL);
	BOTTOM_PANEL.add(lblLatency);
	BOTTOM_PANEL.add(slider);
	BOTTOM_PANEL.add(lblMs);
	BOTTOM_PANEL.add(buttonPause);
	BOTTOM_PANEL.add(buttonDraw);

	getContentPane().add(BOTTOM_PANEL);
	getContentPane().add(surf);

    }

    public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		test ex = new test();
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
