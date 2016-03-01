package ecumene.exo.analyze;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.SimContext;
import ecumene.exo.view.ViewerRunnable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.ExceptionListener;
import java.io.File;

public class ExoRuntimeAnalyzer extends ViewerRunnable {

	private JFrame frame;
	
	public ExoRuntimeAnalyzer(int id, ExceptionListener listener, String[] args) {
		super(id, listener, args);
	}

	private JPanel currentSeed = new JPanel();
	private JPanel containerFocus = new JPanel();

	private JPanel currentSolar = new JPanel();
	private JPanel containerSolar = new JPanel();

	private JPanel currentPlanet = new JPanel();
	private JPanel containerPlanet = new JPanel();

	private JLabel stepLabel = new JLabel("Current Step: " + 0);
	private JPanel currentSim = new JPanel();
	private JPanel containerSim = new JPanel();

	private Timer simStepper;
	private int   simStepsPerItr = 1;

	private Dimension windowSize  = new Dimension(430, 720);
	private Dimension sectionSize = new Dimension(10, 22); // displacements in jpanel from borders

	@Override
	public void init() throws Throwable {
		frame = new JFrame("Runtime Analyzer");
		frame.setVisible(false);
		{
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(windowSize);
			frame.setIconImage(ImageIO.read(new File("./resources/logo.png")));
			frame.setLocationRelativeTo(null);
			frame.setLayout(new FlowLayout());

			{ // Galaxy Config
				containerFocus.setLayout(new BoxLayout(containerFocus, BoxLayout.Y_AXIS));
				TitledBorder containerSeedBorder = BorderFactory.createTitledBorder("Galaxy Configuration");
				containerSeedBorder.setTitleJustification(TitledBorder.LEFT);
				containerFocus.setBorder(containerSeedBorder);
				containerFocus.add(currentSeed);
				JPanel setCurrentFocus = new JPanel();
				final JTextField focusField = new JTextField(20);
				setCurrentFocus.add(focusField);
				JButton setFocus = new JButton("Set Focus");
				setFocus.addActionListener(ae -> {
					if (focusField.getText().equals("")) ExoRuntime.INSTANCE.getContext().getGalaxy().setFollow(-1); // -1 points to nothing
					else                                 ExoRuntime.INSTANCE.getContext().getGalaxy().setFollow(Integer.parseInt(focusField.getText()));
				});
				setCurrentFocus.add(setFocus);
				containerFocus.add(setCurrentFocus);
				frame.add(containerFocus);
			}

			{ // Solar Config
				containerSolar.setLayout(new BoxLayout(containerSolar, BoxLayout.Y_AXIS));
				TitledBorder containerSolarBorder = BorderFactory.createTitledBorder("Solar Configuration");
				containerSolarBorder.setTitleJustification(TitledBorder.LEFT);
				containerSolar.setBorder(containerSolarBorder);
				containerSolar.add(currentSolar);
				JPanel setCurrentFocus = new JPanel();
				final JTextField solarFocusField = new JTextField(20);
				JButton setSolarFocus = new JButton("Set Focus");
				setSolarFocus.addActionListener(ae -> {
					if (solarFocusField.getText().equals("")) ExoRuntime.INSTANCE.getContext().getSolarSystem().setFollow(-1); // -1 points to nothing
					else                                      ExoRuntime.INSTANCE.getContext().getSolarSystem().setFollow(Integer.parseInt(solarFocusField.getText()));
				});

				setCurrentFocus.add(solarFocusField);
				setCurrentFocus.add(setSolarFocus);
				containerSolar.add(setCurrentFocus, 0);
				frame.add(containerSolar);
			}

			{ // Solar Config
				containerPlanet.setLayout(new GridBagLayout());
				TitledBorder containerPlanetBorder = BorderFactory.createTitledBorder("Planet Configuration");
				containerPlanetBorder.setTitleJustification(TitledBorder.LEFT);
				containerPlanet.setBorder(containerPlanetBorder);
				containerPlanet.add(currentPlanet);
				JButton cleanupMoonTrackPos = new JButton("Clear Tracked Moons");
				cleanupMoonTrackPos.addActionListener(ae -> ExoRuntime.INSTANCE.getContext().getPlanet().getMap().clearTrackedPositions());

				containerPlanet.setPreferredSize(new Dimension((int) containerFocus.getPreferredSize().getWidth(), 60));
				containerPlanet.add(cleanupMoonTrackPos, 0);
				frame.add(containerPlanet);
			}

			{ // Simulation Config
				containerSim.setLayout(new BoxLayout(containerSim, BoxLayout.Y_AXIS));
				TitledBorder containerSimBorder = BorderFactory.createTitledBorder("Simulator Configuration");
				containerSimBorder.setTitleJustification(TitledBorder.LEFT);
				containerSim.setBorder(containerSimBorder);
				JPanel stepSim = new JPanel();
				containerSim.add(new JSeparator());
				containerSim.add(stepLabel);
				containerSim.add(currentSim);
				JButton step = new JButton("Toggle Step");
				step.addActionListener(ae -> {
					ExoRuntime.INSTANCE.getContext().running = !ExoRuntime.INSTANCE.getContext().running;
					if(simStepper != null) simStepper.stop();
					simStepper = new Timer(60, new StepTimer());
					simStepper.start();
				});
				JLabel label = new JLabel("Target Sim. Steps per ms");
				JLabel label2 = new JLabel("(Limited to 2^31-1)");
				SpinnerModel model = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
				JSpinner spinner = new JSpinner(model);
				spinner.addChangeListener(ae -> simStepsPerItr = (int) spinner.getValue());
				stepSim.add(step);
				containerSim.add(new JSeparator());
				containerSim.add(stepSim);
				containerSim.add(new JSeparator());
				containerSim.add(label);
				containerSim.setPreferredSize(new Dimension((int) containerFocus.getPreferredSize().getWidth(), 150));
				containerSim.add(spinner);
				containerSim.add(label2);
				frame.add(containerSim);
			}
		}
		frame.setVisible(true);
	}

	private class StepTimer implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if (ExoRuntime.INSTANCE.getContext().running) {
				for(int i = 0; i < simStepsPerItr; i++) {
					ExoRuntime.INSTANCE.getContext().step();
					stepLabel.setText("Current Step: " + ExoRuntime.INSTANCE.getContext().getSteps());
					stepLabel.repaint();
				}
			} // If running step, if not skip frame
		}
	}
	
	@Override
	public void kill(int id) {
		frame.dispose();
	}

	@Override
	public void onContextChanged(SimContext context) {}

	@Override
	public void onStep(SimContext context, int step) {}
	
}
