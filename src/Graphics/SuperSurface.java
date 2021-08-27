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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashSet;

import javax.swing.JPanel;

import util.HashPoint;

public abstract class SuperSurface extends JPanel
	implements ComponentListener, MouseListener, MouseWheelListener, MouseMotionListener {

    private Point LastDragCoordinates;
    protected HashSet<HashPoint> forbidList = new HashSet<>();

    /**
     * 
     */
    private static final long serialVersionUID = 133870799168380262L;

    public SuperSurface() {
	addComponentListener(this);
	addMouseListener(this);
	addMouseWheelListener(this);

    }

    public abstract void setGridSize(int size);

    public abstract int getGridSize();

    protected abstract void doDrawing(Graphics g);

    public static HashPoint convert2Point(int x, int y) {
	return new HashPoint(x, y);
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	doDrawing(g);
    }

    @Override
    public void componentResized(ComponentEvent e) {
	repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	resetForbidList();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
	int notches = e.getWheelRotation();
	int zoom = Math.abs(notches) / notches;
	setGridSize(getGridSize() + zoom);
	repaint();

	// int zoom = Math.abs(notches) / notches * ((ZOOM_IN_WHEN_SCROLL_UP) ? -1 : 1);
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
	// TODO Auto-generated method stub

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
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub

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
     * @return the recentlyDrawnPoints
     */
    public HashSet<HashPoint> getForbidList() {
	return forbidList;
    }

    /**
     * @param recentlyDrawnPoints
     *                                the recentlyDrawnPoints to set
     */
    public void setForbidList(HashSet<HashPoint> recentlyDrawnPoints) {
	this.forbidList = recentlyDrawnPoints;
    }

    public void resetForbidList() {
	this.forbidList = new HashSet<>();
    }

}
