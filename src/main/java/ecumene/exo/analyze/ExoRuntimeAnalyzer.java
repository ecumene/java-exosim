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

	private JLabel stepLabel = new JLabel("Current Step: " + 0);
	private JPanel currentSim = new JPanel();
	private JPanel containerSim = new JPanel();

	private Timer simStepper;
	private int   simStepsPerItr = 1;
	
	@Override
	public void init() throws Throwable {
		frame = new JFrame("Runtime Analyzer");
		frame.setVisible(false);
		{
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(430, 720);
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

			{ // Simulation Config
				containerSim.setLayout(new BoxLayout(containerSim, BoxLayout.Y_AXIS));
				TitledBorder containerSimBorder = BorderFactory.createTitledBorder("Simulator Configuration");
				JSlider slider = new JSlider();
				slider.setValue(100);
				slider.addChangeListener(cl -> {
					if(slider.getValueIsAdjusting()){
						ExoRuntime.INSTANCE.getContext().setStepInterp((int) (slider.getValue()));
						System.out.println((int) (slider.getValue()));
					}
				});
				containerSimBorder.setTitleJustification(TitledBorder.LEFT);
				containerSim.setBorder(containerSimBorder);
				JPanel stepSim = new JPanel();
				containerSim.add(stepLabel);
				containerSim.add(currentSim);
				JButton step = new JButton("Toggle Step");
				step.addActionListener(ae -> {
					ExoRuntime.INSTANCE.getContext().running = !ExoRuntime.INSTANCE.getContext().running;
					if(simStepper != null) simStepper.stop();
					simStepper = new Timer(ExoRuntime.INSTANCE.getContext().getStepInterp(), new StepTimer());
					simStepper.start();
				});
				SpinnerModel model = new SpinnerNumberModel(1, 1, 1000, 1);
				JSpinner spinner = new JSpinner(model);
				spinner.addChangeListener(ae -> {
					simStepsPerItr = (int)spinner.getValue();
				});
				stepSim.add(step);
				containerSim.add(stepSim);
				containerSim.setPreferredSize(new Dimension((int) containerFocus.getPreferredSize().getWidth(), 140));
				containerSim.add(slider);
				containerSim.add(spinner);
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
	public void onStep(SimContext context, int step, float interp) {}
	
}
