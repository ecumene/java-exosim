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

import ecumene.exo.ExoRunnable;
import ecumene.exo.ExoRuntime;
import ecumene.exo.sim.ESContext;

public class ExoRuntimeAnalyzer extends ExoRunnable {

	private JFrame    frame;
	private ESContext context;
	
	public ExoRuntimeAnalyzer(int id, ExceptionListener listener, String[] args) {
		super(id, listener, args);
		this.context = new ESContext(args.length < 1 ? Long.parseLong(args[0]) : 0, 0);
	}

	private JLabel seedLabel = new JLabel("Seed: " + 0);
	private JPanel currentSeed = new JPanel();
	private JPanel containerSeed = new JPanel();

	private JLabel solarLabel = new JLabel("Solar System: " + 0);
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
		    TitledBorder containerSeedBorder = BorderFactory.createTitledBorder("Seed Config");
		    containerSeedBorder.setTitleJustification(TitledBorder.LEFT);
		    containerSeed.setBorder(containerSeedBorder);
			containerSeed.add(seedLabel);
			containerSeed.add(currentSeed);
			JPanel setCurrentSeed = new JPanel();
			final JTextField seedField = new JTextField(20);
			setCurrentSeed.add(seedField);
			JButton setSeed = new JButton("Set");
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
					if(sum.length() > 19) {
						seedLabel.setText("Seed: " + 0 + " (input too long!)");
					} else {
						longSum = Long.valueOf(sum);
						seedLabel.setText("Seed: " + sum);
					}
					
					ExoRuntime.INSTANCE.setContext(new ESContext(longSum, 0));
					containerSeed.repaint();
				}
			});
			setCurrentSeed.add(setSeed);
			containerSeed.add(setCurrentSeed);
			frame.add(containerSeed);
			
			containerSolar.setLayout(new BoxLayout(containerSolar , BoxLayout.Y_AXIS));
		    TitledBorder containerSolarBorder = BorderFactory.createTitledBorder("Solar Config");
		    containerSolarBorder.setTitleJustification(TitledBorder.LEFT);
		    containerSolar.setBorder(containerSolarBorder);
		    containerSolar.add(solarLabel);
			containerSolar.add(currentSolar);
			JPanel setCurrentSolar = new JPanel();
			final JTextField solarIndexField = new JTextField(20);
			setCurrentSolar.add(solarIndexField);
			JButton setSolarIndex = new JButton("Set");
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
					if(sum.length() > 19) {
						solarLabel.setText("Seed: " + 0 + " (input is too long!)");
					} else {
						intSum = Integer.valueOf(sum);
						solarLabel.setText("Seed: " + sum);
					}
					
					ESContext oldContext = ExoRuntime.INSTANCE.getContext();
					ExoRuntime.INSTANCE.setContext(new ESContext(oldContext.getSeed(), intSum));
					containerSolar.repaint();
				}
			});
			setCurrentSolar.add(setSolarIndex);
			containerSolar.add(setCurrentSolar);
			frame.add(containerSolar);

			containerSim.setLayout(new BoxLayout(containerSim, BoxLayout.Y_AXIS));
		    TitledBorder containerSimBorder = BorderFactory.createTitledBorder("Simulator Settings");
		    containerSimBorder .setTitleJustification(TitledBorder.LEFT);
		    containerSim.setBorder(containerSimBorder);
			JPanel stepSim = new JPanel();
		    containerSim.add(stepLabel);
			containerSim.add(currentSim);
			JButton step = new JButton("Step");
			step.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					context.step();
					stepLabel.setText("Current Step: " + ExoRuntime.INSTANCE.getContext().getSeed());
				}
			});
			stepSim.add(step);
			containerSim.add(stepSim);
			frame.add(containerSim);
		}
		frame.setVisible(true);
		containerSim.setPreferredSize(new Dimension(containerSeed.getWidth(), containerSeed.getHeight()));
		frame.repaint();
	}
	
	@Override
	public void kill(int id) {
		frame.dispose();
	}

	@Override
	public void onContextChanged(ESContext context) {}

	@Override
	public void onStep(ESContext context, int step) {}
	
}
