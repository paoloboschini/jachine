import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Main class with main frame and panels.
 * @author Paolo Boschini
 */
public class Main {

	/** Spinner for setting bpm. */
	private JSpinner bpmSpinner;

	/** An indicator sequencer showing what step is being triggered. */
	private Indicator indicator;

	/** The kick instrument. */
	private SampleInstrument kick;

	/** The snare instrument. */
	private SampleInstrument snare;

	/** The hithat instrument. */
	private SampleInstrument hithat;

	/** Player object running the thread for the sequence. */
	private Player player;

	/** File name of kick sample. */
	private final String KICK_SAMPLE = "kick.wav";

	/** File name of snare sample. */
	private final String SNARE_SAMPLE = "snare.wav";

	/** File name of hithat sample. */
	private final String HITHAT_SAMPLE = "hithat.wav";

	/**
	 * Default constructor.
	 */
	public Main() {
		final JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = startButton.getText();
				if (text.equals("Start")) {
					startButton.setText("Stop");
					if(player != null)
						player.kill();
					int bpm = (Integer) bpmSpinner.getValue();
					player = new Player(bpm, indicator, kick, snare, hithat);
				} else {
					startButton.setText("Start");
					player.kill();
				}
			}
		});

		JButton randomizeButton = new JButton("Randomize");
		randomizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// clear pads first
				kick.clearPads();
				snare.clearPads();
				hithat.clearPads();

				// randomize
				kick.randomize();
				snare.randomize();
				hithat.randomize();
			}
		});

		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				kick.clearPads();
				snare.clearPads();
				hithat.clearPads();
			}
		});

		bpmSpinner = new JSpinner(new SpinnerNumberModel(120, 60, 240, 1));
		bpmSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				JSpinner spinner = (JSpinner)evt.getSource();
				int newBpm = (Integer) spinner.getValue();
				player.setBpm(newBpm);
			}
		});

		JPanel north = new JPanel();
		north.add(startButton);
		north.add(randomizeButton);
		north.add(clearButton);
		north.add(bpmSpinner);

		kick = new SampleInstrument(KICK_SAMPLE, 50, 50);
		snare = new SampleInstrument(SNARE_SAMPLE, 50, 50);
		hithat = new SampleInstrument(HITHAT_SAMPLE, 50, 50);
		indicator = new Indicator(50, 10);

		JPanel center = new JPanel(new GridLayout(4, 0, 0, 20));
		center.add(wrapInJPanel(kick));
		center.add(wrapInJPanel(snare));
		center.add(wrapInJPanel(hithat));
		center.add(wrapInJPanel(indicator));

		JFrame frame = new JFrame();
		frame.getContentPane().add(north, BorderLayout.NORTH);
		frame.getContentPane().add(center,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Wraps a JPanel into another JPanel
	 * for consequent margins.
	 * @param comp the panel to be wrapped
	 * @return a wrapper JPanel
	 */
	private JPanel wrapInJPanel(JPanel comp) {
		JPanel p = new JPanel();
		p.add(comp);
		return p;
	}

	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}
}
