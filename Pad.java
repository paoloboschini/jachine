import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * A pad in an instrument sequence. A pad can
 * be clicked and turned on/off to trigger the
 * corresponding audio sample in the sequence.
 * @author Paolo Boschini
 */
public class Pad extends JPanel implements MouseListener {

	/** Provides status about a pad. */
	private boolean isOn;

	/**
	 * Default constructor.
	 * 
	 * @param widthPad
	 *		the width for this pad
	 * @param heigthPad
	 *		the height for this pad
	 */
	public Pad(int width, int height) {
		setMinimumSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setPreferredSize(new Dimension(width,height));
		isOn = false;
		setBackground(Color.white);
		setBorder(new LineBorder(Color.black));
		addMouseListener(this);
	}
	
	/**
	 * Return the status of a pad.
	 * @return the status on/off
	 */
	public boolean isOn() {
		return isOn;
	}
	
	/**
	 * Sets the state of this pad.
	 */
	public void setState(boolean state) {
		isOn = state;
		setBackground(state ? Color.red : Color.white);
	}

	/**
	 * Change the status of this pad on click events.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (getParent() instanceof SampleInstrument) {
			isOn = !isOn;
			setBackground(isOn ? Color.red : Color.white);			
		}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
