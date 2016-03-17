package ecumene.exo.view.rmap;

import java.beans.ExceptionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.runtime.viewer.ViewerRunnable;
import org.joml.Vector3f;

public abstract class RMVRenderer extends ViewerRunnable {

	protected JRMViewer renderer = null;
	protected RMap      pMap     = null;
	protected JFrame    frame;
	protected Vector3f navigation;
	
	public RMVRenderer(int id, ExceptionListener exceptionListener, RMap map, Vector3f navigation) {
		super(id, exceptionListener);
		this.navigation = navigation;
		pMap = map;
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
	
	protected JRMViewer constructRenderer(){
		return new JRMViewer(navigation, pMap);
	}
}
