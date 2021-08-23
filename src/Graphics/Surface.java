package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;

import javax.swing.JPanel;

import pzemtsov.Hash;
import pzemtsov.Hasher;
import pzemtsov.Life;
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

    private int gridmap_width = 500;
    private int gridmap_height = 500;

    private int window_width;
    private int window_height;

    private Point index_offset;
    private Point pixel_offset;

    private static final String[] ACORN = new String[] { "##  ###", ":::#:::", ":#" };
    private HashSet<HashPoint> hashedPoints;
    private Hash map;

    public Surface(int window_width, int window_heigth) {
	super();
	this.window_height = window_heigth;
	this.window_width = window_width;
	index_offset = new Point(-gridmap_width / 2 + 1, -gridmap_height / 2 + 1);
	pixel_offset = new Point(index_offset.x * grid_size + window_width / 2 - 64,
		index_offset.y * grid_size + window_heigth / 2 - 64);
	map = new Hash(new Hasher());
	map.reset();
	Life.put(map, ACORN, index_offset);
	hashedPoints = new HashSet<>(map.get());
    }

    private void doDrawing(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	int w = getWidth();
	int h = getHeight();

	// Draw Background
	g2d.setColor(darkestBlue);
	g2d.fillRect(0, 0, w, h);

	g2d.setColor(new Color(145, 163, 176));
	int sum = 0;
	for (int i = 0; i < gridmap_width; i++)
	    for (int j = 0; j < gridmap_height; j++) {
		// RENDER OPTIMIZATION
		Rectangle rect = createRectangle(new Point(i, j), pixel_offset, grid_size);
		int check_margin = 10;
		boolean check_left_x = (rect.x + rect.width < -check_margin);
		boolean check_right_x = (rect.x > getWidth() + check_margin);
		boolean check_up_y = (rect.y + rect.height < -check_margin);
		boolean check_down_y = (rect.y > getHeight() + check_margin);

		if (check_down_y || check_left_x || check_right_x || check_up_y)
		    continue;

		g2d.draw(rect);
	    }

	g2d.setColor(new Color(255, 255, 255));
	for (HashPoint pt : hashedPoints) {
	    g2d.fill(createRectangle(new Point(pt.x, pt.y), pixel_offset, grid_size));
	}
    }

    private Rectangle createRectangle(Point loc, Point offset, int size) {
	return new Rectangle(offset.x + loc.x * size, offset.y + loc.y * size, size, size);

	// return new Rectangle(loc.x * size, loc.y * size, size, size);

    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	doDrawing(g);
    }

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
	grid_size = size;

	if (grid_size < 5)
	    grid_size = 5;
	if(grid_size > 20)
	    grid_size = 20;

    }

}
