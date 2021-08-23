package pzemtsov;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import util.HashPoint;

final class Hash_Reference extends Worker
{
    public static final int HASH_SIZE = 8192;

    HashSet<HashPoint> field = new HashSet<HashPoint> (HASH_SIZE);
    HashMap<HashPoint, Integer> counts = new HashMap<HashPoint, Integer> (HASH_SIZE);

    @Override
    public void reset ()
    {
        field.clear ();
        counts.clear ();
    }
    
    @Override
    public void put (int x, int y)
    {
        set (new HashPoint (x, y));
    }

    @Override
    public Set<HashPoint> get ()
    {
        return field;
    }
    
    private void inc (HashPoint w)
    {
        Integer c = counts.get (w);
        counts.put (w, c == null ? 1 : c + 1);
    }

    private void dec (HashPoint w)
    {
        int c = counts.get (w) - 1;
        if (c != 0) {
            counts.put (w, c);
        } else {
            counts.remove (w);
        }
    }
    
    void set (HashPoint w)
    {
        for (HashPoint p : w.neighbours ()) {
            inc (p);
        }
        field.add (w);
    }
    
    void reset (HashPoint w)
    {
        for (HashPoint p : w.neighbours ()) {
            dec (p);
        }
        field.remove (w);
    }
    
    @Override
    public void step ()
    {
        ArrayList<HashPoint> toReset = new ArrayList<HashPoint> ();
        ArrayList<HashPoint> toSet = new ArrayList<HashPoint> ();
        for (HashPoint w : field) {
            Integer c = counts.get (w);
            if (c == null || c < 2 || c > 3) toReset.add (w);
        }
        for (HashPoint w : counts.keySet ()) {
            if (counts.get (w) == 3 && ! field.contains (w)) toSet.add (w);
        }
        for (HashPoint w : toSet) {
            set (w);
        }
        for (HashPoint w : toReset) {
            reset (w);
        }
    }
}
