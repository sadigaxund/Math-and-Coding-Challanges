package LongPoints;

import Data.LongUtil;
import Data.Point;

public final class LongPoint3 extends LongUtil {
    private final long v;

    public LongPoint3(long v) {
	this.v = v;
    }

    public LongPoint3(int x, int y) {
	this.v = fromPoint(x, y);
    }

    public Point toPoint() {
	return toPoint(getV());
    }

    @Override
    public boolean equals(Object v2) {
	return ((LongPoint3) v2).getV() == getV();
    }

    @Override
    public int hashCode() {
	return hi(getV()) * 11 + lo(getV()) * 17;
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
