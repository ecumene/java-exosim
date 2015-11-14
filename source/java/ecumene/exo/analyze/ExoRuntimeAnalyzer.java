package ecumene.exo.analyze;

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
		this.context = new ESContext(args.length < 1 ? Long.parseLong(args[0]) : 0);
	}

	private JLabel seedLabel = new JLabel("Seed: " + 0);
	private JPanel currentSeed = new JPanel();
	private JPanel containerSeed = new JPanel();
	
	@Override
	public void init() throws Throwable {
		frame = new JFrame("Runtime Analyzer");
		frame.setVisible(false);
		{
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(480, 800);
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
					
					ExoRuntime.INSTANCE.setContext(new ESContext(longSum));
					containerSeed.repaint();
				}
			});
			setCurrentSeed.add(setSeed);
			containerSeed.add(setCurrentSeed);
			frame.add(containerSeed);
			
		}
		frame.setVisible(true);
	}
	
	@Override
	public void kill(int id) {
		frame.dispose();
	}

	@Override
	public void onContextChanged(ESContext context) {}
	
}
