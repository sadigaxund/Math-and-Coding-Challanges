import java.awt.Rectangle;

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

public class Grid extends Rectangle {
    private boolean isAlive = false;
    private int row;
    private int column;
    private int gridSize;

    /**
     * 
     */
    private static final long serialVersionUID = 2892865424401791072L;

    /**
     * @return the isAlive
     */
    public boolean isAlive() {
	return isAlive;
    }

    /**
     * @param isAlive
     *                    the isAlive to set
     */
    public void setAlive(boolean isAlive) {
	this.isAlive = isAlive;
    }

    /**
     * @return the row
     */
    public int getRow() {
	return row;
    }

    /**
     * @param row
     *                the row to set
     */
    public void setRow(int row) {
	this.row = row;
    }

    /**
     * @return the column
     */
    public int getColumn() {
	return column;
    }

    /**
     * @param column
     *                   the column to set
     */
    public void setColumn(int column) {
	this.column = column;
    }

    /**
     * @return the gridSize
     */
    public int getGridSize() {
	return gridSize;
    }

    /**
     * @param gridSize
     *                     the gridSize to set
     */
    public void setGridSize(int gridSize) {
	this.gridSize = gridSize;
    }

}
