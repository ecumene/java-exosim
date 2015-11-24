package ecumene.exo.sim.map.real;

import java.beans.ExceptionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import ecumene.exo.ExoRunnable;

public abstract class ExoRMapRenderer extends ExoRunnable {

	private   RObject       selected = null;
	protected JRMapRenderer renderer = null;
	protected RMap          pMap     = null;
	protected JFrame frame;
	
	public ExoRMapRenderer(int id, ExceptionListener exceptionListener, RMap map, String[] args) {
		super(id, exceptionListener, args);
		pMap = map;
	}
	
	public RObject getSelected(){
		return selected;
	}

	@Override
	public void init() throws Throwable {
		frame = new JFrame("RMap Point Rendering System");
		frame.setVisible(false);
		if(renderer == null) renderer = constructRenderer();		
		{
			frame.setSize(600, 600);
			frame.setLocationRelativeTo(null);
			frame.setIconImage(ImageIO.read(new File("./resources/logo.png")));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.add(renderer);
		}
		frame.setVisible(true);
	}

	@Override
	public void kill(int id) {
		frame.dispose();
	}
	
	protected JRMapRenderer constructRenderer(){
		return new JRMapRenderer(pMap);
	}
}
