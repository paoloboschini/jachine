import java.util.Random;

/**
 * Instrument sequencer with triggable pads.
 * @author Paolo Boschini
 */
public class SampleInstrument extends Instrument {

	/** Random instance. */
	Random random;

	/**
	 * Default constructor.
	 * @param fileName
	 *		the file path for the sample in this instrument
	 * @param widthPad
	 *		the width for the pads in this instrument sequence
	 * @param heigthPad
	 *		the height for the pads in this instrument sequence
	 */
	public SampleInstrument(String fileName, int widthPad, int heightPad) {
		super(fileName, widthPad, heightPad);
		random = new Random();
	}

	/**
	 * Randomizes the active pads in this instrument.
	 */
	public void randomize() {
		for (Pad pad : pads) {
			pad.setState(random.nextInt(10) < 3 ? true : false);
		}
	}
}