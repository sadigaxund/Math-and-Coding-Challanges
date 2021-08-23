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

public class LongUtil {
    public static final int OFFSET = 0x80000000;
    public static final long DX = 0x100000000L;
    public static final long DY = 1;

    public static long w(int hi, int lo) {
	return ((long) hi << 32) | lo & 0xFFFFFFFFL;
    }

    public static int hi(long w) {
	return (int) (w >>> 32);
    }

    public static int lo(long w) {
	return (int) w;
    }

    public static int x(long w) {
	return hi(w) - OFFSET;
    }

    public static int y(long w) {
	return lo(w) - OFFSET;
    }

    public static long fromPoint(int x, int y) {
	return w(x + OFFSET, y + OFFSET);
    }

    public static HashPoint toPoint(long w) {
	return new HashPoint(x(w), y(w));
    }
}