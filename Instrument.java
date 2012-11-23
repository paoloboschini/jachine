import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

/**
 * Instrument sequencer, represented as a JPanel with pads.
 * Each row represent an instrument.
 * @author Paolo Boschini
 */
public class Instrument extends JPanel {

	/** The number of steps in the sequencer. */
	static final int STEPS = 16;

	/** The pads for this instrument. */
	protected Pad[] pads = new Pad[STEPS];

	/** Current step in sequencer. */
	private int step = 0;
	
	/** The audio file for this instrument. */
	File soundFile;

	/**
	 * Default constructor.
	 * 
	 * @param widthPad
	 *		the width for the pads in this instrument sequence
	 * @param heigthPad
	 *		the height for the pads in this instrument sequence
	 */
	public Instrument(int widthPad, int heigthPad) {
		setLayout(new GridLayout(0, STEPS, 10, 10));
		for (int i = 0; i < STEPS; i++) {
			Pad pad = new Pad(widthPad, heigthPad);
			pads[i] = pad;
			add(pad);
		}
	}

	/**
	 * Constructor for loading an audio sample file.
	 * 
	 * @param fileName
	 *		the file path for the sample in this instrument
	 * @param widthPad
	 *		the width for the pads in this instrument sequence
	 * @param heigthPad
	 *		the height for the pads in this instrument sequence
	 */
	public Instrument(String fileName, int widthPad, int heigthPad) {
		this(widthPad, heigthPad);
		soundFile = new File(fileName);
		prepareClip(); // prepare the clip to avoid initial delay
	}

	/**
	 * Prepare the audio stream and the clip for playing
	 * this sample instrument.
	 * @return the clip for this sample instrument
	 */
	private Clip prepareClip() {
		Clip clip = null;
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile); 
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			return clip;
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return clip;
	}

	/**
	 * Plays this sample instrument once.
	 */
	public void play() {
		prepareClip().start();
	}

	/**
	 * Gets the current step in the sequence.
	 * @return the current step
	 */
	public int getStep() {
		return step;
	}

	/**
	 * Sets the new step in the sequence.
	 * @return the new step
	 */
	public void setStep(int step) {
		this.step = step;
	}

	/**
	 * Increment the current step in the sequence.
	 */
	public void incrementStep() {
		step++;
	}

	/**
	 * Decrement the current step in the sequence.
	 */
	public void decrementStep() {
		step--;
	}

	/**
	 * Return the pads in this instrument.
	 * @return the pads
	 */
	public Pad[] getPads() {
		return pads;
	}
	
	/**
	 * Clears the pads for this instrument.
	 */
	public void clearPads() {
		for (Pad pad : pads) {
			pad.setState(false);
		}
	}
}