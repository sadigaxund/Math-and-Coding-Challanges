package pzemtsov;

import static util.LongUtil.DX;
import static util.LongUtil.DY;
import static util.LongUtil.fromPoint;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public final class HashGrid extends Worker {
    public static final int HASH_CAPACITY = 256 * 1024;

    private final LinkedCell[] table;
    private int count = 0;
    LinkedCell full_list;

    private final String name;
    private LinkedCell[] toReset = new LinkedCell[128];
    private LinkedCell[] toSet = new LinkedCell[128];

    public HashGrid() {

	this.name = getClass().getName();
	this.table = new LinkedCell[HASH_CAPACITY];
	full_list = new LinkedCell(0, 0);
	full_list.prev = full_list.next = full_list;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public void reset() {
	for (int i = 0; i < table.length; i++) {
	    table[i] = null;
	}
	full_list.prev = full_list.next = full_list;
    }

    private int hash(long key) {
	return (int) (key % 946840871);
    }

    private int index(int hash) {
	return hash & (table.length - 1);
    }

    public LinkedCell get(long key) {
	for (LinkedCell c = table[index(hash(key))]; c != null; c = c.table_next) {
	    if (c.position == key) {
		return c;
	    }
	}
	return null;
    }

    private void add_to_list(LinkedCell cell) {
	cell.next = full_list.next;
	cell.next.prev = cell;
	cell.prev = full_list;
	full_list.next = cell;
    }

    private void remove_from_list(LinkedCell cell) {
	cell.next.prev = cell.prev;
	cell.prev.next = cell.next;
    }

    public void put(LinkedCell cell) {
	int index = cell.index = index(hash(cell.position));
	cell.table_next = table[index];
	table[index] = cell;
	add_to_list(cell);
	++count;
    }
    
  
    public boolean remove(LinkedCell cell) {
	if (cell == null)
	    return false;
	int index = cell.index;
	if (table[index] == cell) {
	    table[index] = cell.table_next;
	    --count;
	    remove_from_list(cell);
	    return true;
	}
	for (LinkedCell c = table[index]; c != null; c = c.table_next) {
	    if (c.table_next == cell) {
		c.table_next = cell.table_next;
		--count;
		remove_from_list(cell);
		return true;
	    }
	}
	return false;
    }

    @Override
    public int size() {
	return count;
    }

    @Override
    public Set<Point> get() {
	final HashSet<Point> result = new HashSet<Point>();
	for (LinkedCell cell = full_list.next; cell != full_list; cell = cell.next) {
	    if (cell.live) {
		result.add(cell.toPoint());
	    }
	}
	return result;
    }
 
    public LinkedCell getLinkedCellObject(int x, int y) {
	long pos = fromPoint(x, y);
	for (LinkedCell cell = full_list.next; cell != full_list; cell = cell.next) {
	    if (cell.live && cell.equals(pos)) {
		return cell;

	    }
	}
	return null;
    }

    private void inc(long w) {
	LinkedCell c = get(w);
	if (c == null) {
	    put(new LinkedCell(w, 1));
	} else {
	    c.inc();
	}
    }

    private void dec(long w) {
	LinkedCell c = get(w);
	if (c != null && !c.dec() && !c.live) {
	    remove(c);
	}
    }

    void set(LinkedCell c) {
	long w = c.position;
	inc(w - DX - DY);
	inc(w - DX);
	inc(w - DX + DY);
	inc(w - DY);
	inc(w + DY);
	inc(w + DX - DY);
	inc(w + DX);
	inc(w + DX + DY);
	c.set();
    }

    void reset(LinkedCell c) {
	long w = c.position;
	dec(w - DX - DY);
	dec(w - DX);
	dec(w - DX + DY);
	dec(w - DY);
	dec(w + DY);
	dec(w + DX - DY);
	dec(w + DX);
	dec(w + DX + DY);
	if (c.neighbours == 0) {
	    remove(c);
	} else {
	    c.reset();
	}
    }

    @Override
    public void put(int x, int y) {
	long w = fromPoint(x, y);
	LinkedCell c = get(w);
	if (c == null) {
	    put(c = new LinkedCell(w, 0, true));
	}
	set(c);
    }

    @Override
    public void step() {
	if (size() > toSet.length) {
	    toReset = new LinkedCell[size() * 2];
	    toSet = new LinkedCell[size() * 2];
	}
	int setPtr = 0;
	int resetPtr = 0;

	for (LinkedCell cell = full_list.next; cell != full_list; cell = cell.next) {
	    if (cell.live) {
		if (cell.neighbours < 2 || cell.neighbours > 3)
		    toReset[resetPtr++] = cell;
	    } else {
		if (cell.neighbours == 3)
		    toSet[setPtr++] = cell;
	    }
	}

	for (int i = 0; i < setPtr; i++) {
	    set(toSet[i]);
	}
	for (int i = 0; i < resetPtr; i++) {
	    reset(toReset[i]);
	}
    }

    /**
     * @return the full_list
     */
    public LinkedCell getFull_list() {
        return full_list;
    }

    /**
     * @param full_list the full_list to set
     */
    public void setFull_list(LinkedCell full_list) {
        this.full_list = full_list;
    }
    
    

}
