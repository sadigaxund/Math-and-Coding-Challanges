package Graphics;

import java.awt.Point;
import java.awt.event.ActionListener;

public class BiStateButton extends MultiStateButton implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public BiStateButton(String icon1, String icon2, Point iconSize) {
	super(iconSize, icon1, icon2);

    }

    public BiStateButton(String icon1, String icon2, int w, int h) {
	super(w, h, icon1, icon2);
    }

    public BiStateButton(String icon1, String icon2, int size) {
	super(size, icon1, icon2);
    }

    public boolean getState() {
	return (super.getIndex() == 1) ? true : false;
    }

    public void setState(boolean b) {
	super.setIndex((b) ? 1 : 0);
    }

}
