package ecumene.exo.sim.solar;

import java.awt.Graphics;
import java.awt.Graphics2D;

import ecumene.exo.sim.ESContext;
import ecumene.exo.sim.IESContextListener;
import ecumene.exo.sim.map.real.JRMapRenderer;
import ecumene.exo.sim.map.real.RMap;

public class JExoSolarMapRenderer extends JRMapRenderer implements IESContextListener {

	public JExoSolarMapRenderer(RMap pMap) {
		super(pMap);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics = (Graphics2D) g;
		graphics.drawString("Solar Abstraction", 0, 10);
		graphics.drawString("Press P to toggle positions", 0, 20);
		graphics.drawString("Press V to toggle vectors", 0, 20);
		graphics.drawString("Artifact Count (+ sun): " + pMap.getMap().length, 0, 30);

	}

	@Override
	public void onContextChanged(ESContext context) {
		repaint();
	}
	
}
