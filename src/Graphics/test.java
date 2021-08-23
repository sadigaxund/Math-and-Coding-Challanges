/***************************************************************************
 *   MIT License
 *   
 *   Copyright (c) 2021 Sadig Akhund
 *   
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *   
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *   
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 *
 * 
 **************************************************************************/
package Graphics;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class test extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    test frame = new test();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public test() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);

	JPanel panel = new JPanel();

	panel.setBorder(UIManager.getBorder("RadioButton.border"));

	JPanel panel_1 = new JPanel();
	FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
	flowLayout_1.setHgap(0);
	flowLayout_1.setVgap(0);
	GroupLayout groupLayout = new GroupLayout(getContentPane());
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addComponent(panel, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
		.addGroup(groupLayout.createSequentialGroup()
			.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
			.addPreferredGap(ComponentPlacement.RELATED)
			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)));
	panel.setLayout(null);
	int margin = 10;
	int panel_height = 35;
	int component_height = panel_height - margin * 2;
	JLabel lblLatency = new JLabel("Latency:");
	lblLatency.setBounds(margin, margin, 50, component_height);
	panel.add(lblLatency);

	JSlider slider = new JSlider();
	slider.setBounds(lblLatency.getX() + lblLatency.getWidth() + margin, margin-1, 150, component_height + 5);
	slider.setValue(1000);
	slider.setSnapToTicks(true);
	slider.repaint();
	slider.setMinorTickSpacing(100);
	slider.setMinimum(5);
	slider.setMaximum(2500);
	panel.add(slider);

	JLabel lblMs = new JLabel("0000 ms");
	lblMs.setBounds(slider.getX() + slider.getWidth() + margin, margin, 50, component_height);
	panel.add(lblMs);

	JLabel label = new JLabel("");
	label.setBounds(358, 15, 0, 0);
	panel.add(label);
	getContentPane().setLayout(groupLayout);

	slider.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent arg0) {
		lblMs.setText(slider.getValue() + " ms");
	    }
	});

    }
}
