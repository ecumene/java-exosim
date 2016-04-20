package ecumene.exo.view.rmap.galaxy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.ISimContextListener;
import ecumene.exo.sim.abstractions.galaxy.IExoGalaxyObject;
import ecumene.exo.sim.common.map.real.RMap;
import ecumene.exo.sim.common.map.real.RPoint;
import ecumene.exo.view.rmap.JRMViewer;
import org.joml.Vector3f;

public class JRMVGalaxyRenderer extends JRMViewer implements ISimContextListener {

	public JRMVGalaxyRenderer(Vector3f navigation, final RMap pMap) {
		super(navigation, pMap);
		this.navigation = navigation;
		getRendererList().add(new RMVGalaxySingularityRenderer(this));
		getRendererList().add(new RMVGalaxyOrbiterRenderer    (this));
		getRendererList().remove(getDefaultRPointRenderer());
	}
		
	public RMap getRMap(){
		return pMap;
	}
	
	public void setRMap(RMap pMap){
		this.pMap = pMap;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		g.setColor(primary);
		graphics.drawString("Galactic Abstraction", 0, 10);
		graphics.drawString("Press N to toggle names", 0, 20);
		graphics.drawString("Count: " + pMap.getMap().length, 0, 30);
	}

	@Override
	public void onContextChanged(SimContext context) {
		repaint();
	}
}
