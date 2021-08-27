package pzemtsov;

import java.awt.Point;
import java.util.Set;

import util.HashPoint;

public abstract class Worker {
    abstract void reset();

    public abstract void put(int x, int y);

    abstract Set<Point> get();

    int size() {
	return get().size();
    }

    abstract void step();

    String getName() {
	return getClass().getName();
    }
}
