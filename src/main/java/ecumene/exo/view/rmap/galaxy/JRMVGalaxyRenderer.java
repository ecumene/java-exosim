package ecumene.exo.view.rmap.galaxy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.joml.Vector2f;

import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.ISimContextListener;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RPoint;
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
		System.out.println(navigation);
		Graphics2D graphics = (Graphics2D) g;
		g.setColor(new Color(0, 255, 0));
		graphics.drawString("Galactic Abstraction", 0, 10);
		graphics.drawString("Press N to toggle names", 0, 20);
		graphics.drawString("Count: " + pMap.getMap().length, 0, 30);
	}

	@Override
	public void onContextChanged(SimContext context) {
		repaint();
	}
}
