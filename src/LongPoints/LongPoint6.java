package LongPoints;

import Data.LongUtil;
import Data.Point;

public final class LongPoint6 extends LongUtil {
    private final long v;

    public LongPoint6(long v) {
	this.v = v;
    }

    public LongPoint6(int x, int y) {
	this.v = fromPoint(x, y);
    }

    public Point toPoint() {
	return toPoint(getV());
    }

    @Override
    public boolean equals(Object v2) {
	return ((LongPoint6) v2).getV() == getV();
    }

    @Override
    public int hashCode() {
	return (int) (getV() % 946840871);
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
