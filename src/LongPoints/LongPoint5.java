package LongPoints;

import Data.LongUtil;
import Data.Point;

public final class LongPoint5 extends LongUtil {
    private final long v;

    public LongPoint5(long v) {
	this.v = v;
    }

    public LongPoint5(int x, int y) {
	this.v = fromPoint(x, y);
    }

    public Point toPoint() {
	return toPoint(getV());
    }

    @Override
    public boolean equals(Object v2) {
	return ((LongPoint5) v2).getV() == getV();
    }

    @Override
    public int hashCode() {
	long x = getV() * 541725397157L;
	return lo(x) ^ hi(x);
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
