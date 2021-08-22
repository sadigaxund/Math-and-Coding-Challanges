package LongPoints;

import Data.LongUtil;
import Data.Point;

public final class LongPoint4 extends LongUtil {
    private final long v;

    public LongPoint4(long v) {
	this.v = v;
    }

    public LongPoint4(int x, int y) {
	this.v = fromPoint(x, y);
    }

    public Point toPoint() {
	return toPoint(getV());
    }

    @Override
    public boolean equals(Object v2) {
	return ((LongPoint4) v2).getV() == getV();
    }

    @Override
    public int hashCode() {
	return hi(getV()) * 1735499 + lo(getV()) * 7436369;
    }

    @Override
    public String toString() {
	return toPoint().toString();
    }

    /**
     * @return the v
     */
    public long getV() {
	return v;
    }
}
