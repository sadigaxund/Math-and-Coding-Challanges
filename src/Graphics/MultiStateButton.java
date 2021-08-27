package Graphics;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import Utils.JImages;

public class MultiStateButton extends JButton implements MouseListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String[] icons;
    private int index;
    private Point iconSize;

    public MultiStateButton(Point iconSize, String... icons) {
	super();
	addMouseListener(this);
	this.icons = icons;
	this.iconSize = iconSize;
	setIndex(0);

    }

    public MultiStateButton(int w, int h, String... icons) {
	this(new Point(w, h), icons);
    }

    public MultiStateButton(int size, String... icons) {
	this(size, size, icons);
    }

    public void inc() {
	index++;
	if (index >= icons.length)
	    index = 0;
	setIndex(index);
    }

    public void dec() {
	index--;
	if (index < 0)
	    index = icons.length - 1;
	setIndex(index);
    }

    public int getIndex() {
	return index;
    }

    public void setIndex(int index) {
	this.index = index;
	Image Icon = JImages.scaleImage(new ImageIcon("./res/" + icons[index] + ".png").getImage(), iconSize.x,
		iconSize.y);
	setIcon(new ImageIcon(Icon));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

	if (e.getButton() == 1)
	    inc();

	if (e.getButton() == 3)
	    dec();

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub

    }
}
