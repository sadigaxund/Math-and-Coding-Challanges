package Graphics;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import Utils.JImages;
import Utils.JUtil;

public class MultiStateButton extends JButton implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String[] icons;
    private int index;
    private Point iconSize;

    public MultiStateButton(Point iconSize, String... icons) {
	super();
	addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
	traverse();
    }

    public void traverse() {
	index++;
	if (index >= icons.length)
	    index = 0;
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
}
