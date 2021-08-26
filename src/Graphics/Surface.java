package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import pzemtsov.Hash;
import pzemtsov.Hasher;
import pzemtsov.Life;
import pzemtsov.Worker;
import util.HashPoint;

/***************************************************************************
 * MIT License
 * 
 * Copyright (c) 2021 Sadig Akhund
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * 
 **************************************************************************/

public class Surface extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -8120139481082133405L;

    private int grid_size = 16;
    private Color darkestBlue = new Color(21, 34, 56);

    private Point windowSize;
    private Point pixel_offset;

    private static final String[] ACORN = new String[] { "OO  OOO", ":::O:::", ":O" };
    private static final String[] GUN = new String[] { "                        O             ",
	    "                      O O             ", "            OO      OO            OO",
	    "           O   O    OO            OO", "OO        O     O   OO", "OO        O   O OO    O O",
	    "          O     O       O", "           O   O", "            OO" };

    private static final String[] DEMONOID = new String[] { "....OO......OO....", "...O.O......O.O...",
	    "...O..........O...", "OO.O..........O.OO", "OO.O.O..OO..O.O.OO", "...O.O.O..O.O.O...",
	    "...O.O.O..O.O.O...", "OO.O.O..OO..O.O.OO", "OO.O..........O.OO", "...O..........O...",
	    "...O.O......O.O...", "....OO......OO...." };

    private HashSet<HashPoint> hashedPoints;
    private Hash map;

    // public static void main(String[] str) {
    // System.out.println(getPatternLength(DEMONOID));
    // }

    public Surface(int window_width, int window_heigth) {
	super();
	windowSize = new Point(window_width, window_heigth);
	setPattern(GUN);

    }

    private void doDrawing(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	int w = getWidth();
	int h = getHeight();

	// Draw Background
	g2d.setColor(darkestBlue);
	g2d.fillRect(0, 0, w, h);

	/* Render only visible dead cells */
	g2d.setColor(new Color(0, 80, 110));
	int x_off = pixel_offset.x % grid_size;
	int y_off = pixel_offset.y % grid_size;

	// +- 1 is for creating larger gridmap than the visible window frame
	for (int i = -1; i < windowSize.x / grid_size + 1; i++)
	    for (int j = -1; j < windowSize.y / grid_size + 1; j++) {
		Rectangle rect = createRectangle(new Point(i, j), new Point(x_off, y_off), grid_size);
		g2d.draw(rect);
	    }

	/* Render Alive Cells all over the coordinate system */
	g2d.setColor(new Color(255, 255, 255));
	for (HashPoint pt : hashedPoints) {
	    // RENDER OPTIMIZATION
	    Rectangle rect = createRectangle(new Point(pt.x, pt.y), pixel_offset, grid_size);
	    /*
	     * Note: by adding some variable instead of 0, you can adjust the margin from
	     * where the cells should be rendered. Forex.: if negative value a cell will be
	     * deleted before it can disappear
	     */
	    if ((rect.x + rect.width < -0) || // left - horizontal check <br>
		    (rect.x > getWidth() + 0) || // right - horizontal check <br>
		    (rect.y + rect.height < -0) || // up - vertical check <br>
		    (rect.y > getHeight() + 0)) // down - vertical check <br>
		continue;
	    g2d.fill(rect);
	}
    }

    private Rectangle createRectangle(Point loc, Point offset, int size) {
	return new Rectangle(offset.x + loc.x * size, offset.y + loc.y * size, size, size);
    }

    public static void put(Worker w, String[] p) {
	put(w, p, new Point(0, 0));
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

    public HashPoint convertCoords2Index(int x, int y) {
	x -= pixel_offset.x; // remove x offset remainder
	y -= pixel_offset.y; // remove y offset remainder
	x /= grid_size;
	y /= grid_size;
	return new HashPoint(x, y);
    }

    public void clickCell(int x, int y) {
	HashPoint pt = convertCoords2Index(x, y);

	if (!hashedPoints.remove(pt)) {
	    hashedPoints.add(pt);
	    map.put(pt.x, pt.y);
	}
	repaint();

    }

    public void clickCell(int x, int y, HashSet<HashPoint> forbiddenList) {
	HashPoint pt2Add = convertCoords2Index(x, y);
	for (HashPoint pt : forbiddenList)
	    if (pt.equals(pt2Add))
		return;

	clickCell(x, y);
	forbiddenList.add(pt2Add);
    }

    private static Point getPatternLength(String[] strs) {
	Point retval = new Point(0, strs.length);

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

    private void setPattern(String[] pattern) {
	Point patternSize = getPatternLength(pattern);
	int w = windowSize.x / grid_size + 2;
	int h = windowSize.y / grid_size + 2;
	pixel_offset = new Point(-(w - patternSize.x) / 2, -(h - patternSize.y) / 2);
	map = new Hash(new Hasher());
	map.reset();
	put(map, pattern, pixel_offset);
	hashedPoints = new HashSet<>(map.get());
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	doDrawing(g);
    }

    /**********************************************************
     * GETTERS & SETTERS
     ***********************************************************/
    /**
     * @return the hashedPoints
     */
    public HashSet<HashPoint> getHashedPoints() {
	return hashedPoints;
    }

    /**
     * @param hashedPoints
     *                         the hashedPoints to set
     */
    public void setHashedPoints(HashSet<HashPoint> hashedPoints) {
	this.hashedPoints = hashedPoints;
    }

    /**
     * @return the offset
     */
    public Point getOffset() {
	return pixel_offset;
    }

    /**
     * @param pixel_offset
     *                         the offset to set
     */
    public void setOffset(int x, int y) {
	pixel_offset.x = x;
	pixel_offset.y = y;
    }

    public void addOffset(int x, int y) {
	setOffset(pixel_offset.x + x, pixel_offset.y + y);
    }

    public void step() {
	map.step();
	hashedPoints = new HashSet<>(map.get());
	repaint();
    }

    /**
     * @return the grid_size
     */
    public int getGridSize() {
	return grid_size;
    }

    /**
     * @param grid_size
     *                      the grid_size to set
     */
    public void setGridSize(int size) {

	Point indexOffset = new Point(pixel_offset.x / grid_size, pixel_offset.y / grid_size);

	grid_size = size;

	if (grid_size < 5)
	    grid_size = 5;
	if (grid_size > 20)
	    grid_size = 20;

	pixel_offset = new Point(indexOffset.x * grid_size, indexOffset.y * grid_size);

    }

}
