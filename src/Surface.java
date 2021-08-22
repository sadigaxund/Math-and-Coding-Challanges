import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

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

    private int grid_size = 32;
    private Color darkestBlue = new Color(21, 34, 56);
    

    private void doDrawing(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	int w = getWidth();
	int h = getHeight();
	g2d.setColor(darkestBlue);
	g2d.fillRect(0, 0, w, h);
	
	
	

	g2d.setColor(new Color(145, 163, 176));
	for (int i = 0; i < w / grid_size + 1; i++)
	    for (int j = 0; j < h / grid_size + 1; j++) {
//		Grid grid = 
		g2d.drawRect(i * grid_size, j * grid_size, grid_size, grid_size);
	    }
    }

    @Override
    public void paintComponent(Graphics g) {

	super.paintComponent(g);
	doDrawing(g);
    }
}
