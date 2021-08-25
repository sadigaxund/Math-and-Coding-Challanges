package Graphics;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import Utils.JImages;
import Utils.JUtil;

public class MyButton extends JButton implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private boolean state = true;
    private String icon1, icon2;
    private Point iconSize;

    public MyButton(String icon1, String icon2, Point iconSize) {
	super();
	addActionListener(this);
	this.icon1 = icon1;
	this.icon2 = icon2;
	this.iconSize = iconSize;
	Image smileFaceIcon = JImages.scaleImage(new ImageIcon("./res/" + icon1 + ".png").getImage(), iconSize.x,
		iconSize.y);
	setIcon(new ImageIcon(smileFaceIcon));
    }

    public MyButton(String icon1, String icon2, int w, int h) {
	this(icon1, icon2, new Point(w, h));
    }

    public MyButton(String icon1, String icon2, int size) {
	this(icon1, icon2, size, size);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	setState(!getState());
    }

    /**
     * @return the state
     */
    public boolean getState() {
	return state;
    }

    /**
     * @param state
     *                  the state to set
     */
    public void setState(boolean state) {
	this.state = state;
	Image smileFaceIcon = JImages.scaleImage(new ImageIcon("./res/" + (state ? icon1 : icon2) + ".png").getImage(),
		iconSize.x, iconSize.y);
	setIcon(new ImageIcon(smileFaceIcon));
    }

}
