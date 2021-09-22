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
package Graphics;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


import javax.swing.JPanel;

import pzemtsov.Worker;
import util.HashPoint;

public abstract class SurfaceAdapter extends JPanel
	implements ComponentListener, MouseListener, MouseWheelListener, MouseMotionListener, Runnable {

    /**
     * 
     */
    private static final long serialVersionUID = 133870799168380262L;

    public static final int ANIMATE_CELL_MODE = 0;
    public static final int ELIMINATE_CELL_MODE = 1;
    public static final int INVERT_CELL_MODE = 2;
   
    public static final String[] BLANK = new String[] {};
    public static final String[] ACORN = new String[] { "OO  OOO", ":::O:::", ":O" };
    public static final String[] GUN = new String[] { "                        O             ",
	    "                      O O             ", "            OO      OO            OO",
	    "           O   O    OO            OO", "OO        O     O   OO", "OO        O   O OO    O O",
	    "          O     O       O", "           O   O", "            OO" };

    public static final String[] DEMONOID = new String[] { "....OO......OO....", "...O.O......O.O...",
	    "...O..........O...", "OO.O..........O.OO", "OO.O.O..OO..O.O.OO", "...O.O.O..O.O.O...",
	    "...O.O.O..O.O.O...", "OO.O.O..OO..O.O.OO", "OO.O..........O.OO", "...O..........O...",
	    "...O.O......O.O...", "....OO......OO...." };
    /**
     * The delay for the game mechanism
     */
    protected int LATENCY = 1000;
    private Point LastDragCoordinates;
    private int mode;
    private boolean drawable;
    protected boolean paused = true;

    public SurfaceAdapter() {
	// ComponentListener, MouseListener, MouseWheelListener, MouseMotionListener
	addComponentListener(this);
	addMouseListener(this);
	addMouseWheelListener(this);
	addMouseMotionListener(this);

    }

    /*******************************************
     ************ ABSTRACT METHODS ************
     *******************************************/

    protected abstract void doDrawing(Graphics g);

    protected abstract int getZoomCoefficient(int notches);

    public abstract void setOffset(int x, int y);

    public abstract void addOffset(int x, int y);

    public abstract void draw(int x, int y, int mode);

    public abstract void draw(Point pt, int mode);

    public abstract void setGridSize(int size);

    public abstract int getGridSize();

    public abstract void resetForbidList();

    /*******************************************
     ************ AUXILARY ************
     *******************************************/

    public static Rectangle createRectangle(Point loc, Point offset, int size) {
	return new Rectangle(offset.x + loc.x * size, offset.y + loc.y * size, size, size);
    }

    public static void put(Worker w, String[] p, Point offset) {
	for (int y = 0; y < p.length; y++) {
	    for (int x = 0; x < p[y].length(); x++) {
		if (p[y].charAt(x) == 'O') {
		    w.put(x - offset.x, y - offset.y);
		}
	    }
	}
    }

    public static void put(Worker w, String[] p, int x, int y) {
	put(w, p, new HashPoint(x, y));
    }
    
    
    public static HashPoint getPatternLength(String[] strs) {
	HashPoint retval = new HashPoint(0, strs.length);

	for (String str : strs) {
	    int ind = 0;
	    char[] inCh = str.toCharArray();
	    for (int i = 0; i < inCh.length; i++) {
		if (inCh[i] == 'O')
		    ind = i;
	    }
	    if (ind > retval.x)
		retval.x = ind;
	}
	retval.setLocation(retval.x + 1, retval.y + 1); // return length instead of index
	return retval;
    }

    /*******************************************
     ************ OVERRIDES ************
     *******************************************/

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	doDrawing(g);
    }

    @Override
    public void componentResized(ComponentEvent e) {
	repaint();
    }

    /*******************************************
     ************ LISTENERS ************
     *******************************************/

    @Override
    public void mouseReleased(MouseEvent e) {
	resetForbidList();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
	setGridSize(getGridSize() + getZoomCoefficient(e.getWheelRotation()));
	repaint();

    }

    @Override
    public void componentHidden(ComponentEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void componentMoved(ComponentEvent arg0) {

    }

    @Override
    public void componentShown(ComponentEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
	/* DRAWING... */
	if (drawable) {
	    draw(e.getX(), e.getY(), mode);
	    repaint();
	    return;
	}

    }

    @Override
    public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
	/* DRAWING... */
	if (drawable) {
	    // paused = true;
	    draw(e.getX(), e.getY(), mode);
	    repaint();
	    return;
	}

	/* DRAGGING... */
	addOffset(e.getX() - LastDragCoordinates.x, e.getY() - LastDragCoordinates.y);
	setLastDragCoordinates(e.getX(), e.getY());
	repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
	setLastDragCoordinates(e.getX(), e.getY());
    }

    /*******************************************
     ************ GETTERS & SETTERS ************
     *******************************************/

    /**
     * @return the lastDragCoordinates
     */
    public Point getLastDragCoordinates() {
	return LastDragCoordinates;
    }

    /**
     * @param lastDragCoordinates
     *                                the lastDragCoordinates to set
     */
    public void setLastDragCoordinates(Point lastDragCoordinates) {
	LastDragCoordinates = lastDragCoordinates;
    }

    public void setLastDragCoordinates(int x, int y) {
	setLastDragCoordinates(new Point(x, y));
    }

    /**
     * @return the mode
     */
    public int getMode() {
	return mode;
    }

    /**
     * @param mode
     *                 the mode to set
     */
    public void setMode(int mode) {
	this.mode = mode;
    }

    /**
     * @return the canDraw
     */
    public boolean isDrawable() {
	return drawable;
    }

    /**
     * @param canDraw
     *                    the canDraw to set
     */
    public void setDrawable(boolean canDraw) {
	this.drawable = canDraw;
    }

    /**
     * @return the paused
     */
    public boolean isPaused() {
	return paused;
    }

    /**
     * @param paused
     *                   the paused to set
     */
    public void setPaused(boolean paused) {
	this.paused = paused;
    }

    /**
     * @return the lATENCY
     */
    public int getLATENCY() {
	return LATENCY;
    }

    /**
     * @param lATENCY
     *                    the lATENCY to set
     */
    public void setLATENCY(int lATENCY) {
	LATENCY = lATENCY;
    }

}
