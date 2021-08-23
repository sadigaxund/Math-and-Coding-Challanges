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
package util;

public class HashPoint {
    public final int x, y;

    public HashPoint(int x, int y) {
	this.x = x;
	this.y = y;
    }

    @Override
    public final boolean equals(Object obj) {
	HashPoint p = (HashPoint) obj;
	return x == p.x && y == p.y;
    }

    @Override
    public final int hashCode() {
	return x * 3 + y * 5;
    }

    @Override
    public String toString() {
	return "(" + x + "," + y + ")";
    }

    public HashPoint[] neighbours() {
	return new HashPoint[] { new HashPoint(x - 1, y - 1), new HashPoint(x - 1, y), new HashPoint(x - 1, y + 1),
		new HashPoint(x, y - 1), new HashPoint(x, y + 1), new HashPoint(x + 1, y - 1), new HashPoint(x + 1, y),
		new HashPoint(x + 1, y + 1) };
    }
}