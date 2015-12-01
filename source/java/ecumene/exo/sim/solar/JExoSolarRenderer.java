package ecumene.exo.sim.solar;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.joml.Vector2f;

import ecumene.exo.sim.ESContext;
import ecumene.exo.sim.IESContextListener;
import ecumene.exo.sim.map.real.JRMapRenderer;
import ecumene.exo.sim.map.real.RMap;
import ecumene.exo.sim.map.real.RPoint;

public class JExoSolarRenderer extends JRMapRenderer implements IESContextListener {

	private ExoSolarMap map;
	
	public JExoSolarRenderer(ExoSolarMap map) {
		super(new RMap());
		this.rendererList.add(new ExoSolarObjectRenderer(this));
		this.rendererList.remove(getDefaultRPointRenderer());
		
		this.map = map;
		
		addKeyListener(new KeyListener() {
			@Override public void keyTyped(KeyEvent e)    {}
			@Override public void keyReleased(KeyEvent e) {}
			@Override public void keyPressed(KeyEvent e)  {
				if(e.getKeyCode() == KeyEvent.VK_V){
					useNames = !useNames; // Use names in solar renderer means to toggle disp. vector rendering
				}
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics = (Graphics2D) g;
		graphics.drawString("Solar Abstraction", 0, 10);
		graphics.drawString("Press V to toggle vectors", 0, 20);
		graphics.drawString("Artifact Count (+ sun): " + pMap.getMap().length, 0, 30);
	}
	
	@Override
	protected void drawPoint(Graphics2D graphics, int id, RPoint point, Vector2f realPos, Vector2f navPos, Vector2f screenPos) {
		super.drawPoint(graphics, id, point, realPos, navPos, screenPos);
	}
	
	public void onStep(float interp){
		pMap = map.step(interp);
	}

	@Override
	public void onContextChanged(ESContext context) {
		repaint();
	}

	public void setSolarMap(ExoSolarMap solarMap) {
		this.map = solarMap;
	}
	
}
