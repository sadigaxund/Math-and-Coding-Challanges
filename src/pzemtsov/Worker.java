package pzemtsov;

import java.util.Set;

import util.HashPoint;

public abstract class Worker {
    abstract void reset();

    abstract void put(int x, int y);

    abstract Set<HashPoint> get();

    int size() {
	return get().size();
    }

    abstract void step();

    String getName() {
	return getClass().getName();
    }
}
