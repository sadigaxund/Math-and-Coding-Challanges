
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
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import Utils.JHardware;

public class MainFrame extends JFrame {
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
    public static final int LATENCY = 100;

    private Surface surf;

    public MainFrame() {
	try {
	    initUI();
	    initMouseAction();
	} catch (AWTException e) {
	}
    }

    private void initUI() {
	surf = new Surface();
	add(surf);
	setTitle("Game of Life");
	setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initMouseAction() throws AWTException {
	int initX = (WINDOW_WIDTH) / 2 + getX();
	int initY = (WINDOW_HEIGHT) / 2 + getY();

	Robot r = new Robot();
	r.mouseMove(initX, initY);
	boolean hold = true;

	surf.addMouseMotionListener(new MouseMotionAdapter() {
	    int prevX = initX;
	    int prevY = initY;

	    @Override
	    public void mouseMoved(MouseEvent e) {
		int currX = e.getX();
		int currY = e.getY();
		prevX = currX;
		prevY = currY;

	    }

	    @Override
	    public void mouseDragged(MouseEvent e) {
		int currX = e.getX();
		int currY = e.getY();

		int diffX = (currX - prevX) ;
		int diffY = (currY - prevY) ;

		System.out.println(diffX + ", " + diffY);
		if (hold) {
		    surf.setLocation(surf.getX() + diffX, surf.getY() + diffY);
		}

	    }
	});
    }

    public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		MainFrame ex = new MainFrame();
		ex.setVisible(true);
	    }
	});
    }
}
