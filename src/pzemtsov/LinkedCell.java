package pzemtsov;

import util.LongUtil;
import util.HashPoint;

public final class LinkedCell {
    final long position;
    int index;
    int neighbours;
    boolean live;
    LinkedCell table_next;
    LinkedCell next;
    LinkedCell prev;

    public LinkedCell(long position, int neighbours, boolean live) {
	this.position = position;
	this.neighbours = neighbours;
	this.live = live;
    }

    public LinkedCell(long position, int neighbours) {
	this(position, neighbours, false);
    }

    public HashPoint toPoint() {
	return LongUtil.toPoint(position);
    }

    public void inc() {
	++neighbours;
    }

    public boolean dec() {
	return --neighbours != 0;
    }

    public void set() {
	live = true;
    }

    public void reset() {
	live = false;
    }

    @Override
    public boolean equals(Object cell) {
	return ((LinkedCell) cell).position == position;
    }

    public boolean equals(long position) {
	return this.position == position;
    }
}
