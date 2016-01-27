package ecumene.exo.analyze;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.ExceptionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.Timer;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.sim.SimContext;
import ecumene.exo.view.ViewerRunnable;

public class ExoRuntimeAnalyzer extends ViewerRunnable {

	private JFrame frame;
	
	public ExoRuntimeAnalyzer(int id, ExceptionListener listener, String[] args) {
		super(id, listener, args);
	}

	private JPanel currentSeed = new JPanel();
	private JPanel containerSeed = new JPanel();

	private JPanel currentSolar = new JPanel();
	private JPanel containerSolar = new JPanel();

	private JLabel stepLabel = new JLabel("Current Step: " + 0);
	private JPanel currentSim = new JPanel();
	private JPanel containerSim = new JPanel();
	
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
			
			containerSeed.setLayout(new BoxLayout(containerSeed , BoxLayout.Y_AXIS));
		    TitledBorder containerSeedBorder = BorderFactory.createTitledBorder("Galaxy Configuration");
		    containerSeedBorder.setTitleJustification(TitledBorder.LEFT);
		    containerSeed.setBorder(containerSeedBorder);
			containerSeed.add(currentSeed);
			JPanel setCurrentSeed = new JPanel();
			final JTextField seedField = new JTextField(20);
			setCurrentSeed.add(seedField);
			JButton setSeed = new JButton("Set Seed");
			setSeed.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					char[] input = seedField.getText().toCharArray();
					String sum = "";
					for(int i = 0; i < input.length; i++){
						int charNum = Character.getNumericValue(input[i]);
						if(charNum == -1) charNum = 0;
						sum += charNum;
					}
					long longSum = 0;
					if(!(sum.length() > 19)) {
						longSum = Long.valueOf(sum);
					} 
					
					containerSeed.repaint();
				}
			});
			setCurrentSeed.add(setSeed);
			containerSeed.add(setCurrentSeed);
			frame.add(containerSeed);
			
			containerSolar.setLayout(new BoxLayout(containerSolar , BoxLayout.Y_AXIS));
		    TitledBorder containerSolarBorder = BorderFactory.createTitledBorder("Solar Configuration");
		    containerSolarBorder.setTitleJustification(TitledBorder.LEFT);
		    containerSolar.setBorder(containerSolarBorder);
			containerSolar.add(currentSolar);
			JPanel setCurrentSolar = new JPanel();
			JPanel setCurrentFocus = new JPanel();
			final JTextField solarIndexField = new JTextField(20);
			final JTextField solarFocusField = new JTextField(20);
			setCurrentSolar.add(solarIndexField);
			JButton setSolarIndex = new JButton("Set Index");
			JButton setSolarFocus = new JButton("Set Focus");
			setSolarIndex.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					char[] input = solarIndexField.getText().toCharArray();
					String sum = "";
					for(int i = 0; i < input.length; i++){
						int charNum = Character.getNumericValue(input[i]);
						if(charNum == -1) charNum = 0;
						sum += charNum;
					}
					int intSum = 0;
					if(!(sum.length() > 19)) {
						intSum = Integer.valueOf(sum);
					}
					containerSolar.repaint();
				}
			});
			setSolarFocus.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(solarFocusField.getText().equals("")) ExoRuntime.INSTANCE.getContext().getSolarSystem().setFollow(-1); // -1 points to nothing
					else                                     ExoRuntime.INSTANCE.getContext().getSolarSystem().setFollow(Integer.parseInt(solarFocusField.getText()));
				}
			});
			setCurrentSolar.add(setSolarIndex);
			setCurrentFocus.add(solarFocusField);
			setCurrentFocus.add(setSolarFocus);
			containerSolar.add(setCurrentSolar, 0);
			containerSolar.add(setCurrentFocus, 1);
			frame.add(containerSolar);

			containerSim.setLayout(new BoxLayout(containerSim, BoxLayout.Y_AXIS));
			TitledBorder containerSimBorder = BorderFactory.createTitledBorder("Simulator Configuration");
		    containerSimBorder .setTitleJustification(TitledBorder.LEFT);
		    containerSim.setBorder(containerSimBorder);
			JPanel stepSim = new JPanel();
		    containerSim.add(stepLabel);
			containerSim.add(currentSim);
			JButton step = new JButton("Step");
			ExoRuntime.INSTANCE.getContext().setStepInterp(1f);
			final Timer timer = new Timer(100, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(ExoRuntime.INSTANCE.getContext().running){
						ExoRuntime.INSTANCE.getContext().step();
						stepLabel.setText("Current Step: " + ExoRuntime.INSTANCE.getContext().getSteps());
						stepLabel.repaint();
					} // If running step, if not skip frame
				}
			});
			step.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ExoRuntime.INSTANCE.getContext().running = !ExoRuntime.INSTANCE.getContext().running; // Toggle running
				}
			});
			stepSim.add(step);
			containerSim.add(stepSim);
			containerSim.setPreferredSize(new Dimension((int)containerSeed.getPreferredSize().getWidth(), 100));
			frame.add(containerSim);
			timer.start();
		}
		frame.setVisible(true);
		frame.repaint();
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
