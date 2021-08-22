package LongPoints;

import Data.LongUtil;
import Data.Point;

public final class LongPoint extends LongUtil {
    private final long v;

    public LongPoint(long v) {
	this.v = v;
    }

    public LongPoint(int x, int y) {
	this.v = fromPoint(x, y);
    }

    public Point toPoint() {
	return toPoint(getV());
    }

    @Override
    public boolean equals(Object v2) {
	return ((LongPoint) v2).getV() == getV();
    }

    @Override
    public int hashCode() {
	return hi(getV()) * 3 + lo(getV()) * 5;
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
