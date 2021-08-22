package LongPoints;

import java.util.zip.CRC32;

import Data.LongUtil;
import Data.Point;

public final class LongPoint7 extends LongUtil {
    private final long v;

    public LongPoint7(long v) {
	this.v = v;
    }

    public LongPoint7(int x, int y) {
	this.v = fromPoint(x, y);
    }

    public Point toPoint() {
	return toPoint(getV());
    }

    @Override
    public boolean equals(Object v2) {
	return ((LongPoint7) v2).getV() == getV();
    }

    @Override
    public int hashCode() {
	CRC32 crc = new CRC32();
	crc.update((int) (getV() >>> 0) & 0xFF);
	crc.update((int) (getV() >>> 8) & 0xFF);
	crc.update((int) (getV() >>> 16) & 0xFF);
	crc.update((int) (getV() >>> 24) & 0xFF);
	crc.update((int) (getV() >>> 32) & 0xFF);
	crc.update((int) (getV() >>> 40) & 0xFF);
	crc.update((int) (getV() >>> 48) & 0xFF);
	crc.update((int) (getV() >>> 56) & 0xFF);
	return (int) crc.getValue();
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
