package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

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
    private HashSet<HashPoint> hashedPoints;
    private Point offset = new Point(0, 0);
    private int map_width = 500;
    private int map_height = 500;

    public Surface() {
	super();
	hashedPoints = new HashSet<>();
    }

    private void doDrawing(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	int w = getWidth();
	int h = getHeight();

	// Draw Background
	g2d.setColor(darkestBlue);
	g2d.fillRect(0, 0, w, h);

	g2d.setColor(new Color(145, 163, 176));
	for (int i = 0; i < map_width; i++)
	    for (int j = 0; j < map_height; j++) {
		g2d.draw(createRectangle(new Point(i, j), offset, grid_size));
	    }

	g2d.setColor(new Color(255, 255, 255));
	for (HashPoint pt : hashedPoints) {
	    g2d.fill(createRectangle(new Point(pt.x, pt.y), offset, grid_size));
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
	return offset;
    }

    /**
     * @param offset
     *                   the offset to set
     */
    public void setOffset(int x, int y) {
	offset.x = x;
	offset.y = y;
    }

    public void addOffset(int x, int y) {
	setOffset(offset.x + x, offset.y + y);
    }

}
