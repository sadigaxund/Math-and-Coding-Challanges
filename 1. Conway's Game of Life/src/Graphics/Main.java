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
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Utils.JHardware;

public class Main extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 6187669053453265918L;

    public Main() {
	try {
	    initUI();
	    initListeners();
	} catch (AWTException e) {
	}
    }

    private void initUI() {
	setIconImage(new ImageIcon("./res/life.png").getImage());
	setTitle("Game of Life");
	setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	BOTTOM_PANEL = new JPanel();
	BOTTOM_PANEL.setName("bottomPanel");
	BOTTOM_PANEL.setBackground(new Color(190, 210, 230));
	BOTTOM_PANEL.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

	surf = new Surface(WINDOW_WIDTH, WINDOW_HEIGHT);
	surf.ZOOM_IN_WHEN_SCROLL_UP = ZOOM_IN_WHEN_SCROLL_UP;

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
	lblLatency.setName("latencyLbl");
	lblLatency.setHorizontalAlignment(SwingConstants.CENTER);
	lblLatency.setFont(new Font("Calibri Light", Font.BOLD, 15));

	slider = new JSlider(JSlider.HORIZONTAL, 0, 3000, 1000);
	slider.setOpaque(false);
	slider.setName("slider");
	slider.setMajorTickSpacing(1000);
	slider.setMinorTickSpacing(500);
	slider.setPaintTicks(true);

	lblMs = new JLabel("1000 ms");
	lblMs.setName("msLbl");
	lblMs.setHorizontalAlignment(SwingConstants.LEADING);
	lblMs.setFont(new Font("Calibri Light", Font.BOLD, 15));

	pauseButton = new BiStateButton("pause", "play", BUTTON_ICON_SIZE);
	pauseButton.setState(true);
	pauseButton.setName("pauseBtn");

	drawButton = new BiStateButton("select", "pencil", BUTTON_SIZE * 8 / 10);
	drawButton.setName("drawBtn");
	drawModeButton = new MultiStateButton(BUTTON_ICON_SIZE, "white", "darkestBlue", "inverted");
	drawModeButton.setName("modeBtn");
	eraseBtn = new MultiStateButton(BUTTON_ICON_SIZE * 11 / 10, "eraser");
	eraseBtn.setName("eraseBtn");
	

	SpringLayout sl_BOTTOM_PANEL = new SpringLayout();
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, lblLatency, X_MARGIN, SpringLayout.WEST, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, lblLatency, LATENCY_LABEL_WIDTH + X_MARGIN, SpringLayout.WEST,
		BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, lblLatency, Y_MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, lblLatency, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, slider, X_MARGIN, SpringLayout.EAST, lblLatency);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, slider, SLIDER_WIDTH + X_MARGIN, SpringLayout.WEST,
		lblLatency);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, slider, Y_MARGIN + 2, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, slider, 0, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, lblMs, X_MARGIN, SpringLayout.EAST, slider);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, lblMs, MS_LABEL_WIDTH + X_MARGIN, SpringLayout.EAST, slider);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, lblMs, Y_MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, lblMs, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, pauseButton, X_MARGIN * 5, SpringLayout.EAST, lblMs);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, pauseButton, BUTTON_SIZE + X_MARGIN * 5, SpringLayout.EAST, lblMs);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, pauseButton, Y_MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, pauseButton, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, drawButton, X_MARGIN, SpringLayout.EAST, pauseButton);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, drawButton, BUTTON_SIZE + X_MARGIN, SpringLayout.EAST,
		pauseButton);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, drawButton, Y_MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, drawButton, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, drawModeButton, X_MARGIN, SpringLayout.EAST, drawButton);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, drawModeButton, BUTTON_SIZE + X_MARGIN, SpringLayout.EAST,
		drawButton);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, drawModeButton, Y_MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, drawModeButton, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);

	sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, eraseBtn, X_MARGIN, SpringLayout.EAST, drawModeButton);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, eraseBtn, BUTTON_SIZE + X_MARGIN, SpringLayout.EAST,
		drawModeButton);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, eraseBtn, Y_MARGIN, SpringLayout.NORTH, BOTTOM_PANEL);
	sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, eraseBtn, -Y_MARGIN, SpringLayout.SOUTH, BOTTOM_PANEL);


	
	BOTTOM_PANEL.setLayout(sl_BOTTOM_PANEL);
	BOTTOM_PANEL.add(lblLatency);
	BOTTOM_PANEL.add(slider);
	BOTTOM_PANEL.add(lblMs);
	BOTTOM_PANEL.add(pauseButton);
	BOTTOM_PANEL.add(drawButton);
	BOTTOM_PANEL.add(drawModeButton);
	BOTTOM_PANEL.add(eraseBtn);

	getContentPane().add(BOTTOM_PANEL);
	getContentPane().add(surf);

    }

    private void initListeners() throws AWTException {
	int initX = (WINDOW_WIDTH) / 2 + getX();
	int initY = (WINDOW_HEIGHT) / 2 + getY();

	Robot r = new Robot();
	r.mouseMove(initX, initY);
	surf.setLastDragCoordinates(initX, initY);

	slider.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent arg0) {
		lblMs.setText(slider.getValue() + " ms");
		surf.setLATENCY(slider.getValue() + 5);
	    }
	});

	drawButton.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		surf.setDrawable(drawButton.getState());
		if (drawButton.getState()) {
		    surf.setPaused(drawButton.getState());
		    pauseButton.setState(drawButton.getState());
		}
	    }
	});
	pauseButton.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		surf.setPaused(pauseButton.getState());
		if (!pauseButton.getState()) {
		    surf.setDrawable(pauseButton.getState());
		    drawButton.setState(pauseButton.getState());
		}
	    }
	});
	drawModeButton.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		surf.setMode(drawModeButton.getIndex());
	    }
	});
	eraseBtn.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		surf.clear();
		surf.repaint();
	    }
	});

    }

    public static void main(String[] args) {
	Main ex = new Main();
	ex.setVisible(true);
	new Thread(ex.surf).start();
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
    public static final int SCREEN_WIDTH = (int) JHardware.getScreenSize().getWidth();
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
    private BiStateButton pauseButton;
    private BiStateButton drawButton;
    private MultiStateButton drawModeButton;
    private MultiStateButton eraseBtn;
}
