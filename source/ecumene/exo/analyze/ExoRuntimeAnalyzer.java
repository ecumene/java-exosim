package ecumene.exo.analyze;

import java.beans.ExceptionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import ecumene.exo.ExoRunnable;

public class ExoRuntimeAnalyzer extends ExoRunnable{

	private JFrame frame;
	
	public ExoRuntimeAnalyzer(int id, ExceptionListener listener, String[] args) {
		super(id, listener, args);
	}
	
	@Override
	public void init() throws Throwable {
		frame = new JFrame("Runtime Analyzer");
		frame.setVisible(false);
		{
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(480, 800);
			frame.setIconImage(ImageIO.read(new File("./resources/logo.png")));
			frame.setLocationRelativeTo(null);
			
		}
		frame.setVisible(true);
	}
	
	@Override
	public void kill(int id) {
		frame.dispose();
	}
	
}
