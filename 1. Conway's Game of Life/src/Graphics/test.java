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

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import Utils.JHardware;
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

    public static final int BOTTOM_PANEL_HEIGHT = 50;

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

	int X_MARGIN = 5;
	int Y_MARGIN = 5;
	int latencyLblW = 50;
	int msLblW = 60;
	int sliderW = 250;
	int buttonSize = 40;
	int buttonIconSize = 25;

	JLabel lblLatency = new JLabel("DELAY");
	lblLatency.setHorizontalAlignment(SwingConstants.CENTER);
	lblLatency.setFont(new Font("Calibri Light", Font.BOLD, 15));
	JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 3000, 1000);
	slider.setOpaque(false);
	slider.setMajorTickSpacing(1000);
	slider.setMinorTickSpacing(500);
	slider.setPaintTicks(true);
	JLabel	lblMs = new JLabel("1000 ms");
	lblMs.setHorizontalAlignment(SwingConstants.LEADING);
	lblMs.setFont(new Font("Calibri Light", Font.BOLD, 14));
	BiStateButton buttonPause = new BiStateButton("pause", "play", buttonIconSize);
	buttonPause.setState(true);
	BiStateButton drawButton = new BiStateButton("select", "pencil", buttonIconSize + 8);
	MultiStateButton drawModeButton = new MultiStateButton(buttonIconSize, "white", "darkestBlue", "inverted");

	SpringLayout sl_BOTTOM_PANEL = new SpringLayout();

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, lblLatency, X_MARGIN, SpringLayout.WEST, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, lblLatency, latencyLblW + X_MARGIN, SpringLayout.WEST,
		BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, lblLatency, Y_MARGIN - 3, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, lblLatency, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, slider, X_MARGIN, SpringLayout.EAST, lblLatency);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, slider, sliderW + X_MARGIN, SpringLayout.WEST, lblLatency);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, slider, Y_MARGIN + 2, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, slider, 0, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, lblMs, X_MARGIN, SpringLayout.EAST, slider);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, lblMs, msLblW + X_MARGIN, SpringLayout.EAST, slider);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, lblMs, Y_MARGIN - 3, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, lblMs, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, buttonPause, X_MARGIN, SpringLayout.EAST, lblMs);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, buttonPause, buttonSize + X_MARGIN, SpringLayout.EAST, lblMs);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, buttonPause, Y_MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, buttonPause, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, drawButton, X_MARGIN, SpringLayout.EAST, buttonPause);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, drawButton, buttonSize + X_MARGIN, SpringLayout.EAST,
		buttonPause);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, drawButton, Y_MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, drawButton, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, drawModeButton, X_MARGIN, SpringLayout.EAST, drawButton);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, drawModeButton, buttonSize + X_MARGIN, SpringLayout.EAST,
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
