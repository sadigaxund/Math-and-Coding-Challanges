package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

import pzemtsov.HashGrid;
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

public class Surface extends SurfaceAdapter {

    /**
     * 
     */
    private static final long serialVersionUID = -8120139481082133405L;

    private Color deadCellColor = new Color(21, 34, 56); // Darkest Blue
    private Color gridColor = new Color(0, 80, 110); // Greenish Blue
    private HashGrid map;
    private HashSet<Point> forbidList = new HashSet<>();
    private int grid_size = 16;
    private Point pixel_offset;

    public boolean ZOOM_IN_WHEN_SCROLL_UP = true;

    public Surface(int window_width, int window_heigth) {
	super();
	setBounds(getX(), getY(), window_width, window_heigth);
	setPattern(Experimental.getJavaPattern());
    }

    @Override
    protected void doDrawing(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	int w = getWidth();
	int h = getHeight();

	// Draw Background
	g2d.setColor(deadCellColor);
	g2d.fillRect(0, 0, w, h);

	/* Render only visible dead cells */
	g2d.setColor(gridColor);
	int x_off = pixel_offset.x % grid_size;
	int y_off = pixel_offset.y % grid_size;

	// +- 1 is for creating larger gridmap than the visible window frame
	for (int i = -1; i < getWidth() / grid_size + 2; i++)
	    for (int j = -1; j < getHeight() / grid_size + 2; j++) {
		Rectangle rect = createRectangle(new Point(i, j), new Point(x_off, y_off), grid_size);
		g2d.draw(rect);
	    }

	/* Render Alive Cells all over the coordinate system */
	g2d.setColor(Color.WHITE);
	HashSet<Point> hashedPoints = new HashSet<>(map.get());
	for (Point pt : hashedPoints) {
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

    public HashPoint getLocationOnMap(Point pt) {
	pt.x -= pixel_offset.x; // remove x offset remainder
	pt.y -= pixel_offset.y; // remove y offset remainder
	pt.x /= grid_size;
	pt.y /= grid_size;
	return new HashPoint(pt);
    }

    public HashPoint getLocationOnMap(int x, int y) {
	return getLocationOnMap(new Point(x, y));
    }

    public void draw(Point point, int mode) {
	// Convert location on the screen to the location on the map
	Point pt = getLocationOnMap(point);
	
	if (mode != ELIMINATE_CELL_MODE && forbidList.contains(pt))
	    return; // return if the current cell has already being modified while mouse is pressed
	else
	    forbidList.add(pt); // add cell to the list of modified cells
	// DRAWING ...
	switch (mode) {
	case ANIMATE_CELL_MODE:
	    map.put(pt.x, pt.y); // add coords. into the map to make it alive cell
	    break;
	case ELIMINATE_CELL_MODE:
	    map.remove(map.getLinkedCellObject(pt.x, pt.y)); // remove coords. from the map to kill cell
	    break;
	case INVERT_CELL_MODE:
	    if (!map.remove(map.getLinkedCellObject(pt.x, pt.y))) // if the coords. were not possible to be removed, add
		map.put(pt.x, pt.y);
	    break;
	}

    }
    public void clear() {
	Set<Point> s = map.get();
	for(Point pt: s) 
	    map.remove(map.getLinkedCellObject(pt.x, pt.y));
	
//	Point pt = s.iterator().next();
//	map.setFull_list(map.getLinkedCellObject(pt.x, y));
	
    }

    public void draw(int x, int y, int mode) {
	draw(new Point(x, y), mode);

//	draw(new Point(x + grid_size, y), mode);
//	draw(new Point(x + grid_size, y + grid_size), mode);
//	draw(new Point(x, y + grid_size), mode);
//	draw(new Point(x - grid_size, y), mode);
//	draw(new Point(x - grid_size, y - grid_size), mode);
//	draw(new Point(x, y - grid_size), mode);
//	draw(new Point(x + grid_size, y - grid_size), mode);
//	draw(new Point(x - grid_size, y + grid_size), mode);
	
	
    }

    private void setPattern(String[] pattern) {
	Point patternSize = getPatternLength(pattern);
	int w = getWidth() / grid_size + 2;
	int h = getHeight() / grid_size + 2;
	pixel_offset = new Point(-(w - patternSize.x) / 2, -(h - patternSize.y) / 2);
	map = new HashGrid();
	map.reset();
	put(map, pattern, pixel_offset);
    }

    public static void sleep(long ms) {
	try {
	    Thread.sleep(ms);
	} catch (InterruptedException e) {
	}

    }

    private void optimizedSleep() {
	int temp = LATENCY;
	while (LATENCY > 500 && temp > 0) {
	    sleep(50);
	    temp -= 50;
	}

	sleep((temp < 0) ? temp + 50 : LATENCY);
    }

    @Override
    public void run() {
	while (true) {
	    optimizedSleep();

	    if (paused)
		continue;

	    step();
	    repaint();
	}
    }

    /**********************************************************
     * GETTERS & SETTERS
     ***********************************************************/

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
    }

    /**
     * @param grid_size
     *                      the grid_size to set
     */
    public void setGridSize(int size) {

	Point indexOffset = new Point(pixel_offset.x / grid_size, pixel_offset.y / grid_size);

	grid_size = size;

	if (grid_size < 4)
	    grid_size = 4;
	if (grid_size > 20)
	    grid_size = 20;

	pixel_offset = new Point(indexOffset.x * grid_size, indexOffset.y * grid_size);

    }

    @Override
    protected int getZoomCoefficient(int notches) {
	return Math.abs(notches) / notches * ((ZOOM_IN_WHEN_SCROLL_UP) ? -1 : 1);
    }

    /**
     * @return the grid_size
     */
    public int getGridSize() {
	return grid_size;
    }

    /**
     * @return the recentlyDrawnPoints
     */
    public HashSet<Point> getForbidList() {
	return forbidList;
    }

    /**
     * @param recentlyDrawnPoints
     *                                the recentlyDrawnPoints to set
     */
    public void setForbidList(HashSet<Point> recentlyDrawnPoints) {
	this.forbidList = recentlyDrawnPoints;
    }

    public void resetForbidList() {
	this.forbidList = new HashSet<>();
    }

}
