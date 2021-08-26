package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
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

    private Point gridMapSize;
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
	    int check_margin = 10;
	    boolean check_left_x = (rect.x + rect.width < -check_margin);
	    boolean check_right_x = (rect.x > getWidth() + check_margin);
	    boolean check_up_y = (rect.y + rect.height < -check_margin);
	    boolean check_down_y = (rect.y > getHeight() + check_margin);

	    if (check_down_y || check_left_x || check_right_x || check_up_y)
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
/**
 * SpringLayout sl_BOTTOM_PANEL = new SpringLayout();
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, label, 350,
 * SpringLayout.WEST, BOTTOM_PANEL);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, label, 0,
 * SpringLayout.SOUTH, BOTTOM_PANEL);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, label, -341,
 * SpringLayout.EAST, BOTTOM_PANEL);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, panel_2, 0,
 * SpringLayout.NORTH, lblLatency);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, panel_2, 568,
 * SpringLayout.EAST, lblMs); sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH,
 * panel_2, 0, SpringLayout.SOUTH, lblLatency);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, panel_2, -10,
 * SpringLayout.EAST, BOTTOM_PANEL);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, lblLatency, 10,
 * SpringLayout.WEST, BOTTOM_PANEL);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, lblLatency, -6,
 * SpringLayout.WEST, slider); sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH,
 * lblMs, 0, SpringLayout.NORTH, lblLatency);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, lblMs, 6, SpringLayout.EAST,
 * slider); sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, lblMs, 0,
 * SpringLayout.SOUTH, lblLatency);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, lblMs, 245,
 * SpringLayout.EAST, lblLatency);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.WEST, slider, 79,
 * SpringLayout.WEST, BOTTOM_PANEL);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, slider, 2,
 * SpringLayout.NORTH, BOTTOM_PANEL);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, slider, -2,
 * SpringLayout.SOUTH, BOTTOM_PANEL);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.EAST, slider, -677,
 * SpringLayout.EAST, BOTTOM_PANEL);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, lblLatency, 2,
 * SpringLayout.NORTH, BOTTOM_PANEL);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.SOUTH, lblLatency, -2,
 * SpringLayout.SOUTH, BOTTOM_PANEL);
 * sl_BOTTOM_PANEL.putConstraint(SpringLayout.NORTH, label, 2,
 * SpringLayout.NORTH, BOTTOM_PANEL); BOTTOM_PANEL.setLayout(sl_BOTTOM_PANEL);
 * BOTTOM_PANEL.add(lblLatency); BOTTOM_PANEL.add(slider);
 * BOTTOM_PANEL.add(lblMs); BOTTOM_PANEL.add(label); BOTTOM_PANEL.add(panel_2);
 */
