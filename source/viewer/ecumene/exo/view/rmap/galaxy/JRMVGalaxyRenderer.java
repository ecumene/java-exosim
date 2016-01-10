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

public class JRMVGalaxyRenderer extends JRMViewer implements ISimContextListener {
	
	private float rotate;
	private int selected;

	private Vector2f mousePosition = new Vector2f();
	
	public JRMVGalaxyRenderer(final RMap pMap) {
		super(pMap);
		this.rendererList.add(new RMVGalaxySingularityRenderer(this));
		this.rendererList.add(new RMVGalaxyOrbiterRenderer    (this));
		this.rendererList.remove(getDefaultRPointRenderer());
		
		addKeyListener(new KeyListener() {
			@Override public void keyTyped(KeyEvent e) { }
			@Override public void keyReleased(KeyEvent e) { }
			@Override 
			public void keyPressed(KeyEvent e) {
				boolean rotated = false;
				if(e.getKeyCode() == KeyEvent.VK_R){
					rotate = 10f;
					rotated = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_E){
					rotate = -10f;
					rotated = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					rotate = 0;
					rotated = true;
				}
				
				if(rotated){
					for(RPoint point : pMap.getMap()){
						float rotateR = (float)Math.toRadians(rotate);
						float c = (float) Math.cos(rotateR), s = (float) Math.sin(rotateR);
						float x = point.position.x, y = point.position.y;
						
						point.position.x = x * c - y * s;
						point.position.y = x * s + y * c;
					}
				}
				
				rotated = false;
			}
		});
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
