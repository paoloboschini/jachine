/**
 * Player running the thread for the sequence.
 * @author Paolo Boschini
 */
public class Player implements Runnable {

	/** Sleep time between each step increment. */ 
	private int intervalFromBpm;

	/** Determines if the thread is running. */
	private boolean alive = true;

	/** The indicator sequencer. */
	private Instrument indicator;
	
	/** The loaded instruments. */ 
	private Instrument[] instruments;
	
	/** The thread for running the sequence. */
	private Thread thread = new Thread(this);

	/**
	 * Default constructor.
	 * @param bpm the bpm for the sequence
	 * @param indicator the indicator sequencer
	 * @param instruments the instruments sequencer
	 */
	public Player(int bpm, Instrument indicator, Instrument... instruments) {
		intervalFromBpm = 60000 / (bpm * 4); // a beat every fourth steps
		this.indicator = indicator;
		this.instruments = instruments;
		thread.start();
	}

	/**
	 * Updates the indicator changing pad colors.
	 */
	private void updateIndicator() {
		if (indicator.getStep() == 0) {
			indicator.getPads()[indicator.getStep()].setState(true);
			indicator.getPads()[indicator.getPads().length-1].setState(false);
		} else {
			indicator.getPads()[indicator.getStep()-1].setState(false);
			indicator.getPads()[indicator.getStep()].setState(true);
		}
		indicator.incrementStep();
		if (indicator.getStep() == indicator.getPads().length) {
			indicator.setStep(0);		
		}
	}

	/**
	 * Play audio sample corresponding to activated pads.
	 */
	private void updateInstruments() {
		for (Instrument i : instruments) {
			if (i.getPads()[i.getStep()].isOn()) {
				i.play();
			}
			i.incrementStep();
			if (i.getStep() == i.getPads().length) {
				i.setStep(0);
			}		
		}
	}

	/**
	 * Run the sequence updating the instruments
	 * at the given beats per minute.
	 */
	@Override
	public void run() {
		while (alive) {
			updateIndicator();
			updateInstruments();
			try {
				Thread.sleep(intervalFromBpm);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Reset the sequence when user press stop button.
	 */
	public void resetSequence() {
		// Reset indicator
		for (Pad pad : indicator.getPads()) {
			pad.setState(false);
		}
		indicator.setStep(0);
		for (Instrument i : instruments) {
			i.setStep(0);
		}
	}

	/**
	 * Set the bpm, can be changed from the spinner.
	 * @param bpm the new bpm value
	 */
	public void setBpm(int bpm) {
		intervalFromBpm = 60000 / (bpm * 4);
	}
	
	/**
	 * Stops the sequence thread and reset the sequence.
	 */
	public void kill() {
		alive = false;
		resetSequence();
	}
}